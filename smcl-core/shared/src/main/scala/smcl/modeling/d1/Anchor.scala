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


/**
 * Anchor presets and functionality for creating new anchors.
 *
 * @author Aleksi Lukkarinen
 */
object Anchor {

  /** An anchor representing the start of a length. */
  val Start: RatioAnchor = RatioAnchor(0.0)

  /** An anchor representing the end of a length. */
  val End: RatioAnchor = RatioAnchor(1.0)

  /**
   *
   *
   * @param xInPixels
   *
   * @return
   */
  def forPoint(xInPixels: Double): PointAnchor = {
    PointAnchor(xInPixels, None)
  }

  /**
   *
   *
   * @param xInPixels
   * @param name
   *
   * @return
   */
  def forPoint(
      xInPixels: Double,
      name: Option[String]): PointAnchor = {

    PointAnchor(xInPixels, name)
  }

  /**
   *
   *
   * @param ratio
   *
   * @return
   */
  def forRatio(ratio: Double): RatioAnchor = {
    RatioAnchor(ratio, None)
  }

  /**
   *
   *
   * @param ratio
   * @param name
   *
   * @return
   */
  def forRatio(
      ratio: Double,
      name: Option[String]): RatioAnchor = {

    RatioAnchor(ratio, name)
  }

}




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
trait Anchor[HasAnchorType]
    extends CommonAnchorAPI[HasAnchorType] {

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def toPointAnchor(anchored: HasAnchorType): PointAnchor = {
    PointAnchor(internalXWithin(anchored), name)
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalDimsWithin(anchored: HasAnchorType): Dims = {
    Dims(internalXWithin(anchored))
  }

}
