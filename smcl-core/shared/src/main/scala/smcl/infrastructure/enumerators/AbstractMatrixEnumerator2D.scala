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
 * @param upperLeftColumn
 * @param upperLeftRow
 * @param lowerRightColumn
 * @param lowerRightRow
 * @param enumerationStyle
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractMatrixEnumerator2D(
    val upperLeftColumn: Int,
    val upperLeftRow: Int,
    val lowerRightColumn: Int,
    val lowerRightRow: Int,
    val enumerationStyle: MatrixEnumerationStyle2D) {

  /** */
  val width: Int = lowerRightColumn - upperLeftColumn + 1

  /** */
  val height: Int = lowerRightRow - upperLeftRow + 1

  /** */
  private[this]
  var _internalState: MatrixEnumerator2DInternalState =
    MatrixEnumerator2DInternalInitialState

  /**
   *
   *
   * @param s
   */
  @inline
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
  def hasNextCell: Boolean = _internalState.hasNextCell

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
    def hasNextCell: Boolean = true

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
    def hasNextCell: Boolean

  }




}
