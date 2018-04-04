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

package smcl.modeling.d3


import smcl.infrastructure.{CommonValidators, InjectablesRegistry}
import smcl.modeling.misc.RatioBasedAnchor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object RatioAnchor
    extends InjectablesRegistry {

  /** The [[smcl.infrastructure.CommonValidators]] instance to be used by this object. */
  protected lazy val commonValidators: CommonValidators = {
    injectable(InjectablesRegistry.IIdCommonValidators).asInstanceOf[CommonValidators]
  }

  /**
   *
   *
   * @param ratios
   *
   * @return
   */
  def apply(ratios: Seq[Double]): RatioAnchor = {
    apply(ratios.head, ratios(1), ratios(2), None)
  }

  /**
   *
   *
   * @param ratios
   * @param name
   *
   * @return
   */
  def apply(
      ratios: Seq[Double],
      name: Option[String]): RatioAnchor = {

    apply(ratios.head, ratios(1), ratios(2), name)
  }

  /**
   *
   *
   * @param widthRatio
   * @param heightRatio
   * @param depthRatio
   *
   * @return
   */
  def apply(
      widthRatio: Double,
      heightRatio: Double,
      depthRatio: Double): RatioAnchor = {

    apply(widthRatio, heightRatio, depthRatio, None)
  }

  /**
   *
   *
   * @param widthRatio
   * @param heightRatio
   * @param depthRatio
   * @param name
   *
   * @return
   */
  def apply(
      widthRatio: Double,
      heightRatio: Double,
      depthRatio: Double,
      name: Option[String]): RatioAnchor = {

    commonValidators.validateZeroToOneFactor(widthRatio, Some("width ratio"))
    commonValidators.validateZeroToOneFactor(heightRatio, Some("height ratio"))
    commonValidators.validateZeroToOneFactor(depthRatio, Some("depth ratio"))

    // TODO: Validate name

    new RatioAnchor(widthRatio, heightRatio, depthRatio, name)
  }

}




/**
 *
 *
 * @param widthRatio  ratio of the whole width representing the intended anchor position in X direction
 * @param heightRatio ratio of the whole height representing the intended anchor position in Y direction
 * @param depthRatio  ratio of the whole depth representing the intended anchor position in Z direction
 * @param name        name of this anchor
 *
 * @author Aleksi Lukkarinen
 */
case class RatioAnchor private(
    widthRatio: Double,
    heightRatio: Double,
    depthRatio: Double,
    name: Option[String])
    extends RatioBasedAnchor[Dims]
        with Anchor[HasAnchor] {

  /** */
  lazy val ratiosOfWholeDimensions: Seq[Double] =
    Seq(widthRatio, heightRatio, depthRatio)

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  override
  def internalXWithin(anchored: HasAnchor): Double = {
    widthRatio * anchored.width.inPixels
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  override
  def internalYWithin(anchored: HasAnchor): Double = {
    heightRatio * anchored.height.inPixels
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  override
  def internalZWithin(anchored: HasAnchor): Double = {
    depthRatio * anchored.depth.inPixels
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def toPointAnchorFor(anchored: HasDims): PointAnchor = {
    PointAnchor(
      widthRatio * anchored.width.inPixels,
      heightRatio * anchored.height.inPixels,
      depthRatio * anchored.depth.inPixels)
  }

  /**
   *
   *
   * @param newWidthRatio
   * @param newHeightRatio
   * @param newDepthRatio
   * @param newName
   *
   * @return
   */
  def copy(
      newWidthRatio: Double = widthRatio,
      newHeightRatio: Double = heightRatio,
      newDepthRatio: Double = depthRatio,
      newName: Option[String] = name): RatioAnchor = {

    RatioAnchor(
      newWidthRatio,
      newHeightRatio,
      newDepthRatio,
      newName)
  }

  /**
   *
   *
   * @return
   */
  override
  def toString: String = {
    s"RatioAnchor(w: $widthRatio, h: $heightRatio, d: $depthRatio)"
  }

}
