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

package smcl.infrastructure.enumerators


import smcl.infrastructure.enumerators.DownwardsLeftwardsMatrixEnumerator.checkArguments




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object MatrixEnumerator2D {

  /** */
  private
  type LiftedEnumeratorConstructor =
    (Int, Int, Int, Int, MatrixEnumerationStyle2D) => AbstractMatrixEnumerator2D

  /**
   *
   *
   * @param upperLeftColumn
   * @param upperLeftRow
   * @param width
   * @param height
   * @param enumerationStyle
   *
   * @return
   */
  def apply(
      upperLeftColumn: Int,
      upperLeftRow: Int,
      width: Int,
      height: Int,
      enumerationStyle: MatrixEnumerationStyle2D): AbstractMatrixEnumerator2D = {

    checkArguments(upperLeftColumn, upperLeftRow, width, height)

    if (width == 0 || height == 0)
      return NullMatrixEnumerator2D(enumerationStyle)

    val lowerRightColumn = upperLeftColumn + width - 1
    val lowerRightRow = upperLeftRow + height - 1

    val constructor: LiftedEnumeratorConstructor = enumerationStyle match {
      case MESDownwardsLeftwards  => new DownwardsLeftwardsMatrixEnumerator(_, _, _, _, _)
      case MESDownwardsRightwards => new DownwardsRightwardsMatrixEnumerator(_, _, _, _, _)
      case MESLeftwardsDownwards  => new LeftwardsDownwardsMatrixEnumerator(_, _, _, _, _)
      case MESLeftwardsUpwards    => new LeftwardsUpwardsMatrixEnumerator(_, _, _, _, _)
      case MESRightwardsDownwards => new RightwardsDownwardsMatrixEnumerator(_, _, _, _, _)
      case MESRightwardsUpwards   => new RightwardsUpwardsMatrixEnumerator(_, _, _, _, _)
      case MESUpwardsLeftwards    => new UpwardsLeftwardsMatrixEnumerator(_, _, _, _, _)
      case MESUpwardsRightwards   => new UpwardsRightwardsMatrixEnumerator(_, _, _, _, _)
    }

    constructor(
      upperLeftColumn, upperLeftRow,
      lowerRightColumn, lowerRightRow,
      enumerationStyle)
  }

}
