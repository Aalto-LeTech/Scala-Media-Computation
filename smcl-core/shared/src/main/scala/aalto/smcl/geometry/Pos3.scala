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
 * Companion object for [[Pos3]].
 *
 * @author Aleksi Lukkarinen
 */
object Pos3 {

  /** The origo of a three-dimensional Cartesian coordinate system. */
  val Origo = new Pos3(0, 0, 0)

  /**
   * Creates a new [[Pos3]] instance.
   *
   * @param xInPixels
   * @param yInPixels
   * @param zInPixels
   * @tparam ValueType
   *
   * @return
   */
  def apply[ValueType](
      xInPixels: ValueType,
      yInPixels: ValueType,
      zInPixels: ValueType): Pos3[ValueType] = {

    new Pos3(xInPixels, yInPixels, zInPixels)
  }

}




/**
 * Position in a three-dimensional Cartesian coordinate system.
 *
 * @param xInPixels
 * @param yInPixels
 * @param zInPixels
 * @tparam ValueType
 *
 * @author Aleksi Lukkarinen
 */
case class Pos3[ValueType] private(
    xInPixels: ValueType,
    yInPixels: ValueType,
    zInPixels: ValueType)
    extends CartesianPosition(
      Seq(xInPixels, yInPixels, zInPixels)) {

}
