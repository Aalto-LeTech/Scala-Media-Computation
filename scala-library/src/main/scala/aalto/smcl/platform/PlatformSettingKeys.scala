package aalto.smcl.platform


import aalto.smcl.SMCL
import aalto.smcl.common.settings.BaseSettingKeys._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object PlatformSettingKeys {

  SMCL.performInitialization()


  // @formatter:off

  /** */
  case object PlatformBitmapInterpolationMethod extends EnumSettingKey[BitmapInterpolationMethod.Value]

}
