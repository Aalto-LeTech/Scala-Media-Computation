package aalto.smcl.platform


import aalto.smcl.{ModuleInitializationPhase, SMCL}
import aalto.smcl.infrastructure.settings.BaseSettingKeys
import BaseSettingKeys._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait PlatformSettingKeys {

  SMCL.performInitialization(ModuleInitializationPhase.Early)


  // @formatter:off

  /** */
  case object PlatformBitmapInterpolationMethod extends EnumSettingKey[BitmapInterpolationMethod.Value]

}
