package aalto.smcl.images.immutable

import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys.{DefaultRoundingHeightInPixels, DefaultRoundingWidthInPixels, DefaultBackground, DefaultBitmapWidthInPixels, DefaultPrimary}
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object RSquare {

  aalto.smcl.images.SettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with a rectangle drawn on it.
   */
  def apply(
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
      roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
      squareColor: Color = GS.colorFor(DefaultPrimary),
      backgroundColor: Color = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(sideLengthInPixels >= 10, s"Side length of the square must be at least 10 pixels (was $sideLengthInPixels)")
    require(roundingWidthInPixels > 0, s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")
    require(roundingHeightInPixels > 0, s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")
    require(squareColor != null, "The rectangle color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val newBitmap = Bitmap(
      sideLengthInPixels,
      sideLengthInPixels,
      backgroundColor,
      viewerHandling = PreventViewerUpdates)

    newBitmap.drawRoundedRectangle(
      0, 0,
      sideLengthInPixels - 1, sideLengthInPixels - 1,
      roundingWidthInPixels, roundingHeightInPixels,
      isFilled = true,
      lineColor = squareColor,
      fillColor = squareColor,
      viewerHandling)

  }

}
