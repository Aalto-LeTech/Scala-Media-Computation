package aalto.smcl.common


import aalto.smcl.common.ColorOps._
import aalto.smcl.images.operations.AbstractSingleSourceOperation
import aalto.smcl.platform.PlatformColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Color {

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param transparency
   * @param nameOption
   * @return
   */
  private[smcl] def validateColorArguments(red: Int, green: Int, blue: Int,
      transparency: Int, nameOption: Option[String] = None): Tuple5[Int, Int, Int, Int, Option[String]] = {

    require(ByteRange.contains(red),
      s"The 'red' value must be between ${ByteRange.start} and ${ByteRange.end} (was $red)")

    require(ByteRange.contains(green),
      s"The 'green' value must be between ${ByteRange.start} and ${ByteRange.end} (was $green)")

    require(ByteRange.contains(blue),
      s"The 'blue' value must be between ${ByteRange.start} and ${ByteRange.end} (was $blue)")

    require(ByteRange.contains(transparency),
      s"The transparency value must be between ${ByteRange.start} and ${ByteRange.end} (was $transparency)")

    require(nameOption != null, "The nameOption argument must be Option(<name>) or None (was null).")

    var resultNameOption = nameOption
    if (nameOption.nonEmpty) {
      val name = nameOption.get.trim

      require(name != StrEmpty, "The name cannot be empty or contain only whitespace.")

      if (name != nameOption.get)
        resultNameOption = Option(name)
    }

    (red, green, blue, transparency, resultNameOption)
  }


  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param transparency
   * @param nameOption
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, transparency: Int,
      nameOption: Option[String] = None): Color = {

    val (validRed, validGreen, validBlue, validTransparency, validNameOption) =
      validateColorArguments(red, green, blue, transparency, nameOption)

    new Color(validRed, validGreen, validBlue, validTransparency, validNameOption)
  }


  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param nameOption
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, nameOption: Option[String]): Color =
    Color(red, green, blue, MaximumOpaqueness, nameOption)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  def apply(red: Int, green: Int, blue: Int): Color = Color(red, green, blue, MaximumOpaqueness)

  /**
   *
   *
   * @param pixelInt
   * @param nameOption
   * @return
   */
  def apply(pixelInt: Int, nameOption: Option[String]): Color =
    Color(redComponentFrom(pixelInt),
      greenComponentFrom(pixelInt),
      blueComponentFrom(pixelInt),
      transparencyComponentFrom(pixelInt),
      nameOption)

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  def apply(pixelInt: Int): Color =
    Color(redComponentFrom(pixelInt),
      greenComponentFrom(pixelInt),
      blueComponentFrom(pixelInt),
      transparencyComponentFrom(pixelInt))

  /**
   *
   *
   * @param platformColor
   * @return
   */
  private[smcl] def apply(
      platformColor: PlatformColor): Color =
    Color(
      platformColor.red,
      platformColor.green,
      platformColor.blue,
      platformColor.transparency)

  /**
   *
   *
   * @param platformColor
   * @param nameOption
   * @return
   */
  private[smcl] def apply(
      platformColor: PlatformColor,
      nameOption: Option[String]): Color =
    Color(
      platformColor.red,
      platformColor.green,
      platformColor.blue,
      platformColor.transparency,
      nameOption)

}


/**
 *
 *
 * @param red
 * @param green
 * @param blue
 * @param transparency
 * @param nameOption
 *
 * @author Aleksi Lukkarinen
 */
class Color protected(
    val red: Int,
    val green: Int,
    val blue: Int,
    val transparency: Int,
    val nameOption: Option[String] = None) extends {

  /** Returns `true` if this [[Color]] is provided by SMCL, otherwise `false`. */
  val isPreset: Boolean = false

} with Immutable with Tokenizable {

  /** This [[Color]] coded into an `Int`. */
  val asPixelInt: Int = pixelIntFrom(red, green, blue, transparency)

  /** Returns `true` if this [[Color]] is fully opaque, otherwise `false`. */
  val isOpaque: Boolean = transparency == MaximumOpaqueness

  /** Returns `false` if this [[Color]] is fully opaque, otherwise `true`. */
  val isTransparent: Boolean = !isOpaque

  /** Returns `true` if this [[Color]] is created by the user, otherwise `false`. */
  val isUserCreated: Boolean = !isPreset

  /** This [[Color]] represented as a 32-digit binary string of four 8-digit groups. */
  lazy val asBinaryString: String = asPixelInt.toArgbBinaryColorString

  /** This [[Color]] represented as a hexadecimal string. */
  lazy val asHexString: String = asPixelInt.toArgbHexColorString

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "red" -> Option(red.toString),
    "green" -> Option(green.toString),
    "blue" -> Option(blue.toString),
    "transparency" -> Option(transparency.toString)))

  /**
   * Returns a string representation of this [[Color]].
   */
  override def toString: String =
    s"ARGB: 0x$asHexString -- $transparency - $red - $green - $blue"

}
