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

package smcl.infrastructure.jvmawt


import scala.swing.Dialog
import scala.swing.Dialog.{Message, Options}

import smcl.infrastructure.exceptions.UnexpectedInternalError




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class SwingUtils() {

  /**
   *
   *
   * @return
   */
  def yesNoDialogResultAsBoolean(result: Dialog.Result.Value): Boolean = result match {
    case Dialog.Result.Yes    => true
    case Dialog.Result.No     => false
    case Dialog.Result.Closed => false
    case _                    => throw UnexpectedInternalError("Invalid dialog return value.")
  }

  /**
   *
   *
   * @return
   */
  val showParentlessYesNoQuestionDialog: (String, String) => Dialog.Result.Value =
    Dialog.showConfirmation(parent = null, _: String, _: String, Options.YesNo, Message.Question)

}
