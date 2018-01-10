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

package smcl.modeling.d2


import smcl.modeling.Area
import smcl.modeling.d2.LineConcept.LineDataResolver




/**
 * A companion object for [[LineConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object LineConcept {

  /** The number of endpoints (i.e., two: start & end) needed to define a line. */
  val NumberOfEndpointsInLine: Int = 2

  /**
   * Returns a new [[LineConcept]] instance.
   *
   * @param start
   * @param end
   *
   * @return
   */
  @inline
  private
  def apply(start: Pos, end: Pos): LineConcept = {
    val points = Seq(start, end)

    apply(points)
  }

  /**
   * Returns a new [[LineConcept]] instance.
   *
   * @param points
   *
   * @return
   */
  @inline
  private
  def apply(points: Seq[Pos]): LineConcept = {
    require(points.lengthCompare(NumberOfEndpointsInLine) == 0,
      s"A triangle has to have exactly $NumberOfEndpointsInLine corner points (given: ${points.length})")

    val dataResolver = new LineDataResolver(points)

    instantiateLine(dataResolver)
  }

  /**
   * Returns a new [[LineConcept]] instance with a given data resolver instance.
   *
   * @param dataResolver
   *
   * @return
   */
  @inline
  private
  def instantiateLine(dataResolver: LineDataResolver): LineConcept = {
    new LineConcept(dataResolver)
  }




  private
  class LineDataResolver(val points: Seq[Pos])
      extends PolygonDataResolver {

    lazy val boundary: Bounds =
      Bounds(points.head, points(1))

    lazy val position: Pos =
      boundary.upperLeftMarker

    lazy val dimensions: Dims =
      Dims(boundary.width, boundary.height)

    lazy val area: Area = Area.Zero
  }




}




/**
 * A conceptual two-dimensional line that has Cartesian coordinates.
 *
 * @param dataResolver
 *
 * @author Aleksi Lukkarinen
 */
class LineConcept private(dataResolver: LineDataResolver)
    extends PolygonConcept[LineConcept](dataResolver) {

  /** The start point of this line. */
  val start: Pos = points.head

  /** The end point of this line. */
  val end: Pos = points(1)

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): LineConcept = {
    val movedPoints = points map {_.moveBy(offsets: _*)}

    LineConcept(movedPoints)
  }

  /**
   *
   *
   * @param newStart
   * @param newEnd
   *
   * @return
   */
  @inline
  def copy(
      newStart: Pos = start,
      newEnd: Pos = end): LineConcept = {

    if (newStart != start || newEnd != end) {
      return LineConcept(newStart, newEnd)
    }

    this
  }

}
