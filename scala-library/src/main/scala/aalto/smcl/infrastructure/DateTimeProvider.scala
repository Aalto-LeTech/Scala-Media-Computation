package aalto.smcl.infrastructure


import java.util.{Calendar, Date}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class DateTimeProvider private[infrastructure]()
  extends SMCLInitializationInvoker {

  /**
   *
   *
   * @return
   */
  def currentTime: Date = Calendar.getInstance.getTime

}
