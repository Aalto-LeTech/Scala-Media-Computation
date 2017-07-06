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

package aalto.smcl.modeling.d2


import aalto.smcl.infrastructure.InjectablesRegistry
import aalto.smcl.modeling.AbstractPointAnchor




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
object PointAnchor
    extends InjectablesRegistry {

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   * @param name
   *
   * @return
   */
  def apply(
      xInPixels: Double,
      yInPixels: Double,
      name: Option[String]): PointAnchor = {

    // TODO: Validate name

    PointAnchor(xInPixels, yInPixels, name)
  }

}




/**
 *
 *
 * @param name
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
case class PointAnchor private(
    xInPixels: Double,
    yInPixels: Double,
    override val name: Option[String])
    extends AbstractPointAnchor[Dims](Seq(xInPixels, yInPixels), name)
            with Anchor {

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  override def internalXWithin(
      anchored: HasAnchor): Double = xInPixels

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  override def internalYWithin(
      anchored: HasAnchor): Double = yInPixels

}
