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

package aalto.smcl.modeling.d3


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
   * @param coordinates
   *
   * @return
   */
  @inline
  def apply(coordinates: Seq[Double]): PointAnchor = {
    apply(
      coordinates.head,
      coordinates(1),
      coordinates(2),
      None)
  }

  /**
   *
   *
   * @param coordinates
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      coordinates: Seq[Double],
      name: Option[String]): PointAnchor = {

    apply(
      coordinates.head,
      coordinates(1),
      coordinates(2),
      name)
  }

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   * @param zInPixels
   *
   * @return
   */
  @inline
  def apply(
      xInPixels: Double,
      yInPixels: Double,
      zInPixels: Double): PointAnchor = {

    apply(xInPixels, yInPixels, zInPixels, None)
  }

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   * @param zInPixels
   * @param name
   *
   * @return
   */
  def apply(
      xInPixels: Double,
      yInPixels: Double,
      zInPixels: Double,
      name: Option[String]): PointAnchor = {

    // TODO: Validate name

    new PointAnchor(xInPixels, yInPixels, zInPixels, name)
  }

}




/**
 *
 *
 * @param xInPixels
 * @param yInPixels
 * @param zInPixels
 * @param name
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
case class PointAnchor private(
    xInPixels: Double,
    yInPixels: Double,
    zInPixels: Double,
    override val name: Option[String])
    extends AbstractPointAnchor[Dims](
      Seq(xInPixels, yInPixels, zInPixels), name)
            with Anchor[HasAnchor]
            with PointAnchorMembers[HasAnchor] {

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  @inline
  def toRatioAnchorFor(anchored: HasDims): RatioAnchor = {
    RatioAnchor(
      xInPixels / anchored.width.inPixels,
      yInPixels / anchored.height.inPixels,
      zInPixels / anchored.depth.inPixels)
  }

  /**
   *
   *
   * @param offsetX
   * @param offsetY
   * @param offsetZ
   *
   * @return
   */
  @inline
  def + (offsetX: Int, offsetY: Int, offsetZ: Int): PointAnchor = {
    PointAnchor(
      xInPixels + offsetX,
      yInPixels + offsetY,
      yInPixels + offsetZ)
  }

  /**
   *
   *
   * @param offsetX
   * @param offsetY
   * @param offsetZ
   *
   * @return
   */
  @inline
  def - (offsetX: Int, offsetY: Int, offsetZ: Int): PointAnchor = {
    PointAnchor(
      xInPixels - offsetX,
      yInPixels - offsetY,
      yInPixels - offsetZ)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def + (offset: Dims): PointAnchor = {
    PointAnchor(
      xInPixels + offset.width.inPixels,
      yInPixels + offset.height.inPixels,
      zInPixels + offset.depth.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def - (offset: Dims): PointAnchor = {
    PointAnchor(
      xInPixels - offset.width.inPixels,
      yInPixels - offset.height.inPixels,
      zInPixels - offset.depth.inPixels)
  }

}
