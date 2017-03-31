package aalto.smcl.viewers.bitmaps.jvmawt




/**
 * Represents directions for scrolling with scrollbars.
 *
 * @author Aleksi Lukkarinen
 */
private[jvmawt]
object ScrollingDirection {

  /** Type of all directions. */
  trait Value


  /** Upward direction. */
  case object Upwards extends Value


  /** Downward direction. */
  case object Downwards extends Value


  /** Leftward direction. */
  case object Leftwards extends Value


  /** Rightward direction. */
  case object Rightwards extends Value

}
