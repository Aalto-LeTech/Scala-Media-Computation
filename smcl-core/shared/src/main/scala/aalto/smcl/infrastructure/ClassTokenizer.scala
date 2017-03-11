package aalto.smcl.infrastructure

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
 * "name" -> "CreateBitmap",
 * "width" -> "${widthInPixels} px",
 * "height" -> "${heightInPixels} px" // etc.
 * )
 * }}}
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class ClassTokenizer private[infrastructure]() {

  /** Initial capacity of the `StringBuilder` class used for tokenization. */
  private val InitialStringbuilderCapacityInChars = 200

  /** The separator string inserted to separate keys from values. */
  private val KeyValueSeparator = StrColon + StrSpace

  /** The separator string inserted to separate key-value pairs from each other. */
  private val ItemSep = StrSemicolon + StrSpace

  /** The `StringBuilder` instance used for tokenization. */
  private[this] val builder: StringBuilder = new StringBuilder(InitialStringbuilderCapacityInChars)

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
    s.clear()

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
    s ++= StrLeftAngleBracket ++= escape(new ReflectionUtils().shortTypeNameOf(clazz))

  /**
   * Appends key-value pairs to a given `StringBuilder` in the form `"; key: value"`.
   * If `key` is empty or `null`, nothing will be appended. If `key` is a non-empty string
   * but `value` is empty or `null`, only the `key` will be appended (as in `"; key"`).
   *
   * @param clazz   the instance to be tokenized
   * @param s       the `StringBuilder` instance to be used
   */
  private def appendKvPairsOfTo(clazz: Tokenizable, s: StringBuilder): Unit =
    clazz.metaInformation foreach {
      case (k: String, Some(v: String)) => s ++= ItemSep ++= escape(k) ++= KeyValueSeparator ++= escape(v)
      case (k: String, None)            => s ++= ItemSep ++= escape(k)
      case pair                         => throw new IllegalArgumentException(s"Invalid MetaInformationMap data: $pair")
    }

  /**
   * Appends an epilog to a given `StringBuilder` in the form `"]"`.
   *
   * @param s       the `StringBuilder` instance to be used
   */
  private def appendEpilogTo(s: StringBuilder): Unit = s ++= StrRightAngleBracket

  /**
   * Returns an escaped string for tokenization with the `tokenize()` method. The characters to be
   * escaped are the Scala's standard ones plus both colons and semicolons.
   */
  private def escape(part: String): String =
    new StringUtils().escapeString(part)
      .replaceAllLiterally(StrColon, StrColonAsUnicode)
      .replaceAllLiterally(StrSemicolon, StrSemicolonAsUnicode)

}
