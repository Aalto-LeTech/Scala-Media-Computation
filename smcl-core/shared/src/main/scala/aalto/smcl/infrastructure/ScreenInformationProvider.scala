package aalto.smcl.infrastructure


import aalto.smcl.common.Dimension




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait ScreenInformationProvider {

  /**
   * Dimensions of the screen.
   *
   * @return
   */
  def dimensionsInPixels: Dimension

  /**
   *
   *
   * @return
   */
  def resolutionInDotsPerInch: Int

}
