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

package smcl.colors.rgb


/**
 * This trait defines all preset colors available in SMCL. These color form a superset
 * of the colors defined in 5.7.2017 draft of the
 * <a href="https://www.w3.org/TR/2016/WD-css-color-4-20160705/">CSS Color Module Level 4</a>
 * standard.
 *
 * @author Aleksi Lukkarinen
 */
trait PresetColors {

  // Map for all preset colors; content is added by the PresetColor companion object.
  private[colors]
  var _allPresetColors: Map[String, PresetColor] = Map.empty


  /** Color constant for <em>"alice blue"</em> (<code>0xfff0f8ff</code>). */
  val AliceBlue = PresetColor(0xfff0f8ff, "alice blue", "aliceblue")

  /** Color constant for <em>"amethyst"</em> (<code>0xff9966cc</code>). */
  val Amethyst = PresetColor(0xff9966cc, "amethyst", "amethyst")

  /** Color constant for <em>"antique white"</em> (<code>0xfffaebd7</code>). */
  val AntiqueWhite = PresetColor(0xfffaebd7, "antique white", "antiquewhite")

  /** Color constant for <em>"aqua"</em> (<code>0xff00ffff</code>). */
  val Aqua = PresetColor(0xff00ffff, "aqua", "aqua")

  /** Color constant for <em>"aquamarine"</em> (<code>0xff7fffd4</code>). */
  val Aquamarine = PresetColor(0xff7fffd4, "aquamarine", "aquamarine")

  /** Color constant for <em>"azure"</em> (<code>0xfff0ffff</code>). */
  val Azure = PresetColor(0xfff0ffff, "azure", "azure")

  /** Color constant for <em>"beige"</em> (<code>0xfff5f5dc</code>). */
  val Beige = PresetColor(0xfff5f5dc, "beige", "beige")

  /** Color constant for <em>"bisque"</em> (<code>0xffffe4c4</code>). */
  val Bisque = PresetColor(0xffffe4c4, "bisque", "bisque")

  /** Color constant for <em>"black"</em> (<code>0xff000000</code>). */
  val Black = PresetColor(0xff000000, "black", "black")

  /** Color constant for <em>"blanched almond"</em> (<code>0xffffebcd</code>). */
  val BlanchedAlmond = PresetColor(0xffffebcd, "blanched almond", "blanchedalmond")

  /** Color constant for <em>"blue"</em> (<code>0xff0000ff</code>). */
  val Blue = PresetColor(0xff0000ff, "blue", "blue")

  /** Color constant for <em>"blue violet"</em> (<code>0xff8a2be2</code>). */
  val BlueViolet = PresetColor(0xff8a2be2, "blue violet", "blueviolet")

  /** Color constant for <em>"brown"</em> (<code>0xffa52a2a</code>). */
  val Brown = PresetColor(0xffa52a2a, "brown", "brown")

  /** Color constant for <em>"burly wood"</em> (<code>0xffdeb887</code>). */
  val BurlyWood = PresetColor(0xffdeb887, "burly wood", "burlywood")

  /** Color constant for <em>"cadet blue"</em> (<code>0xff5f9ea0</code>). */
  val CadetBlue = PresetColor(0xff5f9ea0, "cadet blue", "cadetblue")

  /** Color constant for <em>"chartreuse"</em> (<code>0xff7fff00</code>). */
  val ChartReuse = PresetColor(0xff7fff00, "chartreuse", "chartreuse")

  /** Color constant for <em>"chocolate"</em> (<code>0xffd2691e</code>). */
  val Chocolate = PresetColor(0xffd2691e, "chocolate", "chocolate")

  /** Color constant for <em>"coral"</em> (<code>0xffff7f50</code>). */
  val Coral = PresetColor(0xffff7f50, "coral", "coral")

  /** Color constant for <em>"cornflower blue"</em> (<code>0xff6495ed</code>). */
  val CornflowerBlue = PresetColor(0xff6495ed, "cornflower blue", "cornflowerblue")

  /** Color constant for <em>"cornsilk"</em> (<code>0xfffff8dc</code>). */
  val CornSilk = PresetColor(0xfffff8dc, "cornsilk", "cornsilk")

  /** Color constant for <em>"crimson"</em> (<code>0xffdc143c</code>). */
  val Crimson = PresetColor(0xffdc143c, "crimson", "crimson")

  /** Color constant for <em>"cyan"</em> (<code>0xff00ffff</code>). */
  val Cyan = PresetColor(0xff00ffff, "cyan", "cyan")

  /** Color constant for <em>"dark blue"</em> (<code>0xff00008b</code>). */
  val DarkBlue = PresetColor(0xff00008b, "dark blue", "darkblue")

  /** Color constant for <em>"dark cyan"</em> (<code>0xff008b8b</code>). */
  val DarkCyan = PresetColor(0xff008b8b, "dark cyan", "darkcyan")

  /** Color constant for <em>"dark golden rod"</em> (<code>0xffb8860b</code>). */
  val DarkGoldenRod = PresetColor(0xffb8860b, "dark golden rod", "darkgoldenrod")

