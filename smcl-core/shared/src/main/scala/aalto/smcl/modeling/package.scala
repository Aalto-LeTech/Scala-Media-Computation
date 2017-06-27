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

package aalto.smcl


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object modeling {

  /** A type for a two-dimensional coordinate pair. */
  type CoordinateTuple = d2.CoordinateTuple

  /** A type alias for 2D boundary. */
  type Bounds = d2.Bounds

  /** A companion object alias for 2D boundary. */
  val Bounds = d2.Bounds

  /** A type alias for 2D circle. */
  type Circle = d2.Circle

  /** A companion object alias for 2D circle. */
  val Circle = d2.Circle

  /** A type alias for 2D dimensions. */
  type Dims = d2.Dims

  /** A companion object alias for 2D dimensions. */
  val Dims = d2.Dims

  /** A type alias for 2D [[d2.HasBounds]] trait. */
  type HasBounds = d2.HasBounds

  /** A type alias for 2D [[d2.HasDims]] trait. */
  type HasDims = d2.HasDims

  /** A type alias for 2D [[d2.HasPos]] trait. */
  type HasPos = d2.HasPos

  /** A type alias for 2D [[d2.Movable]] trait. */
  type Movable[ReturnType] = d2.Movable[ReturnType]

  /** A type alias for 2D position. */
  type Pos = d2.Pos

  /** A companion object alias for 2D position. */
  val Pos = d2.Pos

  /** A type alias for 2D [[d2.Rotatable]] trait. */
  type Rotatable[ReturnType] = d2.Rotatable[ReturnType]

}
