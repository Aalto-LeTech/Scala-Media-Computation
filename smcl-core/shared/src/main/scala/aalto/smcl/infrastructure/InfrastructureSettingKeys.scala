package aalto.smcl.infrastructure

import aalto.smcl.infrastructure.BaseSettingKeys._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait InfrastructureSettingKeys {

  // @formatter:off

  /** */
  case object PlatformBitmapInterpolationMethod extends EnumSettingKey[BitmapInterpolationMethod.Value]

}
