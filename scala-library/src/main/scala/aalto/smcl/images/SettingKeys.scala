package aalto.smcl.images


import aalto.smcl.common.settings.SettingKey




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object SettingKeys {

  /** */
  sealed trait ImageSettingKey extends SettingKey

  // @formatter:off

  /** */
  case object NewBitmapsAreDisplayedAutomatically extends ImageSettingKey

  /** */
  case object DisplayBitmapsAutomaticallyAfterOperations extends ImageSettingKey

  /** */
  case object DefaultBitmapWidthInPixels extends ImageSettingKey

  /** */
  case object DefaultBitmapHeightInPixels extends ImageSettingKey

  /** */
  case object DefaultBackground extends ImageSettingKey

  /** */
  case object DefaultPrimary extends ImageSettingKey

  /** */
  case object DefaultSecondary extends ImageSettingKey

}
