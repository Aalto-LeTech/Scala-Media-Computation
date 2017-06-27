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

import aalto.smcl.bitmaps.fullfeatured.ImageElement




/**
 * Object that has two-dimensional rectangular boundary.
 *
 * @author Aleksi Lukkarinen
 */
trait HasBounds {

  /** */
  def boundary: Option[Bounds]

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  protected def calculateOuterBoundary(elements: Seq[ImageElement]): Option[Bounds] = {
    @tailrec
    def calculateOuterBoundaryRecursion(
        it: Iterator[ImageElement],
        x0: Double, y0: Double, x1: Double, y1: Double): Option[(Double, Double, Double, Double)] = {

      if (!it.hasNext)
        return Some((x0, y0, x1, y1))

      it.next() match {
        case elementWithBoundary: HasBounds =>
          if (elementWithBoundary.boundary.isEmpty) {
            calculateOuterBoundaryRecursion(it, x0, y0, x1, y1)
          }
          else {
            val boundary = elementWithBoundary.boundary.get

            val ul = boundary.upperLeft
            val x0New = math.min(ul.xInPixels, x0)
            val y0New = math.min(ul.yInPixels, y0)

            val lr = boundary.lowerRight
            val x1New = math.max(lr.xInPixels, x1)
            val y1New = math.max(lr.yInPixels, y1)

            calculateOuterBoundaryRecursion(it, x0New, y0New, x1New, y1New)
          }

        case _ =>
          calculateOuterBoundaryRecursion(it, x0, y0, x1, y1)
      }
    }

    val resolvedBoundaryValues =
      if (elements.isEmpty)
        None
      else
        calculateOuterBoundaryRecursion(
          elements.iterator,
          Double.MaxValue, Double.MaxValue,
          Double.MinValue, Double.MinValue)

    resolvedBoundaryValues map[Bounds] {newBounds =>
      Bounds(newBounds._1, newBounds._2, newBounds._3, newBounds._4)
    }
  }

}
