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

  /** The separator string inserted to separate keys from values. */
  private val KEYVALUE_SEP = STR_COLON + STR_SPACE

  /** The separator string inserted to separate key-value pairs from each other. */
  private val ITEM_SEP = STR_SEMICOLON + STR_SPACE

  /** The `StringBuilder` instance used for tokenization. */
  private[this] val builder: StringBuilder = new StringBuilder(INITIAL_STRINGBUILDER_CAPACITY_IN_CHARS)

  /**
   * Returns a tokenized representation of a given class extending the [[Tokenizable]] trait.
   *
   * @param clazz   the instance to be
   */
  def tokenize(clazz: Tokenizable): String = tokenize0(clazz: Tokenizable, builder)

  /**
   * Returns a tokenized representation of a given class extending the [[Tokenizable]] trait.
   * Tokenization is performed using a provided `StringBuilder` instance.
   *
   * @param clazz   the instance to be tokenized
   * @param s       the `StringBuilder` instance to be used
   */
  private def tokenize0(clazz: Tokenizable, s: StringBuilder): String = {
    val appendKv = appendKvPairTo(_: Tuple2[String, String], s)
    val className = ReflectionUtils.symbolOf(clazz).name.decodedName.toString

    s.clear
    s ++= STR_LEFT_ANGLE_BRACKET ++= escapeTokenPart(className)
    clazz.metaInformation.foreach { appendKv(_) }
    s ++= STR_RIGHT_ANGLE_BRACKET
    s.toString()
  }

  /**
   * Appends a given key-value pair to a given `StringBuilder` in the form `"; key: value"`.
   * If `key` is empty or `null`, nothing will be appended. If `key` is a non-empty string
   * but `value` is empty or `null`, only the `key` will be appended (as in `"; key"`).
   *
   * @param pair            the key-value pair to be appended
   * @param shouldSeparate  whether to append the item separator after the key-value pair
   * @param s               the `StringBuilder` instance to be used
   */
  private def appendKvPairTo(pair: Tuple2[String, String], s: StringBuilder): Unit = pair match {
    case (k: String, v: String) if k.nonEmpty && v.nonEmpty => s ++=
      s"${ITEM_SEP}${escapeTokenPart(k)}${KEYVALUE_SEP}${escapeTokenPart(v)}"
    case (k: String, _) if k.nonEmpty => s ++= s"${ITEM_SEP}${escapeTokenPart(k)}"
    case _                            =>
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
