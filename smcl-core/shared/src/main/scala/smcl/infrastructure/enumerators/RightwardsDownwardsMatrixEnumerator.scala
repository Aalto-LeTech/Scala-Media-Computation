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
object RightwardsDownwardsMatrixEnumerator {

  /**
   *
   *
   * @param startColumn
   * @param startRow
   * @param width
   * @param height
   *
   * @return
   */
  def apply(
      startColumn: Int,
      startRow: Int,
      width: Int,
      height: Int): AbstractMatrixEnumerator2D = {

    require(startColumn >= 0 || width >= 0 || startRow >= 0 || height >= 0,
      "Neither row and column numbers nor width and height can be negative.")

    if (width == 0 || height == 0)
      return NullMatrixEnumerator

    new RightwardsDownwardsMatrixEnumerator(
      startColumn,
      startColumn + width - 1,
      startRow,
      startRow + height - 1)
  }

}




/**
 *
 *
 * @param startColumn
 * @param endColumn
 * @param startRow
 * @param endRow
 *
 * @author Aleksi Lukkarinen
 */
class RightwardsDownwardsMatrixEnumerator private(
    override val startColumn: Int,
    override val endColumn: Int,
    override val startRow: Int,
    override val endRow: Int)
    extends AbstractMatrixEnumerator2D(startColumn, endColumn, startRow, endRow) {

  /**
   *
   *
   * @return
   */
  override
  val width: Int = endColumn - startColumn + 1

  /**
   *
   *
   * @return
   */
  override
  val height: Int = endRow - startRow + 1

  /**
   *
   *
   * @return
   */
  def hasNextCell: Boolean =
    currentRow < endRow || currentColumn < endColumn

  /**
   *
   *
   * @return
   */
  override protected
  def enumerationState: MatrixEnumerator2DInternalEnumerationState =
    new MatrixEnumerator2DInternalEnumerationState {

      _currentColumn = startColumn
      _currentRow = startRow
      _rowHasChanged = false
      _columnHasChanged = true

      /**
       *
       *
       * @return
       */
      override
      def advance(): Unit = {
        if (!hasNextCell)
          throw NoMoreCellsToEnumerateError

        if (_currentColumn < endColumn) {
          _currentColumn += 1
          _rowHasChanged = false
        }
        else {
          _currentColumn = startColumn
          _currentRow += 1
          _rowHasChanged = true
        }
      }
    }

}
