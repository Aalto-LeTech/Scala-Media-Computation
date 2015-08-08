package aalto.smcl.common


import scala.swing.Dialog
import scala.swing.Dialog.{Message, Options}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object SwingUtils {

  /**
   *
   *
   * @return
   */
  def yesNoDialogResultAsBoolean(result: Dialog.Result.Value): Boolean = result match {
    case Dialog.Result.Yes => true
    case Dialog.Result.No => false
    case Dialog.Result.Closed => false
    case _ => throw new SMCLUnexpectedInternalError("Invalid dialog return value.")
  }

  /**
   *
   *
   * @return
   */
  val showParentlessYesNoQuestionDialog =
    Dialog.showConfirmation(parent = null, _: String, _: String, Options.YesNo, Message.Question)

}
