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

package aalto.smcl.geometry


/**
 * Companion object for the [[Len]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Len {

  /**
   * Creates a new [[Len]] instance.
   *
   * @param valueInPixels
   *
   * @return
   */
  def apply(valueInPixels: Int): Len = {
    validateLength(valueInPixels)

    new Len(valueInPixels)
  }

  private
  def validateLength(valueInPixels: Int): Unit = {
    require(
      valueInPixels >= 0,
      s"Length cannot be negative (was $valueInPixels)")
  }

}




/**
 * Length of an object expressed as an [[Int]] value.
 *
 * @param valueInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Len private(
    override val valueInPixels: Int)
    extends AbstractLength[Int](valueInPixels) {

}
