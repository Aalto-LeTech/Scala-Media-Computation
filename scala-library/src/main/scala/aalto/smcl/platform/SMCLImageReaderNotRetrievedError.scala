package aalto.smcl.platform


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLImageReaderNotRetrievedError private[smcl](cause: Throwable)
  extends RuntimeException("A suitable image reader for the given image file could not be retrieved.", cause) {

}
