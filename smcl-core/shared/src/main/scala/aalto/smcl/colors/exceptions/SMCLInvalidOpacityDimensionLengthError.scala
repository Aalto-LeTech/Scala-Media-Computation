package aalto.smcl.colors.exceptions


import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLInvalidOpacityDimensionLengthError private[smcl](actualLength: Double)
  extends RuntimeException(s"The opacity dimension must have exactly ${ByteRange.length} items (had $actualLength).") {

}
