package aalto.smcl.infrastructure


import java.util.Locale




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class CommonFileUtils {

  /**
   *
   *
   * @param fileName
   * @return
   */
  def resolveExtensionOf(fileName: String): String = {
    val lastPeriodPosition = fileName.lastIndexOf(StrPeriod)

    if (lastPeriodPosition > -1)
      fileName.substring(lastPeriodPosition + 1).trim.toLowerCase(Locale.getDefault)
    else
      StrEmpty
  }

}