  /** Color constant for <em>"dark gray"</em> (<code>0xffa9a9a9</code>). */
  val DarkGray = PresetColor(0xffa9a9a9, "dark gray", "darkgray")

  /** Color constant for <em>"dark green"</em> (<code>0xff006400</code>). */
  val DarkGreen = PresetColor(0xff006400, "dark green", "darkgreen")

  /** Color constant for <em>"dark grey"</em> (<code>0xffa9a9a9</code>). */
  val DarkGrey: Color = PresetColor(0xffa9a9a9, "dark grey", "darkgrey")

  /** Color constant for <em>"dark khaki"</em> (<code>0xffbdb76b</code>). */
  val DarkKhaki = PresetColor(0xffbdb76b, "dark khaki", "darkkhaki")

  /** Color constant for <em>"dark magenta"</em> (<code>0xff8b008b</code>). */
  val DarkMagenta = PresetColor(0xff8b008b, "dark magenta", "darkmagenta")

  /** Color constant for <em>"dark olive green"</em> (<code>0xff556b2f</code>). */
  val DarkOliveGreen = PresetColor(0xff556b2f, "dark olive green", "darkolivegreen")

  /** Color constant for <em>"dark orange"</em> (<code>0xffff8c00</code>). */
  val DarkOrange = PresetColor(0xffff8c00, "dark orange", "darkorange")

  /** Color constant for <em>"dark orchid"</em> (<code>0xff9932cc</code>). */
  val DarkOrchid = PresetColor(0xff9932cc, "dark orchid", "darkorchid")

  /** Color constant for <em>"dark red"</em> (<code>0xff8b0000</code>). */
  val DarkRed = PresetColor(0xff8b0000, "dark red", "darkred")

  /** Color constant for <em>"dark salmon"</em> (<code>0xffe9967a</code>). */
  val DarkSalmon = PresetColor(0xffe9967a, "dark salmon", "darksalmon")

  /** Color constant for <em>"dark sea green"</em> (<code>0xff8fbc8f</code>). */
  val DarkSeaGreen = PresetColor(0xff8fbc8f, "dark sea green", "darkseagreen")

  /** Color constant for <em>"dark slate blue"</em> (<code>0xff483d8b</code>). */
  val DarkSlateBlue = PresetColor(0xff483d8b, "dark slate blue", "darkslateblue")

  /** Color constant for <em>"dark slate gray"</em> (<code>0xff2f4f4f</code>). */
  val DarkSlateGray = PresetColor(0xff2f4f4f, "dark slate gray", "darkslategray")

  /** Color constant for <em>"dark slate grey"</em> (<code>0xff2f4f4f</code>). */
  val DarkSlateGrey = PresetColor(0xff2f4f4f, "dark slate grey", "darkslategrey")

  /** Color constant for <em>"dark turquoise"</em> (<code>0xff00ced1</code>). */
  val DarkTurquoise = PresetColor(0xff00ced1, "dark turquoise", "darkturquoise")

  /** Color constant for <em>"dark violet"</em> (<code>0xff9400d3</code>). */
  val DarkViolet = PresetColor(0xff9400d3, "dark violet", "darkviolet")

  /** Color constant for <em>"deep pink"</em> (<code>0xffff1493</code>). */
  val DeepPink = PresetColor(0xffff1493, "deep pink", "deeppink")

  /** Color constant for <em>"deep sky blue"</em> (<code>0xff00bfff</code>). */
  val DeepSkyBlue = PresetColor(0xff00bfff, "deep sky blue", "deepskyblue")

  /** Color constant for <em>"dim gray"</em> (<code>0xff696969</code>). */
  val DimGray = PresetColor(0xff696969, "dim gray", "dimgray")

  /** Color constant for <em>"dim grey"</em> (<code>0xff696969</code>). */
  val DimGrey = PresetColor(0xff696969, "dim grey", "dimgrey")

  /** Color constant for <em>"dodger blue"</em> (<code>0xff1e90ff</code>). */
  val DodgerBlue = PresetColor(0xff1e90ff, "dodger blue", "dodgerblue")

  /** Color constant for <em>"fire brick"</em> (<code>0xffb22222</code>). */
  val FireBrick = PresetColor(0xffb22222, "fire brick", "firebrick")

  /** Color constant for <em>"floral white"</em> (<code>0xfffffaf0</code>). */
  val FloralWhite = PresetColor(0xfffffaf0, "floral white", "floralwhite")

  /** Color constant for <em>"forest green"</em> (<code>0xff228b22</code>). */
  val ForestGreen = PresetColor(0xff228b22, "forest green", "forestgreen")

  /** Color constant for <em>"fuchsia"</em> (<code>0xffff00ff</code>). */
  val Fuchsia = PresetColor(0xffff00ff, "fuchsia", "fuchsia")

  /** Color constant for <em>"gainsboro"</em> (<code>0xffdcdcdc</code>). */
  val Gainsboro = PresetColor(0xffdcdcdc, "gainsboro", "gainsboro")

