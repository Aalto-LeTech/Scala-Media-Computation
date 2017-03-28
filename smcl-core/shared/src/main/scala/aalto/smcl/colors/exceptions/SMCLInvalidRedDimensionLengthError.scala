package aalto.smcl.colors.exceptions

import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLInvalidRedDimensionLengthError private[smcl](actualLength: Double)
  extends RuntimeException(s"The red dimension must have exactly ${ByteRange.length} items (had $actualLength).") {

}
