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


import aalto.smcl.infrastructure.StrEmpty




/**
 * This trait defines all preset colors available in SMCL. These color form a superset
 * of the colors defined in 5.7.2017 draft of the
 * <a href="https://www.w3.org/TR/2016/WD-css-color-4-20160705/">CSS Color Module Level 4</a>
 * standard.
 *
 * @author Aleksi Lukkarinen
 */
trait PresetColors {

  // Sequence containing all preset colors; they are added by the PresetRGBAColor constructor.
  private var allPresetColors: Seq[PresetRGBAColor] = Seq()




  /**
   * Preset RGBA color.
   *
   * @param argbInt
   * @param canonicalName
   * @param cssName
   */
  sealed case class PresetRGBAColor private(
      argbInt: Int,
      override val canonicalName: Option[String] = None,
      override val cssName: Option[String] = None) extends {

    /** Returns `true` if this [[PresetRGBAColor]] is provided by SMCL, otherwise `false`. */
    override val isPreset: Boolean = true

  } with
      RGBAColor(
        redComponentOf(argbInt),
        greenComponentOf(argbInt),
        blueComponentOf(argbInt),
        opacityComponentOf(argbInt),
        canonicalName)
      with Immutable {

    allPresetColors = allPresetColors :+ this

    /**
     * Returns a string representation of this [[PresetRGBAColor]].
     */
    override def toString: String =
      s"${super.toString}\nCSS name: ${cssName getOrElse StrEmpty}"
  }




  /** Color constant for <em>"alice blue"</em> (<code>0xfff0f8ff</code>). */
  val AliceBlue = PresetRGBAColor(0xfff0f8ff, Some("alice blue"), Some("aliceblue"))

  /** Color constant for <em>"amethyst"</em> (<code>0xff9966cc</code>). */
  val Amethyst = PresetRGBAColor(0xff9966cc, Some("amethyst"), Some("amethyst"))

  /** Color constant for <em>"antique white"</em> (<code>0xfffaebd7</code>). */
  val AntiqueWhite = PresetRGBAColor(0xfffaebd7, Some("antique white"), Some("antiquewhite"))

  /** Color constant for <em>"aqua"</em> (<code>0xff00ffff</code>). */
  val Aqua = PresetRGBAColor(0xff00ffff, Some("aqua"), Some("aqua"))

  /** Color constant for <em>"aquamarine"</em> (<code>0xff7fffd4</code>). */
  val Aquamarine = PresetRGBAColor(0xff7fffd4, Some("aquamarine"), Some("aquamarine"))

  /** Color constant for <em>"azure"</em> (<code>0xfff0ffff</code>). */
  val Azure = PresetRGBAColor(0xfff0ffff, Some("azure"), Some("azure"))

  /** Color constant for <em>"beige"</em> (<code>0xfff5f5dc</code>). */
  val Beige = PresetRGBAColor(0xfff5f5dc, Some("beige"), Some("beige"))

  /** Color constant for <em>"bisque"</em> (<code>0xffffe4c4</code>). */
  val Bisque = PresetRGBAColor(0xffffe4c4, Some("bisque"), Some("bisque"))

  /** Color constant for <em>"black"</em> (<code>0xff000000</code>). */
  val Black = PresetRGBAColor(0xff000000, Some("black"), Some("black"))

  /** Color constant for <em>"blanched almond"</em> (<code>0xffffebcd</code>). */
  val BlanchedAlmond = PresetRGBAColor(0xffffebcd, Some("blanched almond"), Some("blanchedalmond"))

  /** Color constant for <em>"blue"</em> (<code>0xff0000ff</code>). */
  val Blue = PresetRGBAColor(0xff0000ff, Some("blue"), Some("blue"))

  /** Color constant for <em>"blue violet"</em> (<code>0xff8a2be2</code>). */
  val BlueViolet = PresetRGBAColor(0xff8a2be2, Some("blue violet"), Some("blueviolet"))

  /** Color constant for <em>"brown"</em> (<code>0xffa52a2a</code>). */
  val Brown = PresetRGBAColor(0xffa52a2a, Some("brown"), Some("brown"))

  /** Color constant for <em>"burly wood"</em> (<code>0xffdeb887</code>). */
  val BurlyWood = PresetRGBAColor(0xffdeb887, Some("burly wood"), Some("burlywood"))

