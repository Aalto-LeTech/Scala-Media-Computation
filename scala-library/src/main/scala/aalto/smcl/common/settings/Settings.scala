package aalto.smcl.common.settings


import aalto.smcl.common.Color
import aalto.smcl.common.settings.SettingKeys.{BooleanSettingKey, ColorSettingKey, IntSettingKey, StringSettingKey}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Settings {

  /** */
  private[this] val _booleanMap =
    collection.mutable.HashMap[BooleanSettingKey, BooleanSetting]()

  /** */
  private[this] val _intMap =
    collection.mutable.HashMap[IntSettingKey, IntSetting]()

  /** */
  private[this] val _stringMap =
    collection.mutable.HashMap[StringSettingKey, StringSetting]()

  /** */
  private[this] val _colorMap =
    collection.mutable.HashMap[ColorSettingKey, ColorSetting]()

  /**
   *
   *
   * @return
   */
  def booleans() = _booleanMap.toMap

  /**
   *
   *
   * @return
   */
  def ints() = _intMap.toMap

  /**
   *
   *
   * @return
   */
  def strings() = _stringMap.toMap

  /**
   *
   *
   * @return
   */
  def colors() = _colorMap.toMap

  /**
   *
   *
   * @param value
   * @return
   */
  def += (value: Setting[_]): Unit = value match {
    case b: BooleanSetting => _booleanMap += (b.name -> b)
    case i: IntSetting     => _intMap += (i.name -> i)
    case s: StringSetting  => _stringMap += (s.name -> s)
    case c: ColorSetting   => _colorMap += (c.name -> c)
    case _                 => throw new UnknownSettingTypeError(value)
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def get(key: SettingKeys.Value): Option[Setting[_]] = key match {
    case b: BooleanSettingKey => _booleanMap.get(b)
    case i: IntSettingKey     => _intMap.get(i)
    case s: StringSettingKey  => _stringMap.get(s)
    case c: ColorSettingKey   => _colorMap.get(c)
    case _                    => throw new UnknownSettingTypeError(key)
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def apply(key: SettingKeys.Value): Setting[_] = key match {
    case b: BooleanSettingKey =>
      if (!_booleanMap.contains(b))
        throw new UninitializedSettingError(b)
      _booleanMap(b).asInstanceOf[Setting[_]]

    case i: IntSettingKey =>
      if (!_intMap.contains(i))
        throw new UninitializedSettingError(i)
      _intMap(i).asInstanceOf[Setting[_]]

    case s: StringSettingKey =>
      if (!_stringMap.contains(s))
        throw new UninitializedSettingError(s)
      _stringMap(s).asInstanceOf[Setting[_]]

    case c: ColorSettingKey =>
      if (!_colorMap.contains(c))
        throw new UninitializedSettingError(c)
      _colorMap(c).asInstanceOf[Setting[_]]

    case _ => throw new UnknownSettingTypeError(key)
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def settingFor(key: SettingKeys.Value): Setting[_] = apply(key)

  /**
   *
   *
   * @param key
   * @return
   */
  def booleanSettingFor(key: BooleanSettingKey): BooleanSetting =
    apply(key).asInstanceOf[BooleanSetting]

  /**
   *
   *
   * @param key
   * @return
   */
  def intSettingFor(key: IntSettingKey): IntSetting =
    apply(key).asInstanceOf[IntSetting]

  /**
   *
   *
   * @param key
   * @return
   */
  def stringSettingFor(key: StringSettingKey): StringSetting =
    apply(key).asInstanceOf[StringSetting]

  /**
   *
   *
   * @param key
   * @return
   */
  def colorSettingFor(key: ColorSettingKey): ColorSetting =
    apply(key).asInstanceOf[ColorSetting]

  /**
   *
   *
   * @param key
   * @return
   */
  def isTrueThat(key: BooleanSettingKey): Boolean = booleanSettingFor(key).value


  /**
   *
   *
   * @param key
   * @return
   */
  def intFor(key: IntSettingKey): Int = intSettingFor(key).value

  /**
   *
   *
   * @param key
   * @return
   */
  def stringFor(key: StringSettingKey): String = stringSettingFor(key).value

  /**
   *
   *
   * @param key
   * @return
   */
  def colorFor(key: ColorSettingKey): Color = colorSettingFor(key).value

  /**
   *
   */
  def resetAll(): Unit = {
    Seq(_booleanMap, _colorMap, _intMap, _stringMap).foreach {_.values.foreach {_.reset()}}
  }

}
