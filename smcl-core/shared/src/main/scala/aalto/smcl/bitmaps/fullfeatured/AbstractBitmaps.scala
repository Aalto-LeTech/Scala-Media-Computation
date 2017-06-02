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


import scala.util.{Failure, Success}

import aalto.smcl.bitmaps.BitmapValidator
import aalto.smcl.bitmaps.operations._
import aalto.smcl.colors.ColorValidator
import aalto.smcl.infrastructure._
import aalto.smcl.settings.{NewBitmapsAreDisplayedAutomatically, UpdateViewerPerDefaults, ViewerUpdateStyle}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object AbstractBitmaps extends InjectablesRegistry {

  /** The ColorValidator instance to be used by this object. */
  private lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The BitmapValidator instance to be used by this object. */
  private lazy val bitmapValidator: BitmapValidator = {
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
  def apply(
      sourceResourcePath: String,
      viewerHandling: ViewerUpdateStyle): Seq[AbstractBitmap] = {

    // The ImageProvider is trusted with validation of the source resource path.
    val loadedBuffersTry = PRF.tryToLoadImagesFromPath(sourceResourcePath)
    if (loadedBuffersTry.isFailure)
      throw loadedBuffersTry.failed.get

    loadedBuffersTry.get.zipWithIndex map {
      case (Failure(throwable), _) => throw throwable

      case (Success(buffer), index) =>
        val operationList = BitmapOperationList(
          LoadedBitmap(buffer, Option(sourceResourcePath), Option(index)))
        val newBitmap = new Bitmap(
          operationList, bitmapValidator, colorValidator, Identity())

        if (viewerHandling == UpdateViewerPerDefaults) {
          if (NewBitmapsAreDisplayedAutomatically)
            newBitmap.display()
        }

        newBitmap
    }
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def apply(sourceResourcePath: String): Seq[AbstractBitmap] = {
    apply(sourceResourcePath, UpdateViewerPerDefaults)
  }

}
