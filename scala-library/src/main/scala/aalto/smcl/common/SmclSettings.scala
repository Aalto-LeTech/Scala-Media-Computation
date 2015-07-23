package aalto.smcl.common


import aalto.smcl.images.immutable.{NamedColors, Color}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class SmclSettings() extends Mutable {

  /**  */
  var displayNewBitmapsAutomatically: Boolean = true

  /**  */
  var displayBitmapsAutomaticallyAfterOperations: Boolean = true





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
