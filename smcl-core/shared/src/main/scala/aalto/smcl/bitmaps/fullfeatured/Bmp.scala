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
import aalto.smcl.infrastructure.{BitmapBufferAdapter, DrawingSurfaceAdapter, Identity, InjectablesRegistry, PRF}
import aalto.smcl.modeling.Len
import aalto.smcl.modeling.d2._




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
    val offset = Dims(
      0 - upperLeftPos.xInPixels,
      0 - upperLeftPos.yInPixels
    )

    elements.foreach { e =>
      e.renderOn(
        buffer.drawingSurface,
        e.position + offset
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
    private[this] val buffer: Option[BitmapBufferAdapter])
    extends ImageElement
            with HasPos
            with HasDims {

  /** */
  override
  val boundary: Option[Bounds] =
    Some(Bounds(
      position,
      Pos(
        position.xInPixels + width.inPixels,
        position.yInPixels + height.inPixels
      )
    ))

  /** Length of the bitmap. As bitmap has no length, this equals <code>None</code>. */
  val length: Option[Len] = None

  /**
   *
   *
   * @param drawingSurface
   */
  override
  def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      position: Pos): Unit = {

  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): ImageElement = {

    this
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  def moveBy(offsets: Double*): ImageElement = {
    this
  }

}
