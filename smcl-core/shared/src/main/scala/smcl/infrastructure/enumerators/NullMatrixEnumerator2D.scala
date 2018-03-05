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
object NullMatrixEnumerator2D
    extends MatrixEnumerator2DCompanion {

  /**
   *
   *
   * @param enumerationStyle
   *
   * @return
   */
  def apply(enumerationStyle: MatrixEnumerationStyle2D): AbstractMatrixEnumerator2D = {

    new NullMatrixEnumerator2D(enumerationStyle)
  }

}




/**
 *
 *
 * @param enumerationStyle
 *
 * @author Aleksi Lukkarinen
 */
class NullMatrixEnumerator2D private(
    enumerationStyle: MatrixEnumerationStyle2D)
    extends AbstractMatrixEnumerator2D(
      upperLeftColumn = NullMatrixEnumerator2D.UndefinedValue,
      lowerRightColumn = NullMatrixEnumerator2D.UndefinedValue,
      upperLeftRow = NullMatrixEnumerator2D.UndefinedValue,
      lowerRightRow = NullMatrixEnumerator2D.UndefinedValue,
      enumerationStyle = enumerationStyle) {

  setInternalState(enumerationState)

  /**
   *
   *
   * @return
   */
  override
  val width: Int = 0

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
  //noinspection ConvertExpressionToSAM
  override protected
  def enumerationState: MatrixEnumerator2DInternalEnumerationState =
    new MatrixEnumerator2DInternalEnumerationState {

      _currentColumn = NullMatrixEnumerator2D.UndefinedValue
      _currentRow = NullMatrixEnumerator2D.UndefinedValue
      _rowHasChanged = false
      _columnHasChanged = false

      /**
       *
       *
       * @return
       */
      override
      val hasNextCell: Boolean = false

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
