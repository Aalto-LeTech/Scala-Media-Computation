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

package aalto.smcl.modeling.d1


import aalto.smcl.infrastructure.InjectablesRegistry
import aalto.smcl.modeling.misc.AbstractPointAnchor




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
   *
   * @return
   */
  @inline
  def apply(xInPixels: Double): PointAnchor = {
    apply(xInPixels, None)
  }

  /**
   *
   *
   * @param xInPixels
   * @param name
   *
   * @return
   */
  def apply(
      xInPixels: Double,
      name: Option[String]): PointAnchor = {

    // TODO: Validate name

    new PointAnchor(xInPixels, name)
  }

}




/**
 *
 *
 * @param xInPixels
 * @param name
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
case class PointAnchor private(
    xInPixels: Double,
    override val name: Option[String])
    extends AbstractPointAnchor[Dims](Seq(xInPixels), name)
            with Anchor[HasAnchor]
            with PointAnchorMembers[HasAnchor]
