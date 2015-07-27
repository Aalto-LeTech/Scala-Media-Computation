package aalto.smcl.images.immutable


import scala.collection.immutable.HashMap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object NamedColors extends {

  /** */
  val aliceBlue = PresetColor(0xfff0f8ff, Option("alice blue"))

  /** */
  val amethyst = PresetColor(0xff9966cc, Option("amethyst"))

  /** */
  val antiqueWhite = PresetColor(0xfffaebd7, Option("antique white"))

  /** */
  val aqua = PresetColor(0xff00ffff, Option("aqua"))

  /** */
  val aquamarine = PresetColor(0xff7fffd4, Option("aquamarine"))

  /** */
  val azure = PresetColor(0xfff0ffff, Option("azure"))

  /** */
  val beige = PresetColor(0xfff5f5dc, Option("beige"))

  /** */
  val bisque = PresetColor(0xffffe4c4, Option("bisque"))

  /** */
  val black = PresetColor(0xff000000, Option("black"))

  /** */
  val blanchedAlmond = PresetColor(0xffffebcd, Option("blanched almond"))

  /** */
  val blue = PresetColor(0xff0000ff, Option("blue"))

  /** */
  val blueViolet = PresetColor(0xff8a2be2, Option("blue violet"))

  /** */
  val brown = PresetColor(0xffa52a2a, Option("brown"))

  /** */
  val burlyWood = PresetColor(0xffdeb887, Option("burly wood"))

  /** */
  val cadetBlue = PresetColor(0xff5f9ea0, Option("cadet blue"))

  /** */
  val chartreuse = PresetColor(0xff7fff00, Option("chartreuse"))

  /** */
  val chocolate = PresetColor(0xffd2691e, Option("chocolate"))

  /** */
  val coral = PresetColor(0xffff7f50, Option("coral"))

  /** */
  val cornflowerBlue = PresetColor(0xff6495ed, Option("cornflower blue"))

  /** */
  val cornsilk = PresetColor(0xfffff8dc, Option("cornsilk"))

  /** */
  val crimson = PresetColor(0xffdc143c, Option("crimson"))

  /** */
  val cyan = PresetColor(0xff00ffff, Option("cyan"))

  /** */
  val darkBlue = PresetColor(0xff00008b, Option("dark blue"))

  /** */
  val darkCyan = PresetColor(0xff008b8b, Option("dark cyan"))

  /** */
  val darkGoldenrod = PresetColor(0xffb8860b, Option("dark goldenrod"))

  /** */
  val darkGray = PresetColor(0xffa9a9a9, Option("dark gray"))

  /** */
  val darkGreen = PresetColor(0xff006400, Option("dark green"))

  /** */
  val darkKhaki = PresetColor(0xffbdb76b, Option("dark khaki"))

  /** */
  val darkMagenta = PresetColor(0xff8b008b, Option("dark magenta"))

  /** */
  val darkOliveGreen = PresetColor(0xff556b2f, Option("dark olive green"))

  /** */
  val darkOrange = PresetColor(0xffff8c00, Option("dark orange"))

  /** */
  val darkOrchid = PresetColor(0xff9932cc, Option("dark orchid"))

  /** */
  val darkRed = PresetColor(0xff8b0000, Option("dark red"))

  /** */
  val darkSalmon = PresetColor(0xffe9967a, Option("dark salmon"))

  /** */
  val darkSeaGreen = PresetColor(0xff8fbc8f, Option("dark sea green"))

  /** */
  val darkSlateBlue = PresetColor(0xff483d8b, Option("dark slate blue"))

  /** */
  val darkSlateGray = PresetColor(0xff2f4f4f, Option("dark slate gray"))

  /** */
  val darkTurquoise = PresetColor(0xff00ced1, Option("dark turquoise"))

  /** */
  val darkViolet = PresetColor(0xff9400d3, Option("dark violet"))

  /** */
  val deepPink = PresetColor(0xffff1493, Option("deep pink"))

  /** */
  val deepSkyBlue = PresetColor(0xff00bfff, Option("deep sky blue"))

  /** */
  val dimGray = PresetColor(0xff696969, Option("dim gray"))

  /** */
  val dodgerBlue = PresetColor(0xff1e90ff, Option("dodger blue"))

  /** */
  val fireBrick = PresetColor(0xffb22222, Option("fire brick"))

  /** */
  val floralWhite = PresetColor(0xfffffaf0, Option("floral white"))

  /** */
  val forestGreen = PresetColor(0xff228b22, Option("forest green"))

  /** */
  val fuchsia = PresetColor(0xffff00ff, Option("fuchsia"))

  /** */
  val gainsboro = PresetColor(0xffdcdcdc, Option("gainsboro"))

  /** */
  val ghostWhite = PresetColor(0xfff8f8ff, Option("ghost white"))

  /** */
  val gold = PresetColor(0xffffd700, Option("gold"))

  /** */
  val goldenrod = PresetColor(0xffdaa520, Option("goldenrod"))

  /** */
  val gray = PresetColor(0xff808080, Option("gray"))

  /** */
  val green = PresetColor(0xff008000, Option("green"))

  /** */
  val greenYellow = PresetColor(0xffadff2f, Option("green yellow"))

  /** */
  val honeydew = PresetColor(0xfff0fff0, Option("honeydew"))

  /** */
  val hotPink = PresetColor(0xffff69b4, Option("hot pink"))

  /** */
  val indianRed = PresetColor(0xffcd5c5c, Option("indian red"))

  /** */
  val indigo = PresetColor(0xff4b0082, Option("indigo"))

  /** */
  val ivory = PresetColor(0xfffffff0, Option("ivory"))

  /** */
  val khaki = PresetColor(0xfff0e68c, Option("khaki"))

  /** */
  val lavender = PresetColor(0xffe6e6fa, Option("lavender"))

  /** */
  val lavenderBlush = PresetColor(0xfffff0f5, Option("lavender blush"))

  /** */
  val lawnGreen = PresetColor(0xff7cfc00, Option("lawn green"))

  /** */
  val lemonChiffon = PresetColor(0xfffffacd, Option("lemon chiffon"))

  /** */
  val lightBlue = PresetColor(0xffadd8e6, Option("light blue"))

  /** */
  val lightCoral = PresetColor(0xfff08080, Option("light coral"))

  /** */
  val lightCyan = PresetColor(0xffe0ffff, Option("light cyan"))

  /** */
  val lightGoldenrodYellow = PresetColor(0xfffafad2, Option("light goldenrod yellow"))

  /** */
  val lightGreen = PresetColor(0xff90ee90, Option("light green"))

  /** */
  val lightGrey = PresetColor(0xffd3d3d3, Option("light grey"))

  /** */
  val lightPink = PresetColor(0xffffb6c1, Option("light pink"))

  /** */
  val lightSalmon = PresetColor(0xffffa07a, Option("light salmon"))

  /** */
  val lightSeaGreen = PresetColor(0xff20b2aa, Option("light sea green"))

  /** */
  val lightSkyBlue = PresetColor(0xff87cefa, Option("light sky blue"))

  /** */
  val lightSlateGray = PresetColor(0xff778899, Option("light slate gray"))

  /** */
  val lightSteelBlue = PresetColor(0xffb0c4de, Option("light steel blue"))

  /** */
  val lightYellow = PresetColor(0xffffffe0, Option("light yellow"))

  /** */
  val lime = PresetColor(0xff00ff00, Option("lime"))

  /** */
  val limeGreen = PresetColor(0xff32cd32, Option("lime green"))

  /** */
  val linen = PresetColor(0xfffaf0e6, Option("linen"))

  /** */
  val magenta = PresetColor(0xffff00ff, Option("magenta"))

  /** */
  val maroon = PresetColor(0xff800000, Option("maroon"))

  /** */
  val mediumAquamarine = PresetColor(0xff66cdaa, Option("medium aquamarine"))

  /** */
  val mediumBlue = PresetColor(0xff0000cd, Option("medium blue"))

  /** */
  val mediumOrchid = PresetColor(0xffba55d3, Option("medium orchid"))

  /** */
  val mediumPurple = PresetColor(0xff9370db, Option("medium purple"))

  /** */
  val mediumSeaGreen = PresetColor(0xff3cb371, Option("medium sea green"))

  /** */
  val mediumSlateBlue = PresetColor(0xff7b68ee, Option("medium slate blue"))

  /** */
  val mediumSpringGreen = PresetColor(0xff00fa9a, Option("medium spring green"))

  /** */
  val mediumTurquoise = PresetColor(0xff48d1cc, Option("medium turquoise"))

  /** */
  val mediumVioletRed = PresetColor(0xffc71585, Option("medium violet red"))

  /** */
  val midnightBlue = PresetColor(0xff191970, Option("midnight blue"))

  /** */
  val mintCream = PresetColor(0xfff5fffa, Option("mint cream"))

  /** */
  val mistyRose = PresetColor(0xffffe4e1, Option("misty rose"))

  /** */
  val moccasin = PresetColor(0xffffe4b5, Option("moccasin"))

  /** */
  val navajoWhite = PresetColor(0xffffdead, Option("navajo white"))

  /** */
  val navy = PresetColor(0xff000080, Option("navy"))

  /** */
  val oldLace = PresetColor(0xfffdf5e6, Option("old lace"))

  /** */
  val olive = PresetColor(0xff808000, Option("olive"))

  /** */
  val oliveDrab = PresetColor(0xff6b8e23, Option("olive drab"))

  /** */
  val orange = PresetColor(0xffffa500, Option("orange"))

  /** */
  val orangeRed = PresetColor(0xffff4500, Option("orange red"))

  /** */
  val orchid = PresetColor(0xffda70d6, Option("orchid"))

  /** */
  val paleGoldenrod = PresetColor(0xffeee8aa, Option("pale goldenrod"))

  /** */
  val paleGreen = PresetColor(0xff98fb98, Option("pale green"))

  /** */
  val paleTurquoise = PresetColor(0xffafeeee, Option("pale turquoise"))

  /** */
  val paleVioletRed = PresetColor(0xffdb7093, Option("pale violet red"))

  /** */
  val papayaWhip = PresetColor(0xffffefd5, Option("papaya whip"))

  /** */
  val peachPuff = PresetColor(0xffffdab9, Option("peach puff"))

  /** */
  val peru = PresetColor(0xffcd853f, Option("peru"))

  /** */
  val pink = PresetColor(0xffffc0cb, Option("pink"))

  /** */
  val plum = PresetColor(0xffdda0dd, Option("plum"))

  /** */
  val powderBlue = PresetColor(0xffb0e0e6, Option("powder blue"))

  /** */
  val purple = PresetColor(0xff800080, Option("purple"))

  /** */
  val red = PresetColor(0xffff0000, Option("red"))

  /** */
  val rosyBrown = PresetColor(0xffbc8f8f, Option("rosy brown"))

  /** */
  val royalBlue = PresetColor(0xff4169e1, Option("royal blue"))

  /** */
  val saddleBrown = PresetColor(0xff8b4513, Option("saddle brown"))

  /** */
  val salmon = PresetColor(0xfffa8072, Option("salmon"))

  /** */
  val sandyBrown = PresetColor(0xfff4a460, Option("sandy brown"))

  /** */
  val seaGreen = PresetColor(0xff2e8b57, Option("sea green"))

  /** */
  val seashell = PresetColor(0xfffff5ee, Option("seashell"))

  /** */
  val sienna = PresetColor(0xffa0522d, Option("sienna"))

  /** */
  val silver = PresetColor(0xffc0c0c0, Option("silver"))

  /** */
  val skyBlue = PresetColor(0xff87ceeb, Option("sky blue"))

  /** */
  val slateBlue = PresetColor(0xff6a5acd, Option("slate blue"))

  /** */
  val slateGray = PresetColor(0xff708090, Option("slate gray"))

  /** */
  val snow = PresetColor(0xfffffafa, Option("snow"))

  /** */
  val springGreen = PresetColor(0xff00ff7f, Option("spring green"))

  /** */
  val steelBlue = PresetColor(0xff4682b4, Option("steel blue"))

  /** */
  val tan = PresetColor(0xffd2b48c, Option("tan"))

  /** */
  val teal = PresetColor(0xff008080, Option("teal"))

  /** */
  val thistle = PresetColor(0xffd8bfd8, Option("thistle"))

  /** */
  val tomato = PresetColor(0xffff6347, Option("tomato"))

  /** */
  val turquoise = PresetColor(0xff40e0d0, Option("turquoise"))

  /** */
  val violet = PresetColor(0xffee82ee, Option("violet"))

  /** */
  val wheat = PresetColor(0xfff5deb3, Option("wheat"))

  /** */
  val white = PresetColor(0xffffffff, Option("white"))

  /** */
  val whiteSmoke = PresetColor(0xfff5f5f5, Option("white smoke"))

  /** */
  val yellow = PresetColor(0xffffff00, Option("yellow"))

  /** */
  val yellowGreen = PresetColor(0xff9acd32, Option("yellow green"))


} with Map[String, Color] {


  /** */
  private[this] var _colorMap: Map[String, Color] = new HashMap[String, Color]()

  /**
   *
   *
   * @param kv
   * @tparam T
   * @return
   */
  override def +[T >: Color] (kv: (String, T)): Map[String, T] = {
    var keyCandidate = kv._1

    require(keyCandidate != null, "Color map key must not be null.")

    keyCandidate = keyCandidate.trim

    require(keyCandidate.length > 0,
      "Color map key must not be an empty string or contain only white space.")

    require(!_colorMap.contains(keyCandidate),
      s"Key of color map must be unique, but this key ($keyCandidate) was already inserted.")

    val valueCandidate = kv._2.asInstanceOf[Color]

    require(valueCandidate != null, "Value of color map must not be null.")

    _colorMap = _colorMap + (keyCandidate -> valueCandidate)
    _colorMap
  }

  /**
   *
   *
   * @param key
   * @return
   */
  override def get(key: String): Option[Color] = _colorMap.get(key)

  /**
   *
   *
   * @return
   */
  override def iterator: Iterator[(String, Color)] = _colorMap.iterator

  /**
   *
   *
   * @param key
   * @return
   */
  override def - (key: String): Map[String, Color] = _colorMap.-(key)

}
