package aalto.smcl.infrastructure

import java.io.File
import java.nio.file.Path
import java.util.Locale




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class FileUtils {

  /** */
  val ClassFileExtension: String = ".class"

  /** */
  val JarFileExtension: String = ".jar"


  /**
   *
   *
   * @param f
   * @return
   */
  def doesNotRepresentReadableFile(f: File): Boolean =
    !f.isFile || !f.canRead

  /**
   *
   *
   * @param f
   * @return
   */
  def representsReadableFile(f: File): Boolean =
    !doesNotRepresentReadableFile(f)

  /**
   *
   *
   * @param f
   * @return
   */
  def representsReadableDirectory(f: File): Boolean =
    f.isDirectory && f.canRead

  /**
   *
   *
   * @param f
   * @return
   */
  def doesNotRepresentReadableDirectory(f: File): Boolean =
    !representsReadableDirectory(f)

  /**
   *
   *
   * @return
   */
  def hasClassExtension(path: String): Boolean =
    path.endsWith(ClassFileExtension)

  /**
   *
   *
   * @return
   */
  def hasClassExtension(path: File): Boolean =
    hasClassExtension(path.getCanonicalPath)

  /**
   *
   *
   * @return
   */
  def hasClassExtension(path: Path): Boolean =
    hasClassExtension(path.toFile.getCanonicalPath)

  /**
   *
   *
   * @return
   */
  def hasJarExtension(path: String): Boolean =
    path.endsWith(JarFileExtension)

  /**
   *
   *
   * @return
   */
  def hasJarExtension(path: File): Boolean =
    hasJarExtension(path.getCanonicalPath)

  /**
   *
   *
   * @return
   */
  def hasJarExtension(path: Path): Boolean =
    hasJarExtension(path.toFile.getCanonicalPath)

  /**
   *
   *
   * @param fileName
   * @return
   */
  def resolveExtensionOf(fileName: String): String = {
    val lastPeriod = fileName.lastIndexOf(StrPeriod)

    if (lastPeriod > -1)
      fileName.substring(lastPeriod + 1).trim.toLowerCase(Locale.getDefault)
    else
      StrEmpty
  }

}
