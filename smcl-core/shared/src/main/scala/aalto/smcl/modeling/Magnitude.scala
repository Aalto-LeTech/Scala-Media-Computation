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

package aalto.smcl.modeling


import aalto.smcl.infrastructure.{CommonDoubleMathOps, ItemItemMap, ToTuple}




/**
 * Base class for magnitudes, such as length, area, and volume.
 *
 * @param inPixels
 *
 * @author Aleksi Lukkarinen
 */
abstract class Magnitude[ElementType](
    val inPixels: Double)
    extends GeometryObject
            with ToTuple[Tuple1[Double]]
            with ItemItemMap[ElementType, Double]
            with CommonDoubleMathOps[ElementType] {

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  override def toTuple: Tuple1[Double] = {
    Tuple1(inPixels)
  }

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  def toIntTuple: Tuple1[Int] = {
    Tuple1(inPixels.toInt)
  }

}
