package aalto.smcl.platform


import aalto.smcl.SMCL
import aalto.smcl.infrastructure.settings.BaseSettingKeys
import BaseSettingKeys._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait PlatformSettingKeys {

  SMCL.performInitialization()


  // @formatter:off

  /** */
  case object PlatformBitmapInterpolationMethod extends EnumSettingKey[BitmapInterpolationMethod.Value]

}