  /** Color constant for <em>"cadet blue"</em> (<code>0xff5f9ea0</code>). */
  val CadetBlue = PresetRGBAColor(0xff5f9ea0, Some("cadet blue"), Some("cadetblue"))

  /** Color constant for <em>"chartreuse"</em> (<code>0xff7fff00</code>). */
  val ChartReuse = PresetRGBAColor(0xff7fff00, Some("chartreuse"), Some("chartreuse"))

  /** Color constant for <em>"chocolate"</em> (<code>0xffd2691e</code>). */
  val Chocolate = PresetRGBAColor(0xffd2691e, Some("chocolate"), Some("chocolate"))

  /** Color constant for <em>"coral"</em> (<code>0xffff7f50</code>). */
  val Coral = PresetRGBAColor(0xffff7f50, Some("coral"), Some("coral"))

  /** Color constant for <em>"cornflower blue"</em> (<code>0xff6495ed</code>). */
  val CornflowerBlue = PresetRGBAColor(0xff6495ed, Some("cornflower blue"), Some("cornflowerblue"))

  /** Color constant for <em>"cornsilk"</em> (<code>0xfffff8dc</code>). */
  val CornSilk = PresetRGBAColor(0xfffff8dc, Some("cornsilk"), Some("cornsilk"))

  /** Color constant for <em>"crimson"</em> (<code>0xffdc143c</code>). */
  val Crimson = PresetRGBAColor(0xffdc143c, Some("crimson"), Some("crimson"))

  /** Color constant for <em>"cyan"</em> (<code>0xff00ffff</code>). */
  val Cyan = PresetRGBAColor(0xff00ffff, Some("cyan"), Some("cyan"))

  /** Color constant for <em>"dark blue"</em> (<code>0xff00008b</code>). */
  val DarkBlue = PresetRGBAColor(0xff00008b, Some("dark blue"), Some("darkblue"))

  /** Color constant for <em>"dark cyan"</em> (<code>0xff008b8b</code>). */
  val DarkCyan = PresetRGBAColor(0xff008b8b, Some("dark cyan"), Some("darkcyan"))

  /** Color constant for <em>"dark golden rod"</em> (<code>0xffb8860b</code>). */
  val DarkGoldenRod = PresetRGBAColor(0xffb8860b, Some("dark golden rod"), Some("darkgoldenrod"))

  /** Color constant for <em>"dark gray"</em> (<code>0xffa9a9a9</code>). */
  val DarkGray = PresetRGBAColor(0xffa9a9a9, Some("dark gray"), Some("darkgray"))

  /** Color constant for <em>"dark green"</em> (<code>0xff006400</code>). */
  val DarkGreen = PresetRGBAColor(0xff006400, Some("dark green"), Some("darkgreen"))

  /** Color constant for <em>"dark grey"</em> (<code>0xffa9a9a9</code>). */
  val DarkGrey: RGBAColor = PresetRGBAColor(0xffa9a9a9, Some("dark grey"), Some("darkgrey"))

  /** Color constant for <em>"dark khaki"</em> (<code>0xffbdb76b</code>). */
  val DarkKhaki = PresetRGBAColor(0xffbdb76b, Some("dark khaki"), Some("darkkhaki"))

  /** Color constant for <em>"dark magenta"</em> (<code>0xff8b008b</code>). */
  val DarkMagenta = PresetRGBAColor(0xff8b008b, Some("dark magenta"), Some("darkmagenta"))

  /** Color constant for <em>"dark olive green"</em> (<code>0xff556b2f</code>). */
  val DarkOliveGreen = PresetRGBAColor(0xff556b2f, Some("dark olive green"), Some("darkolivegreen"))

  /** Color constant for <em>"dark orange"</em> (<code>0xffff8c00</code>). */
  val DarkOrange = PresetRGBAColor(0xffff8c00, Some("dark orange"), Some("darkorange"))

  /** Color constant for <em>"dark orchid"</em> (<code>0xff9932cc</code>). */
  val DarkOrchid = PresetRGBAColor(0xff9932cc, Some("dark orchid"), Some("darkorchid"))

  /** Color constant for <em>"dark red"</em> (<code>0xff8b0000</code>). */
  val DarkRed = PresetRGBAColor(0xff8b0000, Some("dark red"), Some("darkred"))

  /** Color constant for <em>"dark salmon"</em> (<code>0xffe9967a</code>). */
  val DarkSalmon = PresetRGBAColor(0xffe9967a, Some("dark salmon"), Some("darksalmon"))

