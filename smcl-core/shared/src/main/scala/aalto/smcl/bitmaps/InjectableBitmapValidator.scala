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

package aalto.smcl.bitmaps


import aalto.smcl.infrastructure.exceptions.ImplementationNotSetError




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait InjectableBitmapValidator {

  //
  private var _bitmapValidator: Option[BitmapValidator] = None

  /**
   * Returns the BitmapValidator instance to be used by this object.
   *
   * @return
   *
   * @throws ImplementationNotSetError
   */
  protected def bitmapValidator: BitmapValidator = {
    _bitmapValidator.getOrElse(
      throw ImplementationNotSetError("BitmapValidator"))
  }

  /**
   * Set the BitmapValidator instance to be used by this object.
   *
   * @param validator
   */
  private[smcl] def setBitmapValidator(validator: BitmapValidator): Unit = {
    require(validator != null,
      "The BitmapValidator instance must be given (was null)")

    _bitmapValidator = Some(validator)
  }

}
