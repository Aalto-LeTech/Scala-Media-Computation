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


import scala.ref.WeakReference

import smcl.colors.ColorValidator
import smcl.colors.rgb.Color
import smcl.infrastructure._
import smcl.modeling.AffineTransformation
import smcl.pictures.operations._
import smcl.pictures.{BitmapValidator, PixelRectangle}
import smcl.settings.{VUSPreventViewerUpdates, ViewerUpdateStyle}
import smcl.viewers.{display => displayInViewer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractBitmap private[pictures](
    private[pictures] val operations: BitmapOperationList,
    private val bitmapValidator: BitmapValidator,
    private val colorValidator: ColorValidator,
    val uniqueIdentifier: Identity) extends {

  /** Width of this [[BitmapCompanion]]. */
  val widthInPixels: Double = operations.widthInPixels

  /** Height of this [[BitmapCompanion]]. */
  val heightInPixels: Double = operations.heightInPixels

  /** Rendering buffer for this image. */
  private[this] var _renderingBuffer: WeakReference[BitmapBufferAdapter] =
    WeakReference[BitmapBufferAdapter](null)

} with Displayable
    with RenderableBitmap
    with PixelRectangle
    with Immutable
    with TimestampedCreation {

  /**
   *
   *
   * @param operations
   * @param bitmapValidator
   * @param colorValidator
   * @param uniqueIdentifier
   *
   * @return
   */
  protected def instantiateBitmap(
      operations: BitmapOperationList,
      bitmapValidator: BitmapValidator,
      colorValidator: ColorValidator,
      uniqueIdentifier: Identity): AbstractBitmap

  /**
   * Returns a `BufferedImage` instance representing this [[BitmapCompanion]].
   */
  def toRenderedRepresentation: BitmapBufferAdapter = {
    _renderingBuffer.get getOrElse {
      val rendition = operations.render()

      _renderingBuffer = WeakReference[BitmapBufferAdapter](rendition)

      return rendition
    }
  }

  /**
   * Renders this [[BitmapCompanion]] onto a drawing surface using specified coordinates.
   *
   * @param drawingSurface
   * @param x
   * @param y
   */
  def renderOnto(drawingSurface: DrawingSurfaceAdapter, x: Int, y: Int): Unit = {
    require(drawingSurface != null, "Drawing surface argument cannot be null.")

    val rendition = toRenderedRepresentation
    drawingSurface.drawBitmap(rendition, x, y, ColorValidator.MaximumOpacity)
  }

  /**
   * Renders this [[BitmapCompanion]] onto a drawing surface using specified affine transformation.
   *
   * @param drawingSurface
   * @param transformation
   */
  def renderOnto(
      drawingSurface: DrawingSurfaceAdapter,
      transformation: AffineTransformation): Unit = {

    require(drawingSurface != null, "Drawing surface argument cannot be null.")

    val rendition = toRenderedRepresentation
    drawingSurface.drawBitmap(rendition, transformation)
  }

  /**
   * Returns the initial background color of this [[BitmapCompanion]]
   * (may not be the actual background color at a later time).
   */
  val initialBackgroundColor: Color = {
    operations.initialBackgroundColor
  }

  /**
   * Applies an [[Renderable]] to this [[BitmapCompanion]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[pictures]
  def apply(
      newOperation: Renderable,
      viewerHandling: ViewerUpdateStyle): AbstractBitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    displayAsNewIfNecessary(viewerHandling)(
      instantiateBitmap(
        newOperation +: operations,
        bitmapValidator,
        colorValidator,
        uniqueIdentifier
      ))
  }

  /**
   * Applies an [[Renderable]] to this [[BitmapCompanion]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[pictures]
  def applyInitialization(newOperation: Renderable): AbstractBitmap = {
    apply(newOperation, VUSPreventViewerUpdates)
  }

  /**
   * Applies an [[BufferProvider]] to this [[BitmapCompanion]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[pictures]
  def apply(
      newOperation: BufferProvider,
      viewerHandling: ViewerUpdateStyle): AbstractBitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newOperationList = BitmapOperationList(newOperation)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateBitmap(
        newOperationList,
        bitmapValidator,
        colorValidator,
        uniqueIdentifier
      ))
  }

  /**
   * Applies an [[BufferProvider]] to this [[BitmapCompanion]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[pictures]
  def applyInitialization(newOperation: BufferProvider): AbstractBitmap = {
    apply(newOperation, VUSPreventViewerUpdates)
  }

  /**
   *
   */
  def display(): AbstractBitmap = {
    displayInViewer(this)

    this
  }

}
