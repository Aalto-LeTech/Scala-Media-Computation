package aalto.smcl.images.immutable

import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys.{DefaultBackground, DefaultBitmapHeightInPixels, DefaultBitmapWidthInPixels, DefaultPrimary}
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Ellipse {

  aalto.smcl.images.SettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with an ellipse drawn on it.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param ellipseColor
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def apply(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      ellipseColor: Color = GS.colorFor(DefaultPrimary),
      backgroundColor: Color = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(widthInPixels > 0, s"Width of the ellipse must be at least 1 pixel (was $widthInPixels)")
    require(heightInPixels > 0, s"Height of the ellipse must be at least 1 pixel (was $heightInPixels)")
    require(ellipseColor != null, "The rectangle color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val bitmapWidth = if (widthInPixels % 2 == 0) widthInPixels + 1 else widthInPixels
    val bitmapHeight = if (heightInPixels % 2 == 0) heightInPixels + 1 else heightInPixels

    val newBitmap = Bitmap(
      widthInPixels = bitmapWidth,
      heightInPixels = bitmapHeight,
      initialBackgroundColor = backgroundColor,
      viewerHandling = PreventViewerUpdates)

    val ellipseWidth = bitmapWidth - 3
    val ellipseHeight = bitmapHeight - 3
    val ellipseCenterX = (ellipseWidth / 2) + 1
    val ellipseCenterY = (ellipseHeight / 2) + 1

    newBitmap.drawEllipse(
      ellipseCenterX, ellipseCenterY,
      ellipseWidth, ellipseHeight,
      hasFilling = true,
      lineColor = ellipseColor,
      fillColor = ellipseColor,
      viewerHandling)
  }

}
