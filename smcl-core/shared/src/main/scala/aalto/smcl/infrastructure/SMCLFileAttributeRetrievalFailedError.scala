package aalto.smcl.infrastructure

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLFileAttributeRetrievalFailedError private[smcl](cause: Throwable)
  extends RuntimeException("An error occurred while retrieving file attributes.", cause) {

}
