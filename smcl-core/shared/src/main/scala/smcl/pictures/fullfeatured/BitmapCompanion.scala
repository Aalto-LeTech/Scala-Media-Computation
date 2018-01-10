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


import smcl.colors.ColorValidator
import smcl.colors.rgb.Color
import smcl.infrastructure.{BitmapBufferAdapter, Identity, InjectablesRegistry, _}
import smcl.modeling.d2.Dims
import smcl.pictures.BitmapValidator
import smcl.pictures.operations.{BitmapOperationList, LoadedBitmap, _}
import smcl.settings.{DefaultBackgroundColor, ViewerUpdateStyle}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
abstract class BitmapCompanion[BitmapType <: AbstractBitmap]
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
   * @param sourceResourcePath
   * @param viewerHandling
   *
   * @return
   */
  protected
  def apply(
      sourceResourcePath: String,
      viewerHandling: ViewerUpdateStyle): BitmapType = {

    displayAsNewIfNecessary(viewerHandling)(
      loadNewBitmap(sourceResourcePath))
  }

  /**
   *
   *
   * @param bmpBfrAdapter
   *
   * @return
   */
  private[pictures]
  def fromBitmapBufferAdapter(bmpBfrAdapter: BitmapBufferAdapter): BitmapType = {
    val operationList = BitmapOperationList(
      LoadedBitmap(bmpBfrAdapter, None, Option(0)))

    instantiateBitmap(operationList, bitmapValidator, colorValidator, Identity())
  }

  /**
   * HACK HACK HACK HACK --> REMOVE WHEN UNNECESSARY! See Bitmap as well.
   *
   * @param sourceResourcePath
   *
   * @return
   */
  protected
  def loadNewBitmap(sourceResourcePath: String): BitmapType = {
    // The ImageProvider is trusted with validation of the source resource path.
    val loadedBufferTry = PRF.tryToLoadImageFromPath(sourceResourcePath)
    if (loadedBufferTry.isFailure)
      throw loadedBufferTry.failed.get

    val operationList = BitmapOperationList(
      LoadedBitmap(loadedBufferTry.get, Option(sourceResourcePath), Option(0)))

    instantiateBitmap(operationList, bitmapValidator, colorValidator, Identity())
  }

  /**
   * Creates a new empty [[Bitmap]] instance.
   */
  protected
  def apply(
      widthInPixels: Int,
      heightInPixels: Int,
      initialBackgroundColor: Color,
      viewerHandling: ViewerUpdateStyle): BitmapType = {

    apply(
      widthInPixels,
      heightInPixels,
      initialBackgroundColor,
      None,
      viewerHandling)
  }

  /**
   * Creates a new empty [[Bitmap]] instance and applies a processing function to it.
   */
  protected
  def apply(
      widthInPixels: Int,
      heightInPixels: Int,
      initialBackgroundColor: Color,
      processor: Option[(BitmapType) => BitmapType],
      viewerHandling: ViewerUpdateStyle): BitmapType = {

    displayAsNewIfNecessary(viewerHandling)(
      createEmptyBitmap(
        Dims(widthInPixels, heightInPixels),
        initialBackgroundColor,
        processor
      ))
  }

  /**
   *
   *
   * @param dimensions
   * @param initialBackgroundColor
   * @param processor
   *
   * @return
   */
  protected
  def createEmptyBitmap(
      dimensions: Dims,
      initialBackgroundColor: Color = DefaultBackgroundColor,
      processor: Option[(BitmapType) => BitmapType]): BitmapType = {

    bitmapValidator.validateBitmapSize(dimensions.width, dimensions.height)

    if (bitmapValidator.warningSizeLimitsAreExceeded(dimensions.width, dimensions.height)) {
      println("\n\nWarning: The image is larger than the recommended maximum size")
    }

    require(initialBackgroundColor != null,
      "The background color argument has to be a Color instance (was null).")

    val operationList =
      Clear(initialBackgroundColor) +:
          BitmapOperationList(CreateBitmap(
            dimensions.width.inPixels.closestInt, dimensions.height.inPixels.closestInt))

    val bitmap = instantiateBitmap(operationList, bitmapValidator, colorValidator, Identity())

    processor match {
      case null    => bitmap
      case None    => bitmap
      case Some(p) => p(bitmap)
    }
  }

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
  protected
  def instantiateBitmap(
      operations: BitmapOperationList,
      bitmapValidator: BitmapValidator,
      colorValidator: ColorValidator,
      uniqueIdentifier: Identity): BitmapType

}
