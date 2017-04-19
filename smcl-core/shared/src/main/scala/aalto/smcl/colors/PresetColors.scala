/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package aalto.smcl.colors


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class PresetColors private[colors]()
    extends Map[Symbol, RGBAColor]
            with Immutable {

  /** */
  private[this] lazy val _colorMap = Map[Symbol, RGBAColor](
    'aliceBlue -> PresetRGBAColor(0xfff0f8ff, Option("alice blue")),
    'amethyst -> PresetRGBAColor(0xff9966cc, Option("amethyst")),
    'antiqueWhite -> PresetRGBAColor(0xfffaebd7, Option("antique white")),
    'aqua -> PresetRGBAColor(0xff00ffff, Option("aqua")),
    'aquamarine -> PresetRGBAColor(0xff7fffd4, Option("aquamarine")),
    'azure -> PresetRGBAColor(0xfff0ffff, Option("azure")),
    'beige -> PresetRGBAColor(0xfff5f5dc, Option("beige")),
    'bisque -> PresetRGBAColor(0xffffe4c4, Option("bisque")),
    'black -> PresetRGBAColor(0xff000000, Option("black")),
    'blanchedAlmond -> PresetRGBAColor(0xffffebcd, Option("blanched almond")),
    'blue -> PresetRGBAColor(0xff0000ff, Option("blue")),
    'blueViolet -> PresetRGBAColor(0xff8a2be2, Option("blue violet")),
    'brown -> PresetRGBAColor(0xffa52a2a, Option("brown")),
    'burlyWood -> PresetRGBAColor(0xffdeb887, Option("burly wood")),
    'cadetBlue -> PresetRGBAColor(0xff5f9ea0, Option("cadet blue")),
    'chartreuse -> PresetRGBAColor(0xff7fff00, Option("chartreuse")),
    'chocolate -> PresetRGBAColor(0xffd2691e, Option("chocolate")),
    'coral -> PresetRGBAColor(0xffff7f50, Option("coral")),
    'cornflowerBlue -> PresetRGBAColor(0xff6495ed, Option("cornflower blue")),
    'cornsilk -> PresetRGBAColor(0xfffff8dc, Option("cornsilk")),
    'crimson -> PresetRGBAColor(0xffdc143c, Option("crimson")),
    'cyan -> PresetRGBAColor(0xff00ffff, Option("cyan")),
    'darkBlue -> PresetRGBAColor(0xff00008b, Option("dark blue")),
    'darkCyan -> PresetRGBAColor(0xff008b8b, Option("dark cyan")),
    'darkGoldenrod -> PresetRGBAColor(0xffb8860b, Option("dark goldenrod")),
    'darkGray -> PresetRGBAColor(0xffa9a9a9, Option("dark gray")),
    'darkGreen -> PresetRGBAColor(0xff006400, Option("dark green")),
    'darkKhaki -> PresetRGBAColor(0xffbdb76b, Option("dark khaki")),
    'darkMagenta -> PresetRGBAColor(0xff8b008b, Option("dark magenta")),
    'darkOliveGreen -> PresetRGBAColor(0xff556b2f, Option("dark olive green")),
    'darkOrange -> PresetRGBAColor(0xffff8c00, Option("dark orange")),
    'darkOrchid -> PresetRGBAColor(0xff9932cc, Option("dark orchid")),
    'darkRed -> PresetRGBAColor(0xff8b0000, Option("dark red")),
    'darkSalmon -> PresetRGBAColor(0xffe9967a, Option("dark salmon")),
    'darkSeaGreen -> PresetRGBAColor(0xff8fbc8f, Option("dark sea green")),
    'darkSlateBlue -> PresetRGBAColor(0xff483d8b, Option("dark slate blue")),
    'darkSlateGray -> PresetRGBAColor(0xff2f4f4f, Option("dark slate gray")),
    'darkTurquoise -> PresetRGBAColor(0xff00ced1, Option("dark turquoise")),
    'darkViolet -> PresetRGBAColor(0xff9400d3, Option("dark violet")),
    'deepPink -> PresetRGBAColor(0xffff1493, Option("deep pink")),
    'deepSkyBlue -> PresetRGBAColor(0xff00bfff, Option("deep sky blue")),
    'dimGray -> PresetRGBAColor(0xff696969, Option("dim gray")),
    'dodgerBlue -> PresetRGBAColor(0xff1e90ff, Option("dodger blue")),
    'fireBrick -> PresetRGBAColor(0xffb22222, Option("fire brick")),
    'floralWhite -> PresetRGBAColor(0xfffffaf0, Option("floral white")),
    'forestGreen -> PresetRGBAColor(0xff228b22, Option("forest green")),
    'fuchsia -> PresetRGBAColor(0xffff00ff, Option("fuchsia")),
    'gainsboro -> PresetRGBAColor(0xffdcdcdc, Option("gainsboro")),
    'ghostWhite -> PresetRGBAColor(0xfff8f8ff, Option("ghost white")),
    'gold -> PresetRGBAColor(0xffffd700, Option("gold")),
    'goldenrod -> PresetRGBAColor(0xffdaa520, Option("goldenrod")),
    'gray -> PresetRGBAColor(0xff808080, Option("gray")),
    'green -> PresetRGBAColor(0xff008000, Option("green")),
    'greenYellow -> PresetRGBAColor(0xffadff2f, Option("green yellow")),
    'honeydew -> PresetRGBAColor(0xfff0fff0, Option("honeydew")),
    'hotPink -> PresetRGBAColor(0xffff69b4, Option("hot pink")),
    'indianRed -> PresetRGBAColor(0xffcd5c5c, Option("indian red")),
    'indigo -> PresetRGBAColor(0xff4b0082, Option("indigo")),
    'ivory -> PresetRGBAColor(0xfffffff0, Option("ivory")),
    'khaki -> PresetRGBAColor(0xfff0e68c, Option("khaki")),
    'lavender -> PresetRGBAColor(0xffe6e6fa, Option("lavender")),
    'lavenderBlush -> PresetRGBAColor(0xfffff0f5, Option("lavender blush")),
    'lawnGreen -> PresetRGBAColor(0xff7cfc00, Option("lawn green")),
    'lemonChiffon -> PresetRGBAColor(0xfffffacd, Option("lemon chiffon")),
    'lightBlue -> PresetRGBAColor(0xffadd8e6, Option("light blue")),
    'lightCoral -> PresetRGBAColor(0xfff08080, Option("light coral")),
    'lightCyan -> PresetRGBAColor(0xffe0ffff, Option("light cyan")),
    'lightGoldenrodYellow -> PresetRGBAColor(0xfffafad2, Option("light goldenrod yellow")),
    'lightGreen -> PresetRGBAColor(0xff90ee90, Option("light green")),
    'lightGrey -> PresetRGBAColor(0xffd3d3d3, Option("light grey")),
    'lightPink -> PresetRGBAColor(0xffffb6c1, Option("light pink")),
    'lightSalmon -> PresetRGBAColor(0xffffa07a, Option("light salmon")),
    'lightSeaGreen -> PresetRGBAColor(0xff20b2aa, Option("light sea green")),
    'lightSkyBlue -> PresetRGBAColor(0xff87cefa, Option("light sky blue")),
    'lightSlateGray -> PresetRGBAColor(0xff778899, Option("light slate gray")),
    'lightSteelBlue -> PresetRGBAColor(0xffb0c4de, Option("light steel blue")),
    'lightYellow -> PresetRGBAColor(0xffffffe0, Option("light yellow")),
    'lime -> PresetRGBAColor(0xff00ff00, Option("lime")),
    'limeGreen -> PresetRGBAColor(0xff32cd32, Option("lime green")),
    'linen -> PresetRGBAColor(0xfffaf0e6, Option("linen")),
    'magenta -> PresetRGBAColor(0xffff00ff, Option("magenta")),
    'maroon -> PresetRGBAColor(0xff800000, Option("maroon")),
    'mediumAquamarine -> PresetRGBAColor(0xff66cdaa, Option("medium aquamarine")),
    'mediumBlue -> PresetRGBAColor(0xff0000cd, Option("medium blue")),
    'mediumOrchid -> PresetRGBAColor(0xffba55d3, Option("medium orchid")),
    'mediumPurple -> PresetRGBAColor(0xff9370db, Option("medium purple")),
    'mediumSeaGreen -> PresetRGBAColor(0xff3cb371, Option("medium sea green")),
    'mediumSlateBlue -> PresetRGBAColor(0xff7b68ee, Option("medium slate blue")),
    'mediumSpringGreen -> PresetRGBAColor(0xff00fa9a, Option("medium spring green")),
    'mediumTurquoise -> PresetRGBAColor(0xff48d1cc, Option("medium turquoise")),
    'mediumVioletRed -> PresetRGBAColor(0xffc71585, Option("medium violet red")),
    'midnightBlue -> PresetRGBAColor(0xff191970, Option("midnight blue")),
    'mintCream -> PresetRGBAColor(0xfff5fffa, Option("mint cream")),
    'mistyRose -> PresetRGBAColor(0xffffe4e1, Option("misty rose")),
    'moccasin -> PresetRGBAColor(0xffffe4b5, Option("moccasin")),
    'navajoWhite -> PresetRGBAColor(0xffffdead, Option("navajo white")),
    'navy -> PresetRGBAColor(0xff000080, Option("navy")),
    'oldLace -> PresetRGBAColor(0xfffdf5e6, Option("old lace")),
    'olive -> PresetRGBAColor(0xff808000, Option("olive")),
    'oliveDrab -> PresetRGBAColor(0xff6b8e23, Option("olive drab")),
    'orange -> PresetRGBAColor(0xffffa500, Option("orange")),
    'orangeRed -> PresetRGBAColor(0xffff4500, Option("orange red")),
    'orchid -> PresetRGBAColor(0xffda70d6, Option("orchid")),
    'paleGoldenrod -> PresetRGBAColor(0xffeee8aa, Option("pale goldenrod")),
    'paleGreen -> PresetRGBAColor(0xff98fb98, Option("pale green")),
    'paleTurquoise -> PresetRGBAColor(0xffafeeee, Option("pale turquoise")),
    'paleVioletRed -> PresetRGBAColor(0xffdb7093, Option("pale violet red")),
    'papayaWhip -> PresetRGBAColor(0xffffefd5, Option("papaya whip")),
    'peachPuff -> PresetRGBAColor(0xffffdab9, Option("peach puff")),
    'peru -> PresetRGBAColor(0xffcd853f, Option("peru")),
    'pink -> PresetRGBAColor(0xffffc0cb, Option("pink")),
    'plum -> PresetRGBAColor(0xffdda0dd, Option("plum")),
    'powderBlue -> PresetRGBAColor(0xffb0e0e6, Option("powder blue")),
    'purple -> PresetRGBAColor(0xff800080, Option("purple")),
    'red -> PresetRGBAColor(0xffff0000, Option("red")),
    'rosyBrown -> PresetRGBAColor(0xffbc8f8f, Option("rosy brown")),
    'royalBlue -> PresetRGBAColor(0xff4169e1, Option("royal blue")),
    'saddleBrown -> PresetRGBAColor(0xff8b4513, Option("saddle brown")),
    'salmon -> PresetRGBAColor(0xfffa8072, Option("salmon")),
    'sandyBrown -> PresetRGBAColor(0xfff4a460, Option("sandy brown")),
    'seaGreen -> PresetRGBAColor(0xff2e8b57, Option("sea green")),
    'seashell -> PresetRGBAColor(0xfffff5ee, Option("seashell")),
    'sienna -> PresetRGBAColor(0xffa0522d, Option("sienna")),
    'silver -> PresetRGBAColor(0xffc0c0c0, Option("silver")),
    'skyBlue -> PresetRGBAColor(0xff87ceeb, Option("sky blue")),
    'slateBlue -> PresetRGBAColor(0xff6a5acd, Option("slate blue")),
    'slateGray -> PresetRGBAColor(0xff708090, Option("slate gray")),
    'snow -> PresetRGBAColor(0xfffffafa, Option("snow")),
    'springGreen -> PresetRGBAColor(0xff00ff7f, Option("spring green")),
    'steelBlue -> PresetRGBAColor(0xff4682b4, Option("steel blue")),
    'tan -> PresetRGBAColor(0xffd2b48c, Option("tan")),
    'teal -> PresetRGBAColor(0xff008080, Option("teal")),
    'thistle -> PresetRGBAColor(0xffd8bfd8, Option("thistle")),
    'tomato -> PresetRGBAColor(0xffff6347, Option("tomato")),
    'turquoise -> PresetRGBAColor(0xff40e0d0, Option("turquoise")),
    'violet -> PresetRGBAColor(0xffee82ee, Option("violet")),
    'wheat -> PresetRGBAColor(0xfff5deb3, Option("wheat")),
    'white -> PresetRGBAColor(0xffffffff, Option("white")),
    'whiteSmoke -> PresetRGBAColor(0xfff5f5f5, Option("white smoke")),
    'yellow -> PresetRGBAColor(0xffffff00, Option("yellow")),
    'yellowGreen -> PresetRGBAColor(0xff9acd32, Option("yellow green"))
  )


  /**
   *
   *
   * @param kv
   * @tparam T
   *
   * @return
   */
  override def +[T >: RGBAColor] (kv: (Symbol, T)): Map[Symbol, T] = _colorMap + kv

  /**
   *
   *
   * @param key
   *
   * @return
   */
  override def get(key: Symbol): Option[RGBAColor] = _colorMap.get(key)

  /**
   *
   *
   * @return
   */
  override def iterator: Iterator[(Symbol, RGBAColor)] = _colorMap.iterator

  /**
   *
   *
   * @param key
   *
   * @return
   */
  override def - (key: Symbol): Map[Symbol, RGBAColor] = _colorMap - key

}
