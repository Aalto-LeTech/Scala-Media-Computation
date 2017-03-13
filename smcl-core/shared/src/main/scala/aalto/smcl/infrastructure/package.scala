package aalto.smcl


import java.awt.{Color => AwtColor}

import scala.collection.GenTraversable
import scala.language.{higherKinds, implicitConversions}

import aalto.smcl.colors.RGBAColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object infrastructure
  extends InfrastructureSettingKeys
  with Constants {

  /** Global settings storage. */
  val GS: Settings = new Settings()


  /**
   */
  private[infrastructure]
  implicit def AwtColorWrapper(self: AwtColor): RichAwtColor =
    new RichAwtColor(self)

  /** */
  //noinspection TypeParameterShadow
  private[smcl]
  implicit def GenTraversableWrapper[E, C[E] <: GenTraversable[E]](self: C[E]): RichGenTraversable[E, C] =
    new RichGenTraversable[E, C](self)

  /**
   */
  private[infrastructure]
  implicit def RGBAColorExtendedWrapper(self: RGBAColor): ExtendedRichRGBAColor =
    new ExtendedRichRGBAColor(self)

}
