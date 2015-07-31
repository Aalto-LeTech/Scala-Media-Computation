package aalto.smcl.images.immutable


import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys.{DefaultBitmapWidthInPixels, DefaultPrimary}
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Square {

  aalto.smcl.images.SettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with a square drawn on it.
   */
  def apply(
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: Color = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(sideLengthInPixels > 0, s"Side length of the square must be at least 1 pixel (was $sideLengthInPixels)")

    Bitmap(sideLengthInPixels, sideLengthInPixels, color, viewerHandling)
  }

}
