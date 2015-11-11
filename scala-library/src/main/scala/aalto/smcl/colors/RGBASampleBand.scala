package aalto.smcl.colors




/**
 * RGBA sample bands.
 *
 * @author Aleksi Lukkarinen
 */
object RGBASampleBand extends Enumeration {

  /** Type alias for this enumeration. */
  type SampleBand = Value

  /** */
  val Red = Value(0)

  /** */
  val Green = Value(1)

  /** */
  val Blue = Value(2)

  /** */
  val Opacity = Value(3)

}
