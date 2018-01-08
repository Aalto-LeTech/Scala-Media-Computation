/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.settings


import smcl.colors.rgb.Color




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class RichSettingMap private[settings](val self: SettingMap) {

  /** First text paragraph of the description of [[RichSettingMap]]. */
  val descriptionTitle: String = "SettingMap"

  /** Information about this [[RichSettingMap]] instance. */
  def describedProperties = Map(
    "Number of settings" -> self.size
  )

  /**
   *
   *
   * @return
   */
  def groupedByType: Map[String, SettingMap] = {
    self.groupBy[String]({case (_, value) => value.typeNamePlural})
  }

  /**
   *
   *
   * @param key
   *
   * @return
   */
  def getBoolSetting(key: String): Setting[Boolean] = {
    self.get(key).asInstanceOf[Setting[Boolean]]
  }

  /**
   *
   *
   * @param key
   *
   * @return
   */
  def getIntSetting(key: String): Setting[Int] = {
    self.get(key).asInstanceOf[Setting[Int]]
  }

  /**
   *
   *
   * @param key
   *
   * @return
   */
  def getStringSetting(key: String): Setting[String] = {
    self.get(key).asInstanceOf[Setting[String]]
  }

  /**
   *
   *
   * @param key
   *
   * @return
   */
  def getColorSetting(key: String): Setting[Color] = {
    self.get(key).asInstanceOf[Setting[Color]]
  }

  /**
   *
   *
   * @param key
   *
   * @return
   */
  def getEnumSetting[EnumType](key: String): ObjectSetting[EnumType] = {
    self.get(key).asInstanceOf[ObjectSetting[EnumType]]
  }

  /**
   *
   */
  def list(): Unit = {
    val settingGroups = groupedByType

    if (settingGroups.isEmpty)
      println("No settings defined")

    settingGroups foreach {case (groupKey, group) =>
      println(s"\n${groupKey.capitalize}:")

      group foreach {case (_, setting) =>
        println(s" - ${setting.key}: ${setting.value}")
      }
    }
  }

  /**
   *
   */
  def resetAll(): Unit = self.values foreach {_.reset()}

}
