package aalto.smcl


import aalto.smcl.common._
import aalto.smcl.common.settings.SmclSettingKey
import aalto.smcl.images.immutable._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object images {

  case object DisplayNewBitmapsAutomatically extends SmclSettingKey
  GlobalSettings.init(DisplayNewBitmapsAutomatically, true)

  case object DisplayBitmapsAutomaticallyAfterOperations extends SmclSettingKey
  GlobalSettings.init(DisplayBitmapsAutomaticallyAfterOperations, true)

  case object DefaultBitmapWidthInPixels extends SmclSettingKey
  GlobalSettings.init(DefaultBitmapWidthInPixels, 50)

  case object DefaultBitmapHeightInPixels extends SmclSettingKey
  GlobalSettings.init(DefaultBitmapWidthInPixels, 50)

  case object DefaultBackgroundColor extends SmclSettingKey
  GlobalSettings.init(DefaultBackgroundColor, PresetColors('white))

  case object DefaultPrimaryColor extends SmclSettingKey
  GlobalSettings.init(DefaultPrimaryColor, PresetColors('black))

  case object DefaultSecondaryColor extends SmclSettingKey
  GlobalSettings.init(DefaultSecondaryColor, PresetColors('black))

  /** */
  val DISPLAY_NEW_BITMAPS_AUTOMATICALLY_DEFAULT: Boolean = true

  /** */
  val DISPLAY_BITMAPS_AUTOMATICALLY_AFTER_OPERATIONS: Boolean = true

  /** */
  val DEFAULT_BITMAP_WIDTH_IN_PIXELS_DEFAULT: Int = 50

  /** */
  val DEFAULT_BITMAP_HEIGHT_IN_PIXELS_DEFAULT: Int = 50

  /** */
  val DEFAULT_BACKGROUND_COLOR_DEFAULT: Color = PresetColors('white)

  /** */
  val DEFAULT_PRIMARY_COLOR_DEFAULT: Color = PresetColors('black)

  /** */
  val DEFAULT_SECONDARY_COLOR_DEFAULT: Color = PresetColors('black)


  ///////////////////////////////////////////////
  //
  // One-variable settings
  //
  ///////////////////////////////////////////////

  /** */
  var displayNewBitmapsAutomatically: Boolean = DISPLAY_NEW_BITMAPS_AUTOMATICALLY_DEFAULT

  /** */
  var displayBitmapsAutomaticallyAfterOperations: Boolean = DISPLAY_BITMAPS_AUTOMATICALLY_AFTER_OPERATIONS


  ///////////////////////////////////////////////
  //
  // Default bitmap width
  //
  ///////////////////////////////////////////////

  /** */
  private[this] var _defaultBitmapWidthInPixels: Int = DEFAULT_BITMAP_WIDTH_IN_PIXELS_DEFAULT

  /**
   *
   */
  def defaultBitmapWidthInPixels: Int = _defaultBitmapWidthInPixels

  /**
   *
   *
   * @param value
   * @return
   */
  def defaultBitmapWidthInPixels_=(value: Int): Unit = {
    require(value > 9, "Default bitmap width must be larger than 9 pixels.")

    _defaultBitmapWidthInPixels = value
  }


  ///////////////////////////////////////////////
  //
  // Default bitmap height
  //
  ///////////////////////////////////////////////

  /** */
  private[this] var _defaultBitmapHeightInPixels: Int = DEFAULT_BITMAP_HEIGHT_IN_PIXELS_DEFAULT

  /**
   *
   */
  def defaultBitmapHeightInPixels: Int = _defaultBitmapHeightInPixels

  /**
   *
   *
   * @param value
   * @return
   */
  def defaultBitmapHeightInPixels_=(value: Int): Unit = {
    require(value > 9, "Default bitmap height must be larger than 9 pixels.")

    _defaultBitmapHeightInPixels = value
  }


  ///////////////////////////////////////////////
  //
  // Default background color
  //
  ///////////////////////////////////////////////

  /** */
  private[this] var _defaultBackgroundColor: Color = DEFAULT_BACKGROUND_COLOR_DEFAULT

  /**
   *
   */
  def defaultBackgroundColor: Color = _defaultBackgroundColor

  /**
   *
   *
   * @param value
   * @return
   */
  def defaultBackgroundColor_=(value: Color): Unit = {
    require(value != null, "Default background color cannot be null.")

    _defaultBackgroundColor = value
  }


  ///////////////////////////////////////////////
  //
  // Default primary color
  //
  ///////////////////////////////////////////////

  /** */
  private[this] var _defaultPrimaryColor: Color = DEFAULT_PRIMARY_COLOR_DEFAULT

  /**
   *
   */
  def defaultPrimaryColor: Color = _defaultPrimaryColor

  /**
   *
   *
   * @param value
   * @return
   */
  def defaultPrimaryColor_=(value: Color): Unit = {
    require(value != null, "Default primary color cannot be null.")

    _defaultPrimaryColor = value
  }


  ///////////////////////////////////////////////
  //
  // Default secondary color
  //
  ///////////////////////////////////////////////

  /** */
  private[this] var _defaultSecondaryColor: Color = DEFAULT_SECONDARY_COLOR_DEFAULT

  /**
   *
   */
  def defaultSecondaryColor: Color = _defaultSecondaryColor

  /**
   *
   *
   * @param value
   * @return
   */
  def defaultSecondaryColor_=(value: Color): Unit = {
    require(value != null, "Default secondary color cannot be null.")

    _defaultSecondaryColor = value
  }


  ///////////////////////////////////////////////
  //
  // Resetting the settings to default values
  //
  ///////////////////////////////////////////////

  /**
   * Resets all settings to their default values.
   */
  def resetToDefaults(): Unit = {
    displayNewBitmapsAutomatically = DISPLAY_NEW_BITMAPS_AUTOMATICALLY_DEFAULT
    displayBitmapsAutomaticallyAfterOperations = DISPLAY_BITMAPS_AUTOMATICALLY_AFTER_OPERATIONS

    defaultBitmapWidthInPixels = DEFAULT_BITMAP_WIDTH_IN_PIXELS_DEFAULT
    defaultBitmapHeightInPixels = DEFAULT_BITMAP_HEIGHT_IN_PIXELS_DEFAULT

    defaultBackgroundColor = DEFAULT_BACKGROUND_COLOR_DEFAULT
    defaultPrimaryColor = DEFAULT_PRIMARY_COLOR_DEFAULT
    defaultSecondaryColor = DEFAULT_SECONDARY_COLOR_DEFAULT
  }

  /** */
  private[this] val _viewerClient = new ViewerClient()

  /** Default width of [[Bitmap]] instances created without giving width. */
  val DEFAULT_IMAGE_WIDTH_IN_PIXELS: Int = 50

  /** Default height of [[Bitmap]] instances created without giving height. */
  val DEFAULT_IMAGE_HEIGHT_IN_PIXELS: Int = 50

  /**
   *
   *
   * @param sourceBitmap
   */
  def display(sourceBitmap: Bitmap): Unit = _viewerClient.display(sourceBitmap)

  /**
   *
   */
  def closeBitmapViewersWithoutSaving(): Unit = _viewerClient.closeAllViewersWithTheForce()

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


  /**
   * A string interpolator for creating [[Bitmap]] instances.
   *
   * @param sc
   */
  implicit class BitmapCreationStringInterpolator(val sc: StringContext) extends AnyVal {

    /**
     *
     *
     * @param args
     * @return
     */
    def bmp(args: Any*): Bitmap = {
      val s = sc.standardInterpolator(StringContext.processEscapes, args)

      // TODO: Replace with real functionality when it is available
      Bitmap()
    }
  }

}
