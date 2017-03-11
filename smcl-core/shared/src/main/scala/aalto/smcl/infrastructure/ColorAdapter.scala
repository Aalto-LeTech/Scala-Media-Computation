package aalto.smcl.infrastructure


import aalto.smcl.colors.RGBAColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait ColorAdapter {

  /**
   *
   *
   * @return
   */
  def red: Int

  /**
   *
   *
   * @return
   */
  def green: Int

  /**
   *
   *
   * @return
   */
  def blue: Int

  /**
   *
   *
   * @return
   */
  def opacity: Int

  /**
   *
   *
   * @return
   */
  def applicationColor: RGBAColor

}
