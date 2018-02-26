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

package smcl.viewers


import smcl.infrastructure.Displayable
import smcl.infrastructure.exceptions.{ImplementationNotSetError, UnknownMediaTypeError}
import smcl.pictures
import smcl.pictures.fullfeatured
import smcl.pictures.fullfeatured.Bitmap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object DefaultViewerApplicationClient extends ViewerApplicationClient {

  /** Bitmap viewer to be used. */
  private
  var _bitmapViewerApplication: Option[BitmapViewerApplication] = None

  /**
   *
   *
   * @param resource
   */
  override def display(resource: Displayable): Unit = {
    resource match {
      case bmp: fullfeatured.Bitmap =>
        bitmapViewerApplication.display(bmp)

      case bmp: pictures.Bitmap =>
        if (bmp.buffer.isDefined)
          bitmapViewerApplication.display(Bitmap(bmp.buffer.get))
        else
          println("No BitmapBufferAdapter to display.")

      case _ =>
        throw UnknownMediaTypeError
    }
  }

  /**
   *
   */
  def closeAllViewersWithoutSaving(): Unit =
    bitmapViewerApplication.closeAllViewersWithoutSaving()

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
    _bitmapViewerApplication.getOrElse(
      throw ImplementationNotSetError("DefaultViewerClient"))

}
