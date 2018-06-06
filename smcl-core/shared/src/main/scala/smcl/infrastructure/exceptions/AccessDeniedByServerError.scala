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


import java.net.URL




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object AccessDeniedByServerError {

  /**
   *
   *
   * @param resourceURL
   * @param httpStatusCode
   *
   * @return
   */
  def apply(
      resourceURL: URL,
      httpStatusCode: Int): AccessDeniedByServerError = {

    new AccessDeniedByServerError(
      resourceURL.toExternalForm,
      Some(httpStatusCode),
      null)
  }

}




/**
 *
 *
 * @param resourceName
 * @param httpStatusCode
 * @param cause
 *
 * @author Aleksi Lukkarinen
 */
final case class AccessDeniedByServerError private[smcl](
    resourceName: String,
    httpStatusCode: Option[Int],
    override val cause: Throwable)
    extends SMCLBaseError({
      val sb = new StringBuilder(200)

      sb ++= s"""Access to \"$resourceName\" was denied by the server."""

      if (httpStatusCode.isDefined)
        sb ++= s" HTTP status code from server: ${httpStatusCode.get}."

      sb.toString()
    }, cause)
