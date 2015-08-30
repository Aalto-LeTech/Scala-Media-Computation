package aalto.smcl.platform


import java.util.{Calendar, Date}

import aalto.smcl.SMCL




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object DateTimeProvider {

  SMCL.performInitialization()


  /**
   *
   *
   * @return
   */
  def currentTime: Date = Calendar.getInstance.getTime

}
