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
import aalto.smcl.modeling.AbstractRatioAnchor




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

    RatioAnchor(widthRatio, heightRatio, name)
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
    extends AbstractRatioAnchor[Dims](
      Seq(widthRatio, heightRatio), name)
            with Anchor {

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

}
