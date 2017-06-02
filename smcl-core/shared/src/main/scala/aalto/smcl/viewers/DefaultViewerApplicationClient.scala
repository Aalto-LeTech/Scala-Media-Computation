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

package aalto.smcl.viewers


import aalto.smcl.bitmaps.fullfeatured.Bitmap
import aalto.smcl.infrastructure.Displayable
import aalto.smcl.infrastructure.exceptions.{ImplementationNotSetError, UnknownMediaTypeError}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object DefaultViewerApplicationClient extends ViewerApplicationClient {

  /** Bitmap viewer to be used. */
  private var _bitmapViewerApplication: Option[BitmapViewerApplication] = None

  /**
   *
   *
   * @param resource
   */
  override def display(resource: Displayable): Unit = {
    resource match {
      case bmp: Bitmap => bitmapViewerApplication.display(bmp)
      case _           => throw UnknownMediaTypeError
    }
  }

  /**
   *
   */
  def closeAllViewersWithoutSaving(): Unit = {
    bitmapViewerApplication.closeAllViewersWithoutSaving()
  }

  /**
   *
   *
   * @param viewer
   */
  def setBitmapViewer(viewer: BitmapViewerApplication): Unit =
    _bitmapViewerApplication = Some(viewer)

  /**
   *
   *
   * @return
   */
  private def bitmapViewerApplication: BitmapViewerApplication =
    _bitmapViewerApplication.getOrElse(throw ImplementationNotSetError("DefaultViewerClient"))

}
