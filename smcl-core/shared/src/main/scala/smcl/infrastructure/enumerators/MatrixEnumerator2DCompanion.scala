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


import smcl.infrastructure.exceptions.{NegativeHeightError, NegativeUpperLeftColumnError, NegativeUpperLeftRowError, NegativeWidthError}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait MatrixEnumerator2DCompanion {

  /** */
  val InitialValue: Int = MatrixEnumerator2DInternalState.InitialValue

  /** */
  val UndefinedValue: Int = Integer.MIN_VALUE

  /**
   *
   *
   * @param upperLeftColumn
   * @param upperLeftRow
   * @param width
   * @param height
   */
  def checkArguments(
      upperLeftColumn: Int,
      upperLeftRow: Int,
      width: Int,
      height: Int): Unit = {

    if (upperLeftColumn < 0)
      throw NegativeUpperLeftColumnError(upperLeftColumn)

    if (upperLeftRow < 0)
      throw NegativeUpperLeftRowError(upperLeftRow)

    if (width < 0)
      throw NegativeWidthError(width)

    if (height < 0)
      throw NegativeHeightError(height)
  }

}
