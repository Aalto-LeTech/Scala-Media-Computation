package aalto.smcl.infrastructure




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object settings {

  /** */
  lazy val BaseSettingKeys: BaseSettingKeys = new BaseSettingKeys()

  /** */
  lazy val SettingValidatorFactory: SettingValidatorFactory = new SettingValidatorFactory()

}
