package aalto.smcl.platform


import java.util.{Calendar, Date}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class DateTimeProvider private[platform]() {

  /**
   *
   *
   * @return
   */
  def currentTime: Date = Calendar.getInstance.getTime

}