  /** Color constant for <em>"ghost white"</em> (<code>0xfff8f8ff</code>). */
  val GhostWhite = PresetColor(0xfff8f8ff, "ghost white", "ghostwhite")

  /** Color constant for <em>"gold"</em> (<code>0xffffd700</code>). */
  val Gold = PresetColor(0xffffd700, "gold", "gold")

  /** Color constant for <em>"golden rod"</em> (<code>0xffdaa520</code>). */
  val GoldenRod = PresetColor(0xffdaa520, "golden rod", "goldenrod")

  /** Color constant for <em>"gray"</em> (<code>0xff808080</code>). */
  val Gray = PresetColor(0xff808080, "gray", "gray")

  /** Color constant for <em>"green"</em> (<code>0xff008000</code>). */
  val Green = PresetColor(0xff008000, "green", "green")

  /** Color constant for <em>"green yellow"</em> (<code>0xffadff2f</code>). */
  val GreenYellow = PresetColor(0xffadff2f, "green yellow", "greenyellow")

  /** Color constant for <em>"grey"</em> (<code>0xff808080</code>). */
  val Grey = PresetColor(0xff808080, "grey", "grey")

  /** Color constant for <em>"honeydew"</em> (<code>0xfff0fff0</code>). */
  val Honeydew = PresetColor(0xfff0fff0, "honeydew", "honeydew")

  /** Color constant for <em>"hot pink"</em> (<code>0xffff69b4</code>). */
  val HotPink = PresetColor(0xffff69b4, "hot pink", "hotpink")

  /** Color constant for <em>"indian red"</em> (<code>0xffcd5c5c</code>). */
  val IndianRed = PresetColor(0xffcd5c5c, "indian red", "indianred")

  /** Color constant for <em>"indigo"</em> (<code>0xff4b0082</code>). */
  val Indigo = PresetColor(0xff4b0082, "indigo", "indigo")

  /** Color constant for <em>"ivory"</em> (<code>0xfffffff0</code>). */
  val Ivory = PresetColor(0xfffffff0, "ivory", "ivory")

  /** Color constant for <em>"khaki"</em> (<code>0xfff0e68c</code>). */
  val Khaki = PresetColor(0xfff0e68c, "khaki", "khaki")

  /** Color constant for <em>"lavender"</em> (<code>0xffe6e6fa</code>). */
  val Lavender = PresetColor(0xffe6e6fa, "lavender", "lavender")

  /** Color constant for <em>"lavender blush"</em> (<code>0xfffff0f5</code>). */
  val LavenderBlush = PresetColor(0xfffff0f5, "lavender blush", "lavenderblush")

  /** Color constant for <em>"lawn green"</em> (<code>0xff7cfc00</code>). */
  val LawnGreen = PresetColor(0xff7cfc00, "lawn green", "lawngreen")

  /** Color constant for <em>"lemon chiffon"</em> (<code>0xfffffacd</code>). */
  val LemonChiffon = PresetColor(0xfffffacd, "lemon chiffon", "lemonchiffon")

  /** Color constant for <em>"light blue"</em> (<code>0xffadd8e6</code>). */
  val LightBlue = PresetColor(0xffadd8e6, "light blue", "lightblue")

  /** Color constant for <em>"light coral"</em> (<code>0xfff08080</code>). */
  val LightCoral = PresetColor(0xfff08080, "light coral", "lightcoral")

  /** Color constant for <em>"light cyan"</em> (<code>0xffe0ffff</code>). */
  val LightCyan = PresetColor(0xffe0ffff, "light cyan", "lightcyan")

  /** Color constant for "light golden rod yellow" (0xfffafad2). */
  val LightGoldenRodYellow =
    PresetColor(0xfffafad2, "light golden rod yellow", "lightgoldenrodyellow")

  /** Color constant for <em>"light gray"</em> (<code>0xffd3d3d3</code>). */
  val LightGray = PresetColor(0xffd3d3d3, "light gray", "lightgray")

  /** Color constant for <em>"light green"</em> (<code>0xff90ee90</code>). */
  val LightGreen = PresetColor(0xff90ee90, "light green", "lightgreen")

  /** Color constant for <em>"light grey"</em> (<code>0xffd3d3d3</code>). */
  val LightGrey = PresetColor(0xffd3d3d3, "light grey", "lightgrey")

  /** Color constant for <em>"light pink"</em> (<code>0xffffb6c1</code>). */
  val LightPink = PresetColor(0xffffb6c1, "light pink", "lightpink")

  /** Color constant for <em>"light salmon"</em> (<code>0xffffa07a</code>). */
  val LightSalmon = PresetColor(0xffffa07a, "light salmon", "lightsalmon")

  /** Color constant for <em>"light sea green"</em> (<code>0xff20b2aa</code>). */
  val LightSeaGreen = PresetColor(0xff20b2aa, "light sea green", "lightseagreen")

