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

package aalto.smcl.infrastructure.exceptions


import aalto.smcl.infrastructure.BaseSettingKeys




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object UninitializedSettingError {

  /**
   *
   *
   * @param settingKey
   *
   * @return
   */
  def apply(settingKey: BaseSettingKeys.Value[_]): UninitializedSettingError =
    UninitializedSettingError(settingKey, null)

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final case class UninitializedSettingError private[smcl](
    settingKey: BaseSettingKeys.Value[_], override val cause: Throwable)
    extends SMCLBaseError(
      s"""No setting with name "${settingKey.toString}" is initialized.""",
      cause)
