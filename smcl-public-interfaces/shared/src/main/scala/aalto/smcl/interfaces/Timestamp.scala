package aalto.smcl.interfaces




/**
 *
 */
trait Timestamp {

  /**
   *
   *
   * @return
   */
  def day: Int

  /**
   *
   *
   * @return
   */
  def month: Int

  /**
   *
   *
   * @return
   */
  def year: Int

  /**
   *
   *
   * @return
   */
  def hour: Int

  /**
   *
   *
   * @return
   */
  def minute: Int

  /**
   *
   *
   * @return
   */
  def second: Int

  /**
   *
   *
   * @return
   */
  def milliSecond: Int

}
