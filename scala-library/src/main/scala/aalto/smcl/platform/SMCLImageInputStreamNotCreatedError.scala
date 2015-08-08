package aalto.smcl.platform


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLImageInputStreamNotCreatedError private[smcl](cause: Throwable)
  extends RuntimeException(
    "Input stream for the image file could not be created, possibly because a cache file could not be created.",
    cause) {

}
