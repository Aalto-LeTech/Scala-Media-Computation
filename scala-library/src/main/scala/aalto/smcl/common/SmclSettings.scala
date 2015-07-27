package aalto.smcl.common


import aalto.smcl.images.immutable.{Color, NamedColors}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class SmclSettings() {

  ///////////////////////////////////////////////
  //
  // Default values of all settings
  //
  ///////////////////////////////////////////////

  /** */
  val DISPLAY_NEW_BITMAPS_AUTOMATICALLY_DEFAULT: Boolean = true

  /** */
  val DISPLAY_BITMAPS_AUTOMATICALLY_AFTER_OPERATIONS: Boolean = true

  /** */
  val DEFAULT_BITMAP_WIDTH_IN_PIXELS_DEFAULT: Int = 50

  /** */
  val DEFAULT_BITMAP_HEIGHT_IN_PIXELS_DEFAULT: Int = 50

  /** */
  val DEFAULT_BACKGROUND_COLOR_DEFAULT: Color = NamedColors.white

  /** */
  val DEFAULT_PRIMARY_COLOR_DEFAULT: Color = NamedColors.black

  /** */
  val DEFAULT_SECONDARY_COLOR_DEFAULT: Color = NamedColors.black


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

}
