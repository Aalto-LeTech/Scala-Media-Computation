package aalto.smcl.bitmaps.immutable.primitives


import aalto.smcl.bitmaps.BitmapSettingKeys.{DefaultBitmapHeightInPixels, DefaultPrimary}
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.common.{Color, GS}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object VLine {

  aalto.smcl.bitmaps.BitmapSettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with a vertical line drawn on it.
   *
   * @param heightInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def apply(
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      color: Color = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(heightInPixels > 0, s"Width of the line must be at least 1 pixel (was $heightInPixels)")
    require(color != null, "The color argument has to be a Color instance (was null).")

    Bitmap(1, heightInPixels, color, viewerHandling)
  }

}
