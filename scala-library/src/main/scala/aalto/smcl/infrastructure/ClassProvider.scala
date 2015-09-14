package aalto.smcl.infrastructure


import java.io.{File, FileFilter}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import java.util.jar.JarFile

import scala.collection.JavaConverters
import scala.collection.mutable.ArrayBuffer
import scala.tools.util.PathResolver
import scala.util.{Failure, Success, Try}

import aalto.smcl.infrastructure.FileUtils._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class ClassProvider {

  /** */
  type TestPathExtensionString = PathString

  /** */
  type RealPathExtensionString = PathString

  /** */
  //private val SmclJarName: String = "rt.jar"
  private val SmclJarName: String = "smcl-scala-library_2.11-0.1-SNAPSHOT.jar"

  /** */
  private val SmclClassRootIdentifyingPackagePath =
    Seq("aalto", "smcl").mkString(File.separator)

  /** */
  private val SystemPropertyNameClassPath: String = "java.class.path"

  /** */
  private val SystemPropertyNameUserDir: String = "user.dir"

  /** */
  private[this] val _classloader = getClass.getClassLoader

  /**
   *
   *
   * @return
   */
  def resolveSystemProperty(name: String): Option[String] = {
    require(name != null, "Name of the property must not be null or able to be trimmed to an empty string (was null)")

    val trimmedName = name.trim
    require(trimmedName.length > 0,
      "Name of the property must not be able to be trimmed to an empty string (was effectively a zero-length string)")

    scala.sys.props.get(trimmedName) map (_.trim) filter (_.length > 0)
  }

  /**
   *
   *
   * @return
   */
  def resolveScalaClasspath(): Seq[PathString] =
    resolveScalaClasspath0(new scala.tools.nsc.Settings())

  /**
   *
   *
   * @return
   */
  protected def resolveScalaClasspath0(nscSettings: scala.tools.nsc.Settings): Seq[PathString] =
    new PathResolver(nscSettings).resultAsURLs.map(_.getPath.trim).sorted

  /**
   *
   *
   * @param parsee
   * @return
   */
  def parseClassPathString(parsee: String): Seq[PathString] =
    parsee.split(File.pathSeparator).map(_.trim).toList.sorted.toSeq

  /**
   *
   *
   * @return
   */
  def resolveJavaClasspath(): Seq[PathString] =
    resolveSystemProperty(SystemPropertyNameClassPath)
        .fold(Seq[PathString]())(parseClassPathString)

  /**
   *
   *
   * @return
   */
  def resolveClasspath(): Seq[PathString] =
    resolveScalaClasspath() orIfEmpty resolveJavaClasspath()

  /**
   *
   */
  def printClasspath(): Unit =
    resolveClasspath() foreach println

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarPathFromClassPath(): Option[File] =
    resolveClasspath().find(_.endsWith(SmclJarName)).fold[Option[File]](None) {path =>
      Try(new File(path)).filter(representsReadableFile).toOption
    }

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarPathViaProtectionDomain(): Option[File] =
    Try(this.getClass.getProtectionDomain).toOption flatMap {domain =>
      Try(new File(domain.getCodeSource.getLocation.toURI)).filter(representsReadableFile).toOption
    }

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarPathAsResource(): Option[File] = {
    val clazz = getClass
    val classFileName = clazz.getSimpleName + ClassFileExtension

    val url = clazz.getResource(classFileName)
    if (url == null)
      return None

    Try(new File(url.toURI)).filter(representsReadableFile).filter(hasJarExtension).toOption
  }

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarPathFromJVM(): Option[File] =
    resolveApplicationJarPathViaProtectionDomain() orElse
        resolveApplicationJarPathAsResource()

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarPath(): Option[File] =
    resolveApplicationJarPathFromJVM() orElse
        resolveApplicationJarPathFromClassPath()

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarContents(): Seq[PathString] =
    resolveApplicationJarPath().fold[Seq[PathString]](Seq[PathString]()) {file =>
      Try(new JarFile(file)) match {
        case Success(jarFile) =>
          JavaConverters.enumerationAsScalaIteratorConverter(jarFile.entries())
              .asScala.map(_.getName.trim).toIndexedSeq.toList.sorted

        case Failure(_) => Seq[PathString]()
      }
    }

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarClasses(): Seq[PathString] =
    resolveApplicationJarContents() filter hasClassExtension

  /**
   *
   *
   * @return
   */
  def printApplicationJarClasses(): Unit =
    resolveApplicationJarClasses() foreach println

  /**
   *
   *
   * @return
   */
  def resolveUserDir(): Option[File] =
    resolveSystemProperty(SystemPropertyNameUserDir).fold[Option[File]](None) {path =>
      Try(new File(path)).filter(representsReadableDirectory).toOption
    }

  /**
   *
   *
   * @return
   */
  def resolveSbtConsoleTimeApplicationClassRootFolders(): Seq[File] =
    resolveUserDir().fold[Seq[File]](Seq[File]()) {dir =>
      val targetPathFile = new File(dir.getAbsolutePath + File.separator + "target")
      if (!representsReadableDirectory(targetPathFile))
        return Seq[File]()

      val subDirFileList = targetPathFile.listFiles(new FileFilter() {

        override def accept(file: File): Boolean =
          representsReadableDirectory(file) && file.getName.startsWith("scala")

      })

      // TODO: Modify to use only classes compiled for the correct Scala version (there might be classes for several versions under the 'target' folder)

      if (subDirFileList.isEmpty)
        return Seq[File]()

      val foundPathFiles = new ArrayBuffer[File]()
      subDirFileList foreach {subDir =>
        val rootPathCandidateFile = new File(subDir.getCanonicalPath + File.separator + "classes")

        if (representsReadableDirectory(rootPathCandidateFile)) {
          val testPackagePathFile = new File(
            rootPathCandidateFile.getCanonicalPath + File.separator +
                SmclClassRootIdentifyingPackagePath + File.separator)

          if (representsReadableDirectory(testPackagePathFile))
            foundPathFiles += rootPathCandidateFile
        }
      }

      foundPathFiles.toList.sorted.toSeq
    }

  /**
   *
   *
   * @return
   */
  def resolveSbtConsoleTimeApplicationClassRootFoldersContents(): Seq[PathString] =
    resolveSbtConsoleTimeApplicationClassRootFolders().flatMap {rootFolderFile =>
      val acceptedFiles = new ArrayBuffer[String]()

      Files.walkFileTree(rootFolderFile.toPath, new SimpleFileVisitor[Path]() {

        override def visitFile(
            contentFilePath: Path,
            attributes: BasicFileAttributes): FileVisitResult = {

          if (attributes.isRegularFile && hasClassExtension(contentFilePath))
            acceptedFiles += contentFilePath.toString

          FileVisitResult.CONTINUE
        }
      })

      acceptedFiles
    }

  /**
   *
   *
   * @return
   */
  def resolveSbtConsoleTimeApplicationClassRootFoldersClasses(): Seq[PathString] =
    resolveSbtConsoleTimeApplicationClassRootFoldersContents() filter hasClassExtension

  /**
   *
   *
   * @return
   */
  def resolveApplicationClassFiles(): Seq[File] = {
    var foundFilePaths = resolveApplicationJarClasses() orIfEmpty
        resolveSbtConsoleTimeApplicationClassRootFoldersContents() orForEmpty {

      throw new RuntimeException("Unable to resolve application class files.")
    }

    foundFilePaths.flatMap(path => Try(new File(path)).toOption).sorted
  }

  /**
   *
   */
  def printApplicationClassFiles(): Unit =
    resolveApplicationClassFiles() foreach println

  /**
   *
   *
   * @param classFile
   * @return
   */
  private def resolveFullClassNameFrom(classFile: File): Option[String] = {
    val path = classFile.getCanonicalPath
    if (path.length < 1)
      return None

    val classRootPosition = path.lastIndexOf(SmclClassRootIdentifyingPackagePath + File.separator)
    if (classRootPosition < 0)
      return None

    val extensionPosition = path.lastIndexOf(ClassFileExtension)
    if (extensionPosition < 0)
      return None

    if (classRootPosition >= extensionPosition)
      return None

    val strippedPath = path.substring(classRootPosition, extensionPosition)
    if (strippedPath.length < 1)
      return None

    val className = strippedPath.replaceAllLiterally(File.separator, ".")

    Some(className)
  }

  /**
   *
   *
   * @return
   */
  def resolveApplicationClassNames(): Seq[String] =
    resolveApplicationClassFiles().flatMap(resolveFullClassNameFrom).sorted

  /**
   *
   */
  def printApplicationClassNames(): Unit =
    resolveApplicationClassNames() foreach println

  /**
   *
   *
   * @param className
   * @return
   */
  def load(className: String): Class[_] =
    _classloader loadClass className

  /**
   *
   *
   * @param classNames
   * @return
   */
  def load(classNames: Seq[String]): Seq[Class[_]] =
    classNames map _classloader.loadClass

  /**
   *
   */
  def loadApplicationClasses(): Seq[Class[_]] =
    resolveApplicationClassNames() map load sortBy (_.toString) orForEmpty {
      throw new RuntimeException("Unable to load application classes.")
    }

}
