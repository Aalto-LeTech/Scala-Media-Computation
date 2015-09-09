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
private[smcl] class ClassInfoProvider {

  /** */
  type TestPathExtensionString = PathString

  /** */
  type RealPathExtensionString = PathString

  /** */
  //private val SmclJarName: String = "resources.jar"
  private val SmclJarName: String = "smcl-scala-library_2.11-0.1-SNAPSHOT.jar"

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
  def resolveScalaClasspath(): Seq[PathString] = {
    val defaultNscSettings = new Settings()
    val classPathResolver = new PathResolver(defaultNscSettings)

    val result = classPathResolver.resultAsURLs
    if (result.isEmpty)
      return Seq[PathString]()

    result.map {_.getPath.trim}
  }

  /**
   *
   *
   * @return
   */
  def resolveJavaClasspath(): Seq[PathString] =
    resolveSystemProperty(SystemPropertyNameClassPath).fold(Seq[PathString]()) {path =>
      path.split(File.pathSeparator).map(_.trim).toList.toSeq
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

    if (classpath.isEmpty)
      println(MessageUnableToResolveClasspath)
    else
      classpath.foreach(println(_))
  }

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarPath(): Option[File] =
    resolveClasspath().find(_.endsWith(SmclJarName)).fold[Option[File]](None) {path =>
      val jarFile: File = new File(path)

      if (!jarFile.isFile || !jarFile.canRead)
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
      val jar = new JarFile(file)

      JavaConverters.enumerationAsScalaIteratorConverter(jar.entries())
          .asScala.toSeq.map {_.getName.trim}
    }

  /**
   *
   *
   * @return
   */
  def resolveUserDir(): Option[File] =
    resolveSystemProperty(SystemPropertyNameUserDir).fold[Option[File]](None) {path =>
      val userDirFile = new File(path)

      if (!userDirFile.isDirectory || !userDirFile.canRead)
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
      if (!targetPathFile.isDirectory || !targetPathFile.canRead)
        return Seq[File]()

      val subDirFileList = targetPathFile.listFiles(new FileFilter() {

        override def accept(file: File): Boolean =
          file.isDirectory && file.canRead && file.getName.startsWith("scala")

      })

      if (subDirFileList.isEmpty)
        return Seq[File]()

      val testPackagePathParts = Seq("aalto", "smcl", "init")
      val testPackagePath = testPackagePathParts.mkString(File.separator, File.separator, "")
      val foundPathFiles = new ArrayBuffer[File]()
      subDirFileList.foreach {subDir =>
        val rootPathCandidateFile = new File(subDir.getCanonicalPath + File.separator + "classes")

        if (rootPathCandidateFile.isDirectory && rootPathCandidateFile.canRead) {
          val testPackagePathFile = new File(rootPathCandidateFile.getCanonicalPath + testPackagePath)

          if (testPackagePathFile.isDirectory && testPackagePathFile.canRead)
            foundPathFiles += rootPathCandidateFile
        }
      }

      if (foundPathFiles.isEmpty)
        return Seq[File]()

      foundPathFiles.toList.toSeq
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

          if (attributes.isRegularFile && contentFilePath.toFile.getCanonicalPath.endsWith(".class"))
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
  def resolveApplicationClassFiles(): Seq[File] = {
    var foundFilePaths = resolveApplicationJarContents()

    if (foundFilePaths.isEmpty)
      foundFilePaths = resolveSbtConsoleTimeApplicationClassRootFoldersContents()

    foundFilePaths.map(new File(_))
  }

  /**
   *
   */
  def printApplicationClassFiles(): Unit = {
    val files = resolveApplicationClassFiles()

    if (files.isEmpty)
      println(MessageUnableToResolveClassfiles)
    else
      files.foreach(println(_))
  }

}
