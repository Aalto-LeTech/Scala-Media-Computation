package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.BitmapSettingKeys._
import aalto.smcl.bitmaps.immutable.ConvolutionKernel
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.bitmaps.operations._
import aalto.smcl.common._




/**
 * Provides a way to add bitmap operations into [[OperableBitmap]] instances.
 *
 * @author Aleksi Lukkarinen
 */
object BitmapOps {

  /**
   * Adds a [[ConvolveWithCustomKernel]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param kernel
   * @param viewerHandling
   * @return
   */
  def convolveWith(
    bmp: OperableBitmap,
    kernel: ConvolutionKernel,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(ConvolveWithCustomKernel(kernel), viewerHandling)
  }

  /**
   * Adds a [[FilterWithComponentTranslationTable]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param translator
   * @param viewerHandling
   * @return
   */
  def filterWith(
    bmp: OperableBitmap,
    translator: RGBAComponentTranslationTable,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(FilterWithComponentTranslationTable(translator), viewerHandling)
  }

  /**
   * Adds a [[Negate]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def toNegative(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(Negate(), viewerHandling)
  }

  /**
   *
   *
   * @return
   */
  def negateRedComponent(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(NegateRedComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateGreenComponent(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(NegateGreenComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateBlueComponent(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(NegateBlueComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateRedAndGreenComponents(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(NegateRedAndGreenComponents(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateRedAndBlueComponents(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(NegateRedAndBlueComponents(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateGreenAndBlueComponents(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(NegateGreenAndBlueComponents(), viewerHandling)


  /**
   *
   *
   * @return
   */
  def keepOnlyRedComponent(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(KeepOnlyRedComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyGreenComponent(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(KeepOnlyGreenComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyBlueComponent(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(KeepOnlyBlueComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyRedAndGreenComponents(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(KeepOnlyRedAndGreenComponents(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyRedAndBlueComponents(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(KeepOnlyRedAndBlueComponents(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyGreenAndBlueComponents(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    bmp.apply(KeepOnlyGreenAndBlueComponents(), viewerHandling)

  /**
   * Adds a [[Clear]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param color
   * @return
   */
  def clear(
    bmp: OperableBitmap,
    color: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap to be cleared cannot be null.")

    bmp.apply(Clear(color), viewerHandling)
  }

  /**
   * Adds a [[DrawLine]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param fromXInPixels
   * @param fromYInPixels
   * @param toXInPixels
   * @param toYInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def drawLine(
    bmp: OperableBitmap,
    fromXInPixels: Int,
    fromYInPixels: Int,
    toXInPixels: Int,
    toYInPixels: Int,
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawLine(
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels,
      color), viewerHandling)
  }

  /**
   * Adds a [[DrawPolyline]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param color
   * @param viewerHandling
   * @return
   */
  def drawPolyline(
    bmp: OperableBitmap,
    xCoordinates: Seq[Int],
    yCoordinates: Seq[Int],
    numberOfCoordinatesToDraw: Int,
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawPolyline(
      xCoordinates, yCoordinates,
      numberOfCoordinatesToDraw,
      color), viewerHandling)
  }

  /**
   * Adds a [[DrawPolygon]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawPolygon(
    bmp: OperableBitmap,
    xCoordinates: Seq[Int],
    yCoordinates: Seq[Int],
    numberOfCoordinatesToDraw: Int,
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawPolygon(
      xCoordinates, yCoordinates,
      numberOfCoordinatesToDraw,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawSquare]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param sideLengthInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawSquare(
    bmp: OperableBitmap,
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawSquare(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawRectangle]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
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
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
   * @param hasBorder
   * @param hasFilling
   * @param color
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
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawRoundedSquare(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
   * @param hasBorder
   * @param hasFilling
   * @param color
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
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawRoundedRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawCircle]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param centerXInPixels
   * @param centerYInPixels
   * @param radiusInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   * @return
   */
  def drawCircle(
    bmp: OperableBitmap,
    centerXInPixels: Int,
    centerYInPixels: Int,
    radiusInPixels: Int = GS.intFor(DefaultCircleRadiusInPixels),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawCircle(
      centerXInPixels, centerYInPixels,
      radiusInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawEllipse]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param centerXInPixels
   * @param centerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   * @return
   */
  def drawEllipse(
    bmp: OperableBitmap,
    centerXInPixels: Int,
    centerYInPixels: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawEllipse(
      centerXInPixels, centerYInPixels,
      widthInPixels, heightInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
  }

  /**
   * Adds a [[DrawArc]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param startAngleInDegrees
   * @param arcAngleInDegrees
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawArc(
    bmp: OperableBitmap,
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    startAngleInDegrees: Int = GS.intFor(DefaultArcStartAngleInDegrees),
    arcAngleInDegrees: Int = GS.intFor(DefaultArcAngleInDgrees),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(DrawArc(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      startAngleInDegrees, arcAngleInDegrees,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
  }

  /**
   * Adds a [[AppendHorizontally]] operation to a given [[OperableBitmap]].
   *
   * @param bitmapsToCombine
   * @param verticalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   * @return
   */
  def appendHorizontally(
    bitmapsToCombine: Bitmap*)(
    verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bitmapsToCombine.length > 1, s"There must be at least two bitmaps to combine (was $bitmapsToCombine)")

    bitmapsToCombine.head.apply(
      AppendHorizontally(bitmapsToCombine)(
        verticalAlignment, paddingInPixels, backgroundColor),
      viewerHandling)
  }

  /**
   * Adds a [[AppendVertically]] operation to a given [[OperableBitmap]].
   *
   * @param bitmapsToCombine
   * @param horizontalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   * @return
   */
  def appendVertically(
    bitmapsToCombine: Bitmap*)(
    horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bitmapsToCombine.length > 1, s"There must be at least two bitmaps to combine (was $bitmapsToCombine)")

    bitmapsToCombine.head.apply(
      AppendVertically(bitmapsToCombine)(
        horizontalAlignment, paddingInPixels, backgroundColor),
      viewerHandling)
  }

  /**
   * Adds a [[FlipHorizontally]] operation to a given [[OperableBitmap]].
   *
   * @param viewerHandling
   * @return
   */
  def flipHorizontally(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(FlipHorizontally(), viewerHandling)
  }

  /**
   * Adds a [[FlipVertically]] operation to a given [[OperableBitmap]].
   *
   * @param viewerHandling
   * @return
   */
  def flipVertically(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(FlipVertically(), viewerHandling)
  }

  /**
   * Adds a [[FlipDiagonally]] operation to a given [[OperableBitmap]].
   *
   * @param viewerHandling
   * @return
   */
  def flipDiagonally(
    bmp: OperableBitmap,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.apply(FlipDiagonally(), viewerHandling)
  }

}


//  /**
//   *
//   */
//  def argbIntAt(x: Int, y: Int): Int = {
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
//    colorComponentsFrom(argbIntAt(x, y))
//  }
//
//  /**
//   *
//   */
//  def setargbIntAt(x: Int, y: Int, argbInt: Int): Unit = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.setRGB(x, y, argbInt)
//  }
//
//  /**
//   *
//   */
//  def setColorComponentsAt(x: Int, y: Int, red: Int, green: Int, blue: Int, opacity: Int): Unit = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.setRGB(x, y, argbIntFrom(red, green, blue, opacity))
//  }
//
//  /**
//   *
//   */
//  def redComponentAt(x: Int, y: Int): Int = redComponentFrom(argbIntAt(x, y))
//
//  /**
//   *
//   */
//  def setRedComponentAt(x: Int, y: Int, red: Int): Unit =
//    setargbIntAt(x, y, withNewRedComponent(argbIntAt(x, y), red))
//
//  /**
//   *
//   */
//  def greenComponentAt(x: Int, y: Int): Int = greenComponentFrom(argbIntAt(x, y))
//
//  /**
//   *
//   */
//  def setGreenComponentAt(x: Int, y: Int, green: Int): Unit =
//    setargbIntAt(x, y, withNewGreenComponent(argbIntAt(x, y), green))
//
//  /**
//   *
//   */
//  def blueComponentAt(x: Int, y: Int): Int = blueComponentFrom(argbIntAt(x, y))
//
//  /**
//   *
//   */
//  def setBlueComponentAt(x: Int, y: Int, blue: Int): Unit =
//    setargbIntAt(x, y, withNewBlueComponent(argbIntAt(x, y), blue))
//
//  /**
//   *
//   */
//  def opacityComponentAt(x: Int, y: Int): Int = opacityComponentFrom(argbIntAt(x, y))
//
//  /**
//   *
//   */
//  def opacityComponentAt_=(x: Int, y: Int, opacity: Int): Unit =
//    setargbIntAt(x, y, withNewOpacityComponent(argbIntAt(x, y), opacity))
