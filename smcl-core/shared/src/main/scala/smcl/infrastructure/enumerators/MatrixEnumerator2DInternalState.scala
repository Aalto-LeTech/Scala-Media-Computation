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
object MatrixEnumerator2DInternalState {

  /** */
  val InitialValue: Int = Integer.MIN_VALUE

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait MatrixEnumerator2DInternalState {

  /** */
  protected
  var _currentColumn: Int = MatrixEnumerator2DInternalState.InitialValue

  /** */
  protected
  var _currentRow: Int = MatrixEnumerator2DInternalState.InitialValue

  /** */
  protected
  var _rowHasChanged: Boolean = false

  /** */
  protected
  var _columnHasChanged: Boolean = false

  /**
   *
   *
   * @return
   */
  @inline
  def currentColumn: Int = _currentColumn

  /**
   *
   *
   * @return
   */
  @inline
  def currentRow: Int = _currentRow

  /**
   *
   *
   * @return
   */
  @inline
  def rowHasChanged: Boolean = hasNextCell && _rowHasChanged

  /**
   *
   *
   * @return
   */
  @inline
  def columnHasChanged: Boolean = hasNextCell && _columnHasChanged

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
  def advance(): Unit

}
