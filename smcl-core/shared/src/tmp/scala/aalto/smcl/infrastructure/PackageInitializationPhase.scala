package aalto.smcl.infrastructure


/**
 * Initialization phases.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object PackageInitializationPhase extends Enumeration {

  /** Type alias for this enumeration. */
  type PackageInitializationPhase = Value

  /** Early initialization. */
  val Early = Value

  /** Late initialization. */
  val Late = Value

}
