package aalto.smcl.bitmaps.viewer


import javax.swing.KeyStroke

import scala.swing.Action
import scala.swing.event.Key




private[viewer] object ViewerAction {

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
private[viewer] class ViewerAction(
    override val title: String,
    private val mnemonicOption: Option[Key.Value] = None,
    private val acceleratorOption: Option[(Key.Value, Key.Modifiers)] = None,
    applyAction: => Unit)
    extends Action(title) {

  mnemonic = mnemonicOption.fold(0) {_.id}
  accelerator = acceleratorOption.fold[Option[KeyStroke]](None) {
    case (k, mod) => Option(KeyStroke.getKeyStroke(k.id, mod))
  }

  /**
   *
   */
  override def apply(): Unit = applyAction

}
