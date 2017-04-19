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

package aalto.smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class SettingValidatorFactory() {

  /** */
  val EmptyValidator = null

  /**
   *
   *
   * @param testFailingIfTrue
   * @param errorMessage
   * @tparam SettingType
   *
   * @return
   */
  def conditionFalseValidator[SettingType](
      testFailingIfTrue: SettingType => Boolean,
      errorMessage: String): SettingType => Option[Throwable] = {
    {
      value =>
        if (testFailingIfTrue(value)) Option(new IllegalArgumentException(errorMessage))
        else None
    }
  }

  /**
   *
   *
   * @param errorMessage
   * @tparam SettingType
   *
   * @return
   */
  def IsNullValidator[SettingType](errorMessage: String): SettingType => Option[Throwable] =
    conditionFalseValidator({
      _ == null
    }, errorMessage)

}
