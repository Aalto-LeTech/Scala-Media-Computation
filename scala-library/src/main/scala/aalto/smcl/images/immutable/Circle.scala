package aalto.smcl.images.immutable


import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys.{DefaultBackground, DefaultBitmapWidthInPixels, DefaultPrimary, NewBitmapsAreDisplayedAutomatically}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Circle {

  aalto.smcl.images.SettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance.
   */
  def apply(
      diameter: Int = GS.intFor(DefaultBitmapWidthInPixels),
      circleColor: Color = GS.colorFor(DefaultPrimary),
      backgroundColor: Color = GS.colorFor(DefaultBackground)): Bitmap = {

    require(diameter >= 10, s"Diameter of the circle must be at least 10 pixels (was $diameter)")

    val imageSide = if (diameter % 2 == 0) diameter + 1 else diameter

    var newBitmap = Bitmap(
      widthInPixels = imageSide,
      heightInPixels = imageSide,
      initialBackgroundColor = backgroundColor)

    val radius = (imageSide - 2) / 2

    newBitmap = newBitmap.drawCircle(
      centerXInPixels = radius + 1,
      centerYInPixels = radius + 1,
      radiusInPixels = radius,
      isFilled = true,
      lineColor = circleColor,
      fillColor = circleColor)

    if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
      newBitmap.display()

    newBitmap
  }

}
