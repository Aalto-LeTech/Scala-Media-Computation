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

package smcl.infrastructure.jvmawt.imageio


import scala.util.{Failure, Success, Try}

import smcl.infrastructure.exceptions._
import smcl.infrastructure.{BitmapBufferAdapter, JVMReflectionUtils}
import smcl.pictures.BitmapValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class JavaResourceImageLoader(
    private val bitmapValidator: BitmapValidator,
    private val supportedReadableFileExtensions: Seq[String]) {

  /**
   *
   *
   * @param path
   * @param shouldLoadOnlyFirst
   *
   * @return
   *
   * @throws OperationPreventedBySecurityManagerError
   */
  def tryToLoad(
      path: String,
      shouldLoadOnlyFirst: Boolean): Seq[Try[BitmapBufferAdapter]] = {

    Seq(Failure(ImageNotFoundError(path, null)))
  }

  /**
   * Tries to find a local Java resource.
   *
   * @return an ``URL`` representing the resource, or ``null`` if either the resource could not be found or access to it was prevented by a ``SecurityManager``
   *
   * @throws OperationPreventedBySecurityManagerError if retrieval of a class loader is prevented by a ``SecurityManager``
   */
  private
  def resolveLocalJavaResource(resourceName: String): Try[String] = {
    val loader = JVMReflectionUtils.getClassLoader

    val resourceURL = loader.getResource(resourceName)
    if (resourceURL == null)
      return Failure(ImageNotFoundError(resourceName, null))

    Success(resourceURL.getPath)
  }

}
