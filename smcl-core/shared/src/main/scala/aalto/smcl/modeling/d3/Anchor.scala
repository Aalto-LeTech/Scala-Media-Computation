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

package aalto.smcl.modeling.d3


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Anchor {

}




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
trait Anchor {

  /**
   * Name of this anchor.
   *
   * @return
   */
  def name: Option[String]

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def toPointAnchor(anchored: HasAnchor): PointAnchor = {
    PointAnchor(
      internalXWithin(anchored),
      internalYWithin(anchored),
      internalZWithin(anchored),
      name)
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalDimsWithin(anchored: HasAnchor): Dims = {
    Dims(
      internalXWithin(anchored),
      internalYWithin(anchored),
      internalZWithin(anchored))
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalXWithin(anchored: HasAnchor): Double

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalYWithin(anchored: HasAnchor): Double

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalZWithin(anchored: HasAnchor): Double

}
