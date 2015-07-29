package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common._




/**
 * Provides functionality to represent a single bitmap operation in the operation trees.
 *
 * @author Aleksi Lukkarinen
 */
private[images] abstract class AbstractOperation
    extends Tokenizable with Immutable {

  /** Operation streams needed to construct  */
  def childOperationListsOption: Option[Array[BitmapOperationList]]

  /** Operation streams needed to construct  */
  def metaInformation: MetaInformationMap

}
