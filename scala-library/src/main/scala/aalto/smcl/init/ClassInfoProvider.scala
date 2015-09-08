package aalto.smcl.init


import java.io.File
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, FileVisitResult, Path, SimpleFileVisitor}
import java.util.jar.{JarEntry, JarFile}

import scala.collection.mutable.ArrayBuffer
import scala.tools.nsc.Settings
import scala.tools.util.PathResolver




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class ClassInfoProvider {

  /** */
  type PathString = String

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
  private val PathsForIdentifyingApplicationRootFolder: Seq[(TestPathExtensionString, RealPathExtensionString)] = {
    val pathPartSequences = Seq(
      (Seq("aalto", "smcl", "init"), Seq("aalto", "smcl", "init")),
      (Seq("src", "main", "scala", "aalto", "smcl", "init"), Seq("src", "main", "scala")))

    pathPartSequences.map {case (testPathExtension, realPathExtension) =>
      (testPathExtension.mkString(File.separator), realPathExtension.mkString(File.separator))
    }
  }

  /** */
  private val MessageUnableToResolveClasspath: String =
    "<unable to resolve current classpath>"


  /**
   *
   *
   * @return
   */
  def resolveScalaClasspath(): Option[Seq[PathString]] = {
    val defaultNscSettings = new Settings()
    val classPathResolver = new PathResolver(defaultNscSettings)

    val urls = classPathResolver.resultAsURLs
    if (urls.isEmpty)
      return None

    Some(urls.map {_.getPath})
  }

  /**
   *
   *
   * @return
   */
  def resolveJavaClasspath(): Option[Seq[PathString]] = {
    val pathString: String = scala.sys.props(SystemPropertyNameClassPath)
    if (pathString == null || pathString.trim.length < 1)
      return None

    Some(pathString.split(File.pathSeparator).toList.toSeq)
  }

  /**
   *
   *
   * @return
   */
  def resolveClasspath(): Option[Seq[PathString]] = {
    val paths = resolveScalaClasspath()
    if (paths.isDefined)
      return paths

    resolveJavaClasspath()
  }

  /**
   *
   */
  def printClasspath(): Unit = {
    val paths = resolveClasspath()
    if (paths.isEmpty) {
      println(MessageUnableToResolveClasspath)
      return
    }

    paths.get.foreach {println(_)}
  }

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarPath(): Option[File] = {
    val classPaths = resolveClasspath()
    if (classPaths.isEmpty)
      return None

    classPaths.get foreach {path =>
      if (path.endsWith(SmclJarName)) {
        val jarFile: File = new File(path)

        if (jarFile.isFile && jarFile.canRead)
          return Some(jarFile)
      }
    }

    None
  }

  /**
   *
   *
   * @return
   */
  def resolveApplicationJarContents(): Option[Seq[PathString]] = {
    val file = resolveApplicationJarPath()
    if (file.isEmpty)
      return None

    val jar = new JarFile(file.get)
    val jarEntries = jar.entries()

    val contents = ArrayBuffer[String]()
    while (jarEntries.hasMoreElements) {
      val entry: JarEntry = jarEntries.nextElement()

      contents += entry.getName
    }

    Some(contents)
  }

  /**
   *
   *
   * @return
   */
  def resolveApplicationClassRootFolder(): Option[File] = {
    val classPaths = resolveClasspath()
    if (classPaths.isEmpty)
      return None

    classPaths.get foreach {path: String =>
      val fileOfPath = new File(path)

      if (fileOfPath.isDirectory) {
        PathsForIdentifyingApplicationRootFolder foreach {
          case (testPathExtension, realPathExtension) =>

            val fileOfSubPath = new File(path + File.separator + testPathExtension)
            if (fileOfSubPath.isDirectory) {
              val fileOfRealSubPath = new File(path + File.separator + realPathExtension)
              if (fileOfRealSubPath.isDirectory)
                return Some(fileOfRealSubPath)
            }
        }
      }
    }

    None
  }

  /**
   *
   *
   * @return
   */
  def resolveApplicationClassRootFolderContents(): Option[Seq[PathString]] = {
    val rootPathFile = resolveApplicationClassRootFolder()
    if (rootPathFile.isEmpty)
      return None

    val rootPath = rootPathFile.get.toPath
    val content = new ArrayBuffer[String]()

    val visitor = new SimpleFileVisitor[Path]() {

      override def visitFile(
          file: Path,
          attributes: BasicFileAttributes): FileVisitResult = {

        println(file.toString)

        if (attributes.isRegularFile)
          content += file.toString

        FileVisitResult.CONTINUE
      }
    }

    Files.walkFileTree(rootPath, visitor)

    Some(content.toSeq)
  }

  /**
   *
   *
   * @return
   */
  //def resolveApplicationClassFiles(): Option[Seq[PathString]] = {

  //}

}
