package aalto.smcl.bitmaps.viewer




/**
 * Represents magnitudes of scrolling for scrollbars.
 *
 * @author Aleksi Lukkarinen
 */
private[viewer]
object ScrollingMagnitude {

  /** Type of all magnitudes. */
  trait Value


  /** Unit magnitude. */
  case object Unit extends Value


  /** Block magnitude. */
  case object Block extends Value

}
