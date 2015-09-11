package aalto


import aalto.smcl.infrastructure.{InitializableModule, Settings}




/**
 * Main package of the Scala Media Computation Library. Subpackages contain
 * functionality related to several areas of multimedia computation.
 *
 * @author Aleksi Lukkarinen
 */
package object smcl extends InitializableModule {


  /** Global settings storage. */
  object GS extends Settings


}
