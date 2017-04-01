package aalto.smcl.infrastructure


import aalto.smcl.geometry.Dimension




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class DefaultScreen(private val _informationProvider: ScreenInformationProvider) {

  /**
   * Dimensions of the screen.
   *
   * @return
   */
  def dimensionsInPixels: Dimension = _informationProvider.dimensionsInPixels

  /**
   *
   *
   * @return
   */
  def resolutionInDotsPerInch: Int = _informationProvider.resolutionInDotsPerInch

  /**
   *
   *
   * @return
   */
  def widthInPixels: Int = dimensionsInPixels.widthInPixels

  /**
   * Height of the screen.
   *
   * @return
   */
  def heightInPixels: Int = dimensionsInPixels.heightInPixels

  /**
   * Area of the screen.
   *
   * @return
   */
  def areaInPixels: Int = widthInPixels * heightInPixels

}
