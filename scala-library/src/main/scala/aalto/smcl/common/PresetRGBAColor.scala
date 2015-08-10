package aalto.smcl.common


import aalto.smcl.common.ColorOps._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[common] object PresetRGBAColor {

  /**
   *
   *
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, opacity: Int,
    nameOption: Option[String] = None): RGBAColor = {

    val args = RGBAColor.validateColorArguments(red, green, blue, opacity, nameOption)

    require(args._5.isDefined,
      "PresetColor instances must be given unique names (was None).")

    new PresetRGBAColor(args._1, args._2, args._3, args._4, args._5)
  }


  /**
   *
   *
   * @param pixelInt
   * @param nameOption
   * @return
   */
  def apply(pixelInt: Int, nameOption: Option[String]): RGBAColor =
    PresetRGBAColor(
      redComponentOf(pixelInt),
      greenComponentOf(pixelInt),
      blueComponentOf(pixelInt),
      opacityComponentOf(pixelInt),
      nameOption)
}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class PresetRGBAColor private[common](
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
