package aalto.smcl.images.immutable.primitives

import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys.{DefaultBackground, DefaultPrimary}
import aalto.smcl.images.immutable.primitives.Bitmap.ViewerUpdateStyle
import aalto.smcl.images.immutable.primitives.Bitmap.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Line {

  aalto.smcl.images.SettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with a line drawn on it. The line can be freely located in
   * the Cartesian coordinate system, but the bitmap will always contain the minimum amount of empty space.
   * That is, the line will always be drawn from one corner of the bitmap to the opposite corner.
   *
   * @param fromXInPixels
   * @param fromYInPixels
   * @param toXInPixels
   * @param toYInPixels
   * @param color
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def apply(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = GS.colorFor(DefaultPrimary),
      backgroundColor: Color = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(color != null, "The line color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val differenceX = toXInPixels - fromXInPixels
    val differenceY = toYInPixels - fromYInPixels
    val slopeSign = Math.signum(differenceY.toDouble / differenceX)
    val bitmapWidth = Math.abs(differenceX)
    val bitmapHeight = Math.abs(differenceY)

    require(bitmapWidth > 0, s"Difference of the x coordinates must be at least 1 pixel (was $bitmapWidth)")
    require(bitmapHeight > 0, s"Difference of the y coordinates must be at least 1 pixel (was $bitmapHeight)")

    val newBitmap = Bitmap(bitmapWidth, bitmapHeight, backgroundColor, PreventViewerUpdates)

    val (x0, y0, x1, y1) =
      if (slopeSign < 0)
        (0, 0, bitmapWidth - 1, bitmapHeight - 1)
      else
        (0, bitmapHeight - 1, bitmapWidth - 1, 0)

    newBitmap.drawLine(x0, y0, x1, y1, color, viewerHandling)
  }

}
