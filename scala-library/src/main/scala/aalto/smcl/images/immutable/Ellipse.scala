package aalto.smcl.images.immutable

import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys.{DefaultBackground, DefaultBitmapHeightInPixels, DefaultBitmapWidthInPixels, DefaultPrimary}
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Ellipse {

  aalto.smcl.images.SettingsInitializer.perform()

  /**
   * Creates a new empty [[Bitmap]] instance with a circle drawn on it.
   */
  def apply(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      circleColor: Color = GS.colorFor(DefaultPrimary),
      backgroundColor: Color = GS.colorFor(DefaultBackground)): Bitmap = {

    require(widthInPixels >= 10, s"Width of the ellipse must be at least 10 pixels (was $widthInPixels)")
    require(heightInPixels >= 10, s"Width of the ellipse must be at least 10 pixels (was $heightInPixels)")

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
      isFilled = true,
      lineColor = circleColor,
      fillColor = circleColor,
      viewerHandling = UpdateViewerPerDefaults)
  }

}
