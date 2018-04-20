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
 * @author Aleksi Lukkarinen
 */
trait ScalableArea[ReturnType] {

  // Scale using a target width
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Scales this object to a given width in relation to its center.
   *
   * @param targetWidth
   *
   * @return
   */
  def scaleHorizontallyTo(targetWidth: Double): ReturnType

  /**
   * Scales this object to a given width in relation to a given point.
   *
   * @param targetWidth
   * @param relativityPoint
   *
   * @return
   */
  def scaleHorizontallyTo(
      targetWidth: Double,
      relativityPoint: Pos): ReturnType

  /**
   * Scales this object to a given width in relation to the origo.
   *
   * @param targetWidth
   *
   * @return
   */
  def scaleHorizontallyToRelativeToOrigo(targetWidth: Double): ReturnType


  // Scale using a target height
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Scales this object to a given height in relation to its center.
   *
   * @param targetHeight
   *
   * @return
   */
  def scaleVerticallyTo(targetHeight: Double): ReturnType

  /**
   * Scales this object to a given height in relation to a given point.
   *
   * @param targetHeight
   * @param relativityPoint
   *
   * @return
   */
  def scaleVerticallyTo(
      targetHeight: Double,
      relativityPoint: Pos): ReturnType

  /**
   * Scales this object to a given height in relation to the origo.
   *
   * @param targetHeight
   *
   * @return
   */
  def scaleVerticallyToRelativeToOrigo(targetHeight: Double): ReturnType


  // Scale using a single length for both target width and target height
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Scales this object in relation to its center by
   * using a single length for both width and height.
   *
   * @param targetSideLength
   *
   * @return
   */
  def scaleTo(targetSideLength: Double): ReturnType

  /**
   * Scales this object in relation to a given point by
   * using a single length for both width and height.
   *
   * @param targetSideLength
   * @param relativityPoint
   *
   * @return
   */
  def scaleTo(
      targetSideLength: Double,
      relativityPoint: Pos): ReturnType

  /**
   * Scales this object in relation to the origo by
   * using a single length for both width and height.
   *
   * @param targetSideLength
   *
   * @return
   */
  def scaleToRelativeToOrigo(targetSideLength: Double): ReturnType


  // Scale using both target width and target height
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Scales this object to given width and height in relation to its center.
   *
   * @param targetWidth
   * @param targetHeight
   *
   * @return
   */
  def scaleTo(
      targetWidth: Double,
      targetHeight: Double): ReturnType

  /**
   * Scales this object to given width and height in relation to a given point.
   *
   * @param targetWidth
   * @param targetHeight
   * @param relativityPoint
   *
   * @return
   */
  def scaleTo(
      targetWidth: Double,
      targetHeight: Double,
      relativityPoint: Pos): ReturnType

  /**
   * Scales this object to given width and height in relation to the origo.
   *
   * @param targetWidth
   * @param targetHeight
   *
   * @return
   */
  def scaleToRelativeToOrigo(
      targetWidth: Double,
      targetHeight: Double): ReturnType

}
