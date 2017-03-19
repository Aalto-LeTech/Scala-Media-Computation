package aalto.smcl.infrastructure


import java.util.Calendar




/**
 *
 */
private[smcl]
class DefaultJvmCalendarProvider extends JvmCalendarProvider {

  /**
   *
   *
   * @return
   */
  override def currentMoment: Calendar = Calendar.getInstance()

}
