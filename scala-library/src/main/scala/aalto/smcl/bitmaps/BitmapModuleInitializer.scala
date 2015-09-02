package aalto.smcl.bitmaps


import aalto.smcl.{GS, ModuleInitializer}
import aalto.smcl.colors.{RGBAColor, PresetColors}
import aalto.smcl.infrastructure.{HorizontalAlignment, VerticalAlignment}
import aalto.smcl.infrastructure.settings.Setting
import aalto.smcl.infrastructure.settings.SettingValidatorFactory._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object BitmapModuleInitializer extends ModuleInitializer {

  //
  // Initialize settings
  //
  addInitializer {() =>
    GS += new Setting[Boolean](
      key = NewBitmapsAreDisplayedAutomatically,
      initialValue = false,
      validator = EmptyValidator)

    GS += new Setting[Boolean](
      key = BitmapsAreDisplayedAutomaticallyAfterOperations,
      initialValue = false,
      validator = EmptyValidator)

    GS += new Setting[Boolean](
      key = ShapesHaveBordersByDefault,
      initialValue = true,
      validator = EmptyValidator)

    GS += new Setting[Boolean](
      key = ShapesHaveFillingsByDefault,
      initialValue = false,
      validator = EmptyValidator)

    GS += new Setting[Boolean](
      key = CanvasesAreResizedBasedOnTransformations,
      initialValue = true,
      validator = EmptyValidator)

    GS += new Setting[Int](
      key = DefaultBitmapWidthInPixels,
      initialValue = 50,
      validator = BitmapValidatorFunctionFactory.BitmapWidthValidator())

    GS += new Setting[Int](
      key = DefaultBitmapHeightInPixels,
      initialValue = 50,
      validator = BitmapValidatorFunctionFactory.BitmapHeightValidator())

    GS += new Setting[Int](
      key = BitmapWidthWarningLimitInPixels,
      initialValue = 800,
      validator = BitmapValidatorFunctionFactory.BitmapWidthValidator())

    GS += new Setting[Int](
      key = BitmapHeightWarningLimitInPixels,
      initialValue = 800,
      validator = BitmapValidatorFunctionFactory.BitmapHeightValidator())

    GS += new Setting[Int](
      key = DefaultCircleRadiusInPixels,
      initialValue = 10,
      validator = ConditionFalseValidator[Int]({
        _ < 1
      }, "Circle radius must be at least 1 pixel"))

    GS += new Setting[Int](
      key = DefaultRoundingWidthInPixels,
      initialValue = 20,
      validator = ConditionFalseValidator[Int]({
        _ < 1
      }, "Rounding width must be at least 1 pixel"))

    GS += new Setting[Int](
      key = DefaultRoundingHeightInPixels,
      initialValue = 20,
      validator = ConditionFalseValidator[Int]({
        _ < 1
      }, "Rounding height must be at least 1 pixel"))

    GS += new Setting[Int](
      key = DefaultPaddingInPixels,
      initialValue = 5,
      validator = ConditionFalseValidator[Int]({
        _ < 0
      }, "Padding cannot be negative"))

    GS += new Setting[Int](
      key = DefaultArcStartAngleInDegrees,
      initialValue = 0,
      validator = EmptyValidator)

    GS += new Setting[Int](
      key = DefaultArcAngleInDegrees,
      initialValue = 180,
      validator = EmptyValidator)

    GS += new Setting[RGBAColor](
      key = DefaultBackground,
      initialValue = PresetColors('white).withAbsoluteOpacity(0),
      validator = IsNullValidator("Color cannot be null"))

    GS += new Setting[RGBAColor](
      key = DefaultPrimary,
      initialValue = PresetColors('black),
      validator = IsNullValidator("Color cannot be null"))

    GS += new Setting[RGBAColor](
      key = DefaultSecondary,
      initialValue = PresetColors('black),
      validator = IsNullValidator("Color cannot be null"))

    GS += new Setting[HorizontalAlignment.Value](
      key = DefaultHorizontalAlignment,
      initialValue = HorizontalAlignment.Left,
      validator = EmptyValidator)

    GS += new Setting[VerticalAlignment.Value](
      key = DefaultVerticalAlignment,
      initialValue = VerticalAlignment.Middle,
      validator = EmptyValidator)
  }

}
