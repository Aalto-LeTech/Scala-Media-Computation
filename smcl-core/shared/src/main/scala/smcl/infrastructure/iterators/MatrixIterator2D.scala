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

package smcl.infrastructure.iterators


import scala.collection.AbstractIterator

import smcl.infrastructure.enumerators.{AbstractMatrixEnumerator2D, _}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object MatrixIterator2D {

  /** */
  private
  type LiftedIteratorConstructor =
    AbstractMatrixEnumerator2D => MatrixIterator2D

  /**
   *
   *
   * @param startColumn
   * @param startRow
   * @param width
   * @param height
   * @param enumerationStyle
   *
   * @return
   */
  def apply(
      startColumn: Int,
      startRow: Int,
      width: Int,
      height: Int,
      enumerationStyle: MatrixEnumerationStyle2D): MatrixIterator2D = {

    val enumerator =
      MatrixEnumerator2D(
        startColumn, startRow, width, height, MESDownwardsLeftwards)

    val constructor: LiftedIteratorConstructor = enumerationStyle match {
      case MESDownwardsLeftwards  => new DownwardsLeftwardsMatrixIterator(_)
      case MESDownwardsRightwards => new DownwardsRightwardsMatrixIterator(_)
      case MESLeftwardsDownwards  => new LeftwardsDownwardsMatrixIterator(_)
      case MESLeftwardsUpwards    => new LeftwardsUpwardsMatrixIterator(_)
      case MESRightwardsDownwards => new RightwardsDownwardsMatrixIterator(_)
      case MESRightwardsUpwards   => new RightwardsUpwardsMatrixIterator(_)
      case MESUpwardsLeftwards    => new UpwardsLeftwardsMatrixIterator(_)
      case MESUpwardsRightwards   => new UpwardsRightwardsMatrixIterator(_)
    }

    constructor(enumerator)
  }

}




/**
 *
 *
 * @param enumerator
 *
 * @author Aleksi Lukkarinen
 */
class MatrixIterator2D(
    private val enumerator: AbstractMatrixEnumerator2D)
    extends AbstractIterator[(Int, Int)] {

  /** */
  val width: Int = enumerator.width

  /** */
  val height: Int = enumerator.height

  /** */
  val upperLeftColumn: Int = enumerator.upperLeftColumn

  /** */
  val lowerRightColumn: Int = enumerator.lowerRightColumn

  /** */
  val upperLeftRow: Int = enumerator.upperLeftRow

  /** */
  val lowerRightRow: Int = enumerator.lowerRightRow

  /**
   *
   *
   * @return
   */
  @inline
  final
  def currentColumn: Int = enumerator.currentColumn

  /**
   *
   *
   * @return
   */
  @inline
  final
  def currentRow: Int = enumerator.currentRow

  /**
   *
   *
   * @return
   */
  @inline
  final
  def rowHasChanged: Boolean = enumerator.rowHasChanged

  /**
   *
   *
   * @return
   */
  @inline
  final
  def columnHasChanged: Boolean = enumerator.columnHasChanged

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def hasNext: Boolean = enumerator.hasNextCell

  /**
   *
   *
   * @return
   */
  @inline
  final
  def hasNoMoreCells: Boolean = enumerator.hasNoMoreCells

  /**
   *
   *
   * @return
   */
  @inline
  final
  def next: (Int, Int) = {
    enumerator.advance()
    enumerator.colRowTuple
  }

}
