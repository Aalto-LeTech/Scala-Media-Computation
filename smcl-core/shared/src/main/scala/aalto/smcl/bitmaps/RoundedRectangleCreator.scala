package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}
import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure.{DefaultBackground, DefaultBitmapHeightInPixels, DefaultBitmapWidthInPixels, DefaultPrimary, DefaultRoundingHeightInPixels, DefaultRoundingWidthInPixels, GS, NewBitmapsAreDisplayedAutomatically}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class RoundedRectangleCreator private[bitmaps]() {

  /**
   * Creates a new empty [[Bitmap]] instance with a rounded-corner rectangle drawn on it.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param color
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def createOne(
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
    roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(widthInPixels >= 5, s"Width of the rectangle must be at least 5 pixels (was $widthInPixels)")
    require(heightInPixels >= 5, s"Height of the rectangle must be at least 5 pixels (was $heightInPixels)")
    require(roundingWidthInPixels > 0, s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")
    require(roundingHeightInPixels > 0, s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")
    require(color != null, "The rectangle color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val newBitmap = Bitmap(
      widthInPixels,
      heightInPixels,
      backgroundColor,
      viewerHandling = PreventViewerUpdates)

    val newRRectangle = newBitmap.drawRoundedRectangle(
      0, 0,
      widthInPixels - 1, heightInPixels - 1,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder = true,
      hasFilling = true,
      color = color,
      fillColor = color,
      PreventViewerUpdates)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
        newRRectangle.display()
    }

    newRRectangle
  }

}
