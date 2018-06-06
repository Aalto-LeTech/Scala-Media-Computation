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
object UnexpectedInternalError {

  /**
   *
   *
   * @param message
   *
   * @return
   */
  def apply(message: String): UnexpectedInternalError = {
    UnexpectedInternalError(message, null)
  }

  /**
   *
   *
   * @param source
   *
   * @return
   */
  def whileInit(source: String): UnexpectedInternalError = {
    apply(source + " was not correctly initialized!")
  }

}




/**
 *
 *
 * @param message
 * @param cause
 *
 * @author Aleksi Lukkarinen
 */
final case class UnexpectedInternalError private[smcl](
    override val message: String,
    override val cause: Throwable)
    extends SMCLBaseError(message, cause)
