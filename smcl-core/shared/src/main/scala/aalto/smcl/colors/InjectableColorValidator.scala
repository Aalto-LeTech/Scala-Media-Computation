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
trait InjectableColorValidator {

  //
  private var _colorValidator: Option[ColorValidator] = None

  /**
   * Returns the ColorValidator instance to be used by this object.
   *
   * @return
   *
   * @throws ImplementationNotSetError
   */
  protected def colorValidator: ColorValidator = {
    _colorValidator.getOrElse(
      throw ImplementationNotSetError("ColorValidator"))
  }

  /**
   * Set the ColorValidator instance to be used by this object.
   *
   * @param validator
   */
  private[smcl] def setColorValidator(validator: ColorValidator): Unit = {
    require(validator != null,
      "The ColorValidator instance must be given (was null)")

    _colorValidator = Some(validator)
  }

}
