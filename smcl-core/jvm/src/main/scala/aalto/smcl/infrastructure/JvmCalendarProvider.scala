package aalto.smcl.infrastructure


import java.util.Calendar




/**
 *
 */
trait JvmCalendarProvider {

  def currentMoment: Calendar

}
