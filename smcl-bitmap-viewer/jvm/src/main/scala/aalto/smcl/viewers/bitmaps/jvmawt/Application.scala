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

package aalto.smcl.viewers.bitmaps.jvmawt


import rx.lang.scala.{JavaConversions, Subject}

import aalto.smcl.bitmaps.Bitmap
import aalto.smcl.infrastructure.jvmawt.SwingUtils
import aalto.smcl.viewers.bitmaps.DisplayBitmapEvent
import aalto.smcl.viewers.{BitmapViewerApplication, ExternalViewerEvent, ForceAllViewersToCloseEvent}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[viewers]
class Application extends BitmapViewerApplication {

  private val _swingUtils = new SwingUtils()

  /** */
  private val _outgoingEventSubject = Subject[ExternalViewerEvent]()

  /** */
  private val _observableOutgoingEventSubject =
    JavaConversions.toScalaObservable(_outgoingEventSubject.asJavaObservable)

  /** */
  private val _viewerManager = new ViewerManager(_observableOutgoingEventSubject)


  /**
   *
   *
   * @param sourceBitmap
   */
  def display(sourceBitmap: Bitmap): Unit = {
    require(sourceBitmap != null, "Source bitmap must not be null.")

    dispatchEvent(DisplayBitmapEvent(sourceBitmap))
  }


  /**
   *
   */
  override def closeAllViewersWithoutSaving(): Unit = {
    if (ifUserWantsToForceAllViewersToBeClosed())
      dispatchEvent(ForceAllViewersToCloseEvent())
  }

  /**
   *
   *
   * @return
   */
  def ifUserWantsToForceAllViewersToBeClosed(): Boolean = {
    val dialogResult = _swingUtils.showParentlessYesNoQuestionDialog(
      "Do you really want to close all bitmap viewers without saving?\nALL unsaved bitmaps will be LOST.",
      "SMCL")

    _swingUtils.yesNoDialogResultAsBoolean(dialogResult)
  }

  /**
   *
   *
   */
  def dispatchEvent(event: ExternalViewerEvent): Unit = {
    require(event != null, "Event to be dispatched must not be null.")

    _outgoingEventSubject.onNext(event)
  }

}
