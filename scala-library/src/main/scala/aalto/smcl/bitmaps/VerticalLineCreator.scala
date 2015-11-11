package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure.{GS, SMCLInitializationInvoker}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class VerticalLineCreator private[bitmaps]() extends SMCLInitializationInvoker {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null


  /**
   * Creates a new empty [[Bitmap]] instance with a vertical line drawn on it.
   *
   * @param heightInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def createOne(
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(heightInPixels > 0, s"Width of the line must be at least 1 pixel (was $heightInPixels)")
    require(color != null, "The color argument has to be a Color instance (was null).")

    Bitmap(1, heightInPixels, color, viewerHandling)
  }

}