  /** Color constant for <em>"dark sea green"</em> (<code>0xff8fbc8f</code>). */
  val DarkSeaGreen = PresetRGBAColor(0xff8fbc8f, Some("dark sea green"), Some("darkseagreen"))

  /** Color constant for <em>"dark slate blue"</em> (<code>0xff483d8b</code>). */
  val DarkSlateBlue = PresetRGBAColor(0xff483d8b, Some("dark slate blue"), Some("darkslateblue"))

  /** Color constant for <em>"dark slate gray"</em> (<code>0xff2f4f4f</code>). */
  val DarkSlateGray = PresetRGBAColor(0xff2f4f4f, Some("dark slate gray"), Some("darkslategray"))

  /** Color constant for <em>"dark slate grey"</em> (<code>0xff2f4f4f</code>). */
  val DarkSlateGrey = PresetRGBAColor(0xff2f4f4f, Some("dark slate grey"), Some("darkslategrey"))

  /** Color constant for <em>"dark turquoise"</em> (<code>0xff00ced1</code>). */
  val DarkTurquoise = PresetRGBAColor(0xff00ced1, Some("dark turquoise"), Some("darkturquoise"))

  /** Color constant for <em>"dark violet"</em> (<code>0xff9400d3</code>). */
  val DarkViolet = PresetRGBAColor(0xff9400d3, Some("dark violet"), Some("darkviolet"))

  /** Color constant for <em>"deep pink"</em> (<code>0xffff1493</code>). */
  val DeepPink = PresetRGBAColor(0xffff1493, Some("deep pink"), Some("deeppink"))

  /** Color constant for <em>"deep sky blue"</em> (<code>0xff00bfff</code>). */
  val DeepSkyBlue = PresetRGBAColor(0xff00bfff, Some("deep sky blue"), Some("deepskyblue"))

  /** Color constant for <em>"dim gray"</em> (<code>0xff696969</code>). */
  val DimGray = PresetRGBAColor(0xff696969, Some("dim gray"), Some("dimgray"))

  /** Color constant for <em>"dim grey"</em> (<code>0xff696969</code>). */
  val DimGrey = PresetRGBAColor(0xff696969, Some("dim grey"), Some("dimgrey"))

  /** Color constant for <em>"dodger blue"</em> (<code>0xff1e90ff</code>). */
  val DodgerBlue = PresetRGBAColor(0xff1e90ff, Some("dodger blue"), Some("dodgerblue"))

  /** Color constant for <em>"fire brick"</em> (<code>0xffb22222</code>). */
  val FireBrick = PresetRGBAColor(0xffb22222, Some("fire brick"), Some("firebrick"))

  /** Color constant for <em>"floral white"</em> (<code>0xfffffaf0</code>). */
  val FloralWhite = PresetRGBAColor(0xfffffaf0, Some("floral white"), Some("floralwhite"))

  /** Color constant for <em>"forest green"</em> (<code>0xff228b22</code>). */
  val ForestGreen = PresetRGBAColor(0xff228b22, Some("forest green"), Some("forestgreen"))

  /** Color constant for <em>"fuchsia"</em> (<code>0xffff00ff</code>). */
  val Fuchsia = PresetRGBAColor(0xffff00ff, Some("fuchsia"), Some("fuchsia"))

  /** Color constant for <em>"gainsboro"</em> (<code>0xffdcdcdc</code>). */
  val Gainsboro = PresetRGBAColor(0xffdcdcdc, Some("gainsboro"), Some("gainsboro"))

  /** Color constant for <em>"ghost white"</em> (<code>0xfff8f8ff</code>). */
  val GhostWhite = PresetRGBAColor(0xfff8f8ff, Some("ghost white"), Some("ghostwhite"))

  /** Color constant for <em>"gold"</em> (<code>0xffffd700</code>). */
  val Gold = PresetRGBAColor(0xffffd700, Some("gold"), Some("gold"))

  /** Color constant for <em>"golden rod"</em> (<code>0xffdaa520</code>). */
  val GoldenRod = PresetRGBAColor(0xffdaa520, Some("golden rod"), Some("goldenrod"))

  /** Color constant for <em>"gray"</em> (<code>0xff808080</code>). */
  val Gray = PresetRGBAColor(0xff808080, Some("gray"), Some("gray"))

  /** Color constant for <em>"green"</em> (<code>0xff008000</code>). */
  val Green = PresetRGBAColor(0xff008000, Some("green"), Some("green"))

