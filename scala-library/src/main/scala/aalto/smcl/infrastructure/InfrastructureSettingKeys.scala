package aalto.smcl.infrastructure


import aalto.smcl.SMCL
import aalto.smcl.infrastructure.BaseSettingKeys._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait InfrastructureSettingKeys {

  SMCL.performInitialization(ModuleInitializationPhase.Early)


  // @formatter:off

  /** */
  case object PlatformBitmapInterpolationMethod extends EnumSettingKey[BitmapInterpolationMethod.Value]

}
