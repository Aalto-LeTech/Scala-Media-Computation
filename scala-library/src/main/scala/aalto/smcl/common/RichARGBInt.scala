package aalto.smcl.common


/**
 * Some methods for composing ARGB-style `Int` values as well as
 * extracting the individual color components from them.
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 */
class RichARGBInt(val self: Int) extends AnyVal {

  /**
   * Returns an immutable map containing individual color components of this ARGB-style `Int`.
   * The keys in the map are `'red`, `'green`, `'blue`, `'opacity`, `'hue`, `'saturation`, and `'intensity`.
   *
   * {{{
   * scala> 0x89ABCDEF.colorComponentMap
   * res0: Map[Symbol,Double] = Map('red -> 171, 'green -> 205, 'blue -> 239, 'opacity -> 137) // 0x89 = 137 etc.
   * }}}
   */
  // TODO: Update the example in the comment!!!!
  def colorComponentMap: Map[Symbol, Double] = colorComponentMapFrom(self)

  /**
   * Returns the red color component of this ARGB-style `Int`.
   */
  def redComponentInt: Int = redComponentOf(self)

  /**
   * Returns the green color component of this ARGB-style `Int`.
   */
  def greenComponentInt: Int = greenComponentOf(self)

  /**
   * Returns the blue color component of this ARGB-style `Int`.
   */
  def blueComponentInt: Int = blueComponentOf(self)

  /**
   * Returns the opacity component of this ARGB-style `Int`.
   */
  def opacityComponentInt: Int = opacityComponentOf(self)

  /**
   * Displays this `Int` as a zero-padded hexadecimal form.
   *
   * {{{
   * scala> -1985229329.toArgbHexColorString
   * res0: String = 89abcdef
   * }}}
   */
  def toArgbHexColorString: String = f"$self%08x"

  /**
   * Displays this `Int` as a zero-padded binary form divided to bytes by spaces.
   *
   * {{{
   * scala> 0x89abcdef.toArgbBinaryColorString
   * res0: String = 10001001 10101011 11001101 11101111
   * }}}
   */
  def toArgbBinaryColorString: String =
    self.toBinaryString.format("$s%32s").replace(StrSpace, StrZero)
        .sliding(OneByte, OneByte).mkString(StrSpace)

}
