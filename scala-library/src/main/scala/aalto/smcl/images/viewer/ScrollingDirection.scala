package aalto.smcl.images.viewer

/**
 * Represents directions for scrolling with scrollbars.
 *
 * @author Aleksi Lukkarinen
 */
object ScrollingDirection {

  /** Type of all directions. */
  trait Value

  /** Upward direction. */
  object Upwards extends Value

  /** Downward direction. */
  object Downwards extends Value

  /** Leftward direction. */
  object Leftwards extends Value

  /** Rightward direction. */
  object Rightwards extends Value

}
