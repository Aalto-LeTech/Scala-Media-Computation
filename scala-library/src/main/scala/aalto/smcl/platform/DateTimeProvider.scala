package aalto.smcl.platform


import java.util.{Calendar, Date}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object DateTimeProvider {

  /**
   *
   *
   * @return
   */
  def currentTime: Date = Calendar.getInstance.getTime

}
