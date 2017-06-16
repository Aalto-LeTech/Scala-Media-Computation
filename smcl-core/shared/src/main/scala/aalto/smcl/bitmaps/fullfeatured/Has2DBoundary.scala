package aalto.smcl.bitmaps.fullfeatured


import scala.annotation.tailrec




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait Has2DBoundary {

  /** */
  def boundary: Option[Boundary]

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  protected def calculateOuterBoundary(elements: Seq[ImageElement]): Option[Boundary] = {
    @tailrec
    def calculateOuterBoundaryRecursion(
        it: Iterator[ImageElement],
        x0: Int, y0: Int, x1: Int, y1: Int): Option[(Int, Int, Int, Int)] = {

      if (!it.hasNext)
        return Some((x0, y0, x1, y1))

      it.next() match {
        case elementWithBoundary: Has2DBoundary =>
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
          Int.MaxValue, Int.MaxValue,
          Int.MinValue, Int.MinValue)

    resolvedBoundaryValues map[Boundary] {newBounds =>
      Boundary(newBounds._1, newBounds._2, newBounds._3, newBounds._4).get
    }
  }

}
