package aalto.smcl.bitmaps


import aalto.smcl.SMCL
import aalto.smcl.bitmaps.BitmapSettingKeys.{BitmapHeightWarningLimitInPixels, BitmapWidthWarningLimitInPixels}
import aalto.smcl.common.GS




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapValidator {

  SMCL.performInitialization()


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
   * @param actualWidthInPixels
   * @return
   */
  def minimumWidthIsNotMet(actualWidthInPixels: Int): Boolean =
    actualWidthInPixels < MinimumBitmapHeightInPixels

  /**
   *
   *
   * @param actualHeightInPixels
   * @return
   */
  def minimumHeightIsNotMet(actualHeightInPixels: Int): Boolean =
    actualHeightInPixels < MinimumBitmapHeightInPixels

  /**
   *
   *
   * @param actualWidthInPixels
   * @return
   */
  def maximumWidthIsExceeded(actualWidthInPixels: Int): Boolean =
    actualWidthInPixels > MaximumBitmapHeightInPixels

  /**
   *
   *
   * @param actualHeightInPixels
   * @return
   */
  def maximumHeightIsExceeded(actualHeightInPixels: Int): Boolean =
    actualHeightInPixels > MaximumBitmapHeightInPixels

  /**
   *
   *
   * @param actualWidthInPixels
   * @return
   */
  def warningWidthLimitIsExceeded(actualWidthInPixels: Int): Boolean =
    actualWidthInPixels > GS.intFor(BitmapWidthWarningLimitInPixels)

  /**
   *
   *
   * @param actualHeightInPixels
   * @return
   */
  def warningHeightLimitIsExceeded(actualHeightInPixels: Int): Boolean =
    actualHeightInPixels > GS.intFor(BitmapHeightWarningLimitInPixels)

  /**
   *
   *
   * @param actualWidthInPixels
   * @param actualHeightInPixels
   * @return
   */
  def minimumSizeLimitsAreNotMet(actualWidthInPixels: Int, actualHeightInPixels: Int): Boolean =
    minimumWidthIsNotMet(actualWidthInPixels) || minimumHeightIsNotMet(actualHeightInPixels)

  /**
   *
   *
   * @param actualWidthInPixels
   * @param actualHeightInPixels
   * @return
   */
  def maximumSizeLimitsAreExceeded(actualWidthInPixels: Int, actualHeightInPixels: Int): Boolean =
    maximumWidthIsExceeded(actualWidthInPixels) || maximumHeightIsExceeded(actualHeightInPixels)

  /**
   *
   *
   * @param actualWidthInPixels
   * @param actualHeightInPixels
   * @return
   */
  def warningSizeLimitsAreExceeded(actualWidthInPixels: Int, actualHeightInPixels: Int): Boolean =
    warningWidthLimitIsExceeded(actualWidthInPixels) && warningHeightLimitIsExceeded(actualHeightInPixels)


  /**
   *
   *
   * @param actualWidthInPixels
   * @param actualHeightInPixels
   *
   * @throws SMCLMinimumBitmapSizeNotMetError
   * @throws SMCLMaximumBitmapSizeExceededError
   */
  def validateBitmapSize(actualWidthInPixels: Int, actualHeightInPixels: Int): Unit = {
    if (minimumSizeLimitsAreNotMet(actualWidthInPixels, actualHeightInPixels)) {
      throw new SMCLMinimumBitmapSizeNotMetError(Option(actualWidthInPixels), Option(actualHeightInPixels))
    }

    if (maximumSizeLimitsAreExceeded(actualWidthInPixels, actualHeightInPixels)) {
      throw new SMCLMaximumBitmapSizeExceededError(Option(actualWidthInPixels), Option(actualHeightInPixels))
    }
  }

}
