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
object HLine {

  aalto.smcl.images.SettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with a horizontal line drawn on it.
   */
  def apply(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: Color = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(widthInPixels > 0, s"Width of the line must be at least 1 pixel (was $widthInPixels)")

    Bitmap(widthInPixels, 1, color, viewerHandling)
  }

}
