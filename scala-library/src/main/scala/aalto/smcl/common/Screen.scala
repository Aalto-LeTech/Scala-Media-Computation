package aalto.smcl.common


import java.awt.Dimension




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Screen {

  /** Dimensions of the screen. */
  private[smcl] val awtDimensions: Dimension = Platform.awtToolkit.getScreenSize

  /** Width of the screen. */
  val width = awtDimensions.width

  /** Height of the screen. */
  val height = awtDimensions.height

  /** Dimensions of the screen as a tuple `(width, height)`. */
  val dimensions = (width, height)

  /** Area of the screen. */
  val area = width * height

  /** Resolution of the screen. */
  val resolution: Int = Platform.awtToolkit.getScreenResolution

}
