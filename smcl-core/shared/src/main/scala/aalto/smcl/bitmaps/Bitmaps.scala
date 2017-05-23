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

package aalto.smcl.bitmaps


import scala.util.{Failure, Success}

import aalto.smcl.bitmaps.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.bitmaps.operations._
import aalto.smcl.colors.ColorValidator
import aalto.smcl.infrastructure._
import aalto.smcl.viewers.{display => displayInViewer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmaps extends InjectableRegistry {

  /** The ColorValidator instance to be used by this object. */
  private lazy val colorValidator: ColorValidator = {
    injectable(InjectableRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The BitmapValidator instance to be used by this object. */
  private lazy val bitmapValidator: BitmapValidator = {
    injectable(InjectableRegistry.IIdBitmapValidator).asInstanceOf[BitmapValidator]
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
      viewerHandling: ViewerUpdateStyle.Value): Seq[Bitmap] = {

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
          if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
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
  def apply(sourceResourcePath: String): Seq[Bitmap] = {
    apply(sourceResourcePath, UpdateViewerPerDefaults)
  }

}
