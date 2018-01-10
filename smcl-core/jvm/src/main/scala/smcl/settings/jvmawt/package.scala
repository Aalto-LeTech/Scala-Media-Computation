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

package smcl.settings


import java.awt.image.AffineTransformOp


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object jvmawt {




  /**
   * A base class for constants that represent methods for performing
   * interpolation needed during AWT-performed affine transformations.
   */
  sealed abstract class AWTAffineTransformationInterpolationMethod(
      val lowLevelValue: Int)




  /**
   * A constant for utilizing the "nearest neighbor" interpolation method.
   */
  case object NearestNeighbor
      extends AWTAffineTransformationInterpolationMethod(
        lowLevelValue = AffineTransformOp.TYPE_NEAREST_NEIGHBOR)




  /**
   * A constant for utilizing the bilinear interpolation method.
   */
  case object Bilinear
      extends AWTAffineTransformationInterpolationMethod(
        lowLevelValue = AffineTransformOp.TYPE_BILINEAR)




  /**
   * A constant for utilizing the bicubic interpolation method.
   */
  case object Bicubic
      extends AWTAffineTransformationInterpolationMethod(
        lowLevelValue = AffineTransformOp.TYPE_BICUBIC)




  /** An internal setting ID for the "AffineTransformationInterpolationMethod" setting. */
  private[smcl]
  val SIdAffineTransformationInterpolationMethod =
    "AffineTransformationInterpolationMethod"

  /** */
  private lazy val AffineTransformationInterpolationMethod0 = {
    Settings(SIdAffineTransformationInterpolationMethod)
        .asInstanceOf[ObjectSetting[AWTAffineTransformationInterpolationMethod]]
  }

  /**
   *
   *
   * @return
   */
  def AffineTransformationInterpolationMethod: AWTAffineTransformationInterpolationMethod = {
    AffineTransformationInterpolationMethod0.value
  }

  /**
   *
   *
   * @param newValue
   */
  def AffineTransformationInterpolationMethod_=(
      newValue: AWTAffineTransformationInterpolationMethod): Unit = {

    AffineTransformationInterpolationMethod0.value = newValue
  }

}
