package aalto.smcl.infrastructure


import java.awt.{Color => AwtColor}

import aalto.smcl.colors.RGBAColor




/**
 *
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure]
class ExtendedRichRGBAColor(val self: RGBAColor) {

  /** This [[RGBAColor]] as a `java.awt.Color`. */
  def toAwtColor: AwtColor = PlatformColor(self).awtColor

  /** This [[RGBAColor]] as a `java.awt.Color` with full opacity. */
  def toOpaqueAwtColor: AwtColor =
    PlatformColor(self.withFullOpacity).awtColor

}

