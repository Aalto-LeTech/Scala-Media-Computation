package aalto.smcl.bitmaps.operations


import aalto.smcl.common.{MetaInformationMap, Tokenizable}




/**
 * Provides functionality to represent a single bitmap operation in the operation trees.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] abstract class AbstractOperation
    extends Tokenizable with Immutable {

  /** Operation streams needed to construct  */
  def childOperationListsOption: Option[Seq[BitmapOperationList]]

  /** Operation streams needed to construct  */
  def metaInformation: MetaInformationMap

}
