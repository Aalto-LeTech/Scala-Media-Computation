package aalto.smcl.platform


import aalto.smcl.SMCL
import aalto.smcl.infrastructure.settings.BaseSettingKeys
import BaseSettingKeys._
import aalto.smcl.init.ModuleInitializationPhase




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
