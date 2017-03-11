package aalto.smcl.infrastructure

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLSuitableImageReaderNotFoundError private[smcl]()
  extends RuntimeException("A suitable image reader for the given image file could not be found.") {

}