  /** Color constant for <em>"green yellow"</em> (<code>0xffadff2f</code>). */
  val GreenYellow = PresetRGBAColor(0xffadff2f, Some("green yellow"), Some("greenyellow"))

  /** Color constant for <em>"grey"</em> (<code>0xff808080</code>). */
  val Grey = PresetRGBAColor(0xff808080, Some("grey"), Some("grey"))

  /** Color constant for <em>"honeydew"</em> (<code>0xfff0fff0</code>). */
  val Honeydew = PresetRGBAColor(0xfff0fff0, Some("honeydew"), Some("honeydew"))

  /** Color constant for <em>"hot pink"</em> (<code>0xffff69b4</code>). */
  val HotPink = PresetRGBAColor(0xffff69b4, Some("hot pink"), Some("hotpink"))

  /** Color constant for <em>"indian red"</em> (<code>0xffcd5c5c</code>). */
  val IndianRed = PresetRGBAColor(0xffcd5c5c, Some("indian red"), Some("indianred"))

  /** Color constant for <em>"indigo"</em> (<code>0xff4b0082</code>). */
  val Indigo = PresetRGBAColor(0xff4b0082, Some("indigo"), Some("indigo"))

  /** Color constant for <em>"ivory"</em> (<code>0xfffffff0</code>). */
  val Ivory = PresetRGBAColor(0xfffffff0, Some("ivory"), Some("ivory"))

  /** Color constant for <em>"khaki"</em> (<code>0xfff0e68c</code>). */
  val Khaki = PresetRGBAColor(0xfff0e68c, Some("khaki"), Some("khaki"))

  /** Color constant for <em>"lavender"</em> (<code>0xffe6e6fa</code>). */
  val Lavender = PresetRGBAColor(0xffe6e6fa, Some("lavender"), Some("lavender"))

  /** Color constant for <em>"lavender blush"</em> (<code>0xfffff0f5</code>). */
  val LavenderBlush = PresetRGBAColor(0xfffff0f5, Some("lavender blush"), Some("lavenderblush"))

  /** Color constant for <em>"lawn green"</em> (<code>0xff7cfc00</code>). */
  val LawnGreen = PresetRGBAColor(0xff7cfc00, Some("lawn green"), Some("lawngreen"))

  /** Color constant for <em>"lemon chiffon"</em> (<code>0xfffffacd</code>). */
  val LemonChiffon = PresetRGBAColor(0xfffffacd, Some("lemon chiffon"), Some("lemonchiffon"))

  /** Color constant for <em>"light blue"</em> (<code>0xffadd8e6</code>). */
  val LightBlue = PresetRGBAColor(0xffadd8e6, Some("light blue"), Some("lightblue"))

  /** Color constant for <em>"light coral"</em> (<code>0xfff08080</code>). */
  val LightCoral = PresetRGBAColor(0xfff08080, Some("light coral"), Some("lightcoral"))

  /** Color constant for <em>"light cyan"</em> (<code>0xffe0ffff</code>). */
  val LightCyan = PresetRGBAColor(0xffe0ffff, Some("light cyan"), Some("lightcyan"))

  /** Color constant for "light golden rod yellow" (0xfffafad2). */
  val LightGoldenRodYellow =
    PresetRGBAColor(0xfffafad2, Some("light golden rod yellow"), Some("lightgoldenrodyellow"))

  /** Color constant for <em>"light gray"</em> (<code>0xffd3d3d3</code>). */
  val LightGray = PresetRGBAColor(0xffd3d3d3, Some("light gray"), Some("lightgray"))

  /** Color constant for <em>"light green"</em> (<code>0xff90ee90</code>). */
  val LightGreen = PresetRGBAColor(0xff90ee90, Some("light green"), Some("lightgreen"))

  /** Color constant for <em>"light grey"</em> (<code>0xffd3d3d3</code>). */
  val LightGrey = PresetRGBAColor(0xffd3d3d3, Some("light grey"), Some("lightgrey"))

  /** Color constant for <em>"light pink"</em> (<code>0xffffb6c1</code>). */
  val LightPink = PresetRGBAColor(0xffffb6c1, Some("light pink"), Some("lightpink"))

  /** Color constant for <em>"light salmon"</em> (<code>0xffffa07a</code>). */
  val LightSalmon = PresetRGBAColor(0xffffa07a, Some("light salmon"), Some("lightsalmon"))

