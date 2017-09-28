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

package aalto.smcl.bitmaps.fullfeatured


import scala.annotation.tailrec

import aalto.smcl.modeling
import aalto.smcl.modeling.d2.{Bounds, HasBounds}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BoundaryCalculator {

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  def fromBoundaries(elements: Seq[HasBounds]): Option[Bounds] = {
    type RecursRetVal = Option[(Double, Double, Double, Double)]

    @tailrec
    def calculateOuterBoundaryRecursion(
        it: Iterator[HasBounds], foundOneBoundary: Boolean,
        x0: Double, y0: Double, x1: Double, y1: Double): RecursRetVal = {

      if (!it.hasNext) {
        if (foundOneBoundary)
          return Some((x0, y0, x1, y1))

        return None
      }

      val elementWithBoundary = it.next()

      if (elementWithBoundary.boundary.isEmpty) {
        calculateOuterBoundaryRecursion(
          it, foundOneBoundary, x0, y0, x1, y1)
      }
      else {
        val boundary = elementWithBoundary.boundary.get

        val ul = boundary.upperLeftMarker
        val x0New = math.min(ul.xInPixels, x0)
        val y0New = math.min(ul.yInPixels, y0)

        val lr = boundary.lowerRightMarker
        val x1New = math.max(lr.xInPixels, x1)
        val y1New = math.max(lr.yInPixels, y1)

        calculateOuterBoundaryRecursion(
          it, foundOneBoundary = true, x0New, y0New, x1New, y1New)
      }
    }

    val resolvedBoundaryValues =
      if (elements.isEmpty)
        None
      else
        calculateOuterBoundaryRecursion(
          elements.iterator, foundOneBoundary = false,
          Double.MaxValue, Double.MaxValue,
          Double.MinValue, Double.MinValue)

    resolvedBoundaryValues map[Bounds] {newBounds =>
      modeling.d2.Bounds(newBounds._1, newBounds._2, newBounds._3, newBounds._4)
    }
  }

}
