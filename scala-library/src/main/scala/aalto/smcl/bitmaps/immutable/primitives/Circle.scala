package aalto.smcl.bitmaps.immutable.primitives


import aalto.smcl.SMCL
import aalto.smcl.bitmaps.BitmapSettingKeys._
import aalto.smcl.bitmaps.ViewerUpdateStyle
import aalto.smcl.bitmaps.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}
import aalto.smcl.common.{GS, RGBAColor}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Circle {

  SMCL.performInitialization()


  /**
   * Creates a new empty [[Bitmap]] instance with a circle drawn on it.
   *
   * @param diameter
   * @param color
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def apply(
    diameter: Int = GS.intFor(DefaultBitmapWidthInPixels),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(diameter > 0, s"Diameter of the circle must be at least 1 pixel (was $diameter)")
    require(color != null, "The circle color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val imageSide = if (diameter % 2 == 0) diameter + 1 else diameter

    val newBitmap = Bitmap(
      widthInPixels = imageSide,
      heightInPixels = imageSide,
      initialBackgroundColor = backgroundColor,
      viewerHandling = PreventViewerUpdates)

    val radius = (imageSide - 2) / 2

    val newCircle = newBitmap.drawCircle(
      centerXInPixels = radius + 1,
      centerYInPixels = radius + 1,
      radiusInPixels = radius,
      hasBorder = true,
      hasFilling = true,
      color = color,
      fillColor = color,
      viewerHandling = PreventViewerUpdates)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
        newCircle.display()
    }

    newCircle
  }

}
