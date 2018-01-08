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

package smcl.infrastructure.exceptions


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object ResourceWithGivenIndexNotFoundError {

  /**
   *
   * @param invalidIndex
   * @param minimumValidIndex
   * @param maximumValidIndex
   *
   * @return
   */
  def apply(
      invalidIndex: Int,
      minimumValidIndex: Int,
      maximumValidIndex: Int): SMCLBaseError = {
    new ResourceWithGivenIndexNotFoundError(
      invalidIndex,
      minimumValidIndex,
      maximumValidIndex,
      null)
  }

}




/**
 *
 * @param invalidIndex
 * @param minimumValidIndex
 * @param maximumValidIndex
 * @param cause
 *
 * @author Aleksi Lukkarinen
 */
final case class ResourceWithGivenIndexNotFoundError private[smcl](
    invalidIndex: Int,
    minimumValidIndex: Int,
    maximumValidIndex: Int,
    override val cause: Throwable)
    extends SMCLBaseError(
      s"Resource #$invalidIndex was not found. " +
          s"The valid indices are from $minimumValidIndex to $maximumValidIndex",
      cause)
