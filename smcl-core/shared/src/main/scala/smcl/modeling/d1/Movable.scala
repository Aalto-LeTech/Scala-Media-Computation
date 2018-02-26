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

package smcl.modeling.d1


import smcl.modeling.Len
import smcl.modeling.misc.AbstractMovable




/**
 *
 *
 * @tparam ReturnType
 *
 * @author Aleksi Lukkarinen
 */
trait Movable[ReturnType]
    extends AbstractMovable[ReturnType] {

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def moveBy(offset: Dims): ReturnType = moveBy(offset.lengthInPixels)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def + (offset: Len): ReturnType = moveBy(offset.inPixels)

  /**
   *
   *
   * @param offsetInPixels
   *
   * @return
   */
  @inline
  def + (offsetInPixels: Double): ReturnType = moveBy(offsetInPixels)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def - (offset: Len): ReturnType = moveBy(-offset.inPixels)

  /**
   *
   *
   * @param offsetInPixels
   *
   * @return
   */
  @inline
  def - (offsetInPixels: Double): ReturnType = moveBy(-offsetInPixels)

  /**
   *
   *
   * @param offsetInPixels
   *
   * @return
   */
  @inline
  def moveBy(offsetInPixels: Double): ReturnType

  /**
   *
   *
   * @param position
   *
   * @return
   */
  @inline
  def moveTo(position: Pos): ReturnType = moveTo(position.inPixels)

  /**
   *
   *
   * @param coordinateInPixels
   *
   * @return
   */
  @inline
  def moveTo(coordinateInPixels: Double): ReturnType

}
