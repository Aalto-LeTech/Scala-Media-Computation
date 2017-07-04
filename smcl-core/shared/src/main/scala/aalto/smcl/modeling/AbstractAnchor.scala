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


import aalto.smcl.modeling.d2.HasAnchor




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
object AbstractAnchor {




  private[modeling]
  trait Left extends AbstractAnchor {

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalXWithin(anchored: HasAnchor): Double = 0

  }




  private[modeling]
  trait HCenter extends AbstractAnchor {

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalXWithin(anchored: HasAnchor): Double =
      anchored.centerFromTopLeft.xInPixels

  }




  private[modeling]
  trait Right extends AbstractAnchor {

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalXWithin(anchored: HasAnchor): Double =
      anchored.dimensions.width.inPixels

  }




  private[modeling]
  trait Top extends AbstractAnchor {

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalYWithin(anchored: HasAnchor): Double = 0

  }




  private[modeling]
  trait VCenter extends AbstractAnchor {

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalYWithin(anchored: HasAnchor): Double =
      anchored.centerFromTopLeft.yInPixels

  }




  private[modeling]
  trait Bottom extends AbstractAnchor {

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalYWithin(anchored: HasAnchor): Double =
      anchored.dimensions.height.inPixels

  }




  case object TopLeft extends Top with Left




  case object TopCenter extends Top with HCenter




  case object TopRight extends Top with Right




  case object CenterLeft extends VCenter with Left




  case object Center extends VCenter with HCenter




  case object CenterRight extends VCenter with Right




  case object BottomLeft extends Bottom with Left




  case object BottomRight extends Bottom with Right




  case object BottomCenter extends Bottom with HCenter




  /**
   *
   *
   * @param deltaFromTopLeft
   */
  case class Absolute(
      private val deltaFromTopLeft: Pos)
      extends AbstractAnchor {

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalXWithin(anchored: HasAnchor): Double = this.deltaFromTopLeft.xInPixels

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalYWithin(anchored: HasAnchor): Double = this.deltaFromTopLeft.yInPixels

  }




}




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
abstract class AbstractAnchor {

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalXWithin(
      anchored: HasAnchor): Double

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalYWithin(
      anchored: HasAnchor): Double

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalPosWithin(anchored: HasAnchor): Pos = {
    val iX = this.internalXWithin(anchored)
    val iY = this.internalYWithin(anchored)

    Pos(iX, iY)
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def toAbsoluteWithin(anchored: HasAnchor): AbstractAnchor.Absolute = {
    val iPos = this.internalPosWithin(anchored)

    AbstractAnchor.Absolute(iPos)
  }

}
