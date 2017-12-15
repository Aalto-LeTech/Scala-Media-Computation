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


import aalto.smcl.modeling.{Area, misc}




/**
 * A conceptual two-dimensional shape that has Cartesian coordinates.
 *
 * @param shapeDataResolver
 * @tparam ObjectType
 *
 * @author Aleksi Lukkarinen
 */
abstract class ShapeConcept[ObjectType](
    protected val shapeDataResolver: ShapeDataResolver)
    extends misc.CartesianShapeConcept[Pos, Dims]
        with HasPos
        with HasDims
        with HasBounds
        with Movable[ObjectType] {

  /**
   * Returns the boundary of this shape.
   *
   * @return
   */
  @inline
  def boundary: Bounds = shapeDataResolver.boundary

  /**
   * Returns the position of this shape.
   *
   * @return
   */
  @inline
  def position: Pos = shapeDataResolver.position

  /**
   * Dimensions of this shape.
   *
   * @return
   */
  @inline
  def dimensions: Dims = shapeDataResolver.dimensions

  /**
   * Returns the area of this shape.
   *
   * @return
   */
  @inline
  def area: Area = shapeDataResolver.area

}
