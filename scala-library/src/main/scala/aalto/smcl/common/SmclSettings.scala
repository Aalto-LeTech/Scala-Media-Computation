package aalto.smcl.common


import aalto.smcl.images.immutable.{Color, NamedColors}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class SmclSettings() extends Mutable {

  ///////////////////////////////////////////////
  //
  // One-variable settings
  //
  ///////////////////////////////////////////////

  /** */
  val DISPLAY_NEW_BITMAPS_AUTOMATICALLY_DEFAULT = true

  /** */
  var displayNewBitmapsAutomatically: Boolean =
    DISPLAY_NEW_BITMAPS_AUTOMATICALLY_DEFAULT

  /** */
  val DISPLAY_BITMAPS_AUTOMATICALLY_AFTER_OPERATIONS = true

  /** */
  var displayBitmapsAutomaticallyAfterOperations: Boolean =
    DISPLAY_BITMAPS_AUTOMATICALLY_AFTER_OPERATIONS


  ///////////////////////////////////////////////
  //
  // Default bitmap width
  //
  ///////////////////////////////////////////////

  /** */
  val DEFAULT_BITMAP_WIDTH_IN_PIXELS_DEFAULT = 50

  /** */
  private[this] var _defaultBitmapWidthInPixels: Int =
    DEFAULT_BITMAP_WIDTH_IN_PIXELS_DEFAULT

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
  val DEFAULT_BITMAP_HEIGHT_IN_PIXELS_DEFAULT = 50

  /** */
  private[this] var _defaultBitmapHeightInPixels: Int =
    DEFAULT_BITMAP_HEIGHT_IN_PIXELS_DEFAULT

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
  val DEFAULT_BACKGROUND_COLOR_DEFAULT = NamedColors.white

  /** */
  private[this] var _defaultBackgroundColor: Color =
    DEFAULT_BACKGROUND_COLOR_DEFAULT

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
  val DEFAULT_PRIMARY_COLOR_DEFAULT = NamedColors.black

  /** */
  private[this] var _defaultPrimaryColor: Color =
    DEFAULT_PRIMARY_COLOR_DEFAULT

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
  val DEFAULT_SECONDARY_COLOR_DEFAULT = NamedColors.black

  /** */
  private[this] var _defaultSecondaryColor: Color =
    DEFAULT_SECONDARY_COLOR_DEFAULT

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
  def reset(): Unit = {
    displayNewBitmapsAutomatically = DISPLAY_NEW_BITMAPS_AUTOMATICALLY_DEFAULT
    displayBitmapsAutomaticallyAfterOperations = DISPLAY_BITMAPS_AUTOMATICALLY_AFTER_OPERATIONS

    defaultBitmapWidthInPixels = DEFAULT_BITMAP_WIDTH_IN_PIXELS_DEFAULT
    defaultBitmapHeightInPixels = DEFAULT_BITMAP_HEIGHT_IN_PIXELS_DEFAULT

    defaultBackgroundColor = DEFAULT_BACKGROUND_COLOR_DEFAULT
    defaultPrimaryColor = DEFAULT_PRIMARY_COLOR_DEFAULT
    defaultSecondaryColor = DEFAULT_SECONDARY_COLOR_DEFAULT
  }

}
