package aalto.smcl.images


import aalto.smcl.common.settings.{Setting, BooleanSetting, ColorSetting, IntSetting}
import aalto.smcl.common.{GS, PresetColors}
import aalto.smcl.images.SettingKeys._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] object SettingsInitializer {

  /**
   *
   */
  def perform(): Unit = {

    GS += BooleanSetting(name = NewBitmapsAreDisplayedAutomatically, initialValue = true)

    GS += BooleanSetting(name = DisplayBitmapsAutomaticallyAfterOperations, initialValue = true)

    GS += BooleanSetting(name = ShapesHaveBordersByDefault, initialValue = true)

    GS += BooleanSetting(name = ShapesHaveFillingsByDefault, initialValue = false)

    GS += IntSetting(
      name = DefaultBitmapWidthInPixels,
      initialValue = 50,
      validator = {value =>
        if (value < 10) Option(new IllegalArgumentException("Bitmap width must be at least 10 pixels"))
        else None
      })

    GS += IntSetting(
      name = DefaultBitmapHeightInPixels,
      initialValue = 50,
      validator = {value =>
        if (value < 10) Option(new IllegalArgumentException("Bitmap height must be at least 10 pixels"))
        else None
      })

    GS += IntSetting(
      name = DefaultCircleRadiusInPixels,
      initialValue = 10,
      validator = {value =>
        if (value < 1) Option(new IllegalArgumentException("Circle radius must be at least 1 pixel"))
        else None
      })

    GS += IntSetting(
      name = DefaultRoundingWidthInPixels,
      initialValue = 10,
      validator = {value =>
        if (value < 1) Option(new IllegalArgumentException("Rounding width must be at least 1 pixel"))
        else None
      })

    GS += IntSetting(
      name = DefaultRoundingHeightInPixels,
      initialValue = 10,
      validator = {value =>
        if (value < 1) Option(new IllegalArgumentException("Rounding height must be at least 1 pixel"))
        else None
      })

    GS += IntSetting(
      name = DefaultArcStartAngle,
      initialValue = 0,
      validator = Setting.EmptyValidator)

    GS += IntSetting(
      name = DefaultArcAngle,
      initialValue = 180,
      validator = Setting.EmptyValidator)

    GS += ColorSetting(
      name = DefaultBackground,
      initialValue = PresetColors('white),
      validator = {value =>
        if (value == null) Option(new IllegalArgumentException("Color cannot be null"))
        else None
      })

    GS += ColorSetting(
      name = DefaultPrimary,
      initialValue = PresetColors('black),
      validator = {value =>
        if (value == null) Option(new IllegalArgumentException("Color cannot be null"))
        else None
      })

    GS += ColorSetting(
      name = DefaultSecondary,
      initialValue = PresetColors('black),
      validator = {value =>
        if (value == null) Option(new IllegalArgumentException("Color cannot be null"))
        else None
      })
  }

}
