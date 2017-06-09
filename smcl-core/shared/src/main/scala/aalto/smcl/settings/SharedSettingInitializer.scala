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

package aalto.smcl.settings


import aalto.smcl.bitmaps.BitmapValidatorFunctionFactory
import aalto.smcl.colors.rgb._
import aalto.smcl.infrastructure.SettingInitializer




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class SharedSettingInitializer() extends SettingInitializer {

  /**
   *
   */
  def apply(
      settingValidatorFactory: SettingValidatorFactory,
      bitmapValidatorFunctionFactory: BitmapValidatorFunctionFactory): Unit = {

    BooleanSetting(
      key = SIdNewBitmapsAreDisplayedAutomatically,
      initialValue = false,
      validator = settingValidatorFactory.EmptyValidator)

    BooleanSetting(
      key = SIdBitmapsAreDisplayedAutomaticallyAfterOperations,
      initialValue = false,
      validator = settingValidatorFactory.EmptyValidator)

    BooleanSetting(
      key = SIdShapesHaveBordersByDefault,
      initialValue = true,
      validator = settingValidatorFactory.EmptyValidator)

    BooleanSetting(
      key = SIdShapesHaveFillingsByDefault,
      initialValue = false,
      validator = settingValidatorFactory.EmptyValidator)

    BooleanSetting(
      key = SIdCanvasesAreResizedBasedOnTransformations,
      initialValue = true,
      validator = settingValidatorFactory.EmptyValidator)

    IntSetting(
      key = SIdDefaultBitmapWidthInPixels,
      initialValue = 50,
      validator = bitmapValidatorFunctionFactory.BitmapWidthValidator())

    IntSetting(
      key = SIdDefaultBitmapHeightInPixels,
      initialValue = 50,
      validator = bitmapValidatorFunctionFactory.BitmapHeightValidator())

    IntSetting(
      key = SIdBitmapWidthWarningLimitInPixels,
      initialValue = 800,
      validator = bitmapValidatorFunctionFactory.BitmapWidthValidator())

    IntSetting(
      key = SIdBitmapHeightWarningLimitInPixels,
      initialValue = 800,
      validator = bitmapValidatorFunctionFactory.BitmapHeightValidator())

    IntSetting(
      key = SIdDefaultCircleRadiusInPixels,
      initialValue = 10,
      validator = settingValidatorFactory.conditionFalseValidator[Int]({
        _ < 1
      }, "Circle radius must be at least 1 pixel"))

    IntSetting(
      key = SIdDefaultRoundingWidthInPixels,
      initialValue = 20,
      validator = settingValidatorFactory.conditionFalseValidator[Int]({
        _ < 1
      }, "Rounding width must be at least 1 pixel"))

    IntSetting(
      key = SIdDefaultRoundingHeightInPixels,
      initialValue = 20,
      validator = settingValidatorFactory.conditionFalseValidator[Int]({
        _ < 1
      }, "Rounding height must be at least 1 pixel"))

    IntSetting(
      key = SIdDefaultPaddingInPixels,
      initialValue = 5,
      validator = settingValidatorFactory.conditionFalseValidator[Int]({
        _ < 0
      }, "Padding cannot be negative"))

    IntSetting(
      key = SIdDefaultArcStartAngleInDegrees,
      initialValue = 0,
      validator = settingValidatorFactory.EmptyValidator)

    IntSetting(
      key = SIdDefaultArcAngleInDegrees,
      initialValue = 180,
      validator = settingValidatorFactory.EmptyValidator)

    IntSetting(
      key = SIdColorVisualizationTileSideLengthInPixels,
      initialValue = 80,
      validator = settingValidatorFactory.conditionFalseValidator[Int]({
        _ < 20
      }, "Side length of color visualization tiles must be at least 20 pixels"))

    ColorSetting(
      key = SIdDefaultBackgroundColor,
      initialValue = White.withAbsoluteOpacity(0),
      validator = settingValidatorFactory.IsNullValidator("Color cannot be null"))

    ColorSetting(
      key = SIdDefaultPrimaryColor,
      initialValue = Black,
      validator = settingValidatorFactory.IsNullValidator("Color cannot be null"))

    ColorSetting(
      key = SIdDefaultSecondaryColor,
      initialValue = Black,
      validator = settingValidatorFactory.IsNullValidator("Color cannot be null"))

    ObjectSetting[HorizontalAlignment](
      key = SIdDefaultHorizontalAlignment,
      initialValue = HALeft,
      validator = settingValidatorFactory.EmptyValidator)

    ObjectSetting[VerticalAlignment](
      key = SIdDefaultVerticalAlignment,
      initialValue = VAMiddle,
      validator = settingValidatorFactory.EmptyValidator)

  }

}
