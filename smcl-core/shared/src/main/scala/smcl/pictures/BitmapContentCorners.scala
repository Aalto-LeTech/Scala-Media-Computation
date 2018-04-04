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


import smcl.modeling.d2.{Bounds, Pos}




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

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  @inline
  final
  def moveBy(offsetsInPixels: Seq[Double]): BitmapContentCorners =
    internalCopy(
      newUpperLeftCorner = upperLeftCorner.moveBy(offsetsInPixels),
      newUpperRightCorner = upperRightCorner.moveBy(offsetsInPixels),
      newLowerRightCorner = lowerRightCorner.moveBy(offsetsInPixels),
      newLowerLeftCorner = lowerLeftCorner.moveBy(offsetsInPixels))

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   *
   * @return
   */
  @inline
  final
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): BitmapContentCorners = {

    internalCopy(
      newUpperLeftCorner = upperLeftCorner.moveBy(xOffsetInPixels, yOffsetInPixels),
      newUpperRightCorner = upperRightCorner.moveBy(xOffsetInPixels, yOffsetInPixels),
      newLowerRightCorner = lowerRightCorner.moveBy(xOffsetInPixels, yOffsetInPixels),
      newLowerLeftCorner = lowerLeftCorner.moveBy(xOffsetInPixels, yOffsetInPixels))
  }

  /**
   * Creates a copy of these bitmap content corners with given arguments.
   *
   * This is an unsafe method, as it can be used to create [[BitmapContentCorners]]
   * instances, whose internal state is incoherent. As such, it is not for public use.
   *
   * @param newUpperLeftCorner
   * @param newUpperRightCorner
   * @param newLowerRightCorner
   * @param newLowerLeftCorner
   *
   * @return
   */
  @inline
  private final
  def internalCopy(
      newUpperLeftCorner: Pos = upperLeftCorner,
      newUpperRightCorner: Pos = upperRightCorner,
      newLowerRightCorner: Pos = lowerRightCorner,
      newLowerLeftCorner: Pos = lowerLeftCorner): BitmapContentCorners = {

    if (isUndefined)
      return this

    BitmapContentCorners(
      newUpperLeftCorner,
      newUpperRightCorner,
      newLowerRightCorner,
      newLowerLeftCorner)
  }

}
