package aalto.smcl.platform


import java.awt.{Color => AwtColor}

import aalto.smcl.common.Color




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformColor {

  /**
   *
   *
   * @param applicationColor
   * @return
   */
  def apply(applicationColor: Color): PlatformColor =
    new PlatformColor(
      applicationColor.red,
      applicationColor.green,
      applicationColor.blue,
      applicationColor.transparency)

  /**
   *
   *
   * @param awtColor
   * @return
   */
  def apply(awtColor: AwtColor): PlatformColor =
    new PlatformColor(
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
private[smcl] case class PlatformColor(red: Int, green: Int, blue: Int, transparency: Int) {

  /** */
  private[platform] lazy val awtColor = new AwtColor(red, green, blue, transparency)

  /** */
  lazy val applicationColor = Color(red, green, blue, transparency)

}
