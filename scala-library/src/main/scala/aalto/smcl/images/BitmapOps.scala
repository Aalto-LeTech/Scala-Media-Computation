package aalto.smcl.images


import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys._
import aalto.smcl.images.immutable.Bitmap
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.images.operations._




/**
 * Provides a way to add bitmap operations into [[OperableBitmap]] instances.
 *
 * @author Aleksi Lukkarinen
 */
object BitmapOps {

  /**
   * Adds a [[Clear]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param color
   * @return
   */
  def clear(
      bmp: OperableBitmap,
      color: Color = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap to be cleared cannot be null.")

    bmp.apply(Clear(color), viewerHandling)
  }

  /**
   * Adds a [[DrawSquare]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param sideLengthInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawSquare(
      bmp: OperableBitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawSquare(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawRectangle]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawRectangle(
      bmp: OperableBitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawRoundedSquare]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param sideLengthInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawRoundedSquare(
      bmp: OperableBitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
      roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawRoundedSquare(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawRoundedRectangle]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawRoundedRectangle(
      bmp: OperableBitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
      roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawRoundedRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawCircle]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param centerXInPixels
   * @param centerYInPixels
   * @param radiusInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @return
   */
  def drawCircle(
      bmp: OperableBitmap,
      centerXInPixels: Int,
      centerYInPixels: Int,
      radiusInPixels: Int = GS.intFor(DefaultCircleRadiusInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawCircle(
      centerXInPixels, centerYInPixels,
      radiusInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawEllipse]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param centerXInPixels
   * @param centerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @return
   */
  def drawEllipse(
      bmp: OperableBitmap,
      centerXInPixels: Int,
      centerYInPixels: Int,
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawEllipse(
      centerXInPixels, centerYInPixels,
      widthInPixels, heightInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

}


//  /**
//   *
//   */
//  def pixelIntAt(x: Int, y: Int): Int = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.getRGB(x, y)
//  }
//
//  /**
//   *
//   */
//  def colorComponentsAt(x: Int, y: Int): collection.immutable.Map[Symbol, Int] = {
//    colorComponentsFrom(pixelIntAt(x, y))
//  }
//
//  /**
//   *
//   */
//  def setPixelIntAt(x: Int, y: Int, pixelInt: Int): Unit = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.setRGB(x, y, pixelInt)
//  }
//
//  /**
//   *
//   */
//  def setColorComponentsAt(x: Int, y: Int, red: Int, green: Int, blue: Int, transparency: Int): Unit = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.setRGB(x, y, pixelIntFrom(red, green, blue, transparency))
//  }
//
//  /**
//   *
//   */
//  def redComponentAt(x: Int, y: Int): Int = redComponentFrom(pixelIntAt(x, y))
//
//  /**
//   *
//   */
//  def setRedComponentAt(x: Int, y: Int, red: Int): Unit =
//    setPixelIntAt(x, y, withNewRedComponent(pixelIntAt(x, y), red))
//
//  /**
//   *
//   */
//  def greenComponentAt(x: Int, y: Int): Int = greenComponentFrom(pixelIntAt(x, y))
//
//  /**
//   *
//   */
//  def setGreenComponentAt(x: Int, y: Int, green: Int): Unit =
//    setPixelIntAt(x, y, withNewGreenComponent(pixelIntAt(x, y), green))
//
//  /**
//   *
//   */
//  def blueComponentAt(x: Int, y: Int): Int = blueComponentFrom(pixelIntAt(x, y))
//
//  /**
//   *
//   */
//  def setBlueComponentAt(x: Int, y: Int, blue: Int): Unit =
//    setPixelIntAt(x, y, withNewBlueComponent(pixelIntAt(x, y), blue))
//
//  /**
//   *
//   */
//  def transparencyComponentAt(x: Int, y: Int): Int = transparencyComponentFrom(pixelIntAt(x, y))
//
//  /**
//   *
//   */
//  def transparencyComponentAt_=(x: Int, y: Int, transparency: Int): Unit =
//    setPixelIntAt(x, y, withNewTransparencyComponent(pixelIntAt(x, y), transparency))
