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


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object AbstractMatrixEnumerator2D {

  /** */
  val InitialValue: Int = MatrixEnumerator2DInternalState.InitialValue

  /** */
  val UndefinedValue: Int = Integer.MIN_VALUE

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
abstract class AbstractMatrixEnumerator2D(
    val startColumn: Int,
    val endColumn: Int,
    val startRow: Int,
    val endRow: Int) {

  /** */
  private[this]
  var _internalState: MatrixEnumerator2DInternalState =
    MatrixEnumerator2DInternalInitialState

  /** */
  protected
  def setInternalState(s: MatrixEnumerator2DInternalState): Unit =
    _internalState = s

  /**
   *
   *
   * @return
   */
  @inline
  protected
  def enumerationState: MatrixEnumerator2DInternalEnumerationState

  /**
   *
   *
   * @return
   */
  @inline
  def width: Int

  /**
   *
   *
   * @return
   */
  @inline
  def height: Int

  /**
   *
   *
   * @return
   */
  @inline
  def currentColumn: Int = _internalState.currentColumn

  /**
   *
   *
   * @return
   */
  @inline
  def currentRow: Int = _internalState.currentRow

  /**
   *
   *
   * @return
   */
  @inline
  def colRowTuple: (Int, Int) = (currentColumn, currentRow)

  /**
   *
   *
   * @return
   */
  @inline
  def rowHasChanged: Boolean = _internalState.rowHasChanged

  /**
   *
   *
   * @return
   */
  @inline
  def columnHasChanged: Boolean = _internalState.columnHasChanged

  /**
   *
   *
   * @return
   */
  @inline
  def hasNextCell: Boolean

  /**
   *
   *
   * @return
   */
  @inline
  def hasNoMoreCells: Boolean = !hasNextCell

  /**
   *
   *
   * @return
   */
  @inline
  def advance(): Unit = _internalState.advance()




  /**
   *
   */
  private
  object MatrixEnumerator2DInternalInitialState
      extends MatrixEnumerator2DInternalState {

    /**
     *
     *
     * @return
     */
    @inline
    def hasNextCell: Boolean = AbstractMatrixEnumerator2D.this.hasNextCell

    /**
     *
     *
     * @return
     */
    @inline
    override
    def advance(): Unit =
      AbstractMatrixEnumerator2D.this.setInternalState(enumerationState)

  }




  /**
   *
   */
  protected
  abstract class MatrixEnumerator2DInternalEnumerationState
      extends MatrixEnumerator2DInternalState {

    /**
     *
     *
     * @return
     */
    @inline
    def hasNextCell: Boolean = AbstractMatrixEnumerator2D.this.hasNextCell

  }




}
