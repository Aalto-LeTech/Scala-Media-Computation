package aalto


import aalto.smcl.infrastructure.settings.Settings




/**
 * Main package of the Scala Media Computation Library. Subpackages contain
 * functionality related to several areas of multimedia computation.
 *
 * @author Aleksi Lukkarinen
 */
package object smcl {


  /** Global settings storage. */
  object GS extends Settings


  SMCL.performInitialization(ModuleInitializationPhase.Early)

}
