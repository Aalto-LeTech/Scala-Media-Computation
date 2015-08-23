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
object RSquare {

  SMCL.performInitialization()


  /**
   * Creates a new empty [[Bitmap]] instance with a rounded-corner square drawn on it.
   *
   * @param sideLengthInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param color
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def apply(
    sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
    roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(sideLengthInPixels >= 5, s"Side length of the square must be at least 5 pixels (was $sideLengthInPixels)")
    require(roundingWidthInPixels > 0, s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")
    require(roundingHeightInPixels > 0, s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")
    require(color != null, "The rectangle color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val newBitmap = Bitmap(
      sideLengthInPixels,
      sideLengthInPixels,
      backgroundColor,
      viewerHandling = PreventViewerUpdates)

    val newRSquare = newBitmap.drawRoundedRectangle(
      0, 0,
      sideLengthInPixels - 1, sideLengthInPixels - 1,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder = true,
      hasFilling = true,
      color = color,
      fillColor = color,
      PreventViewerUpdates)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
        newRSquare.display()
    }

    newRSquare
  }

}
