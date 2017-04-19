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


import javax.swing.KeyStroke

import scala.swing.Action
import scala.swing.event.Key




private[jvmawt]
object ViewerAction {

  def apply(title: String,
      mnemonicOption: Option[Key.Value] = None,
      acceleratorOption: Option[(Key.Value, Key.Modifiers)] = None)
      (applyAction: => Unit): ViewerAction = {

    new ViewerAction(title, mnemonicOption, acceleratorOption, applyAction)
  }

}




/**
 *
 *
 * @param title
 * @param mnemonicOption
 * @param acceleratorOption
 * @param applyAction
 */
private[jvmawt]
class ViewerAction(
    override val title: String,
    private val mnemonicOption: Option[Key.Value] = None,
    private val acceleratorOption: Option[(Key.Value, Key.Modifiers)] = None,
    applyAction: => Unit)
    extends Action(title) {


  mnemonic = mnemonicOption.fold(0){
    _.id
  }
  accelerator = acceleratorOption.fold[Option[KeyStroke]](None){
    case (k, mod) => Option(KeyStroke.getKeyStroke(k.id, mod))
  }

  /**
   *
   */
  override def apply(): Unit = applyAction

}
