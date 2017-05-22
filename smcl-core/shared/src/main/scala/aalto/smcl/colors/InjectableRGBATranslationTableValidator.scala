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

package aalto.smcl.colors


import aalto.smcl.infrastructure.exceptions.ImplementationNotSetError




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait InjectableRGBATranslationTableValidator {

  //
  private var _rgbaTranslationTableValidator: Option[RGBATranslationTableValidator] = None

  /**
   * Returns the RGBATranslationTableValidator instance to be used by this object.
   *
   * @return
   *
   * @throws ImplementationNotSetError
   */
  protected def rgbaTranslationTableValidator: RGBATranslationTableValidator = {
    _rgbaTranslationTableValidator.getOrElse(
      throw ImplementationNotSetError("RGBATranslationTableValidator"))
  }

  /**
   * Set the RGBATranslationTableValidator instance to be used by this object.
   *
   * @param validator
   */
  private[smcl] def setRGBATranslationTableValidator(validator: RGBATranslationTableValidator): Unit = {
    require(validator != null,
      "The RGBATranslationTableValidator instance must be given (was null)")

    _rgbaTranslationTableValidator = Some(validator)
  }

}
