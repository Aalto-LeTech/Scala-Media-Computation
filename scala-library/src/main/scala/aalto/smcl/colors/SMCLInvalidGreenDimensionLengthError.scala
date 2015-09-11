package aalto.smcl.colors


import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLInvalidGreenDimensionLengthError private[smcl](actualLength: Double)
    extends RuntimeException(s"The green dimension must have exactly ${ByteRange.length} items (had $actualLength).") {

}
