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

import aalto.smcl.settings.SettingValidatorFactory




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class BitmapValidatorFunctionFactory(
    private val settingValidatorFactory: SettingValidatorFactory,
    private val bitmapValidator: BitmapValidator) {

  /**
   *
   *
   * @return
   */
  // @formatter:off
  def BitmapWidthValidator(): Int => Option[Throwable] =
    settingValidatorFactory.conditionFalseValidator[Int](
    { width => bitmapValidator.minimumWidthIsNotMet(width) || bitmapValidator.maximumWidthIsExceeded(width) },
    s"Bitmap width must be between ${BitmapValidator.MinimumBitmapHeightInPixels} " +
      s"and ${BitmapValidator.MaximumBitmapWidthInPixels} pixels")

  // @formatter:on

  /**
   *
   *
   * @return
   */
  // @formatter:off
  def BitmapHeightValidator(): Int => Option[Throwable] =
    settingValidatorFactory.conditionFalseValidator[Int](
    { height => bitmapValidator.minimumHeightIsNotMet(height) || bitmapValidator.maximumHeightIsExceeded(height) },
    s"Bitmap height must be between ${BitmapValidator.MinimumBitmapHeightInPixels} " +
      s"and ${BitmapValidator.MaximumBitmapHeightInPixels} pixels")

  // @formatter:on

}
