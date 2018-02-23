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

package smcl.pictures


import smcl.colors.ColorValidator
import smcl.infrastructure.{BitmapBufferAdapter, Displayable, Identity, InjectablesRegistry, PRF}
import smcl.modeling.d2._
import smcl.modeling.{AffineTransformation, Len}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bmp
    extends InjectablesRegistry {

  /** The ColorValidator instance to be used by this object. */
  protected
  lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The BitmapValidator instance to be used by this object. */
  protected
  lazy val bitmapValidator: BitmapValidator = {
    injectable(InjectablesRegistry.IIdBitmapValidator).asInstanceOf[BitmapValidator]
  }

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  def apply(elements: ImageElement*): Bmp =
    RenderingController.createBitmapFrom(elements: _*)

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

    apply(newIdentity, Pos.Origo, buffer)
  }

  /**
   *
   *
   * @param buffer
   *
   * @return
   */
  def apply(buffer: BitmapBufferAdapter): Bmp = {
    val newIdentity: Identity = Identity()

    apply(newIdentity, Pos.Origo, Some(buffer))
  }

  /**
   *
   *
   * @param identity
   * @param position
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
      return new Bmp(identity, false, Dims.Zeros, position, None)
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
        with Displayable {

  /** */
  override
  val boundary: Bounds =
    if (isRenderable)
      Bounds(
        position,
        Pos(
          position.xInPixels + width.inPixels - 1,
          position.yInPixels + height.inPixels - 1
        )
      )
    else
      Bounds.NotDefined

  /**
   *
   *
   * @return
   */
  @inline
  override
  def isBitmap: Boolean = true

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toBitmap: Bmp = this

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
    if (buffer.isEmpty)
      return "Error: No BitmapBufferAdapter to save."

    buffer.get.saveAsPngTo(filename)
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

  /**
   * Transforms the content of this [[Bmp]] using the specified affine
   * transformation. The position of this [[Bmp]] remains unchanged.
   *
   * @param t
   *
   * @return
   */
  def transformContentWith(t: AffineTransformation): Bmp = {
    if (buffer.isEmpty)
      return this

    val newBuffer = buffer.get.createTransformedVersionWith(
      transformation = t,
      resizeCanvasBasedOnTransformation = false)._1

    new Bmp(
      identity = identity,
      isRenderable = isRenderable,
      dimensions = Dims(newBuffer.widthInPixels, newBuffer.heightInPixels),
      position = position,
      buffer = Some(newBuffer))
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCWAroundOrigo: Bmp = {
    if (buffer.isEmpty)
      return this

    // TODO

    val (newBuffer, upperLeftOffsets) =
      buffer.get.createTransformedVersionWith(
        transformation = AffineTransformation.forOrigoCentredRotationOf90DegsCW,
        resizeCanvasBasedOnTransformation = true)

    //BoundaryCalculator.fromBoundaries()

    this

  }

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: Bmp = rotateBy90DegsCW(position)

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Bmp = {
    if (buffer.isEmpty)
      return this

    this  // TODO
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCWAroundOrigo: Bmp = {
    if (buffer.isEmpty)
      return this

    this  // TODO
  }

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: Bmp = rotateBy90DegsCCW(position)

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Bmp = {
    if (buffer.isEmpty)
      return this

    this  // TODO
  }

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180DegsAroundOrigo: Bmp = {
    if (buffer.isEmpty)
      return this

    this  // TODO
  }

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: Bmp = rotateBy180Degs(position)

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs(centerOfRotation: Pos): Bmp = {
    if (buffer.isEmpty)
      return this

    this  // TODO
  }

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Bmp = {
    if (buffer.isEmpty)
      return this

    this  // TODO
  }

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateBy(angleInDegrees: Double): Bmp =
    rotateBy(angleInDegrees, position)

  /**
   * Rotates this object around a given point by the specified number of degrees.
   *
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Bmp = {

    if (buffer.isEmpty)
      return this

    val (newBuffer, _) =
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
   * @param upperLeftX
   * @param upperLeftY
   * @param lowerRightX
   * @param lowerRightY
   *
   * @return
   */
  override
  def crop(
      upperLeftX: Double,
      upperLeftY: Double,
      lowerRightX: Double,
      lowerRightY: Double): Bmp = {

    println("crop... to be implemented")
    //Pic(self.crop(topLeft.xInt, topLeft.yInt, (topLeft.x + width - 1).round.toInt, (topLeft.y + height - 1).round.toInt), Center) // XXX pwa // XXX anchor

    /*
    if (self.dimensions == background.dimensions)
      self
    else {
      val (cropX, cropY) = (from.x.floor.toInt, from.y.floor.toInt) // XXX pyöristys vai floor?
      val bgWithCroppedPicOnTop = self.crop(cropX, cropY, cropX + background.width - 1, cropY + background.height - 1)  // XXX -1 ei kiva
      PicWithAnchor(bgWithCroppedPicOnTop, background.anchor)    // XXX pwa
    }
    */

    this
  }

  /**
   *
   *
   * @param widthFactor
   * @param heightFactor
   *
   * @return
   */
  override
  def scaleBy(widthFactor: Double, heightFactor: Double): Bmp = {
    this
  }

}
