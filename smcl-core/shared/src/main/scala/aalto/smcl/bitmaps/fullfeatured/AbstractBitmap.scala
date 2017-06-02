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
import aalto.smcl.infrastructure.{BitmapBufferAdapter, Displayable, DrawingSurfaceAdapter, Identity, InjectablesRegistry, PRF, RenderableBitmap, TimestampedCreation}
import aalto.smcl.settings.{BitmapsAreDisplayedAutomaticallyAfterOperations, DefaultBackgroundColor, DefaultBitmapHeightInPixels, DefaultBitmapWidthInPixels, NewBitmapsAreDisplayedAutomatically, PreventViewerUpdates, UpdateViewerPerDefaults, ViewerUpdateStyle}
import aalto.smcl.viewers.{display => displayInViewer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object AbstractBitmap extends InjectablesRegistry {

  /** The ColorValidator instance to be used by this object. */
  private lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The BitmapValidator instance to be used by this object. */
  private lazy val bitmapValidator: BitmapValidator = {
    injectable(InjectablesRegistry.IIdBitmapValidator).asInstanceOf[BitmapValidator]
  }

  /**
   * Creates a new empty [[Bitmap]] instance.
   */
  def apply(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      initialBackgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): AbstractBitmap = {

    bitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    if (bitmapValidator.warningSizeLimitsAreExceeded(widthInPixels, heightInPixels)) {
      println("\n\nWarning: The image is larger than the recommended maximum size")
    }

    require(initialBackgroundColor != null,
      "The background color argument has to be a Color instance (was null).")

    val operationList =
      Clear(initialBackgroundColor) +:
          BitmapOperationList(CreateBitmap(widthInPixels, heightInPixels))

    val newBitmap = new Bitmap(operationList, bitmapValidator, colorValidator, Identity())

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (NewBitmapsAreDisplayedAutomatically)
        newBitmap.display()
    }

    newBitmap
  }

  /**
   *
   *
   * @param sourceResourcePath
   * @param viewerHandling
   *
   * @return
   */
  def apply(
      sourceResourcePath: String,
      viewerHandling: ViewerUpdateStyle): AbstractBitmap = {

    // The ImageProvider is trusted with validation of the source resource path.
    val loadedBufferTry = PRF.tryToLoadImageFromPath(sourceResourcePath)
    if (loadedBufferTry.isFailure)
      throw loadedBufferTry.failed.get

    val operationList = BitmapOperationList(
      LoadedBitmap(loadedBufferTry.get, Option(sourceResourcePath), Option(0)))
    val newBitmap = new Bitmap(
      operationList, bitmapValidator, colorValidator, Identity())

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (NewBitmapsAreDisplayedAutomatically)
        newBitmap.display()
    }

    newBitmap
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def apply(sourceResourcePath: String): AbstractBitmap = {
    apply(sourceResourcePath, UpdateViewerPerDefaults)
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class AbstractBitmap private[bitmaps](
    private[bitmaps] val operations: BitmapOperationList,
    private val bitmapValidator: BitmapValidator,
    private val colorValidator: ColorValidator,
    uniqueIdentifier: Identity) extends {

  /** Width of this [[AbstractBitmap]]. */
  val widthInPixels: Int = operations.widthInPixels

  /** Height of this [[AbstractBitmap]]. */
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
   * Returns a `BufferedImage` instance representing this [[AbstractBitmap]].
   */
  def toRenderedRepresentation: BitmapBufferAdapter = {
    _renderingBuffer.get getOrElse {
      val rendition = operations.render()

      _renderingBuffer = WeakReference[BitmapBufferAdapter](rendition)

      return rendition
    }
  }

  /**
   * Renders this [[AbstractBitmap]] onto a drawing surface using specified coordinates.
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
   * Renders this [[AbstractBitmap]] onto a drawing surface using specified affine transformation.
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
   * Returns the initial background color of this [[AbstractBitmap]]
   * (may not be the actual background color at a later time).
   */
  val initialBackgroundColor: Color = {
    operations.initialBackgroundColor
  }

  /**
   * Applies an [[Renderable]] to this [[AbstractBitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[bitmaps] def apply(
      newOperation: Renderable,
      viewerHandling: ViewerUpdateStyle): AbstractBitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newBitmap = new AbstractBitmap(
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
   * Applies an [[Renderable]] to this [[AbstractBitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps] def applyInitialization(newOperation: Renderable): AbstractBitmap = {
    apply(newOperation, PreventViewerUpdates)
  }

  /**
   * Applies an [[BufferProvider]] to this [[AbstractBitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[bitmaps] def apply(
      newOperation: BufferProvider,
      viewerHandling: ViewerUpdateStyle): AbstractBitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newOperationList = BitmapOperationList(newOperation)
    val newBitmap = new AbstractBitmap(
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
   * Applies an [[BufferProvider]] to this [[AbstractBitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps] def applyInitialization(newOperation: BufferProvider): AbstractBitmap = {
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
