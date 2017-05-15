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

package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.exceptions.{MaximumBitmapSizeExceededError, MinimumBitmapSizeNotMetError}
import aalto.smcl.infrastructure.{BitmapHeightWarningLimitInPixels, BitmapWidthWarningLimitInPixels, GS}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class BitmapValidator private[smcl]() {

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
   *
   * @return
   */
  @inline
  def minimumWidthIsNotMet(actualWidthInPixels: Int): Boolean =
  actualWidthInPixels < MinimumBitmapHeightInPixels

  /**
   *
   *
   * @param actualHeightInPixels
   *
   * @return
   */
  @inline
  def minimumHeightIsNotMet(actualHeightInPixels: Int): Boolean =
  actualHeightInPixels < MinimumBitmapHeightInPixels

  /**
   *
   *
   * @param actualWidthInPixels
   *
   * @return
   */
  @inline
  def maximumWidthIsExceeded(actualWidthInPixels: Int): Boolean =
  actualWidthInPixels > MaximumBitmapHeightInPixels

  /**
   *
   *
   * @param actualHeightInPixels
   *
   * @return
   */
  @inline
  def maximumHeightIsExceeded(actualHeightInPixels: Int): Boolean =
  actualHeightInPixels > MaximumBitmapHeightInPixels

  /**
   *
   *
   * @param actualWidthInPixels
   *
   * @return
   */
  @inline
  def warningWidthLimitIsExceeded(actualWidthInPixels: Int): Boolean =
  actualWidthInPixels > GS.intFor(BitmapWidthWarningLimitInPixels)

  /**
   *
   *
   * @param actualHeightInPixels
   *
   * @return
   */
  @inline
  def warningHeightLimitIsExceeded(actualHeightInPixels: Int): Boolean =
  actualHeightInPixels > GS.intFor(BitmapHeightWarningLimitInPixels)

  /**
   *
   *
   * @param actualWidthInPixels
   * @param actualHeightInPixels
   *
   * @return
   */
  @inline
  def minimumSizeLimitsAreNotMet(actualWidthInPixels: Int, actualHeightInPixels: Int): Boolean =
  minimumWidthIsNotMet(actualWidthInPixels) || minimumHeightIsNotMet(actualHeightInPixels)

  /**
   *
   *
   * @param actualWidthInPixels
   * @param actualHeightInPixels
   *
   * @return
   */
  @inline
  def maximumSizeLimitsAreExceeded(actualWidthInPixels: Int, actualHeightInPixels: Int): Boolean =
  maximumWidthIsExceeded(actualWidthInPixels) || maximumHeightIsExceeded(actualHeightInPixels)

  /**
   *
   *
   * @param actualWidthInPixels
   * @param actualHeightInPixels
   *
   * @return
   */
  @inline
  def warningSizeLimitsAreExceeded(actualWidthInPixels: Int, actualHeightInPixels: Int): Boolean =
  warningWidthLimitIsExceeded(actualWidthInPixels) && warningHeightLimitIsExceeded(actualHeightInPixels)

  /**
   *
   *
   * @param actualWidthInPixels
   * @param actualHeightInPixels
   *
   * @throws MinimumBitmapSizeNotMetError
   * @throws MaximumBitmapSizeExceededError
   */
  @inline
  def validateBitmapSize(actualWidthInPixels: Int, actualHeightInPixels: Int): Unit = {
    if (minimumSizeLimitsAreNotMet(actualWidthInPixels, actualHeightInPixels)) {
      throw new MinimumBitmapSizeNotMetError(
        Option(actualWidthInPixels), Option(actualHeightInPixels), None, None, new BitmapValidator())
    }

    if (maximumSizeLimitsAreExceeded(actualWidthInPixels, actualHeightInPixels)) {
      throw new MaximumBitmapSizeExceededError(
        Option(actualWidthInPixels), Option(actualHeightInPixels), None, None, new BitmapValidator())
    }
  }

}
