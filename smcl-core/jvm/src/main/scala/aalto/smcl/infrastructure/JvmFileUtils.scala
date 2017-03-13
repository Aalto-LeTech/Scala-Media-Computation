package aalto.smcl.infrastructure

import java.io.File




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class JvmFileUtils {

  /**
    *
    *
    * @param f
    * @return
    */
  def representsReadableFile(f: File): Boolean =
    f.isFile && f.canRead

  /**
   *
   *
   * @param f
   * @return
   */
  def doesNotRepresentReadableFile(f: File): Boolean =
    !representsReadableFile(f)

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

}
