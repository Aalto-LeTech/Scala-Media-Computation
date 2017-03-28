package aalto.smcl.infrastructure.exceptions


import aalto.smcl.infrastructure.BaseSettingKeys




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLSettingValidationError private[smcl](setting: BaseSettingKeys.Value[_], cause: Throwable)
  extends RuntimeException(
    s"""Validation of setting "${setting.toString}" failed (see the upstream exception).""",
    cause) {

}
