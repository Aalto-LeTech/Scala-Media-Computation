package aalto.smcl.common


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLInvalidOpacityDimensionLengthError private[smcl](actualLength: Double)
  extends RuntimeException(s"The opacity dimension must have exactly ${ByteRange.length} items (had $actualLength).") {

}
