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
object UpwardsLeftwardsMatrixEnumerator
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
      MESUpwardsLeftwards)
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
class UpwardsLeftwardsMatrixEnumerator private[enumerators](
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
      _rowHasChanged = true
      _columnHasChanged = false

      /**
       *
       *
       * @return
       */
      final
      def hasNextCell: Boolean =
        currentColumn > upperLeftColumn || currentRow > upperLeftRow

      /**
       *
       *
       * @return
       */
      override final
      def advance(): Unit = {
        if (!hasNextCell)
          throw NoMoreCellsToEnumerateError

        if (_currentRow > upperLeftRow) {
          _currentRow -= 1
          _columnHasChanged = false
        }
        else {
          _currentRow = lowerRightRow
          _currentColumn -= 1
          _columnHasChanged = true
        }
      }
    }

}
