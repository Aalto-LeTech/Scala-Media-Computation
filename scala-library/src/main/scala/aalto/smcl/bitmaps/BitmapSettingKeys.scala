package aalto.smcl.bitmaps


import aalto.smcl.common.settings.BaseSettingKeys._
import aalto.smcl.common.{HorizontalAlignment, VerticalAlignment}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapSettingKeys {


  // @formatter:off

  /** */
  case object NewBitmapsAreDisplayedAutomatically extends BooleanSettingKey

  /** */
  case object DisplayBitmapsAutomaticallyAfterOperations extends BooleanSettingKey

  /** */
  case object ShapesHaveBordersByDefault extends BooleanSettingKey

  /** */
  case object ShapesHaveFillingsByDefault extends BooleanSettingKey

  /** */
  case object DefaultBitmapWidthInPixels extends IntSettingKey

  /** */
  case object DefaultBitmapHeightInPixels extends IntSettingKey

  /** */
  case object DefaultRoundingWidthInPixels extends IntSettingKey

  /** */
  case object DefaultRoundingHeightInPixels extends IntSettingKey

  /** */
  case object DefaultCircleRadiusInPixels extends IntSettingKey

  /** */
  case object DefaultArcStartAngleInDegrees extends IntSettingKey

  /** */
  case object DefaultArcAngleInDgrees extends IntSettingKey

  /** */
  case object DefaultPaddingInPixels extends IntSettingKey

  /** */
  case object DefaultBackground extends ColorSettingKey

  /** */
  case object DefaultPrimary extends ColorSettingKey

  /** */
  case object DefaultSecondary extends ColorSettingKey

  /** */
  case object DefaultHorizontalAlignment extends EnumSettingKey[HorizontalAlignment.Value]

  /** */
  case object DefaultVerticalAlignment extends EnumSettingKey[VerticalAlignment.Value]

}
