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

package smcl.modeling.d2


/**
 *
 *
 * @tparam ReturnType
 *
 * @author Aleksi Lukkarinen
 */
trait Scalable[ReturnType] {

  self: HasDims =>


  /** The scaling factor (1.0) that does not cause any effect when scaling. */
  val IdentityScalingFactor: Double = 1.0


  // Width
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Scales the width of this object in relation to its center.
   *
   * @param targetWidth
   *
   * @return
   */
  @inline
  final
  def scaleWidthTo(targetWidth: Double): ReturnType = {
    val widthFactor = targetWidth / dimensions.width.inPixels
    val heightFactor = IdentityScalingFactor

    scaleBy(widthFactor, heightFactor)
  }

  /**
   * Scales the width of this object in relation to its center.
   *
   * @param widthFactor
   *
   * @return
   */
  @inline
  final
  def scaleWidthBy(widthFactor: Double): ReturnType = {
    val heightFactor = IdentityScalingFactor

    scaleBy(widthFactor, heightFactor)
  }


  // Height
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Scales the height of this object in relation to its center.
   *
   * @param targetHeight
   *
   * @return
   */
  @inline
  final
  def scaleHeightTo(targetHeight: Double): ReturnType = {
    val widthFactor = IdentityScalingFactor
    val heightFactor = targetHeight / dimensions.height.inPixels

    scaleBy(widthFactor, heightFactor)
  }

  /**
   * Scales the height of this object in relation to its center.
   *
   * @param heightFactor
   *
   * @return
   */
  @inline
  final
  def scaleHeightBy(heightFactor: Double): ReturnType = {
    val widthFactor = IdentityScalingFactor

    scaleBy(widthFactor, heightFactor)
  }


  // Both Width and Height
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Scales this object in relation to its center.
   *
   * @param targetSideLength
   *
   * @return
   */
  @inline
  final
  def scaleTo(targetSideLength: Double): ReturnType =
    scaleTo(targetSideLength, targetSideLength)

  /**
   * Scales this object in relation to its center.
   *
   * @param factor
   *
   * @return
   */
  @inline
  final
  def scaleBy(factor: Double): ReturnType =
    scaleBy(factor, factor)

  /**
   * Scales this object in relation to its center.
   *
   * @param targetWidth
   * @param targetHeight
   *
   * @return
   */
  @inline
  final
  def scaleTo(
      targetWidth: Double,
      targetHeight: Double): ReturnType = {

    val widthFactor = targetWidth / dimensions.width.inPixels
    val heightFactor = targetHeight / dimensions.height.inPixels

    scaleBy(widthFactor, heightFactor)
  }

  /**
   * Scales this object in relation to its center.
   *
   * @param widthFactor
   * @param heightFactor
   *
   * @return
   */
  def scaleBy(
      widthFactor: Double,
      heightFactor: Double): ReturnType

}
