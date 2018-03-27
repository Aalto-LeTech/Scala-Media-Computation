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

package smcl.infrastructure


import smcl.colors.rgb.{Color, ColorComponentTranslationTable}
import smcl.modeling.AffineTransformation
import smcl.modeling.d2.Dims
import smcl.pictures.ConvolutionKernel
import smcl.settings.{CanvasesAreResizedBasedOnTransformations, DefaultBackgroundColor}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
trait BitmapBufferAdapter {

  /**
   *
   *
   * @return
   */
  def widthInPixels: Int

  /**
   *
   *
   * @return
   */
  def heightInPixels: Int

  /**
   *
   *
   * @return
   */
  def areaInPixels: Int

  /**
   *
   *
   * @return
   */
  def drawingSurface: DrawingSurfaceAdapter

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   *
   * @return
   */
  def colorAt(
      xInPixels: Double,
      yInPixels: Double): Color

  /**
   *
   *
   * @param colorToTrim
   *
   * @return
   */
  def trim(colorToTrim: Color = DefaultBackgroundColor): BitmapBufferAdapter

  /**
   *
   *
   * @param function
   *
   * @return
   */
  def iteratePixelsWith(
      function: (Int, Int, Int, Int) => (Int, Int, Int, Int)): BitmapBufferAdapter

  /**
   *
   *
   * @return
   */
  def colorComponentArrays: (Array[Int], Array[Int], Array[Int], Array[Int])

  /**
   *
   *
   * @return
   */
  def setColorComponentArrays(
      reds: Array[Int],
      greens: Array[Int],
      blues: Array[Int],
      opacities: Array[Int]): Unit

  /**
   *
   *
   * @param transformation
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def createTransformedVersionWith(
      transformation: AffineTransformation,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): (BitmapBufferAdapter, Dims)

  /**
   *
   *
   * @param minXInPixels
   * @param minYInPixels
   * @param maxXInPixels
   * @param maxYInPixels
   *
   * @return
   */
  def boundaryOverflowsForLTRB(
      minXInPixels: Double,
      minYInPixels: Double,
      maxXInPixels: Double,
      maxYInPixels: Double): (Double, Double, Double, Double)

  /**
   *
   *
   * @param kernel
   *
   * @return
   */
  def createFilteredVersionWith(
      kernel: ConvolutionKernel): BitmapBufferAdapter

  /**
   *
   *
   * @param translator
   *
   * @return
   */
  def createFilteredVersionWith(
      translator: ColorComponentTranslationTable): BitmapBufferAdapter

  /**
   *
   *
   * @return
   */
  def copy: BitmapBufferAdapter

  /**
   *
   *
   * @param topLeftXInPixels
   * @param topLeftYInPixels
   * @param bottomRightXInPixels
   * @param bottomRightYInPixels
   *
   * @return
   */
  def copyPortionXYXY(
      topLeftXInPixels: Double,
      topLeftYInPixels: Double,
      bottomRightXInPixels: Double,
      bottomRightYInPixels: Double): BitmapBufferAdapter

  /**
   *
   *
   * @param topLeftXInPixels
   * @param topLeftYInPixels
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def copyPortionXYWH(
      topLeftXInPixels: Double,
      topLeftYInPixels: Double,
      widthInPixels: Double,
      heightInPixels: Double): BitmapBufferAdapter

  /**
   *
   *
   * @return
   */
  def emptyAlike: BitmapBufferAdapter

  /**
   *
   *
   * @param filename
   *
   * @return
   */
  def saveAsPngTo(filename: String): String

}
