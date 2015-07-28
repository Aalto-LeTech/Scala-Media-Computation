package aalto.smcl.common




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object PresetColors extends Map[Symbol, Color] with Immutable {

  /** */
  private[this] val _colorMap = Map[Symbol, Color](
    'aliceBlue -> PresetColor(0xfff0f8ff, Option("alice blue")),
    'amethyst -> PresetColor(0xff9966cc, Option("amethyst")),
    'antiqueWhite -> PresetColor(0xfffaebd7, Option("antique white")),
    'aqua -> PresetColor(0xff00ffff, Option("aqua")),
    'aquamarine -> PresetColor(0xff7fffd4, Option("aquamarine")),
    'azure -> PresetColor(0xfff0ffff, Option("azure")),
    'beige -> PresetColor(0xfff5f5dc, Option("beige")),
    'bisque -> PresetColor(0xffffe4c4, Option("bisque")),
    'black -> PresetColor(0xff000000, Option("black")),
    'blanchedAlmond -> PresetColor(0xffffebcd, Option("blanched almond")),
    'blue -> PresetColor(0xff0000ff, Option("blue")),
    'blueViolet -> PresetColor(0xff8a2be2, Option("blue violet")),
    'brown -> PresetColor(0xffa52a2a, Option("brown")),
    'burlyWood -> PresetColor(0xffdeb887, Option("burly wood")),
    'cadetBlue -> PresetColor(0xff5f9ea0, Option("cadet blue")),
    'chartreuse -> PresetColor(0xff7fff00, Option("chartreuse")),
    'chocolate -> PresetColor(0xffd2691e, Option("chocolate")),
    'coral -> PresetColor(0xffff7f50, Option("coral")),
    'cornflowerBlue -> PresetColor(0xff6495ed, Option("cornflower blue")),
    'cornsilk -> PresetColor(0xfffff8dc, Option("cornsilk")),
    'crimson -> PresetColor(0xffdc143c, Option("crimson")),
    'cyan -> PresetColor(0xff00ffff, Option("cyan")),
    'darkBlue -> PresetColor(0xff00008b, Option("dark blue")),
    'darkCyan -> PresetColor(0xff008b8b, Option("dark cyan")),
    'darkGoldenrod -> PresetColor(0xffb8860b, Option("dark goldenrod")),
    'darkGray -> PresetColor(0xffa9a9a9, Option("dark gray")),
    'darkGreen -> PresetColor(0xff006400, Option("dark green")),
    'darkKhaki -> PresetColor(0xffbdb76b, Option("dark khaki")),
    'darkMagenta -> PresetColor(0xff8b008b, Option("dark magenta")),
    'darkOliveGreen -> PresetColor(0xff556b2f, Option("dark olive green")),
    'darkOrange -> PresetColor(0xffff8c00, Option("dark orange")),
    'darkOrchid -> PresetColor(0xff9932cc, Option("dark orchid")),
    'darkRed -> PresetColor(0xff8b0000, Option("dark red")),
    'darkSalmon -> PresetColor(0xffe9967a, Option("dark salmon")),
    'darkSeaGreen -> PresetColor(0xff8fbc8f, Option("dark sea green")),
    'darkSlateBlue -> PresetColor(0xff483d8b, Option("dark slate blue")),
    'darkSlateGray -> PresetColor(0xff2f4f4f, Option("dark slate gray")),
    'darkTurquoise -> PresetColor(0xff00ced1, Option("dark turquoise")),
    'darkViolet -> PresetColor(0xff9400d3, Option("dark violet")),
    'deepPink -> PresetColor(0xffff1493, Option("deep pink")),
    'deepSkyBlue -> PresetColor(0xff00bfff, Option("deep sky blue")),
    'dimGray -> PresetColor(0xff696969, Option("dim gray")),
    'dodgerBlue -> PresetColor(0xff1e90ff, Option("dodger blue")),
    'fireBrick -> PresetColor(0xffb22222, Option("fire brick")),
    'floralWhite -> PresetColor(0xfffffaf0, Option("floral white")),
    'forestGreen -> PresetColor(0xff228b22, Option("forest green")),
    'fuchsia -> PresetColor(0xffff00ff, Option("fuchsia")),
    'gainsboro -> PresetColor(0xffdcdcdc, Option("gainsboro")),
    'ghostWhite -> PresetColor(0xfff8f8ff, Option("ghost white")),
    'gold -> PresetColor(0xffffd700, Option("gold")),
    'goldenrod -> PresetColor(0xffdaa520, Option("goldenrod")),
    'gray -> PresetColor(0xff808080, Option("gray")),
    'green -> PresetColor(0xff008000, Option("green")),
    'greenYellow -> PresetColor(0xffadff2f, Option("green yellow")),
    'honeydew -> PresetColor(0xfff0fff0, Option("honeydew")),
    'hotPink -> PresetColor(0xffff69b4, Option("hot pink")),
    'indianRed -> PresetColor(0xffcd5c5c, Option("indian red")),
    'indigo -> PresetColor(0xff4b0082, Option("indigo")),
    'ivory -> PresetColor(0xfffffff0, Option("ivory")),
    'khaki -> PresetColor(0xfff0e68c, Option("khaki")),
    'lavender -> PresetColor(0xffe6e6fa, Option("lavender")),
    'lavenderBlush -> PresetColor(0xfffff0f5, Option("lavender blush")),
    'lawnGreen -> PresetColor(0xff7cfc00, Option("lawn green")),
    'lemonChiffon -> PresetColor(0xfffffacd, Option("lemon chiffon")),
    'lightBlue -> PresetColor(0xffadd8e6, Option("light blue")),
    'lightCoral -> PresetColor(0xfff08080, Option("light coral")),
    'lightCyan -> PresetColor(0xffe0ffff, Option("light cyan")),
    'lightGoldenrodYellow -> PresetColor(0xfffafad2, Option("light goldenrod yellow")),
    'lightGreen -> PresetColor(0xff90ee90, Option("light green")),
    'lightGrey -> PresetColor(0xffd3d3d3, Option("light grey")),
    'lightPink -> PresetColor(0xffffb6c1, Option("light pink")),
    'lightSalmon -> PresetColor(0xffffa07a, Option("light salmon")),
    'lightSeaGreen -> PresetColor(0xff20b2aa, Option("light sea green")),
    'lightSkyBlue -> PresetColor(0xff87cefa, Option("light sky blue")),
    'lightSlateGray -> PresetColor(0xff778899, Option("light slate gray")),
    'lightSteelBlue -> PresetColor(0xffb0c4de, Option("light steel blue")),
    'lightYellow -> PresetColor(0xffffffe0, Option("light yellow")),
    'lime -> PresetColor(0xff00ff00, Option("lime")),
    'limeGreen -> PresetColor(0xff32cd32, Option("lime green")),
    'linen -> PresetColor(0xfffaf0e6, Option("linen")),
    'magenta -> PresetColor(0xffff00ff, Option("magenta")),
    'maroon -> PresetColor(0xff800000, Option("maroon")),
    'mediumAquamarine -> PresetColor(0xff66cdaa, Option("medium aquamarine")),
    'mediumBlue -> PresetColor(0xff0000cd, Option("medium blue")),
    'mediumOrchid -> PresetColor(0xffba55d3, Option("medium orchid")),
    'mediumPurple -> PresetColor(0xff9370db, Option("medium purple")),
    'mediumSeaGreen -> PresetColor(0xff3cb371, Option("medium sea green")),
    'mediumSlateBlue -> PresetColor(0xff7b68ee, Option("medium slate blue")),
    'mediumSpringGreen -> PresetColor(0xff00fa9a, Option("medium spring green")),
    'mediumTurquoise -> PresetColor(0xff48d1cc, Option("medium turquoise")),
    'mediumVioletRed -> PresetColor(0xffc71585, Option("medium violet red")),
    'midnightBlue -> PresetColor(0xff191970, Option("midnight blue")),
    'mintCream -> PresetColor(0xfff5fffa, Option("mint cream")),
    'mistyRose -> PresetColor(0xffffe4e1, Option("misty rose")),
    'moccasin -> PresetColor(0xffffe4b5, Option("moccasin")),
    'navajoWhite -> PresetColor(0xffffdead, Option("navajo white")),
    'navy -> PresetColor(0xff000080, Option("navy")),
    'oldLace -> PresetColor(0xfffdf5e6, Option("old lace")),
    'olive -> PresetColor(0xff808000, Option("olive")),
    'oliveDrab -> PresetColor(0xff6b8e23, Option("olive drab")),
    'orange -> PresetColor(0xffffa500, Option("orange")),
    'orangeRed -> PresetColor(0xffff4500, Option("orange red")),
    'orchid -> PresetColor(0xffda70d6, Option("orchid")),
    'paleGoldenrod -> PresetColor(0xffeee8aa, Option("pale goldenrod")),
    'paleGreen -> PresetColor(0xff98fb98, Option("pale green")),
    'paleTurquoise -> PresetColor(0xffafeeee, Option("pale turquoise")),
    'paleVioletRed -> PresetColor(0xffdb7093, Option("pale violet red")),
    'papayaWhip -> PresetColor(0xffffefd5, Option("papaya whip")),
    'peachPuff -> PresetColor(0xffffdab9, Option("peach puff")),
    'peru -> PresetColor(0xffcd853f, Option("peru")),
    'pink -> PresetColor(0xffffc0cb, Option("pink")),
    'plum -> PresetColor(0xffdda0dd, Option("plum")),
    'powderBlue -> PresetColor(0xffb0e0e6, Option("powder blue")),
    'purple -> PresetColor(0xff800080, Option("purple")),
    'red -> PresetColor(0xffff0000, Option("red")),
    'rosyBrown -> PresetColor(0xffbc8f8f, Option("rosy brown")),
    'royalBlue -> PresetColor(0xff4169e1, Option("royal blue")),
    'saddleBrown -> PresetColor(0xff8b4513, Option("saddle brown")),
    'salmon -> PresetColor(0xfffa8072, Option("salmon")),
    'sandyBrown -> PresetColor(0xfff4a460, Option("sandy brown")),
    'seaGreen -> PresetColor(0xff2e8b57, Option("sea green")),
    'seashell -> PresetColor(0xfffff5ee, Option("seashell")),
    'sienna -> PresetColor(0xffa0522d, Option("sienna")),
    'silver -> PresetColor(0xffc0c0c0, Option("silver")),
    'skyBlue -> PresetColor(0xff87ceeb, Option("sky blue")),
    'slateBlue -> PresetColor(0xff6a5acd, Option("slate blue")),
    'slateGray -> PresetColor(0xff708090, Option("slate gray")),
    'snow -> PresetColor(0xfffffafa, Option("snow")),
    'springGreen -> PresetColor(0xff00ff7f, Option("spring green")),
    'steelBlue -> PresetColor(0xff4682b4, Option("steel blue")),
    'tan -> PresetColor(0xffd2b48c, Option("tan")),
    'teal -> PresetColor(0xff008080, Option("teal")),
    'thistle -> PresetColor(0xffd8bfd8, Option("thistle")),
    'tomato -> PresetColor(0xffff6347, Option("tomato")),
    'turquoise -> PresetColor(0xff40e0d0, Option("turquoise")),
    'violet -> PresetColor(0xffee82ee, Option("violet")),
    'wheat -> PresetColor(0xfff5deb3, Option("wheat")),
    'white -> PresetColor(0xffffffff, Option("white")),
    'whiteSmoke -> PresetColor(0xfff5f5f5, Option("white smoke")),
    'yellow -> PresetColor(0xffffff00, Option("yellow")),
    'yellowGreen -> PresetColor(0xff9acd32, Option("yellow green"))
  )


  /**
   *
   *
   * @param kv
   * @tparam T
   * @return
   */
  override def +[T >: Color] (kv: (Symbol, T)): Map[Symbol, T] = _colorMap + kv

  /**
   *
   *
   * @param key
   * @return
   */
  override def get(key: Symbol): Option[Color] = _colorMap.get(key)

  /**
   *
   *
   * @return
   */
  override def iterator: Iterator[(Symbol, Color)] = _colorMap.iterator

  /**
   *
   *
   * @param key
   * @return
   */
  override def - (key: Symbol): Map[Symbol, Color] = _colorMap - key

}
