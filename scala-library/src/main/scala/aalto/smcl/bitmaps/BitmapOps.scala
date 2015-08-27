package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.bitmaps.immutable.ConvolutionKernel
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.bitmaps.operations._
import aalto.smcl.common._




/**
 * Provides a way to modify [[Bitmap]] instances using "stand-alone" functions
 * instead of OOP-based `object.operation` approach. The functions are made
 * available by the package object.
 *
 * @author Aleksi Lukkarinen
 */
trait BitmapOps {

  /**
   *
   *
   * @param bmp
   * @param kernel
   * @param viewerHandling
   * @return
   */
  def convolveWith(
      bmp: Bitmap,
      kernel: ConvolutionKernel,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.convolveWith(kernel, viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param translator
   * @param viewerHandling
   * @return
   */
  def filterWith(
      bmp: Bitmap,
      translator: RGBAComponentTranslationTable,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.filterWith(translator, viewerHandling)
  }


  /**
   *
   *
   * @param bitmapsToOverlayFromBottomToTop
   * @param horizontalAlignment
   * @param verticalAlignment
   * @param opacityForAllBitmaps
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def overlayPerAlignments(
      bitmapsToOverlayFromBottomToTop: Bitmap*)(
      horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
      verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
      opacityForAllBitmaps: Int = ColorValidator.MaximumRgbaOpacity,
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    bitmapsToOverlayFromBottomToTop.head.overlayPerAlignments(bitmapsToOverlayFromBottomToTop.tail: _*)(
      horizontalAlignment,
      verticalAlignment,
      opacityForAllBitmaps,
      backgroundColor,
      viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def toNegative(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negate(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def negateRedComponent(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateRedComponent(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def negateGreenComponent(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateGreenComponent(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def negateBlueComponent(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateBlueComponent(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def negateRedAndGreenComponents(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateRedAndGreenComponents(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def negateRedAndBlueComponents(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateRedAndBlueComponents(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def negateGreenAndBlueComponents(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateGreenAndBlueComponents(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def keepOnlyRedComponent(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyRedComponent(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def keepOnlyGreenComponent(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyGreenComponent(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def keepOnlyBlueComponent(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyBlueComponent(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def keepOnlyRedAndGreenComponents(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyRedAndGreenComponents(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def keepOnlyRedAndBlueComponents(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyRedAndBlueComponents(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def keepOnlyGreenAndBlueComponents(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyGreenAndBlueComponents(viewerHandling)
  }

  /**
   * Adds a [[Posterize]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param strengthAsPercentage
   * @param viewerHandling
   */
  def posterize(
      bmp: Bitmap,
      strengthAsPercentage: Int,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap to be cleared cannot be null.")

    bmp.posterize(strengthAsPercentage, viewerHandling)
  }

  /**
   * Adds a [[Clear]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param color
   * @return
   */
  def clear(
      bmp: Bitmap,
      color: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap to be cleared cannot be null.")

    bmp.clear(color, viewerHandling)
  }

  /**
   * Adds a [[DrawLine]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawLine(
      fromXInPixels,
      fromYInPixels,
      toXInPixels,
      toYInPixels,
      color,
      viewerHandling)
  }

  /**
   * Adds a [[DrawPolyline]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
      xCoordinates: Seq[Int],
      yCoordinates: Seq[Int],
      numberOfCoordinatesToDraw: Int,
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawPolyline(
      xCoordinates,
      yCoordinates,
      numberOfCoordinatesToDraw,
      color,
      viewerHandling)
  }

  /**
   * Adds a [[DrawPolygon]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
      xCoordinates: Seq[Int],
      yCoordinates: Seq[Int],
      numberOfCoordinatesToDraw: Int,
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawPolygon(
      xCoordinates,
      yCoordinates,
      numberOfCoordinatesToDraw,
      hasBorder,
      hasFilling,
      color,
      fillColor,
      viewerHandling)
  }

  /**
   * Adds a [[DrawSquare]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawSquare(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      sideLengthInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor,
      viewerHandling)
  }

  /**
   * Adds a [[DrawRectangle]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
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

    bmp.drawRectangle(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      widthInPixels,
      heightInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor,
      viewerHandling)
  }

  /**
   * Adds a [[DrawRoundedSquare]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
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

    bmp.drawRoundedSquare(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      sideLengthInPixels,
      roundingWidthInPixels,
      roundingHeightInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor,
      viewerHandling)
  }

  /**
   * Adds a [[DrawRoundedRectangle]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
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

    bmp.drawRoundedRectangle(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      widthInPixels,
      heightInPixels,
      roundingWidthInPixels,
      roundingHeightInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor,
      viewerHandling)
  }

  /**
   * Adds a [[DrawCircle]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
      centerXInPixels: Int,
      centerYInPixels: Int,
      radiusInPixels: Int = GS.intFor(DefaultCircleRadiusInPixels),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawCircle(
      centerXInPixels,
      centerYInPixels,
      radiusInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor,
      viewerHandling)
  }

  /**
   * Adds a [[DrawEllipse]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
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

    bmp.drawEllipse(
      centerXInPixels,
      centerYInPixels,
      widthInPixels,
      heightInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor,
      viewerHandling)
  }

  /**
   * Adds a [[DrawArc]] operation to a given [[Bitmap]].
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
      bmp: Bitmap,
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

    bmp.drawArc(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      widthInPixels,
      heightInPixels,
      startAngleInDegrees,
      arcAngleInDegrees,
      hasBorder,
      hasFilling,
      color,
      fillColor,
      viewerHandling)
  }


  /**
   * Adds a [[OverlayFreely]] operation to a given [[Bitmap]].
   *
   * @param bottomBitmap
   * @param topBitmap
   * @param topBitmapUpperLeftX
   * @param topBitmapUpperLeftY
   * @param topBitmapOpacity
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def overlayFreely(
      bottomBitmap: Bitmap,
      topBitmap: Bitmap,
      topBitmapUpperLeftX: Int,
      topBitmapUpperLeftY: Int,
      topBitmapOpacity: Int = ColorValidator.MaximumRgbaOpacity,
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    bottomBitmap.layFreelyUnder(
      topBitmap,
      topBitmapUpperLeftX,
      topBitmapUpperLeftY,
      topBitmapOpacity,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[AppendHorizontally]] operation to a given [[Bitmap]].
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

    bitmapsToCombine.head.appendOnRight(bitmapsToCombine.tail: _*)(
      verticalAlignment,
      paddingInPixels,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[AppendHorizontally]] operation to a given [[Bitmap]].
   *
   * @param bitmapLeft
   * @param bitmapRight
   * @return
   */
  def sew(bitmapLeft: Bitmap, bitmapRight: Bitmap): Bitmap =
    appendHorizontally(bitmapLeft, bitmapRight)(VerticalAlignment.Middle)

  /**
   * Adds a [[AppendVertically]] operation to a given [[Bitmap]].
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

    bitmapsToCombine.head.appendOnBottom(bitmapsToCombine.tail: _*)(
      horizontalAlignment,
      paddingInPixels,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[AppendVertically]] operation to a given [[Bitmap]].
   *
   * @param bitmapTop
   * @param bitmapBottom
   * @return
   */
  def pile(bitmapTop: Bitmap, bitmapBottom: Bitmap): Bitmap =
    appendVertically(bitmapTop, bitmapBottom)(HorizontalAlignment.Center)

  /**
   * Adds a [[FlipHorizontally]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def flipHorizontally(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.flipHorizontally(viewerHandling)
  }

  /**
   * Adds a [[FlipVertically]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def flipVertically(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.flipVertically(viewerHandling)
  }

  /**
   * Adds a [[FlipDiagonally]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param viewerHandling
   * @return
   */
  def flipDiagonally(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.flipDiagonally(viewerHandling)
  }

  /**
   * Adds a [[Rotate]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param angleInDegrees
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def rotateDegs(
      bmp: Bitmap,
      angleInDegrees: Double,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotateDegs(
      angleInDegrees,
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[Rotate]] operation to a given [[Bitmap]] for rotating that bitmap -90 degrees.
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def rotate90DegsCw(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate90DegsCw(
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[Rotate]] operation to a given [[Bitmap]] for rotating that bitmap 90 degrees.
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def rotate90DegsCcw(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate90DegsCcw(
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[Rotate]] operation to a given [[Bitmap]] for rotating that bitmap 180 degrees.
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def rotate180Degs(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate180Degs(
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[Rotate]] operation to a given [[Bitmap]] for rotating that bitmap -90 degrees.
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def turn(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate90DegsCw(
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[Rotate]] operation to a given [[Bitmap]] for rotating that bitmap 90 degrees.
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def unturn(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate90DegsCcw(
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[Scale]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param scalingFactorHorizontal
   * @param scalingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   * @param viewerHandling
   * @return
   */
  def scale(
      bmp: Bitmap,
      scalingFactorHorizontal: Double = 1.0,
      scalingFactorVertical: Double = 1.0,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.scale(
      scalingFactorHorizontal,
      scalingFactorVertical,
      resizeCanvasBasedOnTransformation,
      viewerHandling)
  }

  /**
   * Adds a [[Scale]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param scalingFactor
   * @return
   */
  def scale(bmp: Bitmap, scalingFactor: Double): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.scale(scalingFactor)
  }

  /**
   * Adds a [[Scale]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param scalingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param viewerHandling
   * @return
   */
  def scaleHorizontally(
      bmp: Bitmap,
      scalingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    bmp.scaleHorizontally(
      scalingFactor,
      resizeCanvasBasedOnTransformation,
      viewerHandling)
  }

  /**
   * Adds a [[Scale]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param scalingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param viewerHandling
   * @return
   */
  def scaleVertically(
      bmp: Bitmap,
      scalingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    bmp.scaleVertically(
      scalingFactor,
      resizeCanvasBasedOnTransformation,
      viewerHandling)
  }

  /**
   * Adds a [[Shear]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param shearingFactorHorizontal
   * @param shearingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def shear(
      bmp: Bitmap,
      shearingFactorHorizontal: Double = 0.0,
      shearingFactorVertical: Double = 0.0,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shear(
      shearingFactorHorizontal,
      shearingFactorVertical,
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[Shear]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param shearingFactor
   * @return
   */
  def shear(bmp: Bitmap, shearingFactor: Double): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shear(shearingFactor)
  }


  /**
   * Adds a [[Shear]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def shearHorizontally(
      bmp: Bitmap,
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shearHorizontally(
      shearingFactor,
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[Shear]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def shearVertically(
      bmp: Bitmap,
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shearVertically(
      shearingFactor,
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }


  /**
   * Adds a [[ReplicateHorizontally]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def replicateHorizontally(
      bmp: Bitmap,
      numberOfReplicas: Int,
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.replicateHorizontally(
      numberOfReplicas,
      paddingInPixels,
      backgroundColor,
      viewerHandling)
  }

  /**
   * Adds a [[ReplicateVertically]] operation to a given [[Bitmap]].
   *
   * @param bmp
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def replicateVertically(
      bmp: Bitmap,
      numberOfReplicas: Int,
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.replicateVertically(
      numberOfReplicas,
      paddingInPixels,
      backgroundColor,
      viewerHandling)
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