  /** Color constant for <em>"light sea green"</em> (<code>0xff20b2aa</code>). */
  val LightSeaGreen = PresetRGBAColor(0xff20b2aa, Some("light sea green"), Some("lightseagreen"))

  /** Color constant for <em>"light sky blue"</em> (<code>0xff87cefa</code>). */
  val LightSkyBlue = PresetRGBAColor(0xff87cefa, Some("light sky blue"), Some("lightskyblue"))

  /** Color constant for <em>"light slate gray"</em> (<code>0xff778899</code>). */
  val LightSlateGray = PresetRGBAColor(0xff778899, Some("light slate gray"), Some("lightslategray"))

  /** Color constant for <em>"light slate grey"</em> (<code>0xff778899</code>). */
  val LightSlateGrey = PresetRGBAColor(0xff778899, Some("light slate grey"), Some("lightslategrey"))

  /** Color constant for <em>"light steel blue"</em> (<code>0xffb0c4de</code>). */
  val LightSteelBlue = PresetRGBAColor(0xffb0c4de, Some("light steel blue"), Some("lightsteelblue"))

  /** Color constant for <em>"light yellow"</em> (<code>0xffffffe0</code>). */
  val LightYellow = PresetRGBAColor(0xffffffe0, Some("light yellow"), Some("lightyellow"))

  /** Color constant for <em>"lime"</em> (<code>0xff00ff00</code>). */
  val Lime = PresetRGBAColor(0xff00ff00, Some("lime"), Some("lime"))

  /** Color constant for <em>"lime green"</em> (<code>0xff32cd32</code>). */
  val LimeGreen = PresetRGBAColor(0xff32cd32, Some("lime green"), Some("limegreen"))

  /** Color constant for <em>"linen"</em> (<code>0xfffaf0e6</code>). */
  val Linen = PresetRGBAColor(0xfffaf0e6, Some("linen"), Some("linen"))

  /** Color constant for <em>"magenta"</em> (<code>0xffff00ff</code>). */
  val Magenta = PresetRGBAColor(0xffff00ff, Some("magenta"), Some("magenta"))

  /** Color constant for <em>"maroon"</em> (<code>0xff800000</code>). */
  val Maroon = PresetRGBAColor(0xff800000, Some("maroon"), Some("maroon"))

  /** Color constant for <em>"medium aquamarine"</em> (<code>0xff66cdaa</code>). */
  val MediumAquamarine = PresetRGBAColor(0xff66cdaa, Some("medium aquamarine"), Some("mediumaquamarine"))

  /** Color constant for <em>"medium blue"</em> (<code>0xff0000cd</code>). */
  val MediumBlue = PresetRGBAColor(0xff0000cd, Some("medium blue"), Some("mediumblue"))

  /** Color constant for <em>"medium orchid"</em> (<code>0xffba55d3</code>). */
  val MediumOrchid = PresetRGBAColor(0xffba55d3, Some("medium orchid"), Some("mediumorchid"))

  /** Color constant for <em>"medium purple"</em> (<code>0xff9370db</code>). */
  val MediumPurple = PresetRGBAColor(0xff9370db, Some("medium purple"), Some("mediumpurple"))

  /** Color constant for <em>"medium sea green"</em> (<code>0xff3cb371</code>). */
  val MediumSeaGreen = PresetRGBAColor(0xff3cb371, Some("medium sea green"), Some("mediumseagreen"))

  /** Color constant for <em>"medium slate blue"</em> (<code>0xff7b68ee</code>). */
  val MediumSlateBlue = PresetRGBAColor(0xff7b68ee, Some("medium slate blue"), Some("mediumslateblue"))

  /** Color constant for <em>"medium spring green"</em> (<code>0xff00fa9a</code>). */
  val MediumSpringGreen = PresetRGBAColor(0xff00fa9a, Some("medium spring green"), Some("mediumspringbreen"))

  /** Color constant for <em>"medium turquoise"</em> (<code>0xff48d1cc</code>). */
  val MediumTurquoise = PresetRGBAColor(0xff48d1cc, Some("medium turquoise"), Some("mediumturquoise"))

  /** Color constant for <em>"medium violet red"</em> (<code>0xffc71585</code>). */
  val MediumVioletRed = PresetRGBAColor(0xffc71585, Some("medium violet red"), Some("mediumvioletred"))

  /** Color constant for <em>"midnight blue"</em> (<code>0xff191970</code>). */
  val MidnightBlue = PresetRGBAColor(0xff191970, Some("midnight blue"), Some("midnightblue"))

