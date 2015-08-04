package aalto.smcl.bitmaps.immutable.primitives


import aalto.smcl.bitmaps.BitmapSettingKeys.{DefaultBitmapWidthInPixels, DefaultPrimary}
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.common.{Color, GS}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object HLine {

  aalto.smcl.bitmaps.BitmapSettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with a horizontal line drawn on it.
   *
   * @param widthInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def apply(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: Color = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(widthInPixels > 0, s"Width of the line must be at least 1 pixel (was $widthInPixels)")
    require(color != null, "The color argument has to be a Color instance (was null).")

    Bitmap(widthInPixels, 1, color, viewerHandling)
  }

}
