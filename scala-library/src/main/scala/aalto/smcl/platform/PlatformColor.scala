package aalto.smcl.platform


import java.awt.{Color => AwtColor}

import aalto.smcl.SMCL
import aalto.smcl.common.RGBAColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformColor {

  SMCL.performInitialization()


  /**
   *
   *
   * @param applicationColor
   * @return
   */
  def apply(applicationColor: RGBAColor): PlatformColor =
    new PlatformColor(
      applicationColor.red,
      applicationColor.green,
      applicationColor.blue,
      applicationColor.opacity)

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
private[smcl] case class PlatformColor(red: Int, green: Int, blue: Int, opacity: Int) {

  /** */
  private[platform] lazy val awtColor = new AwtColor(red, green, blue, opacity)

  /** */
  lazy val applicationColor = RGBAColor(red, green, blue, opacity)

}
