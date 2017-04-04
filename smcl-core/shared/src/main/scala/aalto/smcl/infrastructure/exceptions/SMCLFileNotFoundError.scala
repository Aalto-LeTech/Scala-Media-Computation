package aalto.smcl.infrastructure.exceptions




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLFileNotFoundError private[smcl](filename: String, cause: Throwable)
  extends RuntimeException(s"""File \"$filename\" cannot be found.""", cause) {

}
