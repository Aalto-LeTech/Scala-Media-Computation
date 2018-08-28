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


import java.io.IOException

import javax.imageio.ImageWriter

import smcl.colors.rgb.{Color, ColorComponentTranslationTable}
import smcl.infrastructure.exceptions._
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
   * @param width
   * @param height
   *
   * @return
   */
  def scaleTo(
      width: Int,
      height: Int): BitmapBufferAdapter

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
   * @param pathToFile
   *
   * @throws FileOverwritingIsDeniedBySMCLError       if given path points to an existing file (not folder)
   * @throws ImageWriterNotRetrievedError             if the first suitable [[ImageWriter]] cannot be retrieved
   * @throws ImageWritingFailedError                  if an [[IOException]] occurred while writing to the file represented by the given path
   * @throws OperationPreventedBySecurityManagerError if an existing security manager prevents access to the file represented by the given path
   * @throws PathIsNullError                          if given path is null
   * @throws PathIsEmptyOrOnlyWhitespaceError         if given path is an empty string or contains only whitespace
   * @throws PathPointsToFolderError                  if given path points to an existing folder
   * @throws SuitableImageWriterNotFoundError         if no suitable [[ImageWriter]] is found
   * @throws UnableToOpenFileForWritingError          if the file represented by the given path cannot be opened
   */
  def saveAsPngTo(pathToFile: String): Unit

}
