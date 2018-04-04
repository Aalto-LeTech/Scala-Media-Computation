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

package smcl.pictures.iterators


import scala.collection.AbstractIterator

import smcl.infrastructure.enumerators.{AbstractMatrixEnumerator2D, MatrixEnumerationStyle2D, MatrixEnumerator2D}
import smcl.pictures.{Pixel, PixelSnapshot}




/**
 *
 *
 * @param relatedPixelSnapshot
 * @param enumerationStyle
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractPixelSnapshotIterator(
    val relatedPixelSnapshot: PixelSnapshot,
    private val enumerationStyle: MatrixEnumerationStyle2D)
    extends AbstractIterator[Pixel] {

  /** */
  private
  val enumerator: AbstractMatrixEnumerator2D =
    MatrixEnumerator2D(
      upperLeftColumn = 0,
      upperLeftRow = 0,
      width = relatedPixelSnapshot.widthInPixels,
      height = relatedPixelSnapshot.heightInPixels,
      enumerationStyle = enumerationStyle)

  /**
   *
   *
   * @return
   */
  @inline
  final
  def widthInPixels: Int = enumerator.width

  /**
   *
   *
   * @return
   */
  @inline
  final
  def heightInPixels: Int = enumerator.height

  /**
   *
   *
   * @return
   */
  @inline
  final
  def upperLeftColumnInPixels: Int = enumerator.upperLeftColumn

  /**
   *
   *
   * @return
   */
  @inline
  final
  def lowerRightColumnInPixels: Int = enumerator.lowerRightColumn

  /**
   *
   *
   * @return
   */
  @inline
  final
  def upperLeftRowInPixels: Int = enumerator.upperLeftRow

  /**
   *
   *
   * @return
   */
  @inline
  final
  def lowerRightRowInPixels: Int = enumerator.lowerRightRow

  /**
   *
   *
   * @return
   */
  @inline
  final
  def currentColumnInPixels: Int = enumerator.currentColumn

  /**
   *
   *
   * @return
   */
  @inline
  final
  def currentRowInPixels: Int = enumerator.currentRow

  /**
   *
   *
   * @return
   */
  @inline
  final
  def currentCoordinatesInPixels: (Int, Int) = enumerator.colRowTuple

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
  def hasNoMorePixels: Boolean = enumerator.hasNoMoreCells

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def next(): Pixel = {
    enumerator.advance()

    Pixel(
      relatedPixelSnapshot,
      upperLeftColumnInPixels, lowerRightColumnInPixels,
      upperLeftRowInPixels, lowerRightRowInPixels,
      enumerator.currentColumn, enumerator.currentRow)
  }

}
