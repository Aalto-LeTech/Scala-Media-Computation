package aalto.smcl.bitmaps.immutable.primitives


import aalto.smcl.SMCL
import aalto.smcl.bitmaps.BitmapSettingKeys.{DefaultBitmapWidthInPixels, DefaultPrimary}
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.common.{GS, RGBAColor}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Square {

  SMCL.performInitialization()


  /**
   * Creates a new empty [[Bitmap]] instance with a square drawn on it.
   *
   * @param sideLengthInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def apply(
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(sideLengthInPixels > 0, s"Side length of the square must be at least 1 pixel (was $sideLengthInPixels)")
    require(color != null, "The color argument has to be a Color instance (was null).")

    Bitmap(sideLengthInPixels, sideLengthInPixels, color, viewerHandling)
  }

}
