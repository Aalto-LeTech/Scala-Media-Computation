package aalto.smcl.infrastructure


import aalto.smcl.interfaces.Timestamp




/**
 *
 */
case class DefaultTimestamp(
  day: Int, month: Int, year: Int,
  hour: Int, minute: Int, second: Int, milliSecond: Int) extends Timestamp {

}
