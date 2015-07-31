package aalto.smcl.images.immutable


import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys.{DefaultBitmapHeightInPixels, DefaultPrimary}
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object VLine {

  aalto.smcl.images.SettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with a vertical line drawn on it.
   */
  def apply(
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      color: Color = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(heightInPixels > 0, s"Width of the line must be at least 1 pixel (was $heightInPixels)")

    Bitmap(1, heightInPixels, color, viewerHandling)
  }

}
