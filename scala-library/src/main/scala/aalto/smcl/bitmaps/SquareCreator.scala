package aalto.smcl.bitmaps


import aalto.smcl.GS
import aalto.smcl.bitmaps.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.colors.RGBAColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] class SquareCreator private[bitmaps]() {


  /**
   * Creates a new empty [[Bitmap]] instance with a square drawn on it.
   *
   * @param sideLengthInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def createOne(
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(sideLengthInPixels > 0, s"Side length of the square must be at least 1 pixel (was $sideLengthInPixels)")
    require(color != null, "The color argument has to be a Color instance (was null).")

    Bitmap(sideLengthInPixels, sideLengthInPixels, color, viewerHandling)
  }

}
