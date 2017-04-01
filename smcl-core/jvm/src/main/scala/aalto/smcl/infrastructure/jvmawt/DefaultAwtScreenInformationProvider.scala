package aalto.smcl.infrastructure.jvmawt


import aalto.smcl.geometry.Dimension
import aalto.smcl.infrastructure.ScreenInformationProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class DefaultAwtScreenInformationProvider extends ScreenInformationProvider {

  /**
   * Dimensions of the screen.
   *
   * @return
   */
  override def dimensionsInPixels: Dimension = {
    val screenSize = AWTToolkit.getScreenSize
    Dimension(screenSize.width, screenSize.height)
  }

  /**
   *
   *
   * @return
   */
  override def resolutionInDotsPerInch: Int = AWTToolkit.getScreenResolution

}
