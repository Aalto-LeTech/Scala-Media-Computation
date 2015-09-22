package aalto.smcl.colors


/**
 * Components of a RGBA color.
 *
 * @author Aleksi Lukkarinen
 */
object RGBAComponent extends Enumeration {

  /** Type alias for this enumeration. */
  type RGBAComponent = Value

  /** Red component. */
  val Red = Value

  /** Green component. */
  val Green = Value

  /** Blue component. */
  val Blue = Value

  /** Opacity component. */
  val Opacity = Value

}
