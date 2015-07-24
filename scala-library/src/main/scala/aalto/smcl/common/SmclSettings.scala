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

  /**  */
  var displayNewBitmapsAutomatically: Boolean = true

  /**  */
  var displayBitmapsAutomaticallyAfterOperations: Boolean = true





  ///////////////////////////////////////////////
  //
  // Default bitmap width
  //
  ///////////////////////////////////////////////

  /**  */
  private[this] var _defaultBitmapWidthInPixels: Int = 50

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

  /**  */
  private[this] var _defaultBitmapHeightInPixels: Int = 50

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

  /**  */
  private[this] var _defaultBackgroundColor: Color = NamedColors.white

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




}
