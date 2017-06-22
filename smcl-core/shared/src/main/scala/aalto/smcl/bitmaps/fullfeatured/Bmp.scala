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
import aalto.smcl.geometry.{Bounds, HasBounds, HasPos, Len, Pos}
import aalto.smcl.infrastructure.{BitmapBufferAdapter, Identity, InjectablesRegistry, PRF}




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

    bitmapValidator.validateBitmapSize(
      widthInPixels,
      heightInPixels)

    val identity: Identity = Identity()

    val boundary: Bounds =
      Bounds(
        upperLeftXInPixels = 0,
        upperLeftYInPixels = 0,
        lowerRightXInPixels = widthInPixels,
        lowerRightYInPixels = heightInPixels)

    val isrenderable =
      widthInPixels > 0 && heightInPixels > 0

    val buffer =
      if (isrenderable)
        Some(PRF.createPlatformBitmapBuffer(widthInPixels, heightInPixels))
      else
        None

    val bitmap = new Bmp(
      identity,
      isrenderable,
      widthInPixels,
      heightInPixels,
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
 * @param widthInPixels
 * @param heightInPixels
 * @param position
 * @param boundary
 * @param buffer
 *
 * @author Aleksi Lukkarinen
 */
class Bmp private(
    override val identity: Identity,
    val isRenderable: Boolean,
    val widthInPixels: Int,
    val heightInPixels: Int,
    val position: Pos,
    override val boundary: Option[Bounds],
    private[this] val buffer: Option[BitmapBufferAdapter])
    extends ImageElement(identity)
            with HasPos
            with HasBounds {

  /** Length of the bitmap. As bitmap has no length, this equals <code>None</code>. */
  val length: Option[Len] = None

  /**
   *
   *
   * @param drawingSurface
   */
  override def renderOn(drawingSurface: DrawingSurface): Unit = {
    buffer foreach drawingSurface.drawBitmap
  }

  /**
   * Rotates this [[Bmp]].
   *
   * @param angleInDegrees
   *
   * @return
   */
  override def rotateDegs(angleInDegrees: Double): Bmp = {
    this
  }

  /**
   *
   *
   * @return
   */
  override def toBitmap: Bmp = {
    this
  }

}
