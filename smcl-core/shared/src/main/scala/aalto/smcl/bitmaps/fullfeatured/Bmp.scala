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


import aalto.smcl.bitmaps.BitmapValidator
import aalto.smcl.colors.ColorValidator
import aalto.smcl.infrastructure.{BitmapBufferAdapter, Displayable, DrawingSurfaceAdapter, Identity, InjectablesRegistry, PRF}
import aalto.smcl.modeling.d2._
import aalto.smcl.modeling.{AffineTransformation, Len}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bmp
    extends InjectablesRegistry {

  /** The ColorValidator instance to be used by this object. */
  protected lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The BitmapValidator instance to be used by this object. */
  protected lazy val bitmapValidator: BitmapValidator = {
    injectable(InjectablesRegistry.IIdBitmapValidator).asInstanceOf[BitmapValidator]
  }

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  def apply(elements: ImageElement*): Bmp = {
    val boundsOption =
      Image.calculateOuterBoundary(elements)

    if (boundsOption.isEmpty) {
      return Bmp(0, 0)
    }

    val bounds = boundsOption.get
    val width = bounds.width
    val height = bounds.height

    if (width < 1 || height < 1) {
      return Bmp(0, 0)
    }

    bitmapValidator.validateBitmapSize(width, height)

    val buffer: BitmapBufferAdapter =
      PRF.createPlatformBitmapBuffer(width, height)

    val upperLeftPos = bounds.upperLeftMarker
    val offsets = (-upperLeftPos.xInPixels, -upperLeftPos.yInPixels)

    elements.foreach{e =>
      e.renderOn(
        buffer.drawingSurface,
        e.position + offsets
      )
    }

    Bmp(
      Identity(),
      Pos.Origo,
      Some(buffer))
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Int,
      heightInPixels: Int): Bmp = {

    apply(
      Len(widthInPixels),
      Len(heightInPixels))
  }

  /**
   *
   *
   * @param width
   * @param height
   *
   * @return
   */
  def apply(
      width: Len,
      height: Len): Bmp = {

    bitmapValidator.validateBitmapSize(
      width,
      height)

    val newIdentity: Identity = Identity()

    val buffer =
      if (width > 0 && height > 0)
        Some(PRF.createPlatformBitmapBuffer(width, height))
      else
        None

    Bmp(newIdentity, Pos.Origo, buffer)
  }

  /**
   *
   *
   * @param identity
   * @param buffer
   *
   * @return
   */
  private
  def apply(
      identity: Identity,
      position: Pos,
      buffer: Option[BitmapBufferAdapter]): Bmp = {

    if (buffer.isEmpty) {
      return new Bmp(identity, false, Dims(0, 0), position, None)
    }

    val isRenderable =
      buffer.get.widthInPixels > 0 && buffer.get.heightInPixels > 0

    new Bmp(
      identity,
      isRenderable,
      Dims(buffer.get.widthInPixels, buffer.get.heightInPixels),
      position,
      buffer)
  }

}




/**
 *
 *
 * @param identity
 * @param isRenderable
 * @param dimensions
 * @param position
 * @param buffer
 *
 * @author Aleksi Lukkarinen
 */
class Bmp private(
    override val identity: Identity,
    val isRenderable: Boolean,
    val dimensions: Dims,
    val position: Pos,
    private[smcl] val buffer: Option[BitmapBufferAdapter])
    extends ImageElement
            with Displayable
            with HasPos
            with HasDims {

  /** */
  override
  val boundary: Option[Bounds] =
    if (isRenderable)
      Some(Bounds(
        position,
        Pos(
          position.xInPixels + width.inPixels - 1,
          position.yInPixels + height.inPixels - 1
        )
      ))
    else
      None

  /** Length of the bitmap. As bitmap has no length, this equals <code>None</code>. */
  val length: Option[Len] = None

  /**
   *
   *
   * @param drawingSurface
   */
  @inline
  override
  def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      position: Pos): Unit = {

    if (buffer.isEmpty)
      return

    val p = position.toFlooredIntTuple

    drawingSurface.drawBitmap(buffer.get, p._1, p._2)
  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): ImageElement = {

    if (buffer.isEmpty)
      return this

    val newBuffer =
      buffer.get.createTransformedVersionWith(
        AffineTransformation.forPointCentredRotation(
          angleInDegrees,
          centerOfRotation))

    val newDims = Dims(
      newBuffer.widthInPixels,
      newBuffer.heightInPixels)

    new Bmp(identity, isRenderable, newDims, position, Some(newBuffer))
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  def moveBy(offsets: Double*): ImageElement = {
    copy(newPosition = position.moveBy(offsets: _*))
  }

  /**
   *
   *
   * @param filename
   *
   * @return
   */
  @inline
  def saveAsPngTo(filename: String): String = {
    if (buffer.isDefined)
      buffer.get.saveAsPngTo(filename)
    else
      "Error: No BitmapBufferAdapter to save."
  }

  /**
   *
   * @return
   */
  @inline
  def copy(newPosition: Pos = position): Bmp = {
    new Bmp(identity, isRenderable, dimensions, newPosition, buffer)
  }

  /**
   *
   */
  @inline
  override
  def display(): Bmp = {
    super.display()

    this
  }

}
