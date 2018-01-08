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

package smcl.bitmaps


import smcl.modeling.Len
import smcl.settings.SettingValidatorFactory




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
    { widthInPixels =>
        val width = Len(widthInPixels)
        bitmapValidator.minimumWidthIsNotMet(width) || bitmapValidator.maximumWidthIsExceeded(width) },
    s"Bitmap width must be between ${BitmapValidator.MinimumBitmapHeight} " +
      s"and ${BitmapValidator.MaximumBitmapWidth} pixels")

  // @formatter:on

  /**
   *
   *
   * @return
   */
  // @formatter:off
  def BitmapHeightValidator(): Int => Option[Throwable] =
    settingValidatorFactory.conditionFalseValidator[Int](
    { heightInPixels =>
        val height = Len(heightInPixels)
        bitmapValidator.minimumHeightIsNotMet(height) || bitmapValidator.maximumHeightIsExceeded(height)
    },
    s"Bitmap height must be between ${BitmapValidator.MinimumBitmapHeight} " +
      s"and ${BitmapValidator.MaximumBitmapHeight} pixels")

  // @formatter:on

}
