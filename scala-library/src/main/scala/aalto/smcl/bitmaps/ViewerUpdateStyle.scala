       package aalto.smcl.bitmaps


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ViewerUpdateStyle {


  /**
   *
   */
  abstract sealed class Value


  /**
   *
   */
  case object UpdateViewerPerDefaults extends Value


  /**
   *
   */
  case object PreventViewerUpdates extends Value


}
