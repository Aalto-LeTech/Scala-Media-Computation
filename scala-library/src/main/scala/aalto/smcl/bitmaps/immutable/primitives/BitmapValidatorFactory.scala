package aalto.smcl.bitmaps.immutable.primitives


import aalto.smcl.common.settings.SettingValidatorFactory.ConditionFalseValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapValidatorFactory {

  /** */
  val MinimumBitmapWidthInPixels: Int = 1

  /** */
  val MaximumBitmapWidthInPixels: Int = 65000

  /** */
  val MinimumBitmapHeightInPixels: Int = 1

  /** */
  val MaximumBitmapHeightInPixels: Int = 65000

  /**
   *
   *
   * @return
   */
  // @formatter:off
  def BitmapWidthValidator(): Int => Option[Throwable] =
    ConditionFalseValidator[Int](
      {width =>
        width < MinimumBitmapWidthInPixels || width > MaximumBitmapWidthInPixels
      },
      s"Bitmap width must be between $MinimumBitmapWidthInPixels and $MaximumBitmapWidthInPixels pixels")
  // @formatter:on

  /**
   *
   *
   * @return
   */
  // @formatter:off
  def BitmapHeightValidator(): Int => Option[Throwable] =
    ConditionFalseValidator[Int](
      {width =>
        width < MinimumBitmapHeightInPixels || width > MaximumBitmapHeightInPixels
      },
      s"Bitmap height must be between $MinimumBitmapHeightInPixels and $MaximumBitmapHeightInPixels pixels")
  // @formatter:on

}
