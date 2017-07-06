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


/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
object Anchor {

  val TopLeft = RatioAnchor(0.0, 0.0, Some("top left"))

  val TopCenter = RatioAnchor(0.5, 0.0, Some("top center"))

  val TopRight = RatioAnchor(1.0, 0.0, Some("top right"))

  val CenterLeft = RatioAnchor(0.0, 0.5, Some("center left"))

  val Center = RatioAnchor(0.5, 0.5, Some("center"))

  val CenterRight = RatioAnchor(1.0, 0.5, Some("center right"))

  val BottomLeft = RatioAnchor(0.0, 1.0, Some("bottom left"))

  val BottomCenter = RatioAnchor(0.5, 1.0, Some("bottom center"))

  val BottomRight = RatioAnchor(1.0, 1.0, Some("bottom right"))

}




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
trait Anchor {

  /**
   * Name of this anchor.
   *
   * @return
   */
  def name: Option[String]

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def toPointAnchor(anchored: HasAnchor): PointAnchor = {
    PointAnchor(
      internalXWithin(anchored),
      internalYWithin(anchored),
      name)
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalDimsWithin(anchored: HasAnchor): Dims = {
    Dims(
      internalXWithin(anchored),
      internalYWithin(anchored))
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalXWithin(anchored: HasAnchor): Double

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalYWithin(anchored: HasAnchor): Double

}
