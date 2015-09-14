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
class RichAwtColor(val self: AwtColor) {

  /** This `java.awt.Color` as a [[RGBAColor]]. */
  final def toApplicationColor: RGBAColor = PlatformColor(self).applicationColor

  /** This `java.awt.Color` as a [[RGBAColor]] with full opacity. */
  final def withFullOpacity: RGBAColor = PlatformColor(self).applicationColor.withFullOpacity

}
