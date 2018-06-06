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
object RedirectionRequestedError {

  /**
   *
   *
   * @param resourceURL
   * @param newAddress
   * @param httpStatusCode
   *
   * @return
   */
  def apply(
      resourceURL: URL,
      newAddress: String,
      httpStatusCode: Int): RedirectionRequestedError = {

    new RedirectionRequestedError(
      resourceURL.toExternalForm,
      newAddress,
      Some(httpStatusCode),
      null)
  }

}




/**
 *
 *
 * @param resourceName
 * @param newAddress
 * @param httpStatusCode
 * @param cause
 *
 * @author Aleksi Lukkarinen
 */
final case class RedirectionRequestedError private[smcl](
    resourceName: String,
    newAddress: String,
    httpStatusCode: Option[Int],
    override val cause: Throwable)
    extends SMCLBaseError({
      val sb = new StringBuilder(200)

      sb ++=
          s"""When requested for \"$resourceName\", the server requested
             |a new address to be used: $newAddress. For security reasons,
             |SMCL by design does not automatically follow redirection
             |requests in certain situations."""
              .stripMargin.replace("\n", " ").trim

      if (httpStatusCode.isDefined)
        sb ++= s" HTTP status code from server: ${httpStatusCode.get}."

      sb.toString()
    }, cause)