  /** Color constant for <em>"light sky blue"</em> (<code>0xff87cefa</code>). */
  val LightSkyBlue = PresetColor(0xff87cefa, "light sky blue", "lightskyblue")

  /** Color constant for <em>"light slate gray"</em> (<code>0xff778899</code>). */
  val LightSlateGray = PresetColor(0xff778899, "light slate gray", "lightslategray")

  /** Color constant for <em>"light slate grey"</em> (<code>0xff778899</code>). */
  val LightSlateGrey = PresetColor(0xff778899, "light slate grey", "lightslategrey")

  /** Color constant for <em>"light steel blue"</em> (<code>0xffb0c4de</code>). */
  val LightSteelBlue = PresetColor(0xffb0c4de, "light steel blue", "lightsteelblue")

  /** Color constant for <em>"light yellow"</em> (<code>0xffffffe0</code>). */
  val LightYellow = PresetColor(0xffffffe0, "light yellow", "lightyellow")

  /** Color constant for <em>"lime"</em> (<code>0xff00ff00</code>). */
  val Lime = PresetColor(0xff00ff00, "lime", "lime")

  /** Color constant for <em>"lime green"</em> (<code>0xff32cd32</code>). */
  val LimeGreen = PresetColor(0xff32cd32, "lime green", "limegreen")

  /** Color constant for <em>"linen"</em> (<code>0xfffaf0e6</code>). */
  val Linen = PresetColor(0xfffaf0e6, "linen", "linen")

  /** Color constant for <em>"magenta"</em> (<code>0xffff00ff</code>). */
  val Magenta = PresetColor(0xffff00ff, "magenta", "magenta")

  /** Color constant for <em>"maroon"</em> (<code>0xff800000</code>). */
  val Maroon = PresetColor(0xff800000, "maroon", "maroon")

  /** Color constant for <em>"medium aquamarine"</em> (<code>0xff66cdaa</code>). */
  val MediumAquamarine = PresetColor(0xff66cdaa, "medium aquamarine", "mediumaquamarine")

  /** Color constant for <em>"medium blue"</em> (<code>0xff0000cd</code>). */
  val MediumBlue = PresetColor(0xff0000cd, "medium blue", "mediumblue")

  /** Color constant for <em>"medium orchid"</em> (<code>0xffba55d3</code>). */
  val MediumOrchid = PresetColor(0xffba55d3, "medium orchid", "mediumorchid")

  /** Color constant for <em>"medium purple"</em> (<code>0xff9370db</code>). */
  val MediumPurple = PresetColor(0xff9370db, "medium purple", "mediumpurple")

  /** Color constant for <em>"medium sea green"</em> (<code>0xff3cb371</code>). */
  val MediumSeaGreen = PresetColor(0xff3cb371, "medium sea green", "mediumseagreen")

  /** Color constant for <em>"medium slate blue"</em> (<code>0xff7b68ee</code>). */
  val MediumSlateBlue = PresetColor(0xff7b68ee, "medium slate blue", "mediumslateblue")

  /** Color constant for <em>"medium spring green"</em> (<code>0xff00fa9a</code>). */
  val MediumSpringGreen = PresetColor(0xff00fa9a, "medium spring green", "mediumspringbreen")

  /** Color constant for <em>"medium turquoise"</em> (<code>0xff48d1cc</code>). */
  val MediumTurquoise = PresetColor(0xff48d1cc, "medium turquoise", "mediumturquoise")

  /** Color constant for <em>"medium violet red"</em> (<code>0xffc71585</code>). */
  val MediumVioletRed = PresetColor(0xffc71585, "medium violet red", "mediumvioletred")

  /** Color constant for <em>"midnight blue"</em> (<code>0xff191970</code>). */
  val MidnightBlue = PresetColor(0xff191970, "midnight blue", "midnightblue")

  /** Color constant for <em>"mint cream"</em> (<code>0xfff5fffa</code>). */
  val MintCream = PresetColor(0xfff5fffa, "mint cream", "mintcream")

  /** Color constant for <em>"misty rose"</em> (<code>0xffffe4e1</code>). */
  val MistyRose = PresetColor(0xffffe4e1, "misty rose", "mistyrose")

  /** Color constant for <em>"moccasin"</em> (<code>0xffffe4b5</code>). */
  val Moccasin = PresetColor(0xffffe4b5, "moccasin", "moccasin")

  /** Color constant for <em>"navajo white"</em> (<code>0xffffdead</code>). */
  val NavajoWhite = PresetColor(0xffffdead, "navajo white", "navajowhite")

  /** Color constant for <em>"navy"</em> (<code>0xff000080</code>). */
  val Navy = PresetColor(0xff000080, "navy", "navy")

  /** Color constant for <em>"old lace"</em> (<code>0xfffdf5e6</code>). */
  val OldLace = PresetColor(0xfffdf5e6, "old lace", "oldlace")

  /** Color constant for <em>"olive"</em> (<code>0xff808000</code>). */
  val Olive = PresetColor(0xff808000, "olive", "olive")

