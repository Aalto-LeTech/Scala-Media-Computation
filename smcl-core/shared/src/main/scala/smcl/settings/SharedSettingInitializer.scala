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

package smcl.settings


import smcl.colors.rgb._
import smcl.infrastructure.SettingInitializer
import smcl.pictures.BitmapValidatorFunctionFactory




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

    DoubleSetting(
      key = SIdDefaultCircleRadiusInPixels,
      initialValue = 10,
      validator = settingValidatorFactory
          .isNegativeDoubleValidator("Circle radius cannot be negative"))

    DoubleSetting(
      key = SIdDefaultStarCuspRadiusInPixels,
      initialValue = 6,
      validator = settingValidatorFactory
          .isNegativeDoubleValidator("Star cusp radius cannot be negative"))

    DoubleSetting(
      key = SIdDefaultRoundingWidthInPixels,
      initialValue = 20,
      validator = settingValidatorFactory
          .isNegativeDoubleValidator("Rounding width cannot be negative"))

    DoubleSetting(
      key = SIdDefaultRoundingHeightInPixels,
      initialValue = 20,
      validator = settingValidatorFactory
          .isNegativeDoubleValidator("Rounding height cannot be negative"))

    DoubleSetting(
      key = SIdDefaultPaddingInPixels,
      initialValue = 5,
      validator = settingValidatorFactory
          .isNegativeDoubleValidator("Padding cannot be negative"))

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
      validator = settingValidatorFactory.isNullValidator("Color cannot be null"))

    ColorSetting(
      key = SIdDefaultPrimaryColor,
      initialValue = Black,
      validator = settingValidatorFactory.isNullValidator("Color cannot be null"))

    ColorSetting(
      key = SIdDefaultSecondaryColor,
      initialValue = Black,
      validator = settingValidatorFactory.isNullValidator("Color cannot be null"))

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
