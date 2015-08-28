package aalto.smcl.platform


import java.awt.geom.{AffineTransform, Rectangle2D}
import java.awt.image._
import java.awt.{AlphaComposite, Graphics2D}

import aalto.smcl.bitmaps._
import aalto.smcl.bitmaps.immutable.ConvolutionKernel
import aalto.smcl.common._
import aalto.smcl.platform.PlatformSettingKeys.PlatformBitmapInterpolationMethod




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformBitmapBuffer {

  /** */
  val NormalizedBufferType = BufferedImage.TYPE_INT_ARGB


  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @return
   */
  def apply(widthInPixels: Int, heightInPixels: Int): PlatformBitmapBuffer = {
    BitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    val newBuffer = createNormalizedLowLevelBitmapBufferOf(widthInPixels, heightInPixels)

    new PlatformBitmapBuffer(newBuffer)
  }

  /**
   *
   *
   * @param awtBufferedImage
   * @return
   */
  def apply(awtBufferedImage: BufferedImage): PlatformBitmapBuffer = {
    require(awtBufferedImage != null, "Provided image buffer cannot be null.")

    BitmapValidator.validateBitmapSize(awtBufferedImage.getWidth, awtBufferedImage.getHeight)

    val normalizedAwtBuffer = convertToNormalizedLowLevelBitmapBufferIfNecessary(awtBufferedImage)

    new PlatformBitmapBuffer(normalizedAwtBuffer)
  }

  /**
   *
   *
   * @param width
   * @param height
   * @return
   */
  private[platform] def createNormalizedLowLevelBitmapBufferOf(
      width: Int,
      height: Int): BufferedImage = {

    val newBuffer = new BufferedImage(width, height, NormalizedBufferType)

    var drawingSurface: Graphics2D = null
    try {
      drawingSurface = newBuffer.createGraphics()
      drawingSurface.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC))
      drawingSurface.setColor(GS.colorFor(DefaultBackground).toAwtColor)
      drawingSurface.fillRect(0, 0, width, height)
    }
    finally {
      drawingSurface.dispose()
    }

    newBuffer
  }


  /**
   *
   *
   * @param buffer
   * @return
   */
  private[platform] def convertToNormalizedLowLevelBitmapBufferIfNecessary(
      buffer: BufferedImage): BufferedImage = {

    var bufferCandidate = buffer

    if (bufferCandidate.getType != NormalizedBufferType) {
      val newBuffer = createNormalizedLowLevelBitmapBufferOf(
        bufferCandidate.getWidth, bufferCandidate.getHeight)

      var drawingSurface: Graphics2D = null
      try {
        drawingSurface = newBuffer.createGraphics()
        drawingSurface.drawImage(bufferCandidate, null, 0, 0)
      }
      finally {
        drawingSurface.dispose()
      }

      bufferCandidate = newBuffer
    }

    bufferCandidate
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class PlatformBitmapBuffer private(val awtBufferedImage: BufferedImage) {

  /** */
  lazy val widthInPixels: Int = awtBufferedImage.getWidth

  /** */
  lazy val heightInPixels: Int = awtBufferedImage.getHeight

  /** */
  def drawingSurface(): PlatformDrawingSurface = PlatformDrawingSurface(this)

  /**
   *
   *
   * @param colorToTrim
   * @return
   */
  def trim(colorToTrim: RGBAColor = GS.colorFor(DefaultBackground)): PlatformBitmapBuffer = {
    val IdxRed = 0
    val IdxGreen = 1
    val IdxBlue = 2
    val IdxOpacity = 3

    val sourceRaster: Raster = awtBufferedImage.getData
    val rasterWidth = sourceRaster.getWidth
    val rasterHeight = sourceRaster.getHeight

    var pixel = Array(0, 0, 0, 0)
    val red = colorToTrim.red
    val green = colorToTrim.green
    val blue = colorToTrim.blue
    val opacity = colorToTrim.opacity

    // Check for "empty" columns on the left edge (and also if the raster is fully "empty")
    var row = 0
    var column = 0
    var deviationFound: Boolean = false
    while (column < rasterWidth && !deviationFound) {
      while (row < rasterHeight && !deviationFound) {
        pixel = sourceRaster.getPixel(column, row, pixel)
        if (pixel(IdxRed) != red
            || pixel(IdxGreen) != green
            || pixel(IdxBlue) != blue
            || pixel(IdxOpacity) != opacity) {

          deviationFound = true
        }

        row += 1
      }

      column += 1
      row = 0
    }
    if (column >= rasterWidth && !deviationFound) {
      // Raster was fully "empty" ==> return 1 x 1 bitmap
      return PlatformBitmapBuffer(1, 1)
    }

    val firstNonEmptyColumn = column - 1

    // At this point, we know that the raster was not fully "empty", which means that there has to be at least one
    // deviating pixel. Consequently, the following loops have to stop before crossing the opposite edge of the raster.


    // Check for "empty" columns on the right edge
    row = 0
    column = rasterWidth - 1
    deviationFound = false
    while (column >= 0 && !deviationFound) {
      while (row < rasterHeight && !deviationFound) {
        pixel = sourceRaster.getPixel(column, row, pixel)
        if (pixel(IdxRed) != red
            || pixel(IdxGreen) != green
            || pixel(IdxBlue) != blue
            || pixel(IdxOpacity) != opacity) {

          deviationFound = true
        }

        row += 1
      }

      column -= 1
      row = 0
    }

    val lastNonEmptyColumn = column + 1

    // Check for "empty" rows on the top edge
    row = 0
    column = 0
    deviationFound = false
    while (row < rasterHeight && !deviationFound) {
      while (column < rasterWidth && !deviationFound) {
        pixel = sourceRaster.getPixel(column, row, pixel)
        if (pixel(IdxRed) != red
            || pixel(IdxGreen) != green
            || pixel(IdxBlue) != blue
            || pixel(IdxOpacity) != opacity) {

          deviationFound = true
        }

        column += 1
      }

      row += 1
      column = 0
    }

    val firstNonEmptyRow = row - 1


    // Check for "empty" rows on the bottom edge
    row = rasterHeight - 1
    column = 0
    deviationFound = false
    while (row >= 0 && !deviationFound) {
      while (column < rasterWidth && !deviationFound) {
        pixel = sourceRaster.getPixel(column, row, pixel)
        if (pixel(IdxRed) != red
            || pixel(IdxGreen) != green
            || pixel(IdxBlue) != blue
            || pixel(IdxOpacity) != opacity) {

          deviationFound = true
        }

        column += 1
      }

      row -= 1
      column = 0
    }

    val lastNonEmptyRow = row + 1


    // Return a copy of the non-empty portion of the source bitmap
    copyPortion(firstNonEmptyColumn, firstNonEmptyRow, lastNonEmptyColumn, lastNonEmptyRow)
  }

  /**
   *
   *
   * @param transformation
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @return
   */
  def createTransfomedVersionWith(
      transformation: AffineTransformation,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground)): PlatformBitmapBuffer = {

    val globalInterpolationMethod =
      GS.enumSettingFor[BitmapInterpolationMethod.Value](PlatformBitmapInterpolationMethod).value.id

    var resultingImageWidth: Int = awtBufferedImage.getWidth
    var resultingImageHeight: Int = awtBufferedImage.getHeight
    val lowLevelTransformation = transformation.platformAffineTransform.awtAffineTransformation
    val transformedContentBoundaries: Rectangle2D =
      new AffineTransformOp(lowLevelTransformation, globalInterpolationMethod)
          .getBounds2D(awtBufferedImage)

    if (resizeCanvasBasedOnTransformation) {
      val offsetLeft = -transformedContentBoundaries.getMinX
      val offsetTop = -transformedContentBoundaries.getMinY
      val offsetRight = transformedContentBoundaries.getMaxX - awtBufferedImage.getWidth
      val offsetBottom = transformedContentBoundaries.getMaxY - awtBufferedImage.getHeight

      if (offsetTop > 0 || offsetLeft > 0) {
        val translationToBringTheRotatedBitmapFullyVisible =
          AffineTransform.getTranslateInstance(offsetLeft, offsetTop)

        lowLevelTransformation preConcatenate translationToBringTheRotatedBitmapFullyVisible
      }

      resultingImageWidth = Math.round(awtBufferedImage.getWidth + offsetLeft + offsetRight).toInt
      resultingImageHeight = Math.round(awtBufferedImage.getHeight + offsetTop + offsetBottom).toInt
    }

    val finalTransformOperation = new AffineTransformOp(lowLevelTransformation, globalInterpolationMethod)
    val resultingBuffer: PlatformBitmapBuffer = PlatformBitmapBuffer(resultingImageWidth, resultingImageHeight)

    resultingBuffer drawingSurface () clearUsing backgroundColor

    finalTransformOperation.filter(awtBufferedImage, resultingBuffer.awtBufferedImage)

    resultingBuffer
  }

  /**
   *
   *
   * @param minX
   * @param minY
   * @param maxX
   * @param maxY
   * @return
   */
  def boundaryOverflowsForLTRB(
      minX: Double,
      minY: Double,
      maxX: Double,
      maxY: Double): (Double, Double, Double, Double) = {

    val overflowLeft = if (minX < 0) -minX else 0
    val overflowTop = if (minY < 0) -minY else 0
    val overflowRight = if (maxX > awtBufferedImage.getWidth) maxX - awtBufferedImage.getWidth else 0
    val overflowBottom = if (maxY > awtBufferedImage.getHeight) maxY - awtBufferedImage.getHeight else 0

    (overflowLeft, overflowTop, overflowRight, overflowBottom)
  }

  /**
   *
   *
   * @param kernel
   * @return
   */
  def createFilteredVersionWith(kernel: ConvolutionKernel): PlatformBitmapBuffer = {
    val lowLevelKernel = new Kernel(kernel.width, kernel.height, kernel.toRowMajorArray)
    val operation = new ConvolveOp(lowLevelKernel)

    val newLowLevelBitmap = operation.filter(
      awtBufferedImage,
      emptyAlike().awtBufferedImage)

    PlatformBitmapBuffer(newLowLevelBitmap)
  }

  /**
   *
   *
   * @param translator
   * @return
   */
  def createFilteredVersionWith(translator: RGBAComponentTranslationTable): PlatformBitmapBuffer = {
    val lowLevelLookupTable = new ShortLookupTable(0, translator.toArray)
    val operation = new LookupOp(lowLevelLookupTable, null)

    val newLowLevelBitmap = operation.filter(
      awtBufferedImage,
      emptyAlike().awtBufferedImage)

    PlatformBitmapBuffer(newLowLevelBitmap)
  }

  /**
   *
   *
   * @return
   */
  def copy(): PlatformBitmapBuffer =
    PlatformBitmapBuffer(BitmapUtils.deepCopy(awtBufferedImage))

  /**
   *
   *
   * @param topLeftX
   * @param topLeftY
   * @param bottomRightX
   * @param bottomRightY
   * @return
   */
  def copyPortion(
      topLeftX: Int,
      topLeftY: Int,
      bottomRightX: Int,
      bottomRightY: Int): PlatformBitmapBuffer = {

    val width = bottomRightX - topLeftX + 1
    val height = bottomRightY - topLeftY + 1

    val sourceBufferArea =
      awtBufferedImage.getSubimage(topLeftX, topLeftY, width, height)

    val newBuffer = PlatformBitmapBuffer.createNormalizedLowLevelBitmapBufferOf(width, height)

    var drawingSurface: Graphics2D = null
    try {
      drawingSurface = newBuffer.createGraphics()
      drawingSurface.drawImage(sourceBufferArea, null, 0, 0)
    }
    finally {
      drawingSurface.dispose()
    }

    PlatformBitmapBuffer(newBuffer)
  }

  /**
   *
   *
   * @return
   */
  def emptyAlike(): PlatformBitmapBuffer =
    PlatformBitmapBuffer(widthInPixels, heightInPixels)

}
