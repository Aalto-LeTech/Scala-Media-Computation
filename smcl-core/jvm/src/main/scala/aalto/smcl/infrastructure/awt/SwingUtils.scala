package aalto.smcl.infrastructure.awt

//import scala.swing.Dialog
//import scala.swing.Dialog.{Message, Options}




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
  val showParentlessYesNoQuestionDialog: (String, String) => Boolean = {
    /*
    val result = Dialog.showConfirmation(parent = null, _: String, _: String, Options.YesNo, Message.Question)

    result match {
      case Dialog.Result.Yes    => true
      case Dialog.Result.No     => false
      case Dialog.Result.Closed => false
      case _                    => throw new SMCLUnexpectedInternalError("Invalid dialog return value.")
    }
    */
    (_: String, _: String) => false
  }

}
