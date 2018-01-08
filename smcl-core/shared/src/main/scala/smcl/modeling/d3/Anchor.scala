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


/**
 * Anchor presets and functionality for creating new anchors.
 *
 * @author Aleksi Lukkarinen
 */
object Anchor {

  /**
   *
   *
   * @param coordinates
   *
   * @return
   */
  @inline
  def forPoint(coordinates: Seq[Double]): PointAnchor = {
    PointAnchor(
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
  def forPoint(
      coordinates: Seq[Double],
      name: Option[String]): PointAnchor = {

    PointAnchor(
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
  def forPoint(
      xInPixels: Double,
      yInPixels: Double,
      zInPixels: Double): PointAnchor = {

    PointAnchor(xInPixels, yInPixels, zInPixels, None)
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
  def forPoint(
      xInPixels: Double,
      yInPixels: Double,
      zInPixels: Double,
      name: Option[String]): PointAnchor = {

    PointAnchor(xInPixels, yInPixels, zInPixels, name)
  }

  /**
   *
   *
   * @param ratios
   *
   * @return
   */
  @inline
  def forRatio(ratios: Seq[Double]): RatioAnchor = {
    RatioAnchor(ratios.head, ratios(1), ratios(2), None)
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
  def forRatio(
      ratios: Seq[Double],
      name: Option[String]): RatioAnchor = {

    RatioAnchor(ratios.head, ratios(1), ratios(2), name)
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
  @inline
  def forRatio(
      widthRatio: Double,
      heightRatio: Double,
      depthRatio: Double): RatioAnchor = {

    RatioAnchor(widthRatio, heightRatio, depthRatio, None)
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
  def forRatio(
      widthRatio: Double,
      heightRatio: Double,
      depthRatio: Double,
      name: Option[String]): RatioAnchor = {

    RatioAnchor(widthRatio, heightRatio, depthRatio, name)
  }

}




/**
 *
 *
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
    PointAnchor(
      internalXWithin(anchored),
      internalYWithin(anchored),
      internalZWithin(anchored),
      name)
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalDimsWithin(anchored: HasAnchorType): Dims = {
    Dims(
      internalXWithin(anchored),
      internalYWithin(anchored),
      internalZWithin(anchored))
  }

}
