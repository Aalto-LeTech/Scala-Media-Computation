package aalto.smcl.common


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ColorOps {

  /**
   *
   */
  def withNewRedComponent(pixelInt: Int, newRed: Int): Int = {
    require(BYTE_RANGE.contains(newRed),
      s"'newRed' must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $newRed)")

    (pixelInt & ~THIRD_BYTE) | (newRed << TWO_BYTES)
  }

  /**
   *
   */
  def withNewGreenComponent(pixelInt: Int, newGreen: Int): Int = {
    require(BYTE_RANGE.contains(newGreen),
      s"'newGreen' must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $newGreen)")

    (pixelInt & ~SECOND_BYTE) | (newGreen << ONE_BYTE)
  }

  /**
   *
   */
  def withNewBlueComponent(pixelInt: Int, newBlue: Int): Int = {
    require(BYTE_RANGE.contains(newBlue),
      s"'newBlue' must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $newBlue)")

    (pixelInt & ~FIRST_BYTE) | newBlue
  }

  /**
   *
   */
  def withNewTransparencyComponent(pixelInt: Int, newTransparency: Int): Int = {
    require(BYTE_RANGE.contains(newTransparency),
      s"'newTransparency' must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $newTransparency)")

    (pixelInt & ~FOURTH_BYTE) | (newTransparency << THREE_BYTES)
  }

  /**
   *
   */
  def colorComponentsFrom(pixelInt: Int): collection.immutable.Map[Symbol, Int] =
    collection.immutable.Map[Symbol, Int](
      'red -> redComponentFrom(pixelInt),
      'green -> greenComponentFrom(pixelInt),
      'blue -> blueComponentFrom(pixelInt),
      'transparency -> transparencyComponentFrom(pixelInt))

  /**
   *
   */
  def redComponentFrom(pixelInt: Int): Int = (pixelInt & THIRD_BYTE) >>> TWO_BYTES

  /**
   * l
   */
  def greenComponentFrom(pixelInt: Int): Int = (pixelInt & SECOND_BYTE) >>> ONE_BYTE

  /**
   *
   */
  def blueComponentFrom(pixelInt: Int): Int = pixelInt & FIRST_BYTE

  /**
   *
   */
  def transparencyComponentFrom(pixelInt: Int): Int = pixelInt >>> THREE_BYTES

  /**
   *
   */
  def pixelIntFrom(red: Int = MIN_RED,
      green: Int = MIN_GREEN,
      blue: Int = MIN_BLUE,
      transparency: Int = FULLY_OPAQUE): Int = {

    require(BYTE_RANGE.contains(red),
      s"The 'red' value must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $red)")

    require(BYTE_RANGE.contains(green),
      s"The 'green' value must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $green)")

    require(BYTE_RANGE.contains(blue),
      s"The 'blue' value must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $blue)")

    require(BYTE_RANGE.contains(transparency),
      s"The transparency value must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $transparency)")

    (transparency << THREE_BYTES) | (red << TWO_BYTES) | (green << ONE_BYTE) | blue
  }


  /**
   * Some methods for composing ARGB-style `Int` values as well as
   * extracting the individual color components from them.
   */
  implicit class RichPixelInt(val self: Int) extends AnyVal {

    /**
     * Returns an immutable map containing individual color components of this ARGB-style `Int`.
     * The keys in the map are `'red`, `'green`, `'blue`, and `'transparency`.
     *
     * {{{
     * scala> 0x89ABCDEF.colorComponentInts
     * res0: Map[Symbol,Int] = Map('red -> 171, 'green -> 205, 'blue -> 239, 'transparency -> 137) // 0x89 = 137 etc.
     * }}}
     */
    def colorComponentMap: Map[Symbol, Int] = colorComponentsFrom(self)

    /**
     * Returns the red color component of this ARGB-style `Int`.
     */
    def redComponentInt: Int = redComponentFrom(self)

    /**
     * Returns the green color component of this ARGB-style `Int`.
     */
    def greenComponentInt: Int = greenComponentFrom(self)

    /**
     * Returns the blue color component of this ARGB-style `Int`.
     */
    def blueComponentInt: Int = blueComponentFrom(self)

    /**
     * Returns the transparency component of this ARGB-style `Int`.
     */
    def transparencyComponentInt: Int = transparencyComponentFrom(self)

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
      self.toBinaryString.format("$s%32s").replace(STR_SPACE, STR_ZERO)
          .sliding(ONE_BYTE, ONE_BYTE).mkString(STR_SPACE)

  }


}
