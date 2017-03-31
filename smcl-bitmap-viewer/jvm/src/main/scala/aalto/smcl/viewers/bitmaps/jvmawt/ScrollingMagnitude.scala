package aalto.smcl.viewers.bitmaps.jvmawt




/**
 * Represents magnitudes of scrolling for scrollbars.
 *
 * @author Aleksi Lukkarinen
 */
private[jvmawt]
object ScrollingMagnitude {

  /** Type of all magnitudes. */
  trait Value


  /** Unit magnitude. */
  case object Unit extends Value


  /** Block magnitude. */
  case object Block extends Value

}
