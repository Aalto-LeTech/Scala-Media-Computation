package aalto.smcl.infrastructure


import java.awt.image.AffineTransformOp




/**
 * Methods for performing interpolation needed during platform-performed bitmap operations.
 *
 * @author Aleksi Lukkarinen
 */
object BitmapInterpolationMethod extends Enumeration {

  /** Type alias for this enumeration. */
  type BitmapInterpolationMethod = Value

  /** Utilize "nearest neighbor" interpolation method. */
  val NearestNeighbor = Value(AffineTransformOp.TYPE_NEAREST_NEIGHBOR)

  /** Utilize bilinear interpolation method. */
  val Bilinear = Value(AffineTransformOp.TYPE_BILINEAR)

  /** Utilize bicubic interpolation method. */
  val Bicubic = Value(AffineTransformOp.TYPE_BICUBIC)

}
