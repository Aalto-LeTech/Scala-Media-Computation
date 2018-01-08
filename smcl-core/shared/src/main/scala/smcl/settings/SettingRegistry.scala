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


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait SettingRegistry {

  /** A type for a String -> Setting mapping. */
  type SettingMap = Map[String, Setting[_]]

  /** A map that contains all initialized settings. */
  private[smcl]
  var allSettings: SettingMap = Map()

  /**
   * Returns a map that contains all initialized settings.
   *
   * @return
   */
  def Settings: SettingMap = allSettings




  /**
   * Registers settings.
   *
   * @author Aleksi Lukkarinen
   */
  class SettingRegisterer() {

    /**
     * Registers a single setting.
     *
     * @param registree the setting to be registered
     */
    def register(registree: Setting[_]): Unit = {
      allSettings = allSettings + (registree.key -> registree)
    }

  }




}
