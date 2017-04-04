package aalto.smcl.infrastructure.exceptions




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLUnknownMediaTypeError private[smcl]()
  extends RuntimeException(
    s"""The given object does not represent a known media type.""") {

}