  /** Color constant for <em>"mint cream"</em> (<code>0xfff5fffa</code>). */
  val MintCream = PresetRGBAColor(0xfff5fffa, Some("mint cream"), Some("mintcream"))

  /** Color constant for <em>"misty rose"</em> (<code>0xffffe4e1</code>). */
  val MistyRose = PresetRGBAColor(0xffffe4e1, Some("misty rose"), Some("mistyrose"))

  /** Color constant for <em>"moccasin"</em> (<code>0xffffe4b5</code>). */
  val Moccasin = PresetRGBAColor(0xffffe4b5, Some("moccasin"), Some("moccasin"))

  /** Color constant for <em>"navajo white"</em> (<code>0xffffdead</code>). */
  val NavajoWhite = PresetRGBAColor(0xffffdead, Some("navajo white"), Some("navajowhite"))

  /** Color constant for <em>"navy"</em> (<code>0xff000080</code>). */
  val Navy = PresetRGBAColor(0xff000080, Some("navy"), Some("navy"))

  /** Color constant for <em>"old lace"</em> (<code>0xfffdf5e6</code>). */
  val OldLace = PresetRGBAColor(0xfffdf5e6, Some("old lace"), Some("oldlace"))

  /** Color constant for <em>"olive"</em> (<code>0xff808000</code>). */
  val Olive = PresetRGBAColor(0xff808000, Some("olive"), Some("olive"))

  /** Color constant for <em>"olive drab"</em> (<code>0xff6b8e23</code>). */
  val OliveDrab = PresetRGBAColor(0xff6b8e23, Some("olive drab"), Some("olivedrab"))

  /** Color constant for <em>"orange"</em> (<code>0xffffa500</code>). */
  val Orange = PresetRGBAColor(0xffffa500, Some("orange"), Some("orange"))

  /** Color constant for <em>"orange red"</em> (<code>0xffff4500</code>). */
  val OrangeRed = PresetRGBAColor(0xffff4500, Some("orange red"), Some("orangered"))

  /** Color constant for <em>"orchid"</em> (<code>0xffda70d6</code>). */
  val Orchid = PresetRGBAColor(0xffda70d6, Some("orchid"), Some("orchid"))

  /** Color constant for <em>"pale golden rod"</em> (<code>0xffeee8aa</code>). */
  val PaleGoldenRod = PresetRGBAColor(0xffeee8aa, Some("pale golden rod"), Some("palegoldenrod"))

  /** Color constant for <em>"pale green"</em> (<code>0xff98fb98</code>). */
  val PaleGreen = PresetRGBAColor(0xff98fb98, Some("pale green"), Some("palegreen"))

  /** Color constant for <em>"pale turquoise"</em> (<code>0xffafeeee</code>). */
  val PaleTurquoise = PresetRGBAColor(0xffafeeee, Some("pale turquoise"), Some("paleturquoise"))

  /** Color constant for <em>"pale violet red"</em> (<code>0xffdb7093</code>). */
  val PaleVioletRed = PresetRGBAColor(0xffdb7093, Some("pale violet red"), Some("palevioletred"))

  /** Color constant for <em>"papaya whip"</em> (<code>0xffffefd5</code>). */
  val PapayaWhip = PresetRGBAColor(0xffffefd5, Some("papaya whip"), Some("papayawhip"))

  /** Color constant for <em>"peach puff"</em> (<code>0xffffdab9</code>). */
  val PeachPuff = PresetRGBAColor(0xffffdab9, Some("peach puff"), Some("peachpuff"))

  /** Color constant for <em>"peru"</em> (<code>0xffcd853f</code>). */
  val Peru = PresetRGBAColor(0xffcd853f, Some("peru"), Some("peru"))

  /** Color constant for <em>"pink"</em> (<code>0xffffc0cb</code>). */
  val Pink = PresetRGBAColor(0xffffc0cb, Some("pink"), Some("pink"))

  /** Color constant for <em>"plum"</em> (<code>0xffdda0dd</code>). */
  val Plum = PresetRGBAColor(0xffdda0dd, Some("plum"), Some("plum"))

  /** Color constant for <em>"powder blue"</em> (<code>0xffb0e0e6</code>). */
  val PowderBlue = PresetRGBAColor(0xffb0e0e6, Some("powder blue"), Some("powderblue"))

