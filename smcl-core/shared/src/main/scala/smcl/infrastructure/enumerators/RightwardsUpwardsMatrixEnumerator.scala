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


import smcl.infrastructure.exceptions.NoMoreCellsToEnumerateError




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object RightwardsUpwardsMatrixEnumerator
    extends MatrixEnumerator2DCompanion {

  /**
   *
   *
   * @param upperLeftColumn
   * @param upperLeftRow
   * @param width
   * @param height
   *
   * @return
   */
  def apply(
      upperLeftColumn: Int,
      upperLeftRow: Int,
      width: Int,
      height: Int): AbstractMatrixEnumerator2D = {

    MatrixEnumerator2D(
      upperLeftColumn, upperLeftRow,
      width, height,
      MESLeftwardsUpwards)
  }

}




/**
 *
 *
 * @param upperLeftColumn
 * @param upperLeftRow
 * @param lowerRightColumn
 * @param lowerRightRow
 * @param enumerationStyle
 *
 * @author Aleksi Lukkarinen
 */
class RightwardsUpwardsMatrixEnumerator private[enumerators](
    override val upperLeftColumn: Int,
    override val upperLeftRow: Int,
    override val lowerRightColumn: Int,
    override val lowerRightRow: Int,
    enumerationStyle: MatrixEnumerationStyle2D)
    extends AbstractMatrixEnumerator2D(
      upperLeftColumn, upperLeftRow, lowerRightColumn, lowerRightRow, enumerationStyle) {

  /**
   *
   *
   * @return
   */
  //noinspection ConvertExpressionToSAM
  override protected final
  def enumerationState: MatrixEnumerator2DInternalEnumerationState =
    new MatrixEnumerator2DInternalEnumerationState {

      _currentColumn = upperLeftColumn
      _currentRow = lowerRightRow
      _rowHasChanged = false
      _columnHasChanged = true

      /**
       *
       *
       * @return
       */
      final
      def hasNextCell: Boolean =
        currentRow > upperLeftRow || currentColumn < lowerRightColumn

      /**
       *
       *
       * @return
       */
      override final
      def advance(): Unit = {
        if (!hasNextCell)
          throw NoMoreCellsToEnumerateError

        if (_currentColumn < lowerRightColumn) {
          _currentColumn += 1
          _rowHasChanged = false
        }
        else {
          _currentColumn = upperLeftColumn
          _currentRow -= 1
          _rowHasChanged = true
        }
      }
    }

}
