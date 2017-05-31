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

package aalto.smcl.infrastructure.jvmawt


import java.awt.{Color => LowLevelColor}

import aalto.smcl.colors.rgb.Color
import aalto.smcl.infrastructure.ColorAdapter




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
   * @param applicationColor
   *
   * @return
   */
  def apply(applicationColor: Color): AWTColorAdapter =
    new AWTColorAdapter(
      applicationColor.red,
      applicationColor.green,
      applicationColor.blue,
      applicationColor.opacity)

  /**
   *
   *
   * @param awtColor
   *
   * @return
   */
  def apply(awtColor: LowLevelColor): AWTColorAdapter =
    new AWTColorAdapter(
      awtColor.getRed,
      awtColor.getGreen,
      awtColor.getBlue,
      awtColor.getAlpha)

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
case class AWTColorAdapter(
    private val redComponent: Int,
    private val greenComponent: Int,
    private val blueComponent: Int,
    private val opacityComponent: Int) extends ColorAdapter {

  /** */
  private[infrastructure]
  lazy val awtColor = new LowLevelColor(redComponent, greenComponent, blueComponent, opacityComponent)

  /**
   *
   *
   * @return
   */
  def applicationColor: Color =
    Color(redComponent, greenComponent, blueComponent, opacityComponent)

  /**
   *
   *
   * @return
   */
  override def red: Int = redComponent

  /**
   *
   *
   * @return
   */
  override def green: Int = greenComponent

  /**
   *
   *
   * @return
   */
  override def blue: Int = blueComponent

  /**
   *
   *
   * @return
   */
  override def opacity: Int = opacityComponent

}
