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

    val constructor = enumerationStyle match {
      case MESDownwardsLeftwards  => (new DownwardsLeftwardsMatrixEnumerator(_, _, _, _, _)).curried
      case MESDownwardsRightwards => (new DownwardsRightwardsMatrixEnumerator(_, _, _, _, _)).curried
      case MESLeftwardsDownwards  => (new LeftwardsDownwardsMatrixEnumerator(_, _, _, _, _)).curried
      case MESLeftwardsUpwards    => (new LeftwardsUpwardsMatrixEnumerator(_, _, _, _, _)).curried
      case MESRightwardsDownwards => (new RightwardsDownwardsMatrixEnumerator(_, _, _, _, _)).curried
      case MESRightwardsUpwards   => (new RightwardsUpwardsMatrixEnumerator(_, _, _, _, _)).curried
      case MESUpwardsLeftwards    => (new UpwardsLeftwardsMatrixEnumerator(_, _, _, _, _)).curried
      case MESUpwardsRightwards   => (new UpwardsRightwardsMatrixEnumerator(_, _, _, _, _)).curried
    }

    constructor(upperLeftColumn)(upperLeftRow)(lowerRightColumn)(lowerRightRow)(enumerationStyle)
  }

}
