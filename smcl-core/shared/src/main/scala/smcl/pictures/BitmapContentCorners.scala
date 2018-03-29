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

package smcl.pictures


import smcl.modeling.AffineTransformation
import smcl.modeling.d2.{BoundaryCalculator, Bounds, Pos}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapContentCorners {

  /** */
  val NotDefined: BitmapContentCorners =
    createInstance(Pos.Origo, Pos.Origo, Pos.Origo, Pos.Origo, isDefined = false)

  /**
   *
   *
   * @param bounds
   */
  def apply(bounds: Bounds): BitmapContentCorners = {
    val ulCorner = bounds.upperLeftCorner
    val urCorner = Pos(bounds.lowerRightCorner.xInPixels, bounds.upperLeftCorner.yInPixels)
    val lrCorner = bounds.lowerRightCorner
    val llCorner = Pos(bounds.upperLeftCorner.xInPixels, bounds.lowerRightCorner.yInPixels)

    apply(ulCorner, urCorner, lrCorner, llCorner)
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param lowerRightCorner
   */
  def apply(
      upperLeftCorner: Pos,
      lowerRightCorner: Pos): BitmapContentCorners = {

    apply(
      upperLeftCorner,
      Pos(lowerRightCorner.xInPixels, upperLeftCorner.yInPixels),
      lowerRightCorner,
      Pos(upperLeftCorner.xInPixels, lowerRightCorner.yInPixels))
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param upperRightCorner
   * @param lowerRightCorner
   * @param lowerLeftCorner
   */
  def apply(
      upperLeftCorner: Pos,
      upperRightCorner: Pos,
      lowerRightCorner: Pos,
      lowerLeftCorner: Pos): BitmapContentCorners = {

    createInstance(
      upperLeftCorner,
      upperRightCorner,
      lowerRightCorner,
      lowerLeftCorner,
      isDefined = true)
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param upperRightCorner
   * @param lowerRightCorner
   * @param lowerLeftCorner
   */
  private
  def createInstance(
      upperLeftCorner: Pos,
      upperRightCorner: Pos,
      lowerRightCorner: Pos,
      lowerLeftCorner: Pos,
      isDefined: Boolean): BitmapContentCorners = {

    new BitmapContentCorners(
      upperLeftCorner,
      upperRightCorner,
      lowerRightCorner,
      lowerLeftCorner,
      isDefined)
  }

}




/**
 *
 *
 * @param upperLeftCorner
 * @param upperRightCorner
 * @param lowerRightCorner
 * @param lowerLeftCorner
 * @param isDefined
 *
 * @author Aleksi Lukkarinen
 */
class BitmapContentCorners private(
    val upperLeftCorner: Pos,
    val upperRightCorner: Pos,
    val lowerRightCorner: Pos,
    val lowerLeftCorner: Pos,
    val isDefined: Boolean) {

  /** */
  lazy val corners: Seq[Pos] =
    Seq(upperLeftCorner, upperRightCorner, lowerRightCorner, lowerLeftCorner)

  /** */
  val isUndefined: Boolean = !isDefined

}
