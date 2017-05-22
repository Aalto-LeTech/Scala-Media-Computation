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


import aalto.smcl.infrastructure.exceptions.ImplementationNotSetError




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait InjectableCommonValidators {

  //
  private var _commonValidators: Option[CommonValidators] = None

  /**
   * Returns the CommonValidators instance to be used by this object.
   *
   * @return
   *
   * @throws ImplementationNotSetError
   */
  protected def commonValidators: CommonValidators = {
    _commonValidators.getOrElse(
      throw ImplementationNotSetError("CommonValidators"))
  }

  /**
   * Set the CommonValidators instance to be used by this object.
   *
   * @param validator
   */
  private[smcl] def setCommonValidators(validator: CommonValidators): Unit = {
    require(validator != null,
      "The CommonValidators instance must be given (was null)")

    _commonValidators = Some(validator)
  }

}
