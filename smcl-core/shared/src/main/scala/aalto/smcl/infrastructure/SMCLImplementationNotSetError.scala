package aalto.smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLImplementationNotSetError private[smcl](classOfMissingImplementation: AnyRef)
  extends RuntimeException(
    s"""Implementation of type ${new ReflectionUtils().shortTypeNameOf(classOfMissingImplementation)} is not set""") {

}
