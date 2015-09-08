package aalto.smcl


import java.awt.{Color => AwtColor}

import scala.language.implicitConversions

import aalto.smcl.colors.RGBAColor
import aalto.smcl.init.InitializableModule




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object platform extends PlatformSettingKeys with InitializableModule {

  /** */
  private[platform]
  implicit def AwtColorWrapper(self: AwtColor): RichAwtColor =
    new RichAwtColor(self)

  /** */
  lazy val DateTimeProvider: DateTimeProvider = new DateTimeProvider()

  /** */
  private[smcl]
  lazy val FontProvider: FontProvider = new FontProvider()

  /** */
  private[smcl]
  lazy val ImageProvider: ImageProvider = new ImageProvider()

  /** */
  private[platform]
  implicit def RGBAColorExtendedWrapper(self: RGBAColor): ExtendedRichRGBAColor =
    new ExtendedRichRGBAColor(self)

  /** */
  lazy val Screen: Screen = new Screen()

  /** */
  lazy val UIProvider: UIProvider = new UIProvider()

  /** */
  lazy val UniqueIdProvider: UniqueIdProvider = new UniqueIdProvider()

}
