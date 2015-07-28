package aalto.smcl.common.settings


import aalto.smcl.common.Color




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Settings extends collection.mutable.Map[SettingKey, Setting[_]] with Mutable {

  /** */
  private[this] val _currentSettingsMap =
    collection.mutable.HashMap[SettingKey, Setting[_]]()

  /**
   *
   *
   * @param kv
   * @return
   */
  override def += (kv: (SettingKey, Setting[_])): Settings.this.type =
    (_currentSettingsMap += kv).asInstanceOf[Settings.this.type]

  /**
   *
   *
   * @param s
   * @return
   */
  def += (s: Setting[_]): collection.mutable.Map[SettingKey, Setting[_]] =
    _currentSettingsMap += (s.name -> s)


  /**
   *
   *
   * @param key
   * @return
   */
  override def -= (key: SettingKey): Settings.this.type =
    throw new UnsupportedOperationException("Removal from the settings map is prohibited.")

  /**
   *
   *
   * @param key
   * @return
   */
  override def get(key: SettingKey): Option[Setting[_]] = {
    if (!_currentSettingsMap.contains(key))
      throw new UninitializedSettingError(key)

    _currentSettingsMap.get(key)
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def settingFor(key: SettingKey): Setting[_] = get(key).get

  /**
   *
   *
   * @param key
   * @return
   */
  def isTrueThat(key: SettingKey): Boolean = {
    val setting = settingFor(key)
    val value = setting.value

    if (!setting.isInstanceOf[BooleanSetting])
      throw new WrongSettingTypeError(key, value)

    value.asInstanceOf[Boolean]
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def intFor(key: SettingKey): Int = {
    val setting = settingFor(key)
    val value = setting.value

    if (!setting.isInstanceOf[IntSetting])
      throw new WrongSettingTypeError(key, value)

    value.asInstanceOf[Int]
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def stringFor(key: SettingKey): String = {
    val setting = settingFor(key)
    val value = setting.value

    if (!setting.isInstanceOf[StringSetting])
      throw new WrongSettingTypeError(key, value)

    value.asInstanceOf[String]
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def colorFor(key: SettingKey): Color = {
    val setting = settingFor(key)
    val value = setting.value

    if (!setting.isInstanceOf[ColorSetting])
      throw new WrongSettingTypeError(key, value)

    value.asInstanceOf[Color]
  }

  /**
   *
   *
   * @return
   */
  override def iterator: Iterator[(SettingKey, Setting[_])] =
    _currentSettingsMap.iterator

  /**
   *
   */
  def resetAll(): Unit = _currentSettingsMap.values.foreach {_.reset()}

}
