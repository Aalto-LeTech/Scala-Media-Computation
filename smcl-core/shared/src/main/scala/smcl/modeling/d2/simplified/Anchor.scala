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


package smcl.modeling.d2.simplified


/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
object Anchor {




  private[modeling]
  trait Left
      extends Anchor {

    def internalXWithin(anchored: HasAnchor) = 0

  }




  private[modeling]
  trait HCenter
      extends Anchor {

    def internalXWithin(anchored: HasAnchor): Double =
      anchored.centerFromTopLeft.x
  }




  private[modeling]
  trait Right
      extends Anchor {

    def internalXWithin(anchored: HasAnchor): Double =
      anchored.width

  }




  private[modeling]
  trait Top
      extends Anchor {

    def internalYWithin(anchored: HasAnchor) = 0

  }




  private[modeling]
  trait VCenter
      extends Anchor {

    def internalYWithin(anchored: HasAnchor): Double =
      anchored.centerFromTopLeft.y

  }




  private[modeling]
  trait Bottom
      extends Anchor {

    def internalYWithin(anchored: HasAnchor): Double =
      anchored.height

  }




  /**
   *
   */
  case object TopLeft extends Top with Left




  /**
   *
   */
  case object TopCenter extends Top with HCenter




  /**
   *
   */
  case object TopRight extends Top with Right




  /**
   *
   */
  case object CenterLeft extends VCenter with Left




  /**
   *
   */
  case object Center extends VCenter with HCenter




  /**
   *
   */
  case object CenterRight extends VCenter with Right




  /**
   *
   */
  case object BottomLeft extends Bottom with Left




  /**
   *
   */
  case object BottomRight extends Bottom with Right




  /**
   *
   */
  case object BottomCenter extends Bottom with HCenter




  /**
   *
   *
   * @param deltaFromTopLeft
   */
  case class Absolute(private val deltaFromTopLeft: Pos)
      extends Anchor {

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalXWithin(anchored: HasAnchor): Double =
      deltaFromTopLeft.x

    /**
     *
     *
     * @param anchored
     *
     * @return
     */
    def internalYWithin(anchored: HasAnchor): Double =
      deltaFromTopLeft.y

  }




}




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
abstract class Anchor {

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
  def internalPosWithin(anchored: HasAnchor) =
    Pos(
      internalXWithin(anchored),
      internalYWithin(anchored))

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def toAbsoluteWithin(anchored: HasAnchor) =
    Anchor.Absolute(internalPosWithin(anchored))

}
