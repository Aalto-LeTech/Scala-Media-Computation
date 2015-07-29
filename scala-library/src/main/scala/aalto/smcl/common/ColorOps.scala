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
    require(ByteRange.contains(newRed),
      s"'newRed' must be between ${ByteRange.start} and ${ByteRange.end} (was $newRed)")

    (pixelInt & ~ThirdByte) | (newRed << TwoBytes)
  }

  /**
   *
   */
  def withNewGreenComponent(pixelInt: Int, newGreen: Int): Int = {
    require(ByteRange.contains(newGreen),
      s"'newGreen' must be between ${ByteRange.start} and ${ByteRange.end} (was $newGreen)")

    (pixelInt & ~SecondByte) | (newGreen << OneByte)
  }

  /**
   *
   */
  def withNewBlueComponent(pixelInt: Int, newBlue: Int): Int = {
    require(ByteRange.contains(newBlue),
      s"'newBlue' must be between ${ByteRange.start} and ${ByteRange.end} (was $newBlue)")

    (pixelInt & ~FirstByte) | newBlue
  }

  /**
   *
   */
  def withNewTransparencyComponent(pixelInt: Int, newTransparency: Int): Int = {
    require(ByteRange.contains(newTransparency),
      s"'newTransparency' must be between ${ByteRange.start} and ${ByteRange.end} (was $newTransparency)")

    (pixelInt & ~FourthByte) | (newTransparency << ThreeBytes)
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
  def redComponentFrom(pixelInt: Int): Int = (pixelInt & ThirdByte) >>> TwoBytes

  /**
   *
   */
  def greenComponentFrom(pixelInt: Int): Int = (pixelInt & SecondByte) >>> OneByte

  /**
   *
   */
  def blueComponentFrom(pixelInt: Int): Int = pixelInt & FirstByte

  /**
   *
   */
  def transparencyComponentFrom(pixelInt: Int): Int = pixelInt >>> ThreeBytes

  /**
   *
   */
  def pixelIntFrom(red: Int = MinimumRed,
      green: Int = MinimumGreen,
      blue: Int = MinimumBlue,
      transparency: Int = FullyOpaque): Int = {

    require(ByteRange.contains(red),
      s"The 'red' value must be between ${ByteRange.start} and ${ByteRange.end} (was $red)")

    require(ByteRange.contains(green),
      s"The 'green' value must be between ${ByteRange.start} and ${ByteRange.end} (was $green)")

    require(ByteRange.contains(blue),
      s"The 'blue' value must be between ${ByteRange.start} and ${ByteRange.end} (was $blue)")

    require(ByteRange.contains(transparency),
      s"The transparency value must be between ${ByteRange.start} and ${ByteRange.end} (was $transparency)")

    (transparency << ThreeBytes) | (red << TwoBytes) | (green << OneByte) | blue
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
      self.toBinaryString.format("$s%32s").replace(StrSpace, StrZero)
          .sliding(OneByte, OneByte).mkString(StrSpace)

  }


}
