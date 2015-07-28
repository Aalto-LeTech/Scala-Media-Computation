package aalto.smcl.common.settings


import scala.collection.mutable._

import aalto.smcl.common.Color




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class SmclSettings extends Mutable {

  /** */
  private[this] val _defaultSettingsMap = HashMap[SmclSettingKey, Any]()

  /** */
  private[this] var _currentSettingsMap = HashMap[SmclSettingKey, Any]()

  /** */
  def map: collection.Map[SmclSettingKey, Any] = _currentSettingsMap.toMap

  /** */
  def defaultMap: collection.Map[SmclSettingKey, Any] = _defaultSettingsMap.toMap

  /**
   *
   *
   * @param key
   * @param value
   * @return
   */
  private[smcl] def init(key: SmclSettingKey, value: Any) = {
    val kv = key -> value

    _defaultSettingsMap += kv
    _currentSettingsMap += kv
  }

  /**
   *
   *
   * @param key
   * @param value
   * @return
   */
  def set(key: SmclSettingKey, value: Any) = {
    if (!_defaultSettingsMap.keySet.contains(key))
      throw new UninitializedSettingError(key)

    _currentSettingsMap += (key -> value)
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def get(key: SmclSettingKey): Any =
    _currentSettingsMap.get(key) match {
      case Some(_) => _
      case _       => throw new UninitializedSettingError(key)
    }

  /**
   *
   *
   * @param key
   * @return
   */
  def getBoolean(key: SmclSettingKey): Boolean =
    _currentSettingsMap.get(key) match {
      case Some(s: Boolean) => s
      case Some(s)          => throw new WrongSettingTypeError(key, s)
      case _                => throw new UninitializedSettingError(key)
    }

  /**
   *
   *
   * @param key
   * @return
   */
  def getString(key: SmclSettingKey): String =
    _currentSettingsMap.get(key) match {
      case Some(s: String) => s
      case Some(s)         => throw new WrongSettingTypeError(key, s)
      case _               => throw new UninitializedSettingError(key)
    }

  /**
   *
   *
   * @param key
   * @return
   */
  def getInt(key: SmclSettingKey): Int =
    _currentSettingsMap.get(key) match {
      case Some(s: Int) => s
      case Some(s)      => throw new WrongSettingTypeError(key, s)
      case _            => throw new UninitializedSettingError(key)
    }

  /**
   *
   *
   * @param key
   * @return
   */
  def getDouble(key: SmclSettingKey): Double =
    _currentSettingsMap.get(key) match {
      case Some(s: Double) => s
      case Some(s)         => throw new WrongSettingTypeError(key, s)
      case _               => throw new UninitializedSettingError(key)
    }

  /**
   *
   *
   * @param key
   * @return
   */
  def getColor(key: SmclSettingKey): Color =
    _currentSettingsMap.get(key) match {
      case Some(s: Color) => s
      case Some(s)        => throw new WrongSettingTypeError(key, s)
      case _              => throw new UninitializedSettingError(key)
    }

  /**
   *
   *
   * @param key
   * @return
   */
  def reset(key: SmclSettingKey): Any =
    _defaultSettingsMap.get(key) match {
      case Some(s) => _currentSettingsMap += (key -> s)
      case _       => throw new UninitializedSettingError(key)
    }

  /**
   *
   *
   */
  def resetAll(): Unit = _currentSettingsMap = _defaultSettingsMap.clone()

}
