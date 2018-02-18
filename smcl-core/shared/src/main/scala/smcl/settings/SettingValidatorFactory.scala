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
private[smcl]
class SettingValidatorFactory() {

  /** */
  val EmptyValidator: Null = null

  /**
   *
   *
   * @param testFailingIfTrue
   * @param errorMessage
   * @tparam SettingDataType
   *
   * @return
   */
  def conditionFalseValidator[SettingDataType](
      testFailingIfTrue: SettingDataType => Boolean,
      errorMessage: String): SettingValidator[SettingDataType] = {

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
   * @tparam SettingDataType
   *
   * @return
   */
  def isNullValidator[SettingDataType](
      errorMessage: String): SettingValidator[SettingDataType] = {

    conditionFalseValidator(_ == null, errorMessage)
  }

  /**
   *
   *
   * @param errorMessage
   *
   * @return
   */
  def isNegativeIntValidator(
      errorMessage: String): SettingValidator[Int] = {

    conditionFalseValidator(_ < 0, errorMessage)
  }

  /**
   *
   *
   * @param errorMessage
   *
   * @return
   */
  def isNegativeDoubleValidator(
      errorMessage: String): SettingValidator[Double] = {

    conditionFalseValidator(_ < 0.0, errorMessage)
  }

}
