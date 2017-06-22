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
 * Companion object for [[Pos1]].
 *
 * @author Aleksi Lukkarinen
 */
object Pos1 {

  /** The origo of a one-dimensional coordinate system. */
  val Origo = new Pos1(0)

  /**
   * Creates a new [[Pos1]] instance.
   *
   * @param valuePixels
   * @tparam ValueType
   *
   * @return
   */
  def apply[ValueType](valuePixels: ValueType): Pos1[ValueType] = {
    new Pos1(valuePixels)
  }

}




/**
 * Position in a one-dimensional coordinate system.
 *
 * @param valueInPixels
 * @tparam ValueType
 *
 * @author Aleksi Lukkarinen
 */
case class Pos1[ValueType] private(
    valueInPixels: ValueType)
    extends CartesianPosition(Seq(valueInPixels)) {

}
