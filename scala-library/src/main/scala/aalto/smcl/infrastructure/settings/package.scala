package aalto.smcl.infrastructure


import aalto.smcl.init.InitializableModule




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object settings extends InitializableModule {

  /** */
  lazy val BaseSettingKeys: BaseSettingKeys = new BaseSettingKeys()

  /** */
  lazy val SettingValidatorFactory: SettingValidatorFactory = new SettingValidatorFactory()

}
