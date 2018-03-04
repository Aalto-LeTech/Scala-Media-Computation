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
object NullMatrixEnumerator
    extends AbstractMatrixEnumerator2D(
      startColumn = AbstractMatrixEnumerator2D.UndefinedValue,
      endColumn = AbstractMatrixEnumerator2D.UndefinedValue,
      startRow = AbstractMatrixEnumerator2D.UndefinedValue,
      endRow = AbstractMatrixEnumerator2D.UndefinedValue) {

  setInternalState(enumerationState)

  /**
   *
   *
   * @return
   */
  override
  def width: Int = 0

  /**
   *
   *
   * @return
   */
  override
  val height: Int = 0

  /**
   *
   *
   * @return
   */
  def hasNextCell: Boolean = false

  /**
   *
   *
   * @return
   */
  override protected
  def enumerationState: MatrixEnumerator2DInternalEnumerationState =
    new MatrixEnumerator2DInternalEnumerationState {

      _currentColumn = AbstractMatrixEnumerator2D.UndefinedValue
      _currentRow = AbstractMatrixEnumerator2D.UndefinedValue
      _rowHasChanged = false
      _columnHasChanged = false

      /**
       *
       *
       * @return
       */
      override
      def advance(): Unit =
        throw NoMoreCellsToEnumerateError
    }

}
