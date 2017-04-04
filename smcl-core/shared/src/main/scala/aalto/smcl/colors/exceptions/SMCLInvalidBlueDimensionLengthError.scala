package aalto.smcl.colors.exceptions


import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLInvalidBlueDimensionLengthError private[smcl](actualLength: Double)
  extends RuntimeException(s"The blue dimension must have exactly ${ByteRange.length} items (had $actualLength).") {

}
