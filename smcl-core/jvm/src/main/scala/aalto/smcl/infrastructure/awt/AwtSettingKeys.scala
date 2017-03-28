package aalto.smcl.infrastructure.awt

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
