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

package aalto.smcl.infrastructure.jvmawt


import java.awt.image.AffineTransformOp




/**
 * Methods for performing interpolation needed during AWT-performed affine transformations.
 *
 * @author Aleksi Lukkarinen
 */
object AwtAffineTransformationInterpolationMethod extends Enumeration {

  /** Type alias for this enumeration. */
  type BitmapInterpolationMethod = Value

  /** Utilize "nearest neighbor" interpolation method. */
  val NearestNeighbor = Value(AffineTransformOp.TYPE_NEAREST_NEIGHBOR)

  /** Utilize bilinear interpolation method. */
  val Bilinear = Value(AffineTransformOp.TYPE_BILINEAR)

  /** Utilize bicubic interpolation method. */
  val Bicubic = Value(AffineTransformOp.TYPE_BICUBIC)

}
