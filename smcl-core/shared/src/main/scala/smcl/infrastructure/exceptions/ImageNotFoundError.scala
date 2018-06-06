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
object ImageNotFoundError {

  /**
   *
   *
   * @param imageName
   *
   * @return
   */
  def apply(imageName: String): ImageNotFoundError = {
    new ImageNotFoundError(imageName, None, null)
  }

  /**
   *
   *
   * @param imageName
   * @param httpStatusCode
   *
   * @return
   */
  def apply(
      imageName: String,
      httpStatusCode: Int): ImageNotFoundError = {

    new ImageNotFoundError(imageName, Some(httpStatusCode), null)
  }

  /**
   *
   *
   * @param imageURL
   * @param httpStatusCode
   *
   * @return
   */
  def apply(
      imageURL: URL,
      httpStatusCode: Int): ImageNotFoundError = {

    new ImageNotFoundError(imageURL.toExternalForm, Some(httpStatusCode), null)
  }

  /**
   *
   *
   * @param imageName
   * @param cause
   *
   * @return
   */
  def apply(
      imageName: String,
      cause: Throwable): ImageNotFoundError = {

    new ImageNotFoundError(imageName, None, cause)
  }

}




/**
 *
 *
 * @param imageName
 * @param httpStatusCode
 * @param cause
 *
 * @author Aleksi Lukkarinen
 */
final
case class ImageNotFoundError private[smcl](
    imageName: String,
    httpStatusCode: Option[Int],
    override val cause: Throwable)
    extends SMCLBaseError({
      val sb = new StringBuilder(200)

      sb ++= s"""Image \"$imageName\" could not be found."""

      if (httpStatusCode.isDefined)
        sb ++= s" HTTP status code from server: ${httpStatusCode.get}."

      sb.toString()
    }, cause)
