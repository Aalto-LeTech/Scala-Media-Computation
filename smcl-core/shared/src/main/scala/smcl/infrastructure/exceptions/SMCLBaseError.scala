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
 */
private[smcl]
object SMCLBaseError {

  /**
   *
   *
   * @param message
   *
   * @return
   */
  def apply(message: String): SMCLBaseError = {
    new SMCLBaseError(message, null)
  }

  /**
   *
   *
   * @param cause
   *
   * @return
   */
  def apply(cause: Throwable): SMCLBaseError = {
    new SMCLBaseError(null, cause)
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
class SMCLBaseError private[smcl](
    val message: String,
    val cause: Throwable)
    extends RuntimeException(message, cause) {

  /**
   *
   *
   * @param arg
   *
   * @return
   */
  def unapply(arg: SMCLBaseError): Option[(String, Throwable)] =
    Some((arg.message, arg.cause))

}
