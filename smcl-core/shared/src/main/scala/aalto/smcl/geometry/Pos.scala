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
 * Companion object for the [[Pos]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** The origo of a two-dimensional Cartesian coordinate system. */
  val Origo = new Pos(0, 0)

  /**
   * Creates a new [[Pos]] instance.
   *
   * @param xInPixels
   * @param yInPixels
   * @tparam ValueType
   *
   * @return
   */
  def apply[ValueType](
      xInPixels: ValueType,
      yInPixels: ValueType): Pos[ValueType] = {

    new Pos(xInPixels, yInPixels)
  }

}




/**
 * Position in a two-dimensional Cartesian coordinate system.
 *
 * @param xInPixels
 * @param yInPixels
 * @tparam ValueType
 *
 * @author Aleksi Lukkarinen
 */
case class Pos[ValueType] private(
    xInPixels: ValueType,
    yInPixels: ValueType)
    extends CartesianPosition(Seq(xInPixels, yInPixels)) {

}
