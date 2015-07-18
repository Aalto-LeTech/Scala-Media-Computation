package aalto.smcl.images.operations


/**
 * A marker trait for those [[AbstractOperation]] classes that
 * need to be able to read pixels of the operated image.
 *
 * @author Aleksi Lukkarinen
 */
private[images] trait PixelReader {
  this: AbstractOperation =>

}
