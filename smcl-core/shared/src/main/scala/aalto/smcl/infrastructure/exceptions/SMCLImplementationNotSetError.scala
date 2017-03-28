package aalto.smcl.infrastructure.exceptions




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLImplementationNotSetError private[smcl](missingImplementationClassName: String)
  extends RuntimeException(s"""Implementation of type $missingImplementationClassName is not set""") {

}
