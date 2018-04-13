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
import smcl.colors.rgb.{Color, ColorComponentTranslationTable}
import smcl.infrastructure.{BitmapBufferAdapter, Displayable, Identity, InjectablesRegistry, PRF}
import smcl.modeling.d2._
import smcl.modeling.{AffineTransformation, Angle, Len}
import smcl.pictures.filters._
import smcl.settings
import smcl.settings.DefaultBackgroundColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmap
    extends InjectablesRegistry {

  /** */
  type LocationToColorGenerator = (Int, Int) => Color

  /** */
  val BackgroundColorGenerator: LocationToColorGenerator = (_, _) => DefaultBackgroundColor

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
  def apply(elements: PictureElement*): Bitmap =
    RenderingController.createBitmapFrom(elements: _*)

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param contentGenerator
   *
   * @return
   */
  def apply(
      widthInPixels: Int,
      heightInPixels: Int,
      contentGenerator: LocationToColorGenerator): Bitmap = {

    apply(widthInPixels, heightInPixels).setColorsByLocation(contentGenerator)
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
      heightInPixels: Int): Bitmap = {

    apply(
      Len(widthInPixels),
      Len(heightInPixels))
  }

  /**
   *
   *
   * @param width
   * @param height
   * @param contentGenerator
   *
   * @return
   */
  def apply(
      width: Len,
      height: Len,
      contentGenerator: LocationToColorGenerator): Bitmap = {

    apply(width, height).setColorsByLocation(contentGenerator)
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
      height: Len): Bitmap = {

    bitmapValidator.validateBitmapSize(width, height)

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
   * @param sourceResourcePath
   *
   * @return
   */
  def apply(sourceResourcePath: String): Bitmap = {
    // The ImageProvider is trusted with validation of the source resource path.
    val loadedBufferTry = PRF.tryToLoadImageFromPath(sourceResourcePath)
    if (loadedBufferTry.isFailure)
      throw loadedBufferTry.failed.get

    apply(loadedBufferTry.get)
  }

  /**
   *
   *
   * @param buffer
   *
   * @return
   */
  def apply(buffer: BitmapBufferAdapter): Bitmap = {
    val newIdentity: Identity = Identity()

    apply(newIdentity, Pos.Origo, Option(buffer))
  }

  /**
   *
   *
   * @param center
   * @param buffer
   *
   * @return
   */
  private
  def apply(
      center: Pos,
      buffer: Option[BitmapBufferAdapter]): Bitmap = {

    val newIdentity: Identity = Identity()

    apply(newIdentity, center, buffer)
  }

  /**
   *
   *
   * @param identity
   * @param center
   * @param buffer
   *
   * @return
   */
  private
  def apply(
      identity: Identity,
      center: Pos,
      buffer: Option[BitmapBufferAdapter]): Bitmap = {

    val isRenderable =
      buffer.isDefined &&
          buffer.get.widthInPixels > 0 &&
          buffer.get.heightInPixels > 0

    val boundary: Bounds =
      if (!isRenderable)
        Bounds.NotDefined
      else
        Bounds.apply(
          center,
          buffer.get.widthInPixels,
          buffer.get.heightInPixels)

    val contentCorners =
      if (!isRenderable)
        BitmapContentCorners.NotDefined
      else
        BitmapContentCorners(boundary)

    new Bitmap(
      identity,
      isRenderable,
      boundary,
      contentCorners,
      buffer)
  }

}




/**
 *
 *
 * @param identity
 * @param isRenderable
 * @param boundary
 * @param buffer
 *
 * @author Aleksi Lukkarinen
 */
class Bitmap private(
    override val identity: Identity,
    val isRenderable: Boolean,
    override val boundary: Bounds,
    val contentCorners: BitmapContentCorners,
    private[smcl] val buffer: Option[BitmapBufferAdapter])
    extends PictureElement
        with Displayable {

  /**
   *
   *
   * @return
   */
  override
  def isBitmap: Boolean = true

  /**
   *
   *
   * @return
   */
  override
  def toBitmap: Bitmap = this

  /**
   *
   *
   * @return
   */
  override
  def toBitmapCopy: Bitmap = {
    val newBuffer = buffer.map(_.copy).orNull

    internalCopy(newBuffer = Option(newBuffer))
  }

  /**
   *
   *
   * @param another
   * @param pixelMerger
   *
   * @return
   */
  override
  def mergePixelsWith(
      another: PictureElement,
      pixelMerger: (Color, Color) => Color): Bitmap = {

    val mergedSnapshot = toPixelSnapshot.mergeWith(
      another.toPixelSnapshot,
      pixelMerger)

    mergedSnapshot.toBitmap
  }

  /**
   *
   *
   * @param translator
   *
   * @return
   */
  def translateColorsWith(translator: ColorComponentTranslationTable): Bitmap =
    toProvideModifiedCopyOfOldBuffer{oldBuffer =>
      oldBuffer.createFilteredVersionWith(translator)
    }

  /**
   *
   *
   * @param translator
   *
   * @return
   */
  private[pictures]
  def translateColorsWith(translator: (Int, Int, Int, Int) => (Int, Int, Int, Int)): Bitmap =
    toProvideModifiedCopyOfOldBuffer{oldBuffer =>
      val newBuffer = oldBuffer.copy

      val (reds, greens, blues, opacities) = newBuffer.colorComponentArrays

      for (i <- reds.indices) {
        val (newRed, newGreen, newBlue, newOpacity) =
          translator(reds(i), greens(i), blues(i), opacities(i))

        reds(i) = newRed
        greens(i) = newGreen
        blues(i) = newBlue
        opacities(i) = newOpacity
      }

      newBuffer.setColorComponentArrays(reds, greens, blues, opacities)
      newBuffer
    }

  /**
   *
   *
   * @param newCopyProvider
   *
   * @return
   */
  private
  def toProvideModifiedCopyOfOldBuffer(
      newCopyProvider: BitmapBufferAdapter => BitmapBufferAdapter): Bitmap = {

    if (buffer.isEmpty)
      return this

    val oldBuffer = buffer.get
    if (oldBuffer.widthInPixels <= 0 || oldBuffer.heightInPixels <= 0)
      return this

    val newBuffer = newCopyProvider(oldBuffer)

    Bitmap(identity, position, Some(newBuffer))
  }

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
      yInPixels: Double): Option[Color] = {

    buffer.map(_.colorAt(xInPixels, yInPixels))
  }

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def applySimpleFilter(f: Filter): Bitmap = f(this).toBitmap

  /**
   *
   *
   * @return
   */
  def keepOnlyRedComponent: Bitmap =
    applySimpleFilter(KeepOnlyRedComponent)

  /**
   *
   *
   * @return
   */
  def keepOnlyRedAndGreenComponents: Bitmap =
    applySimpleFilter(KeepOnlyRedAndGreenComponents)

  /**
   *
   *
   * @return
   */
  def keepOnlyRedAndBlueComponents: Bitmap =
    applySimpleFilter(KeepOnlyRedAndBlueComponents)

  /**
   *
   *
   * @return
   */
  def keepOnlyGreenComponent: Bitmap =
    applySimpleFilter(KeepOnlyGreenComponent)

  /**
   *
   *
   * @return
   */
  def keepOnlyGreenAndBlueComponents: Bitmap =
    applySimpleFilter(KeepOnlyGreenAndBlueComponents)

  /**
   *
   *
   * @return
   */
  def keepOnlyBlueComponent: Bitmap =
    applySimpleFilter(KeepOnlyBlueComponent)

  /**
   *
   *
   * @return
   */
  def negate: Bitmap =
    applySimpleFilter(Negate)

  /**
   *
   *
   * @return
   */
  def negateRedComponent: Bitmap =
    applySimpleFilter(NegateRedComponent)

  /**
   *
   *
   * @return
   */
  def negateRedAndGreenComponents: Bitmap =
    applySimpleFilter(NegateRedAndGreenComponents)

  /**
   *
   *
   * @return
   */
  def negateRedAndBlueComponents: Bitmap =
    applySimpleFilter(NegateRedAndBlueComponents)

  /**
   *
   *
   * @return
   */
  def negateGreenComponent: Bitmap =
    applySimpleFilter(NegateGreenComponent)

  /**
   *
   *
   * @return
   */
  def negateGreenAndBlueComponents: Bitmap =
    applySimpleFilter(NegateGreenAndBlueComponents)

  /**
   *
   *
   * @return
   */
  def negateBlueComponent: Bitmap =
    applySimpleFilter(NegateBlueComponent)

  /**
   *
   *
   * @return
   */
  def toGrayscaleByLightness: Bitmap =
    applySimpleFilter(ToGrayscaleByLightness)

  /**
   *
   *
   * @return
   */
  def toGrayscaleByLuminocity: Bitmap =
    applySimpleFilter(ToGrayscaleByLuminocity)

  /**
   *
   *
   * @param redWeight
   * @param greenWeight
   * @param blueWeight
   *
   * @return
   */
  def toGrayscale(
      redWeight: Double,
      greenWeight: Double,
      blueWeight: Double): Bitmap = {

    val filter = ToWeightedGrayscale(redWeight, greenWeight, blueWeight)

    applySimpleFilter(filter)
  }

  /**
   *
   *
   * @param strengthAsPercentage
   *
   * @return
   */
  def posterize(strengthAsPercentage: Int): Bitmap =
    applySimpleFilter(Posterize(strengthAsPercentage))

  /**
   *
   *
   * @param generator
   *
   * @return
   */
  def setColorsByLocation(generator: (Int, Int) => Color): Bitmap =
    withPixelSnapshot(_.setColorsByLocation(generator))

  /**
   *
   *
   * @param transformers
   *
   * @return
   */
  def transformColorToColor(transformers: Seq[Color => Color]): Bitmap =
    withPixelSnapshot(_.transformColorToColor(transformers))

  /**
   *
   *
   * @param transformer
   *
   * @return
   */
  def transformColorToColor(transformer: Color => Color): Bitmap =
    withPixelSnapshot(_.transformColorToColor(transformer))

  /**
   *
   *
   * @param transformers
   *
   * @return
   */
  def transformLocationColorToColor(transformers: Seq[(Int, Int, Color) => Color]): Bitmap =
    withPixelSnapshot(_.transformLocationColorToColor(transformers))

  /**
   *
   *
   * @param transformer
   *
   * @return
   */
  def transformLocationColorToColor(transformer: (Int, Int, Color) => Color): Bitmap =
    withPixelSnapshot(_.transformLocationColorToColor(transformer))

  /**
   *
   *
   * @param transformers
   *
   * @return
   */
  def iteratePixels(transformers: Seq[Pixel => Unit]): Bitmap =
    withPixelSnapshot(_.iteratePixels(transformers))

  /**
   *
   *
   * @param transformer
   *
   * @return
   */
  def iteratePixels(transformer: Pixel => Unit): Bitmap =
    withPixelSnapshot(_.iteratePixels(transformer))

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def withPixelSnapshot(f: PixelSnapshot => Unit): Bitmap = {
    val snapshot = PixelSnapshot(toBitmapCopy)
    f(snapshot)
    snapshot.toBitmap
  }

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  override
  def moveBy(offsetsInPixels: Seq[Double]): Bitmap =
    internalBufferPreservingCopy(
      newBoundary = boundary.moveBy(offsetsInPixels),
      newContentCorners = contentCorners.moveBy(offsetsInPixels))

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   *
   * @return
   */
  override
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): Bitmap = {

    internalBufferPreservingCopy(
      newBoundary = boundary.moveBy(xOffsetInPixels, yOffsetInPixels),
      newContentCorners = contentCorners.moveBy(xOffsetInPixels, yOffsetInPixels))
  }


  /**
   *
   *
   * @param position
   * @param positionType
   *
   * @return
   */
  override
  def moveTo(
      position: Pos,
      positionType: settings.PositionType): Bitmap = {

    super.moveTo(position, positionType).asInstanceOf[Bitmap]
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   * @param positionType
   *
   * @return
   */
  override
  def moveTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double,
      positionType: settings.PositionType): Bitmap = {

    super.moveTo(xCoordinateInPixels, yCoordinateInPixels, positionType)
        .asInstanceOf[Bitmap]
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  override
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): Bitmap = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    moveBy(
      coordinatesInPixels.head - boundary.upperLeftCorner.xInPixels,
      coordinatesInPixels.tail.head - boundary.upperLeftCorner.yInPixels)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  override
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): Bitmap = {

    moveBy(
      xCoordinateInPixels - boundary.upperLeftCorner.xInPixels,
      yCoordinateInPixels - boundary.upperLeftCorner.yInPixels)
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  override
  def moveCenterTo(coordinatesInPixels: Seq[Double]): Bitmap = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    moveBy(
      coordinatesInPixels.head - boundary.center.xInPixels,
      coordinatesInPixels.tail.head - boundary.center.yInPixels)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  override
  def moveCenterTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): Bitmap = {

    moveBy(
      xCoordinateInPixels - boundary.center.xInPixels,
      yCoordinateInPixels - boundary.center.yInPixels)
  }

  /**
   *
   *
   * @param filename
   *
   * @return
   */
  def saveAsPngTo(filename: String): String =
    buffer.fold("Error: No BitmapBufferAdapter to save.")(_.saveAsPngTo(filename))

  /**
   * Creates a copy of this bitmap with given arguments.
   *
   * This is an unsafe method, as it can be used to create [[Bitmap]] instances,
   * whose internal state is incoherent. As such, it is not for public use.
   *
   * @param newIdentity
   * @param newIsRenderable
   * @param newBoundary
   * @param newContentCorners
   * @param newBuffer
   *
   * @return
   */
  private
  def internalCopy(
      newIdentity: Identity = identity,
      newIsRenderable: Boolean = isRenderable,
      newBoundary: Bounds = boundary,
      newContentCorners: BitmapContentCorners = contentCorners,
      newBuffer: Option[BitmapBufferAdapter] = buffer): Bitmap = {

    new Bitmap(
      newIdentity,
      newIsRenderable,
      newBoundary,
      newContentCorners,
      newBuffer) // e.g., buffer.map(_.copy) to make a new copy of the internal buffer
  }

  /**
   * Creates a copy of this bitmap with given arguments.
   *
   * This is an unsafe method, as it can be used to create [[Bitmap]] instances,
   * whose internal state is incoherent. As such, it is not for public use.
   *
   * @param newIdentity
   * @param newIsRenderable
   * @param newBoundary
   * @param newContentCorners
   *
   * @return
   */
  private
  def internalBufferPreservingCopy(
      newIdentity: Identity = identity,
      newIsRenderable: Boolean = isRenderable,
      newBoundary: Bounds = boundary,
      newContentCorners: BitmapContentCorners = contentCorners): Bitmap = {

    internalCopy(
      newIdentity,
      newIsRenderable,
      newBoundary,
      newContentCorners,
      buffer)
  }

  /**
   *
   */
  override
  def display(): Bitmap = {
    super.display()

    this
  }

