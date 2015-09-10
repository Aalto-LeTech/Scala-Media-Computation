package aalto.smcl.init


import java.io.{File, FileFilter}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import java.util.jar.JarFile

import scala.collection.JavaConverters
import scala.collection.mutable.ArrayBuffer
import scala.tools.nsc.Settings
import scala.tools.util.PathResolver




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
//private[smcl]
class ClassInfoProvider {

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
  private val ClassFileExtension: String = ".class"

  /** */
  private val SystemPropertyNameClassPath: String = "java.class.path"

  /** */
  private val SystemPropertyNameUserDir: String = "user.dir"

  /** */
  private val MessageUnableToResolveClasspath: String =
    "<unable to resolve current classpath>"

  /** */
  private val MessageUnableToResolveClassfiles: String =
    "<unable to resolve current classfiles>"

  /** */
  private[this] val _classloader = getClass.getClassLoader

  /**
   *
   *
   * @return
   */
  private def resolveSystemProperty(name: String): Option[String] = {
    require(name != null, "Name of the property must not be null or able to be trimmed to an empty string (was null)")

    val trimmedName = name.trim
    require(trimmedName.length > 0,
      "Name of the property must not be able to be trimmed to an empty string (was effectively a zero-length string)")

    val property = scala.sys.props(trimmedName)
    if (property == null)
      return None

    val trimmedProperty = property.trim
    if (trimmedProperty.length < 1)
      return None

    Some(trimmedProperty)
  }

  /**
   *
   *
   * @return
   */
  def resolveScalaClasspath(): Seq[PathString] =
    resolveScalaClasspath0(new Settings())

  /**
   *
   *
   * @return
   */
  protected def resolveScalaClasspath0(nscSettings: Settings): Seq[PathString] =
    new PathResolver(nscSettings).resultAsURLs.map(_.getPath.trim).sorted


  /**
   *
   *
   * @return
   */
  def resolveJavaClasspath(): Seq[PathString] =
    resolveSystemProperty(SystemPropertyNameClassPath).fold(Seq[PathString]()) {path =>
      path.split(File.pathSeparator).map(_.trim).toList.sorted.toSeq
    }

  /**
   *
   *
   * @return
   */
  def resolveClasspath(): Seq[PathString] = {
    var classPath = resolveScalaClasspath()

    if (classPath.isEmpty) {
      classPath = resolveJavaClasspath()
    }

    classPath
  }

  /**
   *
   */
  def printClasspath(): Unit = {
    val classpath = resolveClasspath()

    if (classpath.isEmpty) {
      println(MessageUnableToResolveClasspath)
      return
    }

    classpath.foreach(println)
  }

  /**
   *
   *
   * @return
   */
  private def hasClassExtension(path: String): Boolean =
    path.endsWith(ClassFileExtension)

  /**
   *
   *
   * @return
   */
  private def hasClassExtension(path: Path): Boolean =
    hasClassExtension(path.toFile.getCanonicalPath)

  /**
   *
   *
   * @param f
   * @return
   */
  private def isNotReadableFile(f: File): Boolean = !f.isFile || !f.canRead

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarPath(): Option[File] =
    resolveClasspath().find(_.endsWith(SmclJarName)).fold[Option[File]](None) {path =>
      val jarFile: File = new File(path)

      if (isNotReadableFile(jarFile))
        return None

      Some(jarFile)
    }

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarContents(): Seq[PathString] =
    resolveApplicationJarPath().fold[Seq[PathString]](Seq[PathString]()) {file =>
      JavaConverters.enumerationAsScalaIteratorConverter(new JarFile(file).entries())
          .asScala.map(_.getName.trim).toIndexedSeq.toList.sorted
    }

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarClasses(): Seq[PathString] =
    resolveApplicationJarContents().filter(hasClassExtension)

  /**
   *
   *
   * @return
   */
  def printApplicationJarClasses(): Unit =
    resolveApplicationJarClasses().foreach(println)

  /**
   *
   *
   * @param f
   * @return
   */
  private def representsReadableDirectory(f: File) =
    f.isDirectory && f.canRead

  /**
   *
   *
   * @return
   */
  def resolveUserDir(): Option[File] =
    resolveSystemProperty(SystemPropertyNameUserDir).fold[Option[File]](None) {path =>
      val userDirFile = new File(path)

      if (!representsReadableDirectory(userDirFile))
        return None

      Some(userDirFile)
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

      if (subDirFileList.isEmpty)
        return Seq[File]()

      val foundPathFiles = new ArrayBuffer[File]()
      subDirFileList.foreach {subDir =>
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
    resolveSbtConsoleTimeApplicationClassRootFoldersContents().filter(hasClassExtension)

  /**
   *
   *
   * @return
   */
  def resolveApplicationClassFiles(): Seq[File] = {
    var foundFilePaths = resolveApplicationJarClasses()

    if (foundFilePaths.isEmpty)
      foundFilePaths = resolveSbtConsoleTimeApplicationClassRootFoldersContents()

    foundFilePaths.map(new File(_)).sorted
  }

  /**
   *
   */
  def printApplicationClassFiles(): Unit = {
    val files = resolveApplicationClassFiles()

    if (files.isEmpty) {
      println(MessageUnableToResolveClassfiles)
      return
    }

    files.foreach(println)
  }

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

    var classRootPosition = path.lastIndexOf(SmclClassRootIdentifyingPackagePath + File.separator)
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

    val className = strippedPath.replaceAll(File.separator, ".")

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
  def printApplicationClassNames(): Unit = {
    val names = resolveApplicationClassNames()

    if (names.isEmpty) {
      println(MessageUnableToResolveClassfiles)
      return
    }

    names.foreach(println)
  }

  /**
   *
   */
  def loadApplicationClasses(): Seq[Class[_]] =
    resolveApplicationClassFiles().map {
      file =>


        new Object().getClass
    }

}
