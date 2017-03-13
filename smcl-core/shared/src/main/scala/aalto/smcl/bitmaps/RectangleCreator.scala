package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure.GS




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class RectangleCreator private[bitmaps]() {

  /**
   * Creates a new empty [[Bitmap]] instance with a rectangle drawn on it.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def createOne(
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(widthInPixels > 0, s"Width of the rectangle must be at least 1 pixel (was $widthInPixels)")
    require(heightInPixels > 0, s"Height of the rectangle must be at least 1 pixel (was $heightInPixels)")
    require(color != null, "The color argument has to be a Color instance (was null).")

    Bitmap(widthInPixels, heightInPixels, color, viewerHandling)
  }

}