/*
  /**
   * Transforms the content of this [[Bitmap]] using the specified affine
   * transformation. The center of this [[Bitmap]] remains unchanged.
   *
   * @param t
   *
   * @return
   */
  private
  def transformContentWith(t: AffineTransformation): Bitmap = {
    if (buffer.isEmpty)
      return this

    val newBuffer = buffer.get.createTransformedVersionWith(
      transformation = t,
      resizeCanvasBasedOnTransformation = false)._1

    new Bitmap(
      identity = identity,
      isRenderable = isRenderable,
      boundary = boundary,
      buffer = Some(newBuffer))
  }
*/

  /**
   * An internal method to transform the content of this
   * bitmap using a given [[AffineTransformation]].
   *
   * @param transformation
   *
   * @return
   */
  @inline
  private final
  def transformContentUsing(transformation: AffineTransformation): BitmapBufferAdapter = {
    val (newBuffer, _) =
      buffer.get.createTransformedVersionWith(
        transformation = transformation,
        resizeCanvasBasedOnTransformation = true)

    newBuffer
  }

  /**
   * Rotates this bitmap around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCWAroundOrigo: Bitmap = {
    if (!isRenderable)
      return this

    val newBuffer = transformContentUsing(
      AffineTransformation.forPointCentredRotationOf90DegsCW(
        width.half.inPixels, height.half.inPixels))

    val newBounds = boundary.rotateBy90DegsCWAroundOrigo
    val newContentCorners = contentCorners.rotateBy90DegsCWAroundOrigo

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   * Rotates this bitmap around its center by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCW: Bitmap = rotateBy90DegsCW(position)

  /**
   * Rotates this bitmap around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Bitmap = {
    if (!isRenderable)
      return this

    val newBuffer = transformContentUsing(
      AffineTransformation.forPointCentredRotationOf90DegsCW(
        width.half.inPixels, height.half.inPixels))

    val newBounds = boundary.rotateBy90DegsCW(position)
    val newContentCorners = contentCorners.rotateBy90DegsCW(position)

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   * Rotates this bitmap around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCWAroundOrigo: Bitmap = {
    if (!isRenderable)
      return this

    val newBuffer = transformContentUsing(
      AffineTransformation.forPointCentredRotationOf90DegsCCW(
        width.half.inPixels, height.half.inPixels))

    val newBounds = boundary.rotateBy90DegsCCWAroundOrigo
    val newContentCorners = contentCorners.rotateBy90DegsCCWAroundOrigo

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   * Rotates this bitmap around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCW: Bitmap = rotateBy90DegsCCW(position)

  /**
   * Rotates this bitmap around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Bitmap = {
    if (!isRenderable)
      return this

    val newBuffer = transformContentUsing(
      AffineTransformation.forPointCentredRotationOf90DegsCCW(
        width.half.inPixels, height.half.inPixels))

    val newBounds = boundary.rotateBy90DegsCCW(position)
    val newContentCorners = contentCorners.rotateBy90DegsCCW(position)

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   * Rotates this bitmap around origo (0,0) by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180DegsAroundOrigo: Bitmap = {
    if (!isRenderable)
      return this

    val newBuffer = transformContentUsing(
      AffineTransformation.forPointCentredRotationOf180Degs(
        width.half.inPixels, height.half.inPixels))

    val newBounds = boundary.rotateBy180DegsAroundOrigo
    val newContentCorners = contentCorners.rotateBy180DegsAroundOrigo

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   * Rotates this bitmap around its center by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180Degs: Bitmap = rotateBy180Degs(position)

  /**
   * Rotates this bitmap around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy180Degs(centerOfRotation: Pos): Bitmap = {
    if (!isRenderable)
      return this

    val newBuffer = transformContentUsing(
      AffineTransformation.forPointCentredRotationOf180Degs(
        width.half.inPixels, height.half.inPixels))

    val newBounds = boundary.rotateBy180Degs(position)
    val newContentCorners = contentCorners.rotateBy180Degs(position)

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   * Rotates this bitmap around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angle: Angle): Bitmap = rotateByAroundOrigo(angle)

  /**
   * Rotates this bitmap around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Bitmap = {
    if (!isRenderable)
      return this

    val newBuffer = transformContentUsing(
      AffineTransformation.forPointCentredRotation(
        angleInDegrees,
        width.half.inPixels, height.half.inPixels))

    val newCenter = boundary.center.rotateByAroundOrigo(angleInDegrees)
    val newUpperLeftCorner = newCenter - (newBuffer.widthInPixels / 2.0, newBuffer.heightInPixels / 2.0)
    val newLowerRightCorner = newUpperLeftCorner + (newBuffer.widthInPixels - 1, newBuffer.heightInPixels - 1)

    val newBounds = Bounds(newUpperLeftCorner, newLowerRightCorner)
    val newContentCorners = contentCorners.rotateByAroundOrigo(angleInDegrees)

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   * Rotates this bitmap around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateBy(angle: Angle): Bitmap = rotateBy(angle)

  /**
   * Rotates this bitmap around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateBy(angleInDegrees: Double): Bitmap =
    rotateBy(angleInDegrees, position)

  /**
   * Rotates this bitmap around a given point by the specified angle.
   *
   * @param angle
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy(
      angle: Angle,
      centerOfRotation: Pos): Bitmap = {

    rotateBy(angle, centerOfRotation)
  }

  /**
   * Rotates this bitmap around a given point by the specified number of degrees.
   *
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Bitmap = {

    if (!isRenderable)
      return this

    // TODO in all rotation methods: Check params, e.g., Pos has to be defined, Angle mustn't be null

    val newBuffer = transformContentUsing(
      AffineTransformation.forPointCentredRotation(
        angleInDegrees,
        width.half.inPixels, height.half.inPixels))

    val newCenter = boundary.center.rotateByAroundOrigo(angleInDegrees)
    val newUpperLeftCorner = newCenter - (newBuffer.widthInPixels / 2.0, newBuffer.heightInPixels / 2.0)
    val newLowerRightCorner = newUpperLeftCorner + (newBuffer.widthInPixels - 1, newBuffer.heightInPixels - 1)

    val newBounds = Bounds(newUpperLeftCorner, newLowerRightCorner)
    val newContentCorners = contentCorners.rotateByAroundOrigo(angleInDegrees)

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   * Scales this bitmap in relation to its center.
   *
   * @param widthFactor
   * @param heightFactor
   *
   * @return
   */
  override
  def scaleBy(
      widthFactor: Double,
      heightFactor: Double): Bitmap = {

    if (!isRenderable)
      return this

    require(widthFactor > 0, s"The scaling factors must be larger than zero (was $widthFactor)")
    require(heightFactor > 0, s"The scaling factors must be larger than zero (was $heightFactor)")

    val newBuffer = transformContentUsing(
      AffineTransformation.forOrigoRelativeScalingOf(
        widthFactor, heightFactor))

    val newUpperLeftCorner =
      boundary.center - (newBuffer.widthInPixels / 2.0, newBuffer.heightInPixels / 2.0)

    val newLowerRightCorner =
      newUpperLeftCorner + (newBuffer.widthInPixels - 1, newBuffer.heightInPixels - 1)

    val newBounds = Bounds(newUpperLeftCorner, newLowerRightCorner)
    val newContentCorners = contentCorners // TODO: FIX: contentCorners.scaleBy(widthFactor, heightFactor)

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   *
   *
   * @param upperLeftXInPixels
   * @param upperLeftYInPixels
   * @param lowerRightXInPixels
   * @param lowerRightYInPixels
   *
   * @return
   */
  override
  def crop(
      upperLeftXInPixels: Double,
      upperLeftYInPixels: Double,
      lowerRightXInPixels: Double,
      lowerRightYInPixels: Double): Bitmap = {

    if (buffer.isEmpty)
      return this

    // TODO: Check parameters: Have to be inside the bitmap

    val newBuffer = buffer.get.copyPortionXYXY(
      upperLeftXInPixels, upperLeftYInPixels,
      lowerRightXInPixels, lowerRightYInPixels)

    val newUpperLeftCorner = Pos.Origo

    val newLowerRightCorner =
      newUpperLeftCorner + (newBuffer.widthInPixels - 1, newBuffer.heightInPixels - 1)

    val newBounds = Bounds(newUpperLeftCorner, newLowerRightCorner)
    val newContentCorners = BitmapContentCorners(newBounds)

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  override
  def crop(
      upperLeftCorner: Pos,
      widthInPixels: Double,
      heightInPixels: Double): Bitmap = {

    if (buffer.isEmpty)
      return this

    // TODO: Check parameters: Have to be inside the bitmap

    val newBuffer = buffer.get.copyPortionXYWH(
      upperLeftCorner.xInPixels, upperLeftCorner.yInPixels,
      widthInPixels, heightInPixels)

    val newUpperLeftCorner = Pos.Origo
    val newLowerRightCorner =
      newUpperLeftCorner + (newBuffer.widthInPixels - 1, newBuffer.heightInPixels - 1)

    val newBounds = Bounds(newUpperLeftCorner, newLowerRightCorner)
    val newContentCorners = BitmapContentCorners(newBounds)

    internalCopy(
      identity,
      isRenderable,
      newBounds,
      newContentCorners,
      Option(newBuffer))
  }

}
