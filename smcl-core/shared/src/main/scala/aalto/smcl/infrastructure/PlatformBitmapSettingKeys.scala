package aalto.smcl.infrastructure

import aalto.smcl.infrastructure.BaseSettingKeys._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
trait PlatformBitmapSettingKeys {

  // @formatter:off

  /** */
  case object CanvasesAreResizedBasedOnTransformations extends BooleanSettingKey

  /** */
  case object DefaultBackground extends ColorSettingKey

}
