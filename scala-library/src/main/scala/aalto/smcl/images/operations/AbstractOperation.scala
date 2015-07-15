package aalto.smcl.images.operations

import java.awt.image.{ BufferedImage => JBufferedImage }
import scala.collection.mutable.StringBuilder
import aalto.smcl._
import aalto.smcl.images._

/**
 * Provides functionality to represent a single bitmap operation in the operation trees.
 *
 * @author Aleksi Lukkarinen
 */
private[images] abstract class AbstractOperation() {

  val INITIAL_STRINGBUILDER_CAPACITY_IN_CHARS = 50

  /** Operation streams needed to construct  */
  def childOperationListsOption: Option[Array[BitmapOperationList]]

  /** Operation streams needed to construct  */
  def metaInformation: Map[String, String]

  /** Tokenized representation of this operation, after it is calculated. */
  lazy val tokenizedRepresentation: String = toToken

  /**
   * Returns this operation as a formal string, which could be used to assess exercises.
   */
  def toToken(): String = {
    val s: StringBuilder = new StringBuilder(INITIAL_STRINGBUILDER_CAPACITY_IN_CHARS)
    val sep1 = STR_COLON + STR_SPACE
    val sep2 = STR_SEMICOLON + STR_SPACE

    s ++= STR_LEFT_ANGLE_BRACKET
    metaInformation.toSeq.foreach { x =>
      s ++= escapeTokenPart(x._1) ++= sep1 ++= escapeTokenPart(x._2) ++= sep2
    }
    s.dropRight(2) ++= STR_RIGHT_ANGLE_BRACKET
    s.toString()
  }

  /**
   * Returns an escaped string for tokenization with toToken() method. The charaters to be
   * escaped are the Scala's standard ones plus both colons and semicolons.
   */
  private def escapeTokenPart(part: String): String =
    StringUtils.escapeString(part)
      .replaceAllLiterally(STR_COLON, STR_COLON_AS_UNICODE)
      .replaceAllLiterally(STR_SEMICOLON, STR_SEMICOLON_AS_UNICODE)

}
