package aalto.smcl.infrastructure


import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure.BaseSettingKeys._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Settings {

  /** */
  private[this] val _settingMap =
    collection.mutable.HashMap[BaseSettingKeys.Value[_], Setting[_]]()

  /**
   *
   *
   * @return
   */
  def all() = _settingMap.toMap

  /**
   *
   * @return
   */
  def groupedByKeyType() = _settingMap.groupBy[String]({case (key, _) => key.typeNamePlural})

  /**
   *
   *
   * @param value
   * @return
   */
  def += (value: Setting[_]): Unit = _settingMap += (value.key -> value)

  /**
   *
   *
   * @param key
   * @return
   */
  def get(key: BaseSettingKeys.Value[_]): Option[Setting[_]] = _settingMap.get(key)

  /**
   *
   *
   * @param key
   * @return
   */
  def apply(key: BaseSettingKeys.Value[_]): Setting[_] = {
    if (!_settingMap.contains(key))
      throw new SMCLUninitializedSettingError(key)

    _settingMap(key)
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def settingFor(key: BaseSettingKeys.Value[_]): Setting[_] = apply(key)

  /**
   *
   *
   * @param key
   * @return
   */
  def booleanSettingFor(key: BooleanSettingKey): Setting[Boolean] =
    apply(key).asInstanceOf[Setting[Boolean]]

  /**
   *
   *
   * @param key
   * @return
   */
  def intSettingFor(key: IntSettingKey): Setting[Int] =
    apply(key).asInstanceOf[Setting[Int]]

  /**
   *
   *
   * @param key
   * @return
   */
  def stringSettingFor(key: StringSettingKey): Setting[String] =
    apply(key).asInstanceOf[Setting[String]]

  /**
   *
   *
   * @param key
   * @return
   */
  def colorSettingFor(key: ColorSettingKey): Setting[RGBAColor] =
    apply(key).asInstanceOf[Setting[RGBAColor]]

  /**
   *
   *
   * @param key
   * @return
   */
  def enumSettingFor[A](key: EnumSettingKey[A]): Setting[A] =
    apply(key).asInstanceOf[Setting[A]]

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
  def colorFor(key: ColorSettingKey): RGBAColor = colorSettingFor(key).value

  /**
   *
   *
   * @param key
   * @return
   */
  def optionFor[A](key: EnumSettingKey[A]): A = enumSettingFor[A](key).value

  /**
   *
   */
  def list(): Unit = {
    val settingGroups = groupedByKeyType()

    if (settingGroups.isEmpty)
      println("No settings defined")

    settingGroups foreach {case (groupKey, group) =>
      println(s"\n${groupKey.capitalize}:")

      group foreach {case (_, setting) =>
        println(s" - ${setting.key.simpleName}: ${setting.value}")
      }
    }
  }

  /**
   *
   */
  def resetAll(): Unit =
    _settingMap.values foreach {
      _.reset()
    }

  /**
   *
   */
  def toToken: String = {
    val sb = new StringBuilder(100)

    groupedByKeyType()
        .map({case (groupKey, group) => groupKey + ": " + group.size})
        .addString(sb,
          start = StrLeftAngleBracket + ReflectionUtils.shortTypeNameOf(this) + StrSemicolon + StrSpace,
          sep = StrSemicolon + StrSpace,
          end = StrRightAngleBracket)

    sb.toString()
  }

  /**
   *
   */
  override def toString: String = {
    val sb = new StringBuilder(100)
    val settingGroups = groupedByKeyType()

    sb.append(ReflectionUtils.shortTypeNameOf(this))

    if (settingGroups.isEmpty)
      return sb.append(StrComma).append(StrSpace).append("currently empty").append(StrPeriod).toString()

    sb.append(StrSpace + "containing" + StrSpace)

    val countStrings = groupedByKeyType() map {case (groupKey, group) =>
      val typeName = if (group.size == 1) group.head._1.typeNameSingular else group.head._1.typeNamePlural

      group.size.toString + StrSpace + typeName
    }

    val initials = countStrings.init
    if (initials.nonEmpty) {
      initials.addString(sb, StrComma + StrSpace)
      sb.append(StrSpace).append("and").append(StrSpace)
    }

    sb.append(countStrings.last).append(StrPeriod).toString()
  }

}
