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


import scala.annotation.tailrec




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BoundaryCalculator {

  /** Initial value for deremining coordinates of the upper left corner. */
  private
  val InitialUpperLeft: Double = Double.MaxValue

  /** Initial value for deremining coordinates of the lower right corner. */
  private
  val InitialLowerRight: Double = Double.MinValue

  /** Initial value for boundary finding status. */
  private
  val InitiallyNoBoundaryIsFound: Boolean = false

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  def fromBoundaries(elements: Seq[HasBounds]): Bounds = {
    if (elements == null) {
      return Bounds.NotDefined
    }

    val numberOfElements = elements.length

    if (numberOfElements == 0) {
      Bounds.NotDefined
    }
    if (numberOfElements == 1) {
      elements.head.boundary
    }
    else {
      fromBoundariesRecursive(
        elements.iterator,
        InitiallyNoBoundaryIsFound,
        InitialUpperLeft, InitialUpperLeft,
        InitialLowerRight, InitialLowerRight)
    }
  }

  /**
   *
   *
   * @param it
   * @param foundOneBoundary
   * @param x0
   * @param y0
   * @param x1
   * @param y1
   *
   * @return
   */
  @tailrec
  private
  def fromBoundariesRecursive(
      it: Iterator[HasBounds], foundOneBoundary: Boolean,
      x0: Double, y0: Double, x1: Double, y1: Double): Bounds = {

    if (!it.hasNext) {
      if (foundOneBoundary)
        return Bounds(x0, y0, x1, y1)

      return Bounds.NotDefined
    }

    val boundary = it.next().boundary

    if (boundary.isDefined) {
      val ul = boundary.upperLeftMarker
      val lr = boundary.lowerRightMarker

      val x0New = if (ul.xInPixels < x0) ul.xInPixels else x0
      val y0New = if (ul.yInPixels < y0) ul.yInPixels else y0
      val x1New = if (lr.xInPixels > x1) lr.xInPixels else x1
      val y1New = if (lr.yInPixels > y1) lr.yInPixels else y1

      fromBoundariesRecursive(
        it, foundOneBoundary = true, x0New, y0New, x1New, y1New)
    }
    else {
      fromBoundariesRecursive(
        it, foundOneBoundary, x0, y0, x1, y1)
    }
  }

  /**
   *
   *
   * @param position
   *
   * @return
   */
  @inline
  def fromPositions(position: Pos): Bounds = {
    Bounds(position)
  }

  /**
   *
   *
   * @param a
   * @param b
   *
   * @return
   */
  @inline
  def fromPositions(a: Pos, b: Pos): Bounds = {
    val (xMin, xMax) =
      if (a.xInPixels <= b.xInPixels)
        (a.xInPixels, b.xInPixels)
      else
        (b.xInPixels, a.xInPixels)

    val (yMin, yMax) =
      if (a.yInPixels <= b.yInPixels)
        (a.yInPixels, b.yInPixels)
      else
        (b.yInPixels, a.yInPixels)

    Bounds(xMin, yMin, xMax, yMax)
  }

  /**
   *
   *
   * @param a
   * @param b
   * @param c
   *
   * @return
   */
  @inline
  def fromPositions(a: Pos, b: Pos, c: Pos): Bounds = {
    val x1 = a.xInPixels
    val x2 = b.xInPixels
    val x3 = c.xInPixels

    var xMax = x1
    var xMin = x3

    if (x1 >= x2) {                     // x1 >= x2
      if (x1 >= x3) {                   // x1 >= x2 && x1 >= x3 ==> default max == x1 is OK
        if (x2 < x3) {                  // x1 >= x3 > x2
          xMin = x2                     // ==> (x1, x2)
        }
        else { /* DEFAULTS OK */ }      // x1 >= x2 >= x3 ==> (x1, x3) ==> default min == x3 is OK
      }
      else {                            // x3 > x1 >= x2
        xMin = x2                       // ==> (x3, x2)
        xMax = x3
      }
    }
    else {                              // x2 > x1
      if (x1 >= x3) {                   // x2 > x1 >= x3
        xMax = x2                       // ==> (x2, x3) ==> default min == x3 is OK
      }
      else {                            // x2 > x1 && x3 > x1 ==> min == x1
        xMin = x1

        if (x2 >= x3) {                 // x2 >= x3 > x1
          xMax = x2                     // ==> (x2, x1)
        }
        else {                          // x3 > x2 > x1
          xMax = x3                     // ==> (x3, x1)
        }
      }
    }

    val y1 = a.yInPixels
    val y2 = b.yInPixels
    val y3 = c.yInPixels

    var yMax = y1
    var yMin = y3

    if (y1 >= y2) {                     // y1 >= y2
      if (y1 >= y3) {                   // y1 >= y2 && y1 >= y3 ==> default max == y1 is OK
        if (y2 < y3) {                  // y1 >= y3 > y2
          yMin = y2                     // ==> (y1, y2)
        }
        else { /* DEFAULTS OK */ }      // y1 >= y2 >= y3 ==> (y1, y3) ==> default min == y3 is OK
      }
      else {                            // y3 > y1 >= y2
        yMin = y2                       // ==> (y3, y2)
        yMax = y3
      }
    }
    else {                              // y2 > y1
      if (y1 >= y3) {                   // y2 > y1 >= y3
        yMax = y2                       // ==> (y2, y3) ==> default min == y3 is OK
      }
      else {                            // y2 > y1 && y3 > y1 ==> min == y1
        yMin = y1

        if (y2 >= y3) {                 // y2 >= y3 > y1
          yMax = y2                     // ==> (y2, y1)
        }
        else {                          // y3 > y2 > y1
          yMax = y3                     // ==> (y3, y1)
        }
      }
    }

    Bounds(xMin, yMin, xMax, yMax)
  }

  /**
   *
   *
   * @param positions
   *
   * @return
   */
  def fromPositions(positions: Seq[Pos]): Bounds = {
    if (positions == null) {
      return Bounds.NotDefined
    }

    val numberOfPositions = positions.length

    if (numberOfPositions >= 4) {
      fromPositionsRecursive(
        positions.iterator,
        InitiallyNoBoundaryIsFound,
        InitialUpperLeft, InitialUpperLeft,
        InitialLowerRight, InitialLowerRight)
    }
    else if (numberOfPositions == 3) {
      fromPositions(
        positions.head,
        positions(1),
        positions(2))
    }
    else if (numberOfPositions == 2) {
      fromPositions(
        positions.head,
        positions(1))
    }
    else if (numberOfPositions == 1) {
      fromPositions(positions.head)
    }
    else {
      Bounds.NotDefined
    }
  }

  /**
   *
   *
   * @param it
   * @param foundOneBoundary
   * @param x0
   * @param y0
   * @param x1
   * @param y1
   *
   * @return
   */
  @tailrec
  private
  def fromPositionsRecursive(
      it: Iterator[Pos], foundOneBoundary: Boolean,
      x0: Double, y0: Double, x1: Double, y1: Double): Bounds = {

    if (!it.hasNext) {
      if (foundOneBoundary)
        return Bounds(x0, y0, x1, y1)

      return Bounds.NotDefined
    }

    val position = it.next()

    if (position.isDefined) {
      val x0New = if (position.xInPixels < x0) position.xInPixels else x0
      val y0New = if (position.yInPixels < y0) position.yInPixels else y0
      val x1New = if (position.xInPixels > x1) position.xInPixels else x1
      val y1New = if (position.yInPixels > y1) position.yInPixels else y1

      fromPositionsRecursive(
        it, foundOneBoundary = true, x0New, y0New, x1New, y1New)
    }
    else {
      fromPositionsRecursive(it, foundOneBoundary, x0, y0, x1, y1)
    }
  }

}
