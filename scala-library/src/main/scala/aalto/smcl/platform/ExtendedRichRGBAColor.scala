package aalto.smcl.platform


import java.awt.{Color => AwtColor}

import aalto.smcl.common.RGBAColor




/**
 *
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 */
private[platform] class ExtendedRichRGBAColor(val self: RGBAColor) {

  /** This [[RGBAColor]] as a `java.awt.Color`. */
  def toAwtColor: AwtColor = PlatformColor(self).awtColor

  /** This [[RGBAColor]] as a `java.awt.Color` with full opacity. */
  def toOpaqueAwtColor: AwtColor =
    PlatformColor(self.withFullOpacity).awtColor

}

