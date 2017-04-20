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

package aalto.smcl.interfaces


/**
 * Interface for querying objects for common metadata.
 *
 * @author Aleksi Lukkarinen
 */
trait ResourceMetadataSource {

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  def resourceIdOption(bitmapNumber: Int = 0): Option[String]

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  def resourceTimestampOption(bitmapNumber: Int = 0): Option[Timestamp]

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  def resourceTitleOption(bitmapNumber: Int = 0): Option[String]

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  def resourceDescriptionOption(bitmapNumber: Int = 0): Option[String]

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  def resourceAuthorsOption(bitmapNumber: Int = 0): Option[String]

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  def resourceKeywordsOption(bitmapNumber: Int = 0): Option[String]

}
