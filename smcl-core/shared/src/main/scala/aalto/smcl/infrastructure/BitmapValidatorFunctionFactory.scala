package aalto.smcl.infrastructure


import aalto.smcl.bitmaps.BitmapValidator





/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure]
class BitmapValidatorFunctionFactory(bitmapValidator: BitmapValidator) {

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
    { width => bitmapValidator.minimumWidthIsNotMet(width) || bitmapValidator.maximumWidthIsExceeded(width) },
    s"Bitmap width must be between ${bitmapValidator.MinimumBitmapHeightInPixels} " +
      s"and ${bitmapValidator.MaximumBitmapWidthInPixels} pixels")

  // @formatter:on

  /**
   *
   *
   * @return
   */
  // @formatter:off
  def BitmapHeightValidator(): Int => Option[Throwable] =
    _settingValidatorFactory.conditionFalseValidator[Int](
    { height => bitmapValidator.minimumHeightIsNotMet(height) || bitmapValidator.maximumHeightIsExceeded(height) },
    s"Bitmap height must be between ${bitmapValidator.MinimumBitmapHeightInPixels} " +
      s"and ${bitmapValidator.MaximumBitmapHeightInPixels} pixels")

  // @formatter:on

}
