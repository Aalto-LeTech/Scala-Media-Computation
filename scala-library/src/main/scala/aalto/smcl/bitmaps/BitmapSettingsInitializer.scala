package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.BitmapSettingKeys._
import aalto.smcl.bitmaps.immutable.primitives.BitmapValidatorFactory
import aalto.smcl.common.settings.Setting
import aalto.smcl.common.settings.SettingValidatorFactory._
import aalto.smcl.common.{RGBAColor, GS, HorizontalAlignment, PresetColors, VerticalAlignment}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] object BitmapSettingsInitializer {

  /**
   *
   */
  def perform(): Unit = {

    GS += new Setting[Boolean](
      key = NewBitmapsAreDisplayedAutomatically,
      initialValue = true,
      validator = EmptyValidator)

    GS += new Setting[Boolean](
      key = DisplayBitmapsAutomaticallyAfterOperations,
      initialValue = true,
      validator = EmptyValidator)

    GS += new Setting[Boolean](
      key = ShapesHaveBordersByDefault,
      initialValue = true,
      validator = EmptyValidator)

    GS += new Setting[Boolean](
      key = ShapesHaveFillingsByDefault,
      initialValue = false,
      validator = EmptyValidator)

    GS += new Setting[Int](
      key = DefaultBitmapWidthInPixels,
      initialValue = 50,
      validator = BitmapValidatorFactory.BitmapWidthValidator())

    GS += new Setting[Int](
      key = DefaultBitmapHeightInPixels,
      initialValue = 50,
      validator = BitmapValidatorFactory.BitmapHeightValidator())

    GS += new Setting[Int](
      key = DefaultCircleRadiusInPixels,
      initialValue = 10,
      validator = ConditionFalseValidator[Int]({_ < 1}, "Circle radius must be at least 1 pixel"))

    GS += new Setting[Int](
      key = DefaultRoundingWidthInPixels,
      initialValue = 10,
      validator = ConditionFalseValidator[Int]({_ < 1}, "Rounding width must be at least 1 pixel"))

    GS += new Setting[Int](
      key = DefaultRoundingHeightInPixels,
      initialValue = 10,
      validator = ConditionFalseValidator[Int]({_ < 1}, "Rounding height must be at least 1 pixel"))

    GS += new Setting[Int](
      key = DefaultPaddingInPixels,
      initialValue = 5,
      validator = ConditionFalseValidator[Int]({_ < 0}, "Padding cannot be negative"))

    GS += new Setting[Int](
      key = DefaultArcStartAngleInDegrees,
      initialValue = 0,
      validator = EmptyValidator)

    GS += new Setting[Int](
      key = DefaultArcAngleInDgrees,
      initialValue = 180,
      validator = EmptyValidator)

    GS += new Setting[RGBAColor](
      key = DefaultBackground,
      initialValue = PresetColors('white),
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
