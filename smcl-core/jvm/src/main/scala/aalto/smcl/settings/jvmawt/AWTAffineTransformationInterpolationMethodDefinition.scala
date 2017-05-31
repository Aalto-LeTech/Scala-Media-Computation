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

package aalto.smcl.settings.jvmawt


import java.awt.image.AffineTransformOp




/**
 * Methods for performing interpolation needed during AWT-performed affine transformations.
 *
 * @author Aleksi Lukkarinen
 */
trait AWTAffineTransformationInterpolationMethodDefinition {




  /**
   * Type alias for this enumeration.
   */
  sealed abstract class AWTAffineTransformationInterpolationMethod(
      val lowLevelValue: Int)




  /**
   * Utilize "nearest neighbor" interpolation method.
   */
  case object NearestNeighbor extends AWTAffineTransformationInterpolationMethod(
    lowLevelValue = AffineTransformOp.TYPE_NEAREST_NEIGHBOR)




  /**
   * Utilize bilinear interpolation method.
   */
  case object Bilinear extends AWTAffineTransformationInterpolationMethod(
    lowLevelValue = AffineTransformOp.TYPE_BILINEAR)




  /**
   * Utilize bicubic interpolation method.
   */
  case object Bicubic extends AWTAffineTransformationInterpolationMethod(
    lowLevelValue = AffineTransformOp.TYPE_BICUBIC)




}