  /** Color constant for <em>"olive drab"</em> (<code>0xff6b8e23</code>). */
  val OliveDrab = PresetColor(0xff6b8e23, "olive drab", "olivedrab")

  /** Color constant for <em>"orange"</em> (<code>0xffffa500</code>). */
  val Orange = PresetColor(0xffffa500, "orange", "orange")

  /** Color constant for <em>"orange red"</em> (<code>0xffff4500</code>). */
  val OrangeRed = PresetColor(0xffff4500, "orange red", "orangered")

  /** Color constant for <em>"orchid"</em> (<code>0xffda70d6</code>). */
  val Orchid = PresetColor(0xffda70d6, "orchid", "orchid")

  /** Color constant for <em>"pale golden rod"</em> (<code>0xffeee8aa</code>). */
  val PaleGoldenRod = PresetColor(0xffeee8aa, "pale golden rod", "palegoldenrod")

  /** Color constant for <em>"pale green"</em> (<code>0xff98fb98</code>). */
  val PaleGreen = PresetColor(0xff98fb98, "pale green", "palegreen")

  /** Color constant for <em>"pale turquoise"</em> (<code>0xffafeeee</code>). */
  val PaleTurquoise = PresetColor(0xffafeeee, "pale turquoise", "paleturquoise")

  /** Color constant for <em>"pale violet red"</em> (<code>0xffdb7093</code>). */
  val PaleVioletRed = PresetColor(0xffdb7093, "pale violet red", "palevioletred")

  /** Color constant for <em>"papaya whip"</em> (<code>0xffffefd5</code>). */
  val PapayaWhip = PresetColor(0xffffefd5, "papaya whip", "papayawhip")

  /** Color constant for <em>"peach puff"</em> (<code>0xffffdab9</code>). */
  val PeachPuff = PresetColor(0xffffdab9, "peach puff", "peachpuff")

  /** Color constant for <em>"peru"</em> (<code>0xffcd853f</code>). */
  val Peru = PresetColor(0xffcd853f, "peru", "peru")

  /** Color constant for <em>"pink"</em> (<code>0xffffc0cb</code>). */
  val Pink = PresetColor(0xffffc0cb, "pink", "pink")

  /** Color constant for <em>"plum"</em> (<code>0xffdda0dd</code>). */
  val Plum = PresetColor(0xffdda0dd, "plum", "plum")

  /** Color constant for <em>"powder blue"</em> (<code>0xffb0e0e6</code>). */
  val PowderBlue = PresetColor(0xffb0e0e6, "powder blue", "powderblue")

  /** Color constant for <em>"purple"</em> (<code>0xff800080</code>). */
  val Purple = PresetColor(0xff800080, "purple", "purple")

  /** Color constant for <em>"red"</em> (<code>0xffff0000</code>). */
  val Red = PresetColor(0xffff0000, "red", "red")

  /** Color constant for <em>"rosy brown"</em> (<code>0xffbc8f8f</code>). */
  val RosyBrown = PresetColor(0xffbc8f8f, "rosy brown", "rosybrown")

  /** Color constant for <em>"royal blue"</em> (<code>0xff4169e1</code>). */
  val RoyalBlue = PresetColor(0xff4169e1, "royal blue", "royalblue")

  /** Color constant for <em>"saddle brown"</em> (<code>0xff8b4513</code>). */
  val SaddleBrown = PresetColor(0xff8b4513, "saddle brown", "saddlebrown")

  /** Color constant for <em>"salmon"</em> (<code>0xfffa8072</code>). */
  val Salmon = PresetColor(0xfffa8072, "salmon", "salmon")

  /** Color constant for <em>"sandy brown"</em> (<code>0xfff4a460</code>). */
  val SandyBrown = PresetColor(0xfff4a460, "sandy brown", "sandybrown")

  /** Color constant for <em>"sea green"</em> (<code>0xff2e8b57</code>). */
  val SeaGreen = PresetColor(0xff2e8b57, "sea green", "seagreen")

  /** Color constant for <em>"seashell"</em> (<code>0xfffff5ee</code>). */
  val SeaShell = PresetColor(0xfffff5ee, "seashell", "seashell")

  /** Color constant for <em>"sienna"</em> (<code>0xffa0522d</code>). */
  val Sienna = PresetColor(0xffa0522d, "sienna", "sienna")

  /** Color constant for <em>"silver"</em> (<code>0xffc0c0c0</code>). */
  val Silver = PresetColor(0xffc0c0c0, "silver", "silver")

  /** Color constant for <em>"sky blue"</em> (<code>0xff87ceeb</code>). */
  val SkyBlue = PresetColor(0xff87ceeb, "sky blue", "skyblue")

  /** Color constant for <em>"slate blue"</em> (<code>0xff6a5acd</code>). */
  val SlateBlue = PresetColor(0xff6a5acd, "slate blue", "slateblue")

  /** Color constant for <em>"slate gray"</em> (<code>0xff708090</code>). */
  val SlateGray = PresetColor(0xff708090, "slate gray", "slategray")

