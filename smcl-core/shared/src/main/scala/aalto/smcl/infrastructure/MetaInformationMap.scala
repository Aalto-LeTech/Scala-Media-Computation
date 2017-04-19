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
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object MetaInformationMap {

  // Messages
  private val MsgCannotBeNull =
    "MetaInformationMap content key cannot be null"

  private val MsgKeyCannotContainOnlyWhitespace =
    "MetaInformationMap content key cannot contain only white space"

  /**
   * Processes the given content proposal and instantiates
   * a new MetaInformationMap if content is acceptable.
   */
  def apply(className: String,
      contentProposal: Map[String, Option[String]]): MetaInformationMap = {

    var content = Map[String, Option[String]]()

    contentProposal foreach {pair =>
      var key = pair._1
      require(key != null, MsgCannotBeNull)

      key = key.trim()
      require(key.nonEmpty, MsgKeyCannotContainOnlyWhitespace)

      var value = pair._2
      if (value == null)
        value = None

      content = content + (key -> value)
    }

    new MetaInformationMap(className, content)
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class MetaInformationMap private(
    val className: String,
    private val content: Map[String, Option[String]]) extends Immutable {

  /**
   *
   */
  def size: Int = content.size

  /**
   *
   */
  def keyValuePairs: Seq[(String, Option[String])] = content.toSeq

  /**
   *
   */
  def get(key: String): Option[String] = content(key)

  /**
   *
   */
  def foreach[T](f: ((String, Option[String])) => T): Unit = content foreach f

}
