package aalto.smcl.bitmaps


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ViewerUpdateStyle {


  /**
   *
   */
  abstract sealed class Value extends SMCLInitializationInvoker


  /**
   *
   */
  case object UpdateViewerPerDefaults extends Value


  /**
   *
   */
  case object PreventViewerUpdates extends Value


}