  /** Color constant for <em>"slate grey"</em> (<code>0xff708090</code>). */
  val SlateGrey = PresetColor(0xff708090, "slate grey", "slategrey")

  /** Color constant for <em>"snow"</em> (<code>0xfffffafa</code>). */
  val Snow = PresetColor(0xfffffafa, "snow", "snow")

  /** Color constant for <em>"spring green"</em> (<code>0xff00ff7f</code>). */
  val SpringGreen = PresetColor(0xff00ff7f, "spring green", "springgreen")

  /** Color constant for <em>"steel blue"</em> (<code>0xff4682b4</code>). */
  val SteelBlue = PresetColor(0xff4682b4, "steel blue", "steelblue")

  /** Color constant for <em>"tan"</em> (<code>0xffd2b48c</code>). */
  val Tan = PresetColor(0xffd2b48c, "tan", "tan")

  /** Color constant for <em>"teal"</em> (<code>0xff008080</code>). */
  val Teal = PresetColor(0xff008080, "teal", "teal")

  /** Color constant for <em>"thistle"</em> (<code>0xffd8bfd8</code>). */
  val Thistle = PresetColor(0xffd8bfd8, "thistle", "thistle")

  /** Color constant for <em>"tomato"</em> (<code>0xffff6347</code>). */
  val Tomato = PresetColor(0xffff6347, "tomato", "tomato")

  /** Color constant for <em>"turquoise"</em> (<code>0xff40e0d0</code>). */
  val Turquoise = PresetColor(0xff40e0d0, "turquoise", "turquoise")

  /** Color constant for <em>"violet"</em> (<code>0xffee82ee</code>). */
  val Violet = PresetColor(0xffee82ee, "violet", "violet")

  /** Color constant for <em>"wheat"</em> (<code>0xfff5deb3</code>). */
  val Wheat = PresetColor(0xfff5deb3, "wheat", "wheat")

  /** Color constant for <em>"white"</em> (<code>0xffffffff</code>). */
  val White = PresetColor(0xffffffff, "white", "white")

  /** Color constant for <em>"white smoke"</em> (<code>0xfff5f5f5</code>). */
  val WhiteSmoke = PresetColor(0xfff5f5f5, "white smoke", "whitesmoke")

  /** Color constant for <em>"yellow"</em> (<code>0xffffff00</code>). */
  val Yellow = PresetColor(0xffffff00, "yellow", "yellow")

  /** Color constant for <em>"yellow green"</em> (<code>0xff9acd32</code>). */
  val YellowGreen = PresetColor(0xff9acd32, "yellow green", "yellowgreen")


  /** Color constant for <em>"African green"</em> (RGB approximation: 49, 148, 0). */
  val AfricanGreen = PresetColor(Color(49, 148, 0, 255), "African green")

  /** Color constant for <em>"African red"</em> (RGB approximation: 149, 32, 56). */
  val AfricanRed = PresetColor(Color(149, 32, 56, 255), "African red")

  /** Color constant for <em>"African yellow"</em> (RGB approximation: 247, 198, 8). */
  val AfricanYellow = PresetColor(Color(247, 198, 8, 255), "African yellow")

  /** Color constant for <em>"American blue"</em> (RGB approximation: 60, 59, 110). */
  val AmericanBlue = PresetColor(Color(60, 59, 110, 255), "American blue")

  /** Color constant for <em>"American red"</em> (RGB approximation: 178, 34, 52). */
  val AmericanRed = PresetColor(Color(178, 34, 52, 255), "American red")

  /** Color constant for <em>"Australian blue"</em> (RGB approximation: 0, 0, 139). */
  val AustralianBlue = PresetColor(Color(0, 0, 139, 255), "Australian blue")

  /** Color constant for <em>"Australian red"</em> (RGB approximation: 255, 0, 0). */
  val AustralianRed = PresetColor(Color(255, 0, 0, 255), "Australian red")

  /** Color constant for <em>"Austrian red"</em> (RGB approximation: 237, 41, 57). */
  val AustrianRed = PresetColor(Color(237, 41, 57, 255), "Austrian red")

  /** Color constant for <em>"Belgian red"</em> (RGB approximation: 237, 41, 57). */
  val BelgianRed = PresetColor(Color(237, 41, 57, 255), "Belgian red")

  /** Color constant for <em>"Belgian yellow"</em> (RGB approximation: 250, 224, 66). */
  val BelgianYellow = PresetColor(Color(250, 224, 66, 255), "Belgian yellow")

  /** Color constant for <em>"Brazilian blue"</em> (RGB approximation: 0, 39, 118). */
  val BrazilianBlue = PresetColor(Color(0, 39, 118, 255), "Brazilian blue")

  /** Color constant for <em>"Brazilian green"</em> (RGB approximation: 0, 155, 58). */
  val BrazilianGreen = PresetColor(Color(0, 155, 58, 255), "Brazilian green")

