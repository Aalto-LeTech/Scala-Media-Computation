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

package aalto.smcl.infrastructure


import aalto.smcl.bitmaps.ConvolutionKernel
import aalto.smcl.colors.{RGBAColor, RGBAComponentTranslationTable}
import aalto.smcl.geometry.AffineTransformation




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
   * @param colorToTrim
   *
   * @return
   */
  def trim(colorToTrim: RGBAColor = GS.colorFor(DefaultBackground)): BitmapBufferAdapter

  /**
   *
   *
   * @param function
   *
   * @return
   */
  def iteratePixelsWith(function: (Int, Int, Int, Int) => (Int, Int, Int, Int)): BitmapBufferAdapter

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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground)): BitmapBufferAdapter

  /**
   *
   *
   * @param minX
   * @param minY
   * @param maxX
   * @param maxY
   *
   * @return
   */
  def boundaryOverflowsForLTRB(
      minX: Double,
      minY: Double,
      maxX: Double,
      maxY: Double): (Double, Double, Double, Double)

  /**
   *
   *
   * @param kernel
   *
   * @return
   */
  def createFilteredVersionWith(kernel: ConvolutionKernel): BitmapBufferAdapter

  /**
   *
   *
   * @param translator
   *
   * @return
   */
  def createFilteredVersionWith(translator: RGBAComponentTranslationTable): BitmapBufferAdapter

  /**
   *
   *
   * @return
   */
  def copy: BitmapBufferAdapter

  /**
   *
   *
   * @param topLeftX
   * @param topLeftY
   * @param bottomRightX
   * @param bottomRightY
   *
   * @return
   */
  def copyPortionXYXY(
      topLeftX: Int,
      topLeftY: Int,
      bottomRightX: Int,
      bottomRightY: Int): BitmapBufferAdapter

  /**
   *
   *
   * @param topLeftX
   * @param topLeftY
   * @param width
   * @param height
   *
   * @return
   */
  def copyPortionXYWH(
      topLeftX: Int,
      topLeftY: Int,
      width: Int,
      height: Int): BitmapBufferAdapter

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
