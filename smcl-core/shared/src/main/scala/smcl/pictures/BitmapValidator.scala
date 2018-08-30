/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.pictures


import smcl.modeling.Len
import smcl.pictures.exceptions.{MaximumBitmapSizeExceededError, MinimumBitmapSizeNotMetError, PixelSnapshotInvalidatedError}
import smcl.settings.{BitmapHeightWarningLimitInPixels, BitmapWidthWarningLimitInPixels}




/**
  *
  *
  * @author Aleksi Lukkarinen
  */
object BitmapValidator {

  /** */
  val MinimumBitmapWidth: Len = Len.Zero

  /** */
  val MaximumBitmapWidth: Len = Len(65000)

  /** */
  val MinimumBitmapHeight: Len = Len.Zero

  /** */
  val MaximumBitmapHeight: Len = MaximumBitmapWidth

}




/**
  *
  * @author Aleksi Lukkarinen
  */
class BitmapValidator private[smcl]() {


  /**
    *
    * @param actualWidth
    *
    * @return
    */
  @inline
  final
  def minimumWidthIsNotMet(actualWidth: Len): Boolean = {
    actualWidth < BitmapValidator.MinimumBitmapHeight
  }

  /**
    *
    * @param actualHeight
    *
    * @return
    */
  @inline
  final
  def minimumHeightIsNotMet(actualHeight: Len): Boolean = {
    actualHeight < BitmapValidator.MinimumBitmapHeight
  }

  /**
    *
    * @param actualWidth
    *
    * @return
    */
  @inline
  final
  def maximumWidthIsExceeded(actualWidth: Len): Boolean = {
    actualWidth > BitmapValidator.MaximumBitmapHeight
  }

  /**
    *
    * @param actualHeight
    *
    * @return
    */
  @inline
  final
  def maximumHeightIsExceeded(actualHeight: Len): Boolean = {
    actualHeight > BitmapValidator.MaximumBitmapHeight
  }

  /**
    *
    * @param actualWidth
    *
    * @return
    */
  @inline
  final
  def warningWidthLimitIsExceeded(actualWidth: Len): Boolean = {
    actualWidth > BitmapWidthWarningLimitInPixels
  }

  /**
    *
    * @param actualHeight
    *
    */
  @inline
  final
  def warningHeightLimitIsExceeded(actualHeight: Len): Boolean = {
    actualHeight > BitmapHeightWarningLimitInPixels
  }

  /**
    *
    * @param actualWidth
    * @param actualHeight
    *
    * @return
    */
  @inline
  final
  def minimumSizeLimitsAreNotMet(
      actualWidth: Len,
      actualHeight: Len): Boolean = {

    (minimumWidthIsNotMet(actualWidth)
        || minimumHeightIsNotMet(actualHeight))
  }

  /**
    *
    * @param actualWidth
    * @param actualHeight
    *
    * @return
    */
  @inline
  final
  def maximumSizeLimitsAreExceeded(
      actualWidth: Len,
      actualHeight: Len): Boolean = {

    (maximumWidthIsExceeded(actualWidth)
        || maximumHeightIsExceeded(actualHeight))
  }

  /**
    *
    * @param actualWidth
    * @param actualHeight
    *
    * @return
    */
  @inline
  final
  def warningSizeLimitsAreExceeded(
      actualWidth: Len,
      actualHeight: Len): Boolean = {

    (warningWidthLimitIsExceeded(actualWidth)
        && warningHeightLimitIsExceeded(actualHeight))
  }

  /**
    *
    * @param actualWidth
    * @param actualHeight
    *
    * @throws PixelSnapshotInvalidatedError
    * @throws MaximumBitmapSizeExceededError
    */
  @inline
  final
  def validateBitmapSize(
      actualWidth: Len,
      actualHeight: Len): Unit = {

    if (minimumSizeLimitsAreNotMet(actualWidth, actualHeight)) {
      throw MinimumBitmapSizeNotMetError(
        actualWidth, actualHeight,
        BitmapValidator.MinimumBitmapWidth,
        BitmapValidator.MinimumBitmapHeight,
        None, None)
    }

    if (maximumSizeLimitsAreExceeded(actualWidth, actualHeight)) {
      throw MaximumBitmapSizeExceededError(
        actualWidth, actualHeight,
        BitmapValidator.MaximumBitmapWidth,
        BitmapValidator.MaximumBitmapHeight,
        None, None)
    }
  }

}
