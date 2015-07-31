package aalto.smcl.images.immutable


import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys.{DefaultBackground, DefaultBitmapWidthInPixels, DefaultPrimary}
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Circle {

  aalto.smcl.images.SettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with a circle drawn on it.
   */
  def apply(
      diameter: Int = GS.intFor(DefaultBitmapWidthInPixels),
      circleColor: Color = GS.colorFor(DefaultPrimary),
      backgroundColor: Color = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(diameter > 0, s"Diameter of the circle must be at least 1 pixel (was $diameter)")

    val imageSide = if (diameter % 2 == 0) diameter + 1 else diameter

    val newBitmap = Bitmap(
      widthInPixels = imageSide,
      heightInPixels = imageSide,
      initialBackgroundColor = backgroundColor,
      viewerHandling = PreventViewerUpdates)

    val radius = (imageSide - 2) / 2

    newBitmap.drawCircle(
      centerXInPixels = radius + 1,
      centerYInPixels = radius + 1,
      radiusInPixels = radius,
      isFilled = true,
      lineColor = circleColor,
      fillColor = circleColor,
      viewerHandling)
  }

}
