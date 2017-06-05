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
import aalto.smcl.bitmaps.operations._
import aalto.smcl.colors.ColorValidator
import aalto.smcl.colors.rgb.Color
import aalto.smcl.infrastructure.{Identity, InjectablesRegistry, PRF}
import aalto.smcl.settings.{DefaultBackgroundColor, DefaultBitmapHeightInPixels, DefaultBitmapWidthInPixels, NewBitmapsAreDisplayedAutomatically, UpdateViewerPerDefaults, ViewerUpdateStyle}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
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

    displayIfNeeded(loadNewBitmap(
      sourceResourcePath,
      viewerHandling))(viewerHandling)
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
  def loadNewBitmap(
      sourceResourcePath: String,
      viewerHandling: ViewerUpdateStyle): BitmapType = {

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
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      initialBackgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): BitmapType = {

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

    displayIfNeeded(createEmptyBitmap(
      widthInPixels,
      heightInPixels,
      initialBackgroundColor,
      processor
    ))(viewerHandling)
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param initialBackgroundColor
   * @param processor
   *
   * @return
   */
  protected
  def createEmptyBitmap(widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      initialBackgroundColor: Color = DefaultBackgroundColor,
      processor: Option[(BitmapType) => BitmapType]): BitmapType = {

    bitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    if (bitmapValidator.warningSizeLimitsAreExceeded(widthInPixels, heightInPixels)) {
      println("\n\nWarning: The image is larger than the recommended maximum size")
    }

    require(initialBackgroundColor != null,
      "The background color argument has to be a Color instance (was null).")

    val operationList =
      Clear(initialBackgroundColor) +:
          BitmapOperationList(CreateBitmap(widthInPixels, heightInPixels))

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
   * @param bitmap
   * @param viewerHandling
   *
   * @return
   */
  protected
  def displayIfNeeded(
      bitmap: BitmapType)(
      implicit viewerHandling: ViewerUpdateStyle): BitmapType = {

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (NewBitmapsAreDisplayedAutomatically)
        bitmap.display()
    }

    bitmap
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
