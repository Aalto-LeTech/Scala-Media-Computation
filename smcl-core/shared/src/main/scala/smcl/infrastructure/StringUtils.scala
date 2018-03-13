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
class StringUtils {

  /** Characters allowed in a string that represents a hexadecimal number. */
  val HexCharsWithoutPrefixChars: Seq[Char] = ('0' to '9') ++ ('a' to 'f') ++ ('A' to 'F')

  /** As [[HexCharsWithoutPrefixChars]] but as strings. */
  val HexCharsWithoutPrefixCharsAsStrings: Seq[String] =
    HexCharsWithoutPrefixChars.map(_.toString)

  /**
   * Checks whether or not a given string contains
   * only characters 0-9 and A-F, ignoring case.
   *
   * @param s
   *
   * @return
   */
  final def containsOnlyHexCharsWithoutPrefixChars(s: String): Boolean =
    s.forall(HexCharsWithoutPrefixChars.contains(_))

  /**
   * Converts the string into American-style title case, i.e.,
   * every word begins with a capital letter and all other
   * letters are in lower case.
   * <br>
   * This function does not take into account any
   * spellings (e.g., "eLearning" or "StringUtils") that do
   * not conform with the rule above.
   *
   * @param s
   *
   * @return
   */
  final def toAmericanTitleCase(s: String): String =
    s.toLowerCase.split(StrSpace).map(_.capitalize).mkString(StrSpace)

}
