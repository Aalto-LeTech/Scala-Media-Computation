package aalto.smcl.infrastructure.jvmawt


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
class RichAwtColor(val self: LowLevelColor) {

  /** This `java.awt.Color` as a [[RGBAColor]]. */
  final def toApplicationColor: RGBAColor = AwtColorAdapter(self).applicationColor

  /** This `java.awt.Color` as a [[RGBAColor]] with full opacity. */
  final def withFullOpacity: RGBAColor = AwtColorAdapter(self).applicationColor.withFullOpacity

}
