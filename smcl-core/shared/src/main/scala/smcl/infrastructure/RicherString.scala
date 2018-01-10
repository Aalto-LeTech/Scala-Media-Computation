/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object RicherString extends InjectablesRegistry {

  /** The StringUtils instance to be used by this object. */
  private lazy val stringUtils: StringUtils = {
    injectable(InjectablesRegistry.IIdStringUtils).asInstanceOf[StringUtils]
  }

  /**
   * Return a new RicherString instance.
   *
   * @param self
   *
   * @return
   */
  def apply(self: String): RicherString = {
    new RicherString(self, stringUtils)
  }

}




/**
 * Some methods to extend the built-in String class.
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 */
class RicherString private[smcl](
    val self: String,
    private val stringUtils: StringUtils) {

  /**
   * Converts the string into American-style title case, i.e.,
   * every word begins with a capital letter and all other
   * letters are in lower case.
   * <br>
   * This function does not take into account any
   * spellings (e.g., "eLearning" or "StringUtils") that do
   * not conform with the rule above.
   */
  final def toAmericanTitleCase: String = {
    stringUtils.toTitleCase(self)
  }

  /**
   * Returns the <code>StrUnnamed</code> string if the given
   * string is null or contains only whitespace; otherwise,
   * a trimmed version of the string itself is returned.
   *
   * @return the return value
   */
  final def orUnnamed: String = {
    if (self == null)
      return StrUnnamed

    val trimmedString = self.trim
    if (trimmedString.isEmpty)
      return StrUnnamed

    trimmedString
  }

}
