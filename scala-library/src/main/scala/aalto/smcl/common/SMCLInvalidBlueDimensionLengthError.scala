package aalto.smcl.common


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLInvalidBlueDimensionLengthError private[smcl](actualLength: Double)
  extends RuntimeException(s"The blue dimension must have exactly ${ByteRange.length} items (had $actualLength).") {

}
