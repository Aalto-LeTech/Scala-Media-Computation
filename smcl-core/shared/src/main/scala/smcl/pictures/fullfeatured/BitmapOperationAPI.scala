/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.pictures.fullfeatured


import smcl.colors.ColorValidator
import smcl.colors.rgb.{Color, ColorComponentTranslationTable}
import smcl.pictures.ConvolutionKernel
import smcl.settings._




/**
 * Provides a way to modify [[Bitmap]]
 * instances using "stand-alone" functions instead of OOP-based `object.operation`
 * approach. The functions are made available by the package object.
 *
 * @author Aleksi Lukkarinen
 */
private[fullfeatured]
trait BitmapOperationAPI {

  /**
   *
   *
   * @param bmp
   * @param kernel
   * @param viewerHandling
   *
   * @return
   */
  def convolveWith(
      bmp: Bitmap,
      kernel: ConvolutionKernel,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.convolveWith(kernel, viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param translator
   * @param viewerHandling
   *
   * @return
   */
  def filterWith(
      bmp: Bitmap,
      translator: ColorComponentTranslationTable,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.filterWith(translator, viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param function
   * @param viewerHandling
   *
   * @return
   */
  def iteratePixelsWith(
      bmp: Bitmap,
      function: (Int, Int, Int, Int) => (Int, Int, Int, Int),
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.iteratePixelsWith(function, viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param colorToTrim
   * @param viewerHandling
   *
   * @return
   */
  def trim(
      bmp: Bitmap,
      colorToTrim: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.trim(colorToTrim, viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param extraPixelsOntoLeftEdge
   * @param extraPixelsOntoTopEdge
   * @param extraPixelsOntoRightEdge
   * @param extraPixelsOntoBottomEdge
   * @param color
   * @param viewerHandling
   *
   * @return
   */
  def augmentCanvas(
      bmp: Bitmap,
      extraPixelsOntoLeftEdge: Int = 0,
      extraPixelsOntoTopEdge: Int = 0,
      extraPixelsOntoRightEdge: Int = 0,
      extraPixelsOntoBottomEdge: Int = 0,
      color: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.augmentCanvas(
      extraPixelsOntoLeftEdge,
      extraPixelsOntoTopEdge,
      extraPixelsOntoRightEdge,
      extraPixelsOntoBottomEdge,
      color,
      viewerHandling)
  }

  /**
   *
   *
   * @param bottomBitmap
   * @param topBitmap
   * @param topBitmapUpperLeftX
   * @param topBitmapUpperLeftY
   * @param topBitmapOpacity
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def overlay(
      bottomBitmap: Bitmap,
      topBitmap: Bitmap,
      topBitmapUpperLeftX: Int,
      topBitmapUpperLeftY: Int,
      topBitmapOpacity: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    bottomBitmap.underlayBehind(
      topBitmap,
      topBitmapUpperLeftX,
      topBitmapUpperLeftY,
      topBitmapOpacity,
      backgroundColor,
      viewerHandling)
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
   *
   * @return
   */
  def overlayPerAlignments(
      bitmapsToOverlayFromBottomToTop: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      opacityForAllBitmaps: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

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
   * @param color
   *
   * @return
   */
  def clear(
      bmp: Bitmap,
      color: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap to be cleared cannot be null.")

    bmp.clear(color, viewerHandling)
  }

  /**
   *
   *
   * @param bitmapsToCombine
   * @param verticalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendHorizontally(
      bitmapsToCombine: Bitmap*)(
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bitmapsToCombine.length > 1, s"There must be at least two bitmaps to combine (was $bitmapsToCombine)")

    bitmapsToCombine.head.appendOnRight(bitmapsToCombine.tail: _*)(
      verticalAlignment,
      paddingInPixels,
      backgroundColor,
      viewerHandling)
  }

  /**
   *
   *
   * @param bitmapLeft
   * @param bitmapRight
   *
   * @return
   */
  def sew(bitmapLeft: Bitmap, bitmapRight: Bitmap): Bitmap = {
    appendHorizontally(bitmapLeft, bitmapRight)(VAMiddle)
  }

  /**
   *
   *
   * @param bitmapsToCombine
   * @param horizontalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendVertically(
      bitmapsToCombine: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bitmapsToCombine.length > 1, s"There must be at least two bitmaps to combine (was $bitmapsToCombine)")

    bitmapsToCombine.head.appendOnBottom(bitmapsToCombine.tail: _*)(
      horizontalAlignment,
      paddingInPixels,
      backgroundColor,
      viewerHandling)
  }

  /**
   *
   *
   * @param bitmapTop
   * @param bitmapBottom
   *
   * @return
   */
  def pile(bitmapTop: Bitmap, bitmapBottom: Bitmap): Bitmap = {
    appendVertically(bitmapTop, bitmapBottom)(HACenter)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   *
   * @return
   */
  def flipHorizontally(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.flipHorizontally(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   *
   * @return
   */
  def flipVertically(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.flipVertically(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param viewerHandling
   *
   * @return
   */
  def flipDiagonally(
      bmp: Bitmap,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.flipDiagonally(viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param shearingFactorHorizontal
   * @param shearingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def shear(
      bmp: Bitmap,
      shearingFactorHorizontal: Double = 0.0,
      shearingFactorVertical: Double = 0.0,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shear(
      shearingFactorHorizontal,
      shearingFactorVertical,
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param shearingFactor
   *
   * @return
   */
  def shear(bmp: Bitmap, shearingFactor: Double): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shear(shearingFactor)
  }


  /**
   *
   *
   * @param bmp
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def shearHorizontally(
      bmp: Bitmap,
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shearHorizontally(
      shearingFactor,
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def shearVertically(
      bmp: Bitmap,
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shearVertically(
      shearingFactor,
      resizeCanvasBasedOnTransformation,
      backgroundColor,
      viewerHandling)
  }


  /**
   *
   *
   * @param bmp
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def replicateHorizontally(
      bmp: Bitmap,
      numberOfReplicas: Int,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.replicateHorizontally(
      numberOfReplicas,
      paddingInPixels,
      backgroundColor,
      viewerHandling)
  }

  /**
   *
   *
   * @param bmp
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def replicateVertically(
      bmp: Bitmap,
      numberOfReplicas: Int,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

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
//  def setARGBIntAt(x: Int, y: Int, argbInt: Int): Unit = {
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
//    setARGBIntAt(x, y, withNewRedComponent(argbIntAt(x, y), red))
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
//    setARGBIntAt(x, y, withNewGreenComponent(argbIntAt(x, y), green))
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
//    setARGBIntAt(x, y, withNewBlueComponent(argbIntAt(x, y), blue))
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
//    setARGBIntAt(x, y, withNewOpacityComponent(argbIntAt(x, y), opacity))
