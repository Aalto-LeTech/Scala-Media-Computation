package aalto.smcl.infrastructure


import java.awt.Dimension




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Screen() extends SMCLInitializationInvoker {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null


  /** Dimensions of the screen. */
  private[smcl] val awtDimensions: Dimension = new UIProvider().awtToolkit.getScreenSize

  /** Width of the screen. */
  val width = awtDimensions.width

  /** Height of the screen. */
  val height = awtDimensions.height

  /** Dimensions of the screen as a tuple `(width, height)`. */
  val dimensions = (width, height)

  /** Area of the screen. */
  val area = width * height

  /** Resolution of the screen. */
  val resolution: Int = new UIProvider().awtToolkit.getScreenResolution

}