  /** Color constant for <em>"purple"</em> (<code>0xff800080</code>). */
  val Purple = PresetRGBAColor(0xff800080, Some("purple"), Some("purple"))

  /** Color constant for <em>"red"</em> (<code>0xffff0000</code>). */
  val Red = PresetRGBAColor(0xffff0000, Some("red"), Some("red"))

  /** Color constant for <em>"rosy brown"</em> (<code>0xffbc8f8f</code>). */
  val RosyBrown = PresetRGBAColor(0xffbc8f8f, Some("rosy brown"), Some("rosybrown"))

  /** Color constant for <em>"royal blue"</em> (<code>0xff4169e1</code>). */
  val RoyalBlue = PresetRGBAColor(0xff4169e1, Some("royal blue"), Some("royalblue"))

  /** Color constant for <em>"saddle brown"</em> (<code>0xff8b4513</code>). */
  val SaddleBrown = PresetRGBAColor(0xff8b4513, Some("saddle brown"), Some("saddlebrown"))

  /** Color constant for <em>"salmon"</em> (<code>0xfffa8072</code>). */
  val Salmon = PresetRGBAColor(0xfffa8072, Some("salmon"), Some("salmon"))

  /** Color constant for <em>"sandy brown"</em> (<code>0xfff4a460</code>). */
  val SandyBrown = PresetRGBAColor(0xfff4a460, Some("sandy brown"), Some("sandybrown"))

  /** Color constant for <em>"sea green"</em> (<code>0xff2e8b57</code>). */
  val SeaGreen = PresetRGBAColor(0xff2e8b57, Some("sea green"), Some("seagreen"))

  /** Color constant for <em>"seashell"</em> (<code>0xfffff5ee</code>). */
  val SeaShell = PresetRGBAColor(0xfffff5ee, Some("seashell"), Some("seashell"))

  /** Color constant for <em>"sienna"</em> (<code>0xffa0522d</code>). */
  val Sienna = PresetRGBAColor(0xffa0522d, Some("sienna"), Some("sienna"))

  /** Color constant for <em>"silver"</em> (<code>0xffc0c0c0</code>). */
  val Silver = PresetRGBAColor(0xffc0c0c0, Some("silver"), Some("silver"))

  /** Color constant for <em>"sky blue"</em> (<code>0xff87ceeb</code>). */
  val SkyBlue = PresetRGBAColor(0xff87ceeb, Some("sky blue"), Some("skyblue"))

  /** Color constant for <em>"slate blue"</em> (<code>0xff6a5acd</code>). */
  val SlateBlue = PresetRGBAColor(0xff6a5acd, Some("slate blue"), Some("slateblue"))

  /** Color constant for <em>"slate gray"</em> (<code>0xff708090</code>). */
  val SlateGray = PresetRGBAColor(0xff708090, Some("slate gray"), Some("slategray"))

  /** Color constant for <em>"slate grey"</em> (<code>0xff708090</code>). */
  val SlateGrey = PresetRGBAColor(0xff708090, Some("slate grey"), Some("slategrey"))

  /** Color constant for <em>"snow"</em> (<code>0xfffffafa</code>). */
  val Snow = PresetRGBAColor(0xfffffafa, Some("snow"), Some("snow"))

  /** Color constant for <em>"spring green"</em> (<code>0xff00ff7f</code>). */
  val SpringGreen = PresetRGBAColor(0xff00ff7f, Some("spring green"), Some("springgreen"))

  /** Color constant for <em>"steel blue"</em> (<code>0xff4682b4</code>). */
  val SteelBlue = PresetRGBAColor(0xff4682b4, Some("steel blue"), Some("steelblue"))

  /** Color constant for <em>"tan"</em> (<code>0xffd2b48c</code>). */
  val Tan = PresetRGBAColor(0xffd2b48c, Some("tan"), Some("tan"))

  /** Color constant for <em>"teal"</em> (<code>0xff008080</code>). */
  val Teal = PresetRGBAColor(0xff008080, Some("teal"), Some("teal"))

  /** Color constant for <em>"thistle"</em> (<code>0xffd8bfd8</code>). */
  val Thistle = PresetRGBAColor(0xffd8bfd8, Some("thistle"), Some("thistle"))

  /** Color constant for <em>"tomato"</em> (<code>0xffff6347</code>). */
  val Tomato = PresetRGBAColor(0xffff6347, Some("tomato"), Some("tomato"))

