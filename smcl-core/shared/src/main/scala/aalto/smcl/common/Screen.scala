package aalto.smcl.common


import aalto.smcl.infrastructure.{PRF, ScreenInformationProvider}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Screen {

  /**  */
  private val informationProvider: ScreenInformationProvider = PRF.screenInformationProvider


  /**
   * Dimensions of the screen.
   *
   * @return
   */
  def dimensionsInPixels: Dimension = informationProvider.dimensionsInPixels

  /**
   *
   *
   * @return
   */
  def resolutionInDotsPerInch: Int = informationProvider.resolutionInDotsPerInch

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
