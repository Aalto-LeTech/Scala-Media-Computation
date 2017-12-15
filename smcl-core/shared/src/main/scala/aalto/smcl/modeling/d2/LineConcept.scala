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


import aalto.smcl.modeling.Area




/**
 * A conceptual two-dimensional line that has Cartesian coordinates.
 *
 * @author Aleksi Lukkarinen
 */
class LineConcept(
    start: Pos,
    end: Pos,
    shapeDataResolver: ShapeDataResolver)
    extends PolygonConcept[LineConcept](shapeDataResolver) {

  /** The end points of this line. */
  val points: Seq[Pos] = Seq(start, end)

  /** Area of this line. */
  lazy val area: Area = Area.Zero

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): LineConcept = {
    copy(
      newStart = start.moveBy(offsets: _*),
      newEnd = end.moveBy(offsets: _*))
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

    new LineConcept(newStart, newEnd)
  }

}
