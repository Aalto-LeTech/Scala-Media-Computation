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

package smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object enumerators {




  /**
   * Base class for two-dimensional matrix enumeration style constants.
   */
  sealed abstract class MatrixEnumerationStyle2D




  /**
   * A constant for indicating that matrix enumeration proceeds downwards and leftwards.
   */
  case object MESDownwardsLeftwards
      extends MatrixEnumerationStyle2D




  /**
   * A constant for indicating that matrix enumeration proceeds downwards and rightwards.
   */
  case object MESDownwardsRightwards
      extends MatrixEnumerationStyle2D




  /**
   * A constant for indicating that matrix enumeration proceeds leftwards and downwards.
   */
  case object MESLeftwardsDownwards
      extends MatrixEnumerationStyle2D




  /**
   * A constant for indicating that matrix enumeration proceeds leftwards and upwards.
   */
  case object MESLeftwardsUpwards
      extends MatrixEnumerationStyle2D




  /**
   * A constant for indicating that matrix enumeration proceeds rightwards and downwards.
   */
  case object MESRightwardsDownwards
      extends MatrixEnumerationStyle2D




  /**
   * A constant for indicating that matrix enumeration proceeds rightwards and upwards.
   */
  case object MESRightwardsUpwards
      extends MatrixEnumerationStyle2D




  /**
   * A constant for indicating that matrix enumeration proceeds upwards and leftwards.
   */
  case object MESUpwardsLeftwards
      extends MatrixEnumerationStyle2D




  /**
   * A constant for indicating that matrix enumeration proceeds upwards and rightwards.
   */
  case object MESUpwardsRightwards
      extends MatrixEnumerationStyle2D




}
