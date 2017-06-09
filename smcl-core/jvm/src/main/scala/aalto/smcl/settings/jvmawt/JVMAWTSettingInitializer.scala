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
import aalto.smcl.settings.{ObjectSetting, SettingValidatorFactory}




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

    ObjectSetting[AWTAffineTransformationInterpolationMethod](
      key = "AffineTransformationInterpolationMethod",
      initialValue = NearestNeighbor,
      validator = settingValidatorFactory.EmptyValidator)

  }

}
