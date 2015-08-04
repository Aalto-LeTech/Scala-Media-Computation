package aalto.smcl.common


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
case class TimeStamp private(underlayingDate: Date) extends Ordered[Date] {

  /**
   *
   *
   * @param that
   * @return
   */
  override def compare(that: Date): Int = underlayingDate.compareTo(that)

}
