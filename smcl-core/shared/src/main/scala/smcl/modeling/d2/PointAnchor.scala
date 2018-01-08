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

package smcl.modeling.d2


import smcl.infrastructure.InjectablesRegistry
import smcl.modeling.misc.PointBasedAnchor




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
    apply(coordinates, None)
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

    require(coordinates.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must " +
          s"be given (found: ${coordinates.length})")

    apply(coordinates.head, coordinates(1), name)
  }

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   *
   * @return
   */
  @inline
  def apply(
      xInPixels: Double,
      yInPixels: Double): PointAnchor = {

    apply(xInPixels, yInPixels, None)
  }

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      xInPixels: Double,
      yInPixels: Double,
      name: Option[String]): PointAnchor = {

    // TODO: Validate name

    new PointAnchor(xInPixels, yInPixels, name)
  }

}




/**
 *
 *
 * @param xInPixels
 * @param yInPixels
 * @param name
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
case class PointAnchor private(
    xInPixels: Double,
    yInPixels: Double,
    override val name: Option[String])
    extends PointBasedAnchor[Dims]
        with Anchor[HasAnchor]
        with PointAnchorMembers[HasAnchor] {

  /** */
  lazy val dimensions: Seq[Double] =
    Seq(xInPixels, yInPixels)

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
      yInPixels / anchored.height.inPixels)
  }

  /**
   *
   *
   * @param offsetX
   * @param offsetY
   *
   * @return
   */
  @inline
  def + (offsetX: Int, offsetY: Int): PointAnchor = {
    PointAnchor(
      xInPixels + offsetX,
      yInPixels + offsetY)
  }

  /**
   *
   *
   * @param offsetX
   * @param offsetY
   *
   * @return
   */
  @inline
  def - (offsetX: Int, offsetY: Int): PointAnchor = {
    PointAnchor(
      xInPixels - offsetX,
      yInPixels - offsetY)
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
      yInPixels + offset.height.inPixels)
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
      yInPixels - offset.height.inPixels)
  }

  /**
   *
   *
   * @param newXInPixels
   * @param newYInPixels
   * @param newName
   *
   * @return
   */
  @inline
  def copy(
      newXInPixels: Double,
      newYInPixels: Double,
      newName: Option[String]): PointAnchor = {

    PointAnchor(
      newXInPixels,
      newYInPixels,
      newName)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = {
    s"PointAnchor(x: $xInPixels px, y: $yInPixels px)"
  }

}
