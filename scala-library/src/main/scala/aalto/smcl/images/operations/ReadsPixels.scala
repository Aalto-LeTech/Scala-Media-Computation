package aalto.smcl.images.operations

/**
 * Marker trait for those [[BitmapOperation]] classes that
 * need to be able to read pixels of the operated image.
 *
 * @author Aleksi Lukkarinen
 */
trait ReadsPixels { this: BitmapOperation =>

}
