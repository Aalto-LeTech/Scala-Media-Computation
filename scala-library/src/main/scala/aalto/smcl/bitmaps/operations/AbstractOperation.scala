package aalto.smcl.bitmaps.operations


import aalto.smcl.infrastructure.{MetaInformationMap, Tokenizable}




/**
 * Provides functionality to represent a single bitmap operation in the operation trees.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] abstract class AbstractOperation
  extends Tokenizable with Immutable {

  /** Information about this [[AbstractOperation]]. */
  def metaInformation: MetaInformationMap

}
