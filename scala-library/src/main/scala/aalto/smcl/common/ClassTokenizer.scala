package aalto.smcl.common

import aalto.smcl._

/**
 * Creates formal-like textual tokens representing classes. A class to
 * be tokenized has to provide the information necessary for tokenization.
 * This means extending the [[Tokenizable]] trait and defining the
 * meta information map defined by it, like below. The keys and values
 * of the map are specific to an application or a class.
 *
 * {{{
 * /** Information about this [[CreateBitmap]] instance */
 * val metaInformation = Map(
 *   "name" -> "CreateBitmap",
 *   "width" -> "${widthInPixels} px",
 *   "height" -> "${heightInPixels} px" // etc.
 * )
 * }}}
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object ClassTokenizer {

  /** Initial capacity of the `StringBuilder` class used for tokenization. */
  private val INITIAL_STRINGBUILDER_CAPACITY_IN_CHARS = 200

  /** The `StringBuilder` instance used for tokenization. */
  private val s: StringBuilder = new StringBuilder(INITIAL_STRINGBUILDER_CAPACITY_IN_CHARS)

  /** The separator string inserted to separate keys from values. */
  private val keyvalue_sep = STR_COLON + STR_SPACE

  /** The separator string inserted to separate key-value pairs from each other. */
  private val item_sep = STR_SEMICOLON + STR_SPACE

  /**
   * Returns a tokenized representation of a given class extending the [[Tokenizable]] trait.
   *
   * @param clazz   the instance to be
   */
  def tokenize(clazz: Tokenizable): String = {
    val kvPairs = clazz.metaInformation.toSeq

    s.clear
    s ++= STR_LEFT_ANGLE_BRACKET
    s ++= escapeTokenPart(ReflectionUtils.symbolOf(clazz).name.decodedName.toString) ++= item_sep

    kvPairs.foreach { x =>
      s ++= escapeTokenPart(x._1) ++= keyvalue_sep ++= escapeTokenPart(x._2) ++= item_sep
    }
    s.dropRight(2) ++= STR_RIGHT_ANGLE_BRACKET
    s.toString()
  }

  /**
   * Appends a given key-value pair to a given `StringBuilder`.
   */
  private def appendKvPairTo(pair: Tuple2[String, String], s: StringBuilder): Unit = {
    val key = pair._1
    val value = pair._2

    if (key == null || key.isEmpty())
      return

    s ++= escapeTokenPart(key)

    if (value != null && !value.isEmpty())
      s ++= keyvalue_sep ++= escapeTokenPart(value)

    s ++= item_sep
  }

  /**
   * Returns an escaped string for tokenization with the `tokenize()` method. The charaters to be
   * escaped are the Scala's standard ones plus both colons and semicolons.
   */
  private def escapeTokenPart(part: String): String =
    StringUtils.escapeString(part)
      .replaceAllLiterally(STR_COLON, STR_COLON_AS_UNICODE)
      .replaceAllLiterally(STR_SEMICOLON, STR_SEMICOLON_AS_UNICODE)

}