  /** Color constant for <em>"British blue"</em> (RGB approximation: 0, 36, 125). */
  val BritishBlue = PresetColor(Color(0, 36, 125, 255), "British blue")

  /** Color constant for <em>"British red"</em> (RGB approximation: 207, 20, 43). */
  val BritishRed = PresetColor(Color(207, 20, 43, 255), "British red")

  /** Color constant for <em>"Brazilian yellow"</em> (RGB approximation: 254, 223, 0). */
  val BrazilianYellow = PresetColor(Color(254, 223, 0, 255), "Brazilian yellow")

  /** Color constant for <em>"Canadian red"</em> (RGB approximation: 255, 0, 0). */
  val CanadianRed = PresetColor(Color(255, 0, 0, 255), "Canadian red")

  /** Color constant for <em>"Chinese blue"</em> (RGB approximation: 0, 0, 149). */
  val ChineseBlue = PresetColor(Color(0, 0, 149, 255), "Chinese blue")

  /** Color constant for <em>"Chinese red"</em> (RGB approximation: 254, 0, 0). */
  val ChineseRed = PresetColor(Color(254, 0, 0, 255), "Chinese red")

  /** Color constant for <em>"Czech blue"</em> (RGB approximation: 17, 69, 126). */
  val CzechBlue = PresetColor(Color(17, 69, 126, 255), "Czech blue")

  /** Color constant for <em>"Czech red"</em> (RGB approximation: 215, 20, 26). */
  val CzechRed = PresetColor(Color(215, 20, 26, 255), "Czech red")

  /** Color constant for <em>"European blue"</em> (RGB approximation: 0, 51, 153). */
  val EuropeanBlue = PresetColor(Color(0, 51, 153, 255), "European blue")

  /** Color constant for <em>"European yellow"</em> (RGB approximation: 255, 204, 0). */
  val EuropeanYellow = PresetColor(Color(255, 204, 0, 255), "European yellow")

  /** Color constant for <em>"Danish red"</em> (RGB approximation: 198, 12, 48). */
  val DanishRed = PresetColor(Color(198, 12, 48, 255), "Danish red")

  /** Color constant for <em>"Finnish blue"</em> (RGB approximation: 0, 63, 135). */
  val FinnishBlue = PresetColor(Color(0, 63, 135, 255), "Finnish blue")

  /** Color constant for <em>"French blue"</em> (RGB approximation: 0, 35, 149). */
  val FrenchBlue = PresetColor(Color(0, 35, 149, 255), "French blue")

  /** Color constant for <em>"French red"</em> (RGB approximation: 237, 41, 57). */
  val FrenchRed = PresetColor(Color(237, 41, 57, 255), "French red")

  /** Color constant for <em>"German red"</em> (RGB approximation: 221, 0, 0). */
  val GermanRed = PresetColor(Color(221, 0, 0, 255), "German red")

  /** Color constant for <em>"German yellow"</em> (RGB approximation: 255, 206, 0). */
  val GermanYellow = PresetColor(Color(255, 206, 0, 255), "German yellow")

  /** Color constant for <em>"Greek blue"</em> (RGB approximation: 13, 94, 175). */
  val GreekBlue = PresetColor(Color(13, 94, 175, 255), "Greek blue")

  /** Color constant for <em>"Greenlandic red"</em> (RGB approximation: 192, 12, 48). */
  val GreenlandicRed = PresetColor(Color(192, 12, 48, 255), "Greenlandic red")

  /** Color constant for <em>"Hungarian green"</em> (RGB approximation: 67, 111, 77). */
  val HungarianGreen = PresetColor(Color(67, 111, 77, 255), "Hungarian green")

  /** Color constant for <em>"Hungarian red"</em> (RGB approximation: 205, 42, 62). */
  val HungarianRed = PresetColor(Color(205, 42, 62, 255), "Hungarian red")

  /** Color constant for <em>"Icelandic blue"</em> (RGB approximation: 0, 56, 151). */
  val IcelandicBlue = PresetColor(Color(0, 56, 151, 255), "Icelandic blue")

  /** Color constant for <em>"Icelandic red"</em> (RGB approximation: 215, 40, 40). */
  val IcelandicRed = PresetColor(Color(215, 40, 40, 255), "Icelandic red")

  /** Color constant for <em>"Irish green"</em> (RGB approximation: 22, 155, 98). */
  val IrishGreen = PresetColor(Color(22, 155, 98, 255), "Irish green")

  /** Color constant for <em>"Irish orange"</em> (RGB approximation: 255, 136, 62). */
  val IrishOrange = PresetColor(Color(255, 136, 62, 255), "Irish orange")

  /** Color constant for <em>"Italian green"</em> (RGB approximation: 0, 146, 70). */
  val ItalianGreen = PresetColor(Color(0, 146, 70, 255), "Italian green")

  /** Color constant for <em>"Italian red"</em> (RGB approximation: 206, 43, 55). */
  val ItalianRed = PresetColor(Color(206, 43, 55, 255), "Italian red")

