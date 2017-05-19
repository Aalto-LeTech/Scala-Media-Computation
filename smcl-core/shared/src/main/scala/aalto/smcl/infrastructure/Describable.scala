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

package aalto.smcl.infrastructure


/**
 * Provides a textual description for a class based on data that the describee provides.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
trait Describable {

  /** First text paragraph of the description of this class. */
  def descriptionTitle: String

  /** Properties the values of which will be described. */
  def describedProperties: Map[String, Any]

  /** Description of this class. */
  lazy val toDescription: String = {
    val maxTitleLength: Int = describedProperties.keys.map(_.length).max + 1
    val propertyTitleFormat = s"%-${maxTitleLength}s"

    val sb = new StringBuilder(describedProperties.size * 50)

    sb ++= "Object: " ++= descriptionTitle ++= StrNewLine

    if (describedProperties.nonEmpty) {
      sb ++= StrNewLine
      sb ++= "-- Properties --" ++= StrNewLine

      for ((title, value) <- describedProperties.toSeq.sortBy(_._1)) {
        val valueToPrint = if (value == null) "<null>" else value.toString

        sb ++= (title + StrColon).formatted(propertyTitleFormat)

        if (valueToPrint.length > 15) {
          sb ++= StrNewLine ++= StrSpace
        }
        sb ++= StrSpace ++= valueToPrint ++= StrNewLine
      }
    }

    sb.dropRight(StrNewLineLength).toString
  }

}
