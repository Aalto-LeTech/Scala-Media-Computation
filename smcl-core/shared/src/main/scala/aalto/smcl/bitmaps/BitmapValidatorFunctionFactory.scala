package aalto.smcl.bitmaps


import aalto.smcl.infrastructure.SettingValidatorFactory





/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class BitmapValidatorFunctionFactory {

  /** */
  private val _settingValidatorFactory = new SettingValidatorFactory()


  /**
   *
   *
   * @return
   */
  // @formatter:off
  def BitmapWidthValidator(): Int => Option[Throwable] =
    _settingValidatorFactory.conditionFalseValidator[Int](
    { width => BitmapValidator.minimumWidthIsNotMet(width) || BitmapValidator.maximumWidthIsExceeded(width) },
    s"Bitmap width must be between ${BitmapValidator.MinimumBitmapHeightInPixels} " +
      s"and ${BitmapValidator.MaximumBitmapWidthInPixels} pixels")

  // @formatter:on

  /**
   *
   *
   * @return
   */
  // @formatter:off
  def BitmapHeightValidator(): Int => Option[Throwable] =
    _settingValidatorFactory.conditionFalseValidator[Int](
    { height => BitmapValidator.minimumHeightIsNotMet(height) || BitmapValidator.maximumHeightIsExceeded(height) },
    s"Bitmap height must be between ${BitmapValidator.MinimumBitmapHeightInPixels} " +
      s"and ${BitmapValidator.MaximumBitmapHeightInPixels} pixels")

  // @formatter:on

}
