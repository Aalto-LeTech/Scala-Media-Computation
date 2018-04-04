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
object LeftwardsUpwardsMatrixEnumerator
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
class LeftwardsUpwardsMatrixEnumerator private[enumerators](
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

      _currentColumn = lowerRightColumn
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
        currentRow > upperLeftRow || currentColumn > upperLeftColumn

      /**
       *
       *
       * @return
       */
      override final
      def advance(): Unit = {
        if (!hasNextCell)
          throw NoMoreCellsToEnumerateError

        if (_currentColumn > upperLeftColumn) {
          _currentColumn -= 1
          _rowHasChanged = false
        }
        else {
          _currentColumn = lowerRightColumn
          _currentRow -= 1
          _rowHasChanged = true
        }
      }
    }

}
