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


  /** */
  private[platform] implicit def RGBAColorExtendedWrapper(self: RGBAColor): ExtendedRichRGBAColor =
    new ExtendedRichRGBAColor(self)

  /** */
  private[platform] implicit def AwtColorWrapper(self: AwtColor): RichAwtColor =
    new RichAwtColor(self)

}
