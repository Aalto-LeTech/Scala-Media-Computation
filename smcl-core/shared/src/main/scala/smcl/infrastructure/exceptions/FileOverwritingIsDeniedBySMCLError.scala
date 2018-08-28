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
object FileOverwritingIsDeniedBySMCLError {

  /**
   *
   *
   * @param filename
   *
   * @return
   */
  def apply(filename: String): SMCLBaseError = {
    new FileOverwritingIsDeniedBySMCLError(filename, null)
  }

}




/**
 *
 *
 * @param filename
 * @param cause
 *
 * @author Aleksi Lukkarinen
 */
final case class FileOverwritingIsDeniedBySMCLError private[smcl](
    filename: String,
    override val cause: Throwable)
    extends SMCLBaseError(message =
        s"""File \"$filename\" already exists. To prevent accidental data loss, """ +
            "SMCL does not allow files to be overwritten. Please use a different file name " +
            "or rename, move, or delete the existing file, if approppriate.",
      cause = cause)
