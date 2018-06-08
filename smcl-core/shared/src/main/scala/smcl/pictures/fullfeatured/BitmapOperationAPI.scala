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
import smcl.colors.rgb.Color
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

}
