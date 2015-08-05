package aalto.smcl


import java.awt.{Color => AwtColor}

import aalto.smcl.common._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object platform {

  SMCL.performInitialization()


  /**
   *
   *
   * @param self
   */
  private[platform] implicit class ExtendedRichColor(val self: RGBAColor) {

    /** This [[RGBAColor]] as a `java.awt.Color`. */
    def toAwtColor: AwtColor = PlatformColor(self).awtColor

    /** This [[RGBAColor]] as a `java.awt.Color` with full opacity. */
    def toOpaqueAwtColor: AwtColor =
      PlatformColor(self.withFullOpacity).awtColor

  }


  /**
   *
   *
   * @param self
   */
  private[platform] implicit class RichAwtColor(val self: AwtColor) {

    /** This `java.awt.Color` as a [[RGBAColor]]. */
    def toAapplicationColor: RGBAColor = PlatformColor(self).applicationColor

    /** This `java.awt.Color` as a [[RGBAColor]] with full opacity. */
    def withFullOpacity: RGBAColor = PlatformColor(self).applicationColor.withFullOpacity

  }

}
