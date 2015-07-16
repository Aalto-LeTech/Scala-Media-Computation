package aalto.smcl.common

import aalto.smcl.common._

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
   * @param clazz   the instance to be tokenized
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
    s.clear

    appendPrologOfTo(clazz, s)
    appendKvPairsOfTo(clazz, s)
    appendEpilogTo(s)

    s.toString()
  }

  /**
   * Appends a prolog to a given `StringBuilder` in the form `"[ClassName"`.
   *
   * @param clazz   the instance to be tokenized
   * @param s       the `StringBuilder` instance to be used
   */
  private def appendPrologOfTo(clazz: Tokenizable, s: StringBuilder): Unit =
    s ++= STR_LEFT_ANGLE_BRACKET ++= escape(ReflectionUtils.symbolOf(clazz).name.decodedName.toString)

  /**
   * Appends key-value pairs to a given `StringBuilder` in the form `"; key: value"`.
   * If `key` is empty or `null`, nothing will be appended. If `key` is a non-empty string
   * but `value` is empty or `null`, only the `key` will be appended (as in `"; key"`).
   *
   * @param clazz   the instance to be tokenized
   * @param s       the `StringBuilder` instance to be used
   */
  private def appendKvPairsOfTo(clazz: Tokenizable, s: StringBuilder): Unit =
    clazz.metaInformation.foreach { pair =>
      pair match {
        case (k: String, Some(v: String)) => s ++= ITEM_SEP ++= escape(k) ++= KEYVALUE_SEP ++= escape(v)
        case (k: String, None)            => s ++= ITEM_SEP ++= escape(k)
        case _                            => throw new IllegalArgumentException(s"Invalid MetaInformationMap data: ${pair}")
      }
    }

  /**
   * Appends an epilog to a given `StringBuilder` in the form `"]"`.
   *
   * @param s       the `StringBuilder` instance to be used
   */
  private def appendEpilogTo(s: StringBuilder): Unit = s ++= STR_RIGHT_ANGLE_BRACKET

  /**
   * Returns an escaped string for tokenization with the `tokenize()` method. The charaters to be
   * escaped are the Scala's standard ones plus both colons and semicolons.
   */
  private def escape(part: String): String =
    StringUtils.escapeString(part)
      .replaceAllLiterally(STR_COLON, STR_COLON_AS_UNICODE)
      .replaceAllLiterally(STR_SEMICOLON, STR_SEMICOLON_AS_UNICODE)

}
