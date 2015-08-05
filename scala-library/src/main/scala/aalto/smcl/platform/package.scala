package aalto.smcl


import java.awt.{Color => AwtColor}

import aalto.smcl.common._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object platform {

  initialize()


  /**
   *
   */
  def initialize(): Unit = {
    initializeLookAndFeel()
  }

  /**
   *
   */
  private def initializeLookAndFeel(): Unit =
    UIProvider.tryToInitializeSpecificLookAndFeel(UIProvider.NimbusLookAndFeelName)

  /**
   *
   *
   * @param self
   */
  private[platform] implicit class ExtendedRichColor(val self: RGBAColor) {

    /** This [[RGBAColor]] as a `java.awt.Color`. */
    def toAwtColor: AwtColor = PlatformColor(self).awtColor

    /** This [[RGBAColor]] as a `java.awt.Color` with full opaqueness. */
    def toOpaqueAwtColor: AwtColor =
      PlatformColor(self.toOpaqueColor).awtColor

  }

  /**
   *
   *
   * @param self
   */
  private[platform] implicit class RichAwtColor(val self: AwtColor) {

    /** This `java.awt.Color` as a [[RGBAColor]]. */
    def toAapplicationColor: RGBAColor = PlatformColor(self).applicationColor

    /** This `java.awt.Color` as a [[RGBAColor]] with full opaqueness. */
    def toOpaqueColor: RGBAColor = PlatformColor(self).applicationColor.toOpaqueColor

  }

}
