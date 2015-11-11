package aalto.smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLSuitableImageStreamProviderNotFoundError private[smcl]()
  extends RuntimeException(
    "Input stream for the image file could not be created because a suitable stream provider could not be found.") {

}
