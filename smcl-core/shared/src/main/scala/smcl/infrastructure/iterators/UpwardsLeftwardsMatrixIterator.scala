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


import smcl.infrastructure.enumerators.{AbstractMatrixEnumerator2D, UpwardsLeftwardsMatrixEnumerator}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object UpwardsLeftwardsMatrixIterator {

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
      height: Int): MatrixIterator2D = {

    val enumerator =
      UpwardsLeftwardsMatrixEnumerator(
        startColumn, startRow, width, height)

    new UpwardsLeftwardsMatrixIterator(enumerator)
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class UpwardsLeftwardsMatrixIterator private(
    enumerator: AbstractMatrixEnumerator2D)
    extends MatrixIterator2D(enumerator) {

}
