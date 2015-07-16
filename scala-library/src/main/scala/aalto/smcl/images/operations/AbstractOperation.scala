package aalto.smcl.images.operations

import java.awt.image.{ BufferedImage => JBufferedImage }
import scala.collection.mutable.StringBuilder
import aalto.smcl.common._
import aalto.smcl.images._

/**
 * Provides functionality to represent a single bitmap operation in the operation trees.
 *
 * @author Aleksi Lukkarinen
 */
private[images] abstract class AbstractOperation extends Tokenizable {

  /** Tokenized representation of this operation, after it is calculated. */
  private lazy val _tokenizedRepresentation: String = ClassTokenizer.tokenize(this)

  /** Operation streams needed to construct  */
  def childOperationListsOption: Option[Array[BitmapOperationList]]

  /** Operation streams needed to construct  */
  def metaInformation: MetaInformationMap

  /**
   * Returns this operation as a formal string, which could be used to assess exercises.
   */
  def toToken(): String = _tokenizedRepresentation 

}
