package aalto.smcl.colors


import aalto.smcl.SMCL
import aalto.smcl.infrastructure.ModuleInitializationPhase




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PresetRGBAColor {

  SMCL.performInitialization(ModuleInitializationPhase.Early)


  /**
   *
   *
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, opacity: Int,
      nameOption: Option[String] = None): RGBAColor = {

    ColorValidator.validateRgbaColor(red, green, blue, opacity)

    val resultNameOption = ColorValidator.validateColorNameOption(nameOption)

    require(resultNameOption.isDefined,
      "PresetColor instances must be given unique names (was None).")

    new PresetRGBAColor(red, green, blue, opacity, resultNameOption)
  }


  /**
   *
   *
   * @param argbInt
   * @param nameOption
   * @return
   */
  def apply(argbInt: Int, nameOption: Option[String]): RGBAColor =
    PresetRGBAColor(
      redComponentOf(argbInt),
      greenComponentOf(argbInt),
      blueComponentOf(argbInt),
      opacityComponentOf(argbInt),
      nameOption)
}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class PresetRGBAColor private[colors](
    override val red: Int,
    override val green: Int,
    override val blue: Int,
    override val opacity: Int,
    override val nameOption: Option[String] = None) extends {

  /** Returns `true` if this [[RGBAColor]] is provided by SMCL, otherwise `false`. */
  override val isPreset: Boolean = true

} with RGBAColor(red, green, blue, opacity, nameOption) with Immutable {

  /** Default name for an unnamed preset color. */
  private val StrNoName = "<unnamed preset>"

  /**
   * Returns a string representation of this [[RGBAColor]].
   */
  override def toString: String = s"${nameOption.getOrElse(StrNoName)} (${super.toString})"

}