  /** Color constant for <em>"turquoise"</em> (<code>0xff40e0d0</code>). */
  val Turquoise = PresetRGBAColor(0xff40e0d0, Some("turquoise"), Some("turquoise"))

  /** Color constant for <em>"violet"</em> (<code>0xffee82ee</code>). */
  val Violet = PresetRGBAColor(0xffee82ee, Some("violet"), Some("violet"))

  /** Color constant for <em>"wheat"</em> (<code>0xfff5deb3</code>). */
  val Wheat = PresetRGBAColor(0xfff5deb3, Some("wheat"), Some("wheat"))

  /** Color constant for <em>"white"</em> (<code>0xffffffff</code>). */
  val White = PresetRGBAColor(0xffffffff, Some("white"), Some("white"))

  /** Color constant for <em>"white smoke"</em> (<code>0xfff5f5f5</code>). */
  val WhiteSmoke = PresetRGBAColor(0xfff5f5f5, Some("white smoke"), Some("whitesmoke"))

  /** Color constant for <em>"yellow"</em> (<code>0xffffff00</code>). */
  val Yellow = PresetRGBAColor(0xffffff00, Some("yellow"), Some("yellow"))

  /** Color constant for <em>"yellow green"</em> (<code>0xff9acd32</code>). */
  val YellowGreen = PresetRGBAColor(0xff9acd32, Some("yellow green"), Some("yellowgreen"))


  /** Color constant for 10-percent black. */
  val Black10 = PresetRGBAColor(Black.tintByPercentage(90).toArgbInt, Some("black10"), None)

  /** Color constant for 20-percent black. */
  val Black20 = PresetRGBAColor(Black.tintByPercentage(80).toArgbInt, Some("black20"), None)

  /** Color constant for 30-percent black. */
  val Black30 = PresetRGBAColor(Black.tintByPercentage(70).toArgbInt, Some("black30"), None)

  /** Color constant for 40-percent black. */
  val Black40 = PresetRGBAColor(Black.tintByPercentage(60).toArgbInt, Some("black40"), None)

  /** Color constant for 50-percent black. */
  val Black50 = PresetRGBAColor(Black.tintByPercentage(50).toArgbInt, Some("black50"), None)

  /** Color constant for 60-percent black. */
  val Black60 = PresetRGBAColor(Black.tintByPercentage(40).toArgbInt, Some("black60"), None)

  /** Color constant for 70-percent black. */
  val Black70 = PresetRGBAColor(Black.tintByPercentage(30).toArgbInt, Some("black70"), None)

  /** Color constant for 80-percent black. */
  val Black80 = PresetRGBAColor(Black.tintByPercentage(20).toArgbInt, Some("black80"), None)

  /** Color constant for 90-percent black. */
  val Black90 = PresetRGBAColor(Black.tintByPercentage(10).toArgbInt, Some("black90"), None)


  /** Color constant for 10-percent white. */
  val White10 = PresetRGBAColor(White.shadeByPercentage(90).toArgbInt, Some("white10"), None)

  /** Color constant for 20-percent white. */
  val White20 = PresetRGBAColor(White.shadeByPercentage(80).toArgbInt, Some("white20"), None)

  /** Color constant for 30-percent white. */
  val White30 = PresetRGBAColor(White.shadeByPercentage(70).toArgbInt, Some("white30"), None)

  /** Color constant for 40-percent white. */
  val White40 = PresetRGBAColor(White.shadeByPercentage(60).toArgbInt, Some("white40"), None)

  /** Color constant for 50-percent white. */
  val White50 = PresetRGBAColor(White.shadeByPercentage(50).toArgbInt, Some("white50"), None)

  /** Color constant for 60-percent white. */
  val White60 = PresetRGBAColor(White.shadeByPercentage(40).toArgbInt, Some("white60"), None)

  /** Color constant for 70-percent white. */
  val White70 = PresetRGBAColor(White.shadeByPercentage(30).toArgbInt, Some("white70"), None)

  /** Color constant for 80-percent white. */
  val White80 = PresetRGBAColor(White.shadeByPercentage(20).toArgbInt, Some("white80"), None)

  /** Color constant for 90-percent white. */
  val White90 = PresetRGBAColor(White.shadeByPercentage(10).toArgbInt, Some("white90"), None)


  /**
   * All [[PresetRGBAColor]] definitions as a mapping from CSS
   * color names to the actual [[RGBAColor]] objects.
   */
  def PresetColors: Seq[PresetRGBAColor] = allPresetColors

}
