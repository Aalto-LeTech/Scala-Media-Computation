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


import aalto.smcl.infrastructure.exceptions.{ImplementationNotSetError, InjectableNotInjectedError}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object InjectablesRegistry {

  /** Injectable ID for the [[StringUtils]] class. */
  val IIdStringUtils = "StringUtils"

  /** Injectable ID for the [[CommonValidators]] class. */
  val IIdCommonValidators = "CommonValidators"

  /** Injectable ID for the [[aalto.smcl.colors.ColorValidator]] class. */
  val IIdColorValidator = "ColorValidator"

  /** Injectable ID for the [[aalto.smcl.colors.rgb.ColorTranslationTableValidator]] class. */
  val IIdColorTranslationTableValidator = "ColorTranslationTableValidator"

  /** Injectable ID for the [[aalto.smcl.bitmaps.BitmapValidator]] class. */
  val IIdBitmapValidator = "BitmapValidator"

  /** Injectable ID for the [[aalto.smcl.settings.SettingRegistry.SettingRegisterer]] class. */
  val IIdSettingRegisterer = "SettingRegisterer"

  /** Injectable ID for the [[CollectionCreator]] class. */
  val IIdCollectionCreator = "CollectionCreator"

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait InjectablesRegistry {

  /** Map for the injectables to be used by this object. */
  private var injectablesRegistry: Map[String, Any] = Map()

  /**
   * Returns an object from the previously-set injectables registry.
   *
   * @param id
   *
   * @return
   *
   * @throws ImplementationNotSetError
   */
  protected def injectable[InjectableType](id: String): InjectableType = {
    injectablesRegistry.getOrElse(id, {
      throw InjectableNotInjectedError(id)
    }).asInstanceOf[InjectableType]
  }

  /**
   * Sets the injectable object registry to be used by this object.
   *
   * @param registryToUse
   */
  private[smcl]
  def setInjectables(registryToUse: Map[String, Any]): Unit = {
    require(registryToUse != null,
      "The injectable registry must be given (was null)")

    injectablesRegistry = registryToUse
  }

}