  /** Color constant for <em>"Japanese red"</em> (RGB approximation: 188, 0, 45). */
  val JapaneseRed = PresetColor(Color(188, 0, 45, 255), "Japanese red")

  /** Color constant for <em>"Norwegian blue"</em> (RGB approximation: 0, 40, 104). */
  val NorwegianBlue = PresetColor(Color(0, 40, 104, 255), "Norwegian blue")

  /** Color constant for <em>"Norwegian red"</em> (RGB approximation: 239, 43, 45). */
  val NorwegianRed = PresetColor(Color(239, 43, 45, 255), "Norwegian red")

  /** Color constant for <em>"Romanian blue"</em> (RGB approximation: 0, 43, 127). */
  val RomanianBlue = PresetColor(Color(0, 43, 127, 255), "Romanian blue")

  /** Color constant for <em>"Romanian red"</em> (RGB approximation: 206, 17, 38). */
  val RomanianRed = PresetColor(Color(206, 17, 38, 255), "Romanian red")

  /** Color constant for <em>"Romanian yellow"</em> (RGB approximation: 252, 209, 22). */
  val RomanianYellow = PresetColor(Color(252, 209, 22, 255), "Romanian yellow")

  /** Color constant for <em>"Russian blue"</em> (RGB approximation: 0, 57, 166). */
  val RussianBlue = PresetColor(Color(0, 57, 166, 255), "Russian blue")

  /** Color constant for <em>"Russian red"</em> (RGB approximation: 213, 43, 30). */
  val RussianRed = PresetColor(Color(213, 43, 30, 255), "Russian red")

  /** Color constant for <em>"Somali blue"</em> (RGB approximation: 65, 137, 221). */
  val SomaliBlue = PresetColor(Color(65, 137, 221, 255), "Somali blue")

  /** Color constant for <em>"Swedish blue"</em> (RGB approximation: 0, 107, 168). */
  val SwedishBlue = PresetColor(Color(0, 107, 168, 255), "Swedish blue")

  /** Color constant for <em>"Swedish yellow"</em> (RGB approximation: 254, 205, 1). */
  val SwedishYellow = PresetColor(Color(254, 205, 1, 255), "Swedish yellow")

  /** Color constant for <em>"Vietnamese red"</em> (RGB approximation: 218, 37, 29). */
  val VietnameseRed = PresetColor(Color(218, 37, 29, 255), "Vietnamese red")

  /** Color constant for <em>"Vietnamese yellow"</em> (RGB approximation: 255, 255, 0). */
  val VietnameseYellow = PresetColor(Color(255, 255, 0, 255), "Vietnamese yellow")


  /** Color constant for 10-percent black. */
  val Black10 = PresetColor(Black.tintByPercentage(90), "black10")

  /** Color constant for 20-percent black. */
  val Black20 = PresetColor(Black.tintByPercentage(80), "black20")

  /** Color constant for 30-percent black. */
  val Black30 = PresetColor(Black.tintByPercentage(70), "black30")

  /** Color constant for 40-percent black. */
  val Black40 = PresetColor(Black.tintByPercentage(60), "black40")

  /** Color constant for 50-percent black. */
  val Black50 = PresetColor(Black.tintByPercentage(50), "black50")

  /** Color constant for 60-percent black. */
  val Black60 = PresetColor(Black.tintByPercentage(40), "black60")

  /** Color constant for 70-percent black. */
  val Black70 = PresetColor(Black.tintByPercentage(30), "black70")

  /** Color constant for 80-percent black. */
  val Black80 = PresetColor(Black.tintByPercentage(20), "black80")

  /** Color constant for 90-percent black. */
  val Black90 = PresetColor(Black.tintByPercentage(10), "black90")


  /** Color constant for 10-percent white. */
  val White10 = PresetColor(White.shadeByPercentage(90), "white10")

  /** Color constant for 20-percent white. */
  val White20 = PresetColor(White.shadeByPercentage(80), "white20")

  /** Color constant for 30-percent white. */
  val White30 = PresetColor(White.shadeByPercentage(70), "white30")

  /** Color constant for 40-percent white. */
  val White40 = PresetColor(White.shadeByPercentage(60), "white40")

  /** Color constant for 50-percent white. */
  val White50 = PresetColor(White.shadeByPercentage(50), "white50")

  /** Color constant for 60-percent white. */
  val White60 = PresetColor(White.shadeByPercentage(40), "white60")

  /** Color constant for 70-percent white. */
  val White70 = PresetColor(White.shadeByPercentage(30), "white70")

  /** Color constant for 80-percent white. */
  val White80 = PresetColor(White.shadeByPercentage(20), "white80")

  /** Color constant for 90-percent white. */
  val White90 = PresetColor(White.shadeByPercentage(10), "white90")


  /** Color constant for transparent <em>"white"</em>. */
  val Transparent = PresetColor(White.withFullTransparency, "transparent")


  /**
   * All preset color definitions as a mapping from
   * color names to the corresponding [[PresetColor]] objects.
   */
  def PresetColors: Map[String, PresetColor] = _allPresetColors

}
