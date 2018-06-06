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

import smcl.infrastructure.BitmapBufferAdapter
import smcl.infrastructure.exceptions._
import smcl.infrastructure.jvmawt.JVMReflectionUtils
import smcl.pictures.BitmapValidator




/**
 *
 *
 * @param path
 * @param shouldLoadOnlyFirst
 * @param bitmapValidator
 * @param supportedReadableFileExtensions
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class JavaResourceImageLoader(
    path: String,
    shouldLoadOnlyFirst: Boolean,
    private val bitmapValidator: BitmapValidator,
    private val supportedReadableFileExtensions: Seq[String]) {

  /**
   *
   *
   * @return
   *
   * @throws OperationPreventedBySecurityManagerError
   */
  def load: Seq[Try[BitmapBufferAdapter]] = {
    Seq(Failure(ImageNotFoundError(path)))
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
      return Failure(ImageNotFoundError(resourceName))

    Success(resourceURL.getPath)
  }

}
