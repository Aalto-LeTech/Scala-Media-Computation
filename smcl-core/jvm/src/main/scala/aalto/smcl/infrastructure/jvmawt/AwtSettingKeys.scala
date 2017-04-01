package aalto.smcl.infrastructure.jvmawt

import aalto.smcl.infrastructure.BaseSettingKeys._




/**
  *
  *
  * @author Aleksi Lukkarinen
  */
trait AwtSettingKeys {

  /** */
  //noinspection SpellCheckingInspection
  case object AwtAffTransfInterpMethod extends
      EnumSettingKey[AwtAffineTransformationInterpolationMethod.Value]

}
