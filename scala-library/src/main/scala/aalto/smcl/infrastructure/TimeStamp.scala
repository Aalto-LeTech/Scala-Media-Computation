package aalto.smcl.infrastructure

import java.util.Date

import aalto.smcl.platform.DateTimeProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object TimeStamp {

  /**
   *
   *
   * @return
   */
  def apply(): TimeStamp = new TimeStamp(DateTimeProvider.currentTime)

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class TimeStamp private(underlyingDate: Date) extends Ordered[Date] {

  /**
   *
   *
   * @param that
   * @return
   */
  override def compare(that: Date): Int = underlyingDate.compareTo(that)

}
