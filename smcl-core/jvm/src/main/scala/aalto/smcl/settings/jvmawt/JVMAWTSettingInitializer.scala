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

package aalto.smcl.settings.jvmawt


import aalto.smcl.bitmaps.BitmapValidatorFunctionFactory
import aalto.smcl.infrastructure.SettingInitializer
import aalto.smcl.settings.{IntSetting, ObjectSetting, SettingValidatorFactory}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class JVMAWTSettingInitializer() extends SettingInitializer {

  /**
   *
   */
  def apply(
      settingValidatorFactory: SettingValidatorFactory,
      bitmapValidatorFunctionFactory: BitmapValidatorFunctionFactory): Unit = {

    IntSetting(
      key = "ColorVisualizationTileSideLengthInPixels",
      initialValue = 80,
      validator = new SettingValidatorFactory().conditionFalseValidator[Int]({
        _ < 20
      }, "Side length of color visualization tiles' must be at least 20 pixels"))

    ObjectSetting[AWTAffineTransformationInterpolationMethod](
      key = "AffineTransformationInterpolationMethod",
      initialValue = NearestNeighbor,
      validator = settingValidatorFactory.EmptyValidator)

  }

}
