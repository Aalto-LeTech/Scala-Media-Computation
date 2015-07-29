package aalto.smcl.common


import aalto.smcl.common.ColorOps._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[common] object PresetColor {

  /**
   *
   *
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, transparency: Int,
      nameOption: Option[String] = None): Color = {

    val args = Color.validateColorArguments(red, green, blue, transparency, nameOption)

    require(args._5.isDefined,
      "PresetColor instances must be given unique names (was None).")

    new PresetColor(args._1, args._2, args._3, args._4, args._5)
  }


  /**
   *
   *
   * @param pixelInt
   * @param nameOption
   * @return
   */
  def apply(pixelInt: Int, nameOption: Option[String]): Color =
    PresetColor(redComponentFrom(pixelInt),
      greenComponentFrom(pixelInt),
      blueComponentFrom(pixelInt),
      transparencyComponentFrom(pixelInt),
      nameOption)
}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class PresetColor private[common](
    override val red: Int,
    override val green: Int,
    override val blue: Int,
    override val transparency: Int,
    override val nameOption: Option[String] = None) extends {

  /** Returns `true` if this [[Color]] is provided by SMCL, otherwise `false`. */
  override val isPreset: Boolean = true

} with Color (red, green, blue, transparency, nameOption) with Immutable {

  /** Default name for an unnamed preset color. */
  private val StrNoName = "<unnamed preset>"

  /**
   * Returns a string representation of this [[Color]].
   */
  override def toString: String = s"${nameOption.getOrElse(StrNoName)} (${super.toString}})"

}
