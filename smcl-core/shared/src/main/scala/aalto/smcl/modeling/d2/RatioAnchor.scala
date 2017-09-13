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


import aalto.smcl.infrastructure.{CommonValidators, InjectablesRegistry}
import aalto.smcl.modeling.misc.RatioBasedAnchor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object RatioAnchor
    extends InjectablesRegistry {

  /** The [[CommonValidators]] instance to be used by this object. */
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
  @inline
  def apply(ratios: Seq[Double]): RatioAnchor = {
    apply(ratios, None)
  }

  /**
   *
   *
   * @param ratios
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      ratios: Seq[Double],
      name: Option[String]): RatioAnchor = {

    require(ratios.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions ratios must be given (found: ${ratios.length})")

    apply(ratios.head, ratios(1), name)
  }

  /**
   *
   *
   * @param widthRatio
   * @param heightRatio
   *
   * @return
   */
  @inline
  def apply(
      widthRatio: Double,
      heightRatio: Double): RatioAnchor = {

    apply(widthRatio, heightRatio, None)
  }

  /**
   *
   *
   * @param widthRatio
   * @param heightRatio
   * @param name
   *
   * @return
   */
  def apply(
      widthRatio: Double,
      heightRatio: Double,
      name: Option[String]): RatioAnchor = {

    commonValidators.validateZeroToOneFactor(widthRatio, Some("width ratio"))
    commonValidators.validateZeroToOneFactor(heightRatio, Some("height ratio"))

    // TODO: Validate name

    new RatioAnchor(widthRatio, heightRatio, name)
  }

}




/**
 *
 *
 * @param widthRatio  ratio of the whole width representing the intended anchor position in X direction
 * @param heightRatio ratio of the whole height representing the intended anchor position in Y direction
 * @param name        name of this anchor
 *
 * @author Aleksi Lukkarinen
 */
case class RatioAnchor private(
    widthRatio: Double,
    heightRatio: Double,
    override val name: Option[String])
    extends RatioBasedAnchor[Dims]
            with Anchor[HasAnchor] {

  /** */
  lazy val ratiosOfWholeDimensions: Seq[Double] =
    Seq(widthRatio, heightRatio)

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  @inline
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
  @inline
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
  @inline
  def toPointAnchorFor(anchored: HasDims): PointAnchor = {
    PointAnchor(
      widthRatio * anchored.width.inPixels,
      heightRatio * anchored.height.inPixels)
  }

  /**
   *
   *
   * @param newWidthRatio
   * @param newHeightRatio
   * @param newName
   *
   * @return
   */
  @inline
  def copy(
      newWidthRatio: Double = widthRatio,
      newHeightRatio: Double = heightRatio,
      newName: Option[String] = name): RatioAnchor = {

    RatioAnchor(
      newWidthRatio,
      newHeightRatio,
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
    s"RatioAnchor(w: $widthRatio, h: $heightRatio)"
  }

}
