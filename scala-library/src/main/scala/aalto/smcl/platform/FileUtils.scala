package aalto.smcl.platform


import java.util.Locale

import aalto.smcl.SMCL
import aalto.smcl.common._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object FileUtils {

  SMCL.performInitialization()


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
