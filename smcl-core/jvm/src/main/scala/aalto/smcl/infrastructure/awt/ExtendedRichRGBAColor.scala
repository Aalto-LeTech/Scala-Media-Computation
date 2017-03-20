package aalto.smcl.infrastructure.awt


import java.awt.{Color => LowLevelColor}

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
  def toAwtColor: LowLevelColor = AwtColorAdapter(self).awtColor

  /** This [[RGBAColor]] as a `java.awt.Color` with full opacity. */
  def toOpaqueAwtColor: LowLevelColor =
    AwtColorAdapter(self.withFullOpacity).awtColor

}
