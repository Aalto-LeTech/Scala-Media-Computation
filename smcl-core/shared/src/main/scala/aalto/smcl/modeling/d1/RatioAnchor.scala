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


import aalto.smcl.infrastructure.{CommonValidators, InjectablesRegistry}
import aalto.smcl.modeling.misc.AbstractRatioAnchor




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
   * @param ratio
   *
   * @return
   */
  @inline
  def apply(ratio: Double): RatioAnchor = {
    apply(ratio, None)
  }

  /**
   *
   *
   * @param ratio
   * @param name
   *
   * @return
   */
  def apply(
      ratio: Double,
      name: Option[String]): RatioAnchor = {

    commonValidators.validateZeroToOneFactor(ratio, Some("ratio"))

    // TODO: Validate name

    new RatioAnchor(ratio, name)
  }

}




/**
 *
 *
 * @param ratio ratio of the whole width representing the intended anchor position in X direction
 * @param name  name of this anchor
 *
 * @author Aleksi Lukkarinen
 */
case class RatioAnchor private(
    ratio: Double,
    override val name: Option[String])
    extends AbstractRatioAnchor[Dims](Seq(ratio), name)
            with Anchor[HasAnchor] {

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
    ratio * anchored.lengthInPixels
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
    PointAnchor(ratio * anchored.lengthInPixels)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = {
    s"RatioAnchor($ratio)"
  }

}
