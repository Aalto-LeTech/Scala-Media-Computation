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

package smcl.modeling.d1


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
   * @param xInPixels
   *
   * @return
   */
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
    extends PointBasedAnchor[Dims]
        with Anchor[HasAnchor]
        with PointAnchorMembers[HasAnchor] {

  /** */
  lazy val dimensions: Seq[Double] = Seq(xInPixels)

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def toRatioAnchorFor(anchored: HasDims): RatioAnchor = {
    RatioAnchor(xInPixels / anchored.lengthInPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def + (offset: Int): PointAnchor = {
    PointAnchor(xInPixels + offset)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def - (offset: Int): PointAnchor = {
    PointAnchor(xInPixels - offset)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def + (offset: Dims): PointAnchor = {
    PointAnchor(xInPixels + offset.lengthInPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def - (offset: Dims): PointAnchor = {
    PointAnchor(xInPixels - offset.lengthInPixels)
  }

  /**
   *
   *
   * @param newXInPixels
   * @param newName
   *
   * @return
   */
  def copy(
      newXInPixels: Double,
      newName: Option[String]): PointAnchor = {

    PointAnchor(
      newXInPixels,
      newName)
  }

  /**
   *
   *
   * @return
   */
  override
  def toString: String = {
    s"PointAnchor($xInPixels px)"
  }

}
