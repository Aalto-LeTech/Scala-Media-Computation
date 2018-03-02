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

package smcl.infrastructure.jvmawt


import java.awt.{Color => LowLevelColor}

import smcl.colors.rgb.Color
import smcl.infrastructure.ColorAdapter




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object AWTColorAdapter {

  /**
   *
   *
   * @param smclColor
   *
   * @return
   */
  @inline
  def apply(smclColor: Color): AWTColorAdapter =
    new AWTColorAdapter(
      smclColor.red,
      smclColor.green,
      smclColor.blue,
      smclColor.opacity,
      knownSMCLColor = Some(smclColor),
      knownAWTColor = None)

  /**
   *
   *
   * @param awtColor
   *
   * @return
   */
  @inline
  def apply(awtColor: LowLevelColor): AWTColorAdapter =
    new AWTColorAdapter(
      awtColor.getRed,
      awtColor.getGreen,
      awtColor.getBlue,
      awtColor.getAlpha,
      knownSMCLColor = None,
      knownAWTColor = Some(awtColor))

  /**
   *
   *
   * @param smclColor
   *
   * @return
   */
  @inline
  def awtColorFrom(smclColor: Color): LowLevelColor =
    awtColorFrom(
      smclColor.red,
      smclColor.green,
      smclColor.blue,
      smclColor.opacity)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   *
   * @return
   */
  @inline
  def awtColorFrom(
      red: Int,
      green: Int,
      blue: Int,
      opacity: Int): LowLevelColor = {

    new LowLevelColor(red, green, blue, opacity)
  }

  /**
   *
   *
   * @param awtColor
   *
   * @return
   */
  @inline
  def smclColorFrom(awtColor: LowLevelColor): Color =
    Color(
      awtColor.getRed,
      awtColor.getGreen,
      awtColor.getBlue,
      awtColor.getAlpha)

}




/**
 *
 *
 * @param red
 * @param green
 * @param blue
 * @param opacity
 * @param knownSMCLColor
 * @param knownAWTColor
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class AWTColorAdapter private(
    override val red: Int,
    override val green: Int,
    override val blue: Int,
    override val opacity: Int,
    knownSMCLColor: Option[Color],
    knownAWTColor: Option[LowLevelColor]
) extends ColorAdapter {

  /** */
  private
  var memoizedSMCLColor: Option[Color] = knownSMCLColor

  /** */
  private
  var memoizedAWTColor: Option[LowLevelColor] = knownAWTColor

  /**
   *
   *
   * @return
   */
  override
  def smclColor: Color = {
    if (memoizedSMCLColor.isEmpty) {
      memoizedSMCLColor = Some(Color(red, green, blue, opacity))
    }

    memoizedSMCLColor.get
  }

  /**
   *
   *
   * @return
   */
  def awtColor: LowLevelColor = {
    if (memoizedAWTColor.isEmpty) {
      memoizedAWTColor = Some(AWTColorAdapter.awtColorFrom(red, green, blue, opacity))
    }

    memoizedAWTColor.get
  }

}
