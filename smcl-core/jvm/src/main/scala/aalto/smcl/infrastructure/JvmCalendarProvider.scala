package aalto.smcl.infrastructure


import java.util.Calendar




/**
 *
 */
private[smcl]
trait JvmCalendarProvider {

  /**
   *
   *
   * @return
   */
  def currentMoment: Calendar

}
