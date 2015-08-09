package aalto.smcl.bitmaps


import aalto.smcl.SMCL
import aalto.smcl.common.settings.SettingValidatorFactory.ConditionFalseValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] object BitmapValidatorFunctionFactory {

  SMCL.performInitialization()


  /**
   *
   *
   * @return
   */
  // @formatter:off
  def BitmapWidthValidator(): Int => Option[Throwable] =
    ConditionFalseValidator[Int](
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
    ConditionFalseValidator[Int](
    { height => BitmapValidator.minimumHeightIsNotMet(height) || BitmapValidator.maximumHeightIsExceeded(height) },
    s"Bitmap height must be between ${BitmapValidator.MinimumBitmapHeightInPixels} " +
      s"and ${BitmapValidator.MaximumBitmapHeightInPixels} pixels")

  // @formatter:on

}
