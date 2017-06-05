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

package aalto.smcl.bitmaps.fullfeatured


import scala.ref.WeakReference

import aalto.smcl.bitmaps.operations._
import aalto.smcl.bitmaps.{BitmapValidator, PixelRectangle}
import aalto.smcl.colors.ColorValidator
import aalto.smcl.colors.rgb.Color
import aalto.smcl.geometry.AffineTransformation
import aalto.smcl.infrastructure.{BitmapBufferAdapter, Displayable, DrawingSurfaceAdapter, Identity, RenderableBitmap, TimestampedCreation}
import aalto.smcl.settings.{BitmapsAreDisplayedAutomaticallyAfterOperations, PreventViewerUpdates, UpdateViewerPerDefaults, ViewerUpdateStyle}
import aalto.smcl.viewers.{display => displayInViewer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractBitmap private[bitmaps](
    private[bitmaps] val operations: BitmapOperationList,
    private val bitmapValidator: BitmapValidator,
    private val colorValidator: ColorValidator,
    uniqueIdentifier: Identity) extends {

  /** Width of this [[BitmapCompanion]]. */
  val widthInPixels: Int = operations.widthInPixels

  /** Height of this [[BitmapCompanion]]. */
  val heightInPixels: Int = operations.heightInPixels

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
    drawingSurface.drawBitmap(rendition, x, y)
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
  private[bitmaps]
  def apply(
      newOperation: Renderable,
      viewerHandling: ViewerUpdateStyle): AbstractBitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newBitmap = instantiateBitmap(
      newOperation +: operations,
      bitmapValidator,
      colorValidator,
      uniqueIdentifier
    )

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (BitmapsAreDisplayedAutomaticallyAfterOperations)
        newBitmap.display()
    }

    newBitmap
  }

  /**
   * Applies an [[Renderable]] to this [[BitmapCompanion]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps]
  def applyInitialization(newOperation: Renderable): AbstractBitmap = {
    apply(newOperation, PreventViewerUpdates)
  }

  /**
   * Applies an [[BufferProvider]] to this [[BitmapCompanion]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[bitmaps]
  def apply(
      newOperation: BufferProvider,
      viewerHandling: ViewerUpdateStyle): AbstractBitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newOperationList = BitmapOperationList(newOperation)
    val newBitmap = instantiateBitmap(
      newOperationList,
      bitmapValidator,
      colorValidator,
      uniqueIdentifier
    )

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (BitmapsAreDisplayedAutomaticallyAfterOperations)
        newBitmap.display()
    }

    newBitmap
  }

  /**
   * Applies an [[BufferProvider]] to this [[BitmapCompanion]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps]
  def applyInitialization(newOperation: BufferProvider): AbstractBitmap = {
    apply(newOperation, PreventViewerUpdates)
  }

  /**
   *
   *
   * @param filename
   *
   * @return
   */
  def saveAsPngTo(filename: String): String = {
    toRenderedRepresentation.saveAsPngTo(filename)
  }

  /**
   *
   */
  def display(): AbstractBitmap = {
    displayInViewer(this)

    this
  }

}
