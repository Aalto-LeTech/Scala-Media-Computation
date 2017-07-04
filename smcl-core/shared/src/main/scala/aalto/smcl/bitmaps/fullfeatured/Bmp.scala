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

    val identity: Identity = Identity()

    val boundary: Bounds =
      Bounds(
        upperLeftXInPixels = 0,
        upperLeftYInPixels = 0,
        lowerRightXInPixels = 0 + width.inPixels,
        lowerRightYInPixels = 0 + height.inPixels)

    val isrenderable = width > 0 && height > 0

    val buffer =
      if (isrenderable)
        Some(PRF.createPlatformBitmapBuffer(width, height))
      else
        None

    val bitmap = new Bmp(
      identity,
      isrenderable,
      Dims(width, height),
      Pos.Origo,
      Some(boundary),
      buffer)

    bitmap
  }

}




/**
 *
 *
 * @param identity
 * @param isRenderable
 * @param dimensions
 * @param position
 * @param boundary
 * @param buffer
 *
 * @author Aleksi Lukkarinen
 */
class Bmp private(
    override val identity: Identity,
    val isRenderable: Boolean,
    val dimensions: Dims,
    val position: Pos,
    override val boundary: Option[Bounds],
    private[this] val buffer: Option[BitmapBufferAdapter])
    extends ImageElement(identity)
            with HasPos
            with HasDims
            with HasBounds {

  /** Length of the bitmap. As bitmap has no length, this equals <code>None</code>. */
  val length: Option[Len] = None

  /**
   *
   *
   * @param drawingSurface
   */
  def renderOn(drawingSurface: DrawingSurfaceAdapter): Unit = {

  }

  /**
   *
   *
   * @return
   */
  override def toBitmap: Bmp = {
    this
  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override def rotateBy(
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
  override def moveBy(offsets: Double*): ImageElement = {
    this
  }

}
