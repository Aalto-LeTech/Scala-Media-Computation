package aalto.smcl


import scala.language.implicitConversions

import aalto.smcl.infrastructure.LibraryInitializer




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object colors extends ColorOperationsAPI {

  LibraryInitializer.performInitialization()


  /** Color component value representing maximal opacity. */
  val FullyOpaque: Int = ColorValidator.MaximumRgbaOpacity

  /** Color component value representing minimal opacity. */
  val FullyTransparent: Int = ColorValidator.MinimumRgbaOpacity


  /** */
  lazy val ColorValidator: ColorValidator = new ColorValidator()

  /** */
  lazy val PresetColors: PresetColors = new PresetColors()

  /** */
  lazy val RGBATranslationTableValidator: RGBATranslationTableValidator =
    new RGBATranslationTableValidator()


  /** Application of the RichArgbInt class. */
  implicit def ARGBIntWrapper(self: Int): RichARGBInt = new RichARGBInt(self)

  /** Application of the RichRGBAColor class. */
  implicit def RGBAColorWrapper(self: RGBAColor): RichRGBAColor = new RichRGBAColor(self)

}
