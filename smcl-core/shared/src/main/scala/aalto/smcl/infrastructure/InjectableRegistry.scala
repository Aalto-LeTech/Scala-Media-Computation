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

package aalto.smcl.infrastructure


import scala.collection.mutable

import aalto.smcl.infrastructure.exceptions.{ImplementationNotSetError, InjectableNotInjectedError}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object InjectableRegistry {

  /** Injectable ID for the StringUtils class. */
  val IIdStringUtils = "StringUtils"

  /** Injectable ID for the CommonValidators class. */
  val IIdCommonValidators = "CommonValidators"

  /** Injectable ID for the ColorValidator class. */
  val IIdColorValidator = "ColorValidator"

  /** Injectable ID for the RGBATranslationTableValidator class. */
  val IIdRGBATranslationTableValidator = "RGBATranslationTableValidator"

  /** Injectable ID for the BitmapValidator class. */
  val IIdBitmapValidator = "BitmapValidator"

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait InjectableRegistry {

  /** Map for the injectables to be used by this object. */
  private val _injectableRegistry = mutable.Map[String, Any]()

  /**
   * Returns an object previously injected into this object.
   *
   * @param id
   *
   * @return
   *
   * @throws ImplementationNotSetError
   */
  protected def injectable(id: String): Any = {
    _injectableRegistry.getOrElse(id, throw InjectableNotInjectedError(id))
  }

  /**
   * Injects objects to be used by this object.
   *
   * @param content
   */
  private[smcl]
  def inject(content: (String, Any)*): Unit = {
    for ((id, injectable) <- content) {
      require(injectable != null,
        "The injectable object instance must be given (was null)")

      require(id != null,
        "The injectable id must be given (was null)")

      val trimmedId = id.trim
      require(trimmedId.nonEmpty,
        "The injectable id must not be empty or contain only white space.")

      require(!_injectableRegistry.contains(trimmedId),
        s"The given injectable id ($trimmedId) is injected already.")

      _injectableRegistry += (trimmedId -> injectable)
    }
  }

}
