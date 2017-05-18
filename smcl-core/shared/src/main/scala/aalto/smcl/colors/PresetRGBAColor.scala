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
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object PresetRGBAColor {

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   * @param name
   * @param cssName
   *
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, opacity: Int,
      name: Option[String] = None, cssName: Option[String] = None): RGBAColor = {

    ColorValidator.validateRgbaColor(red, green, blue, opacity)

    val validatedName = ColorValidator.validateColorNameOption(name)
    val validatedCssName = ColorValidator.validateColorNameOption(cssName)

    require(validatedName.isDefined,
      "PresetColor instances must be given unique names (was None).")

    new PresetRGBAColor(red, green, blue, opacity, validatedName, validatedCssName)
  }

  /**
   *
   *
   * @param argbInt
   * @param name
   * @param cssName
   *
   * @return
   */
  def apply(argbInt: Int, name: Option[String], cssName: Option[String]): RGBAColor =
    PresetRGBAColor(
      redComponentOf(argbInt),
      greenComponentOf(argbInt),
      blueComponentOf(argbInt),
      opacityComponentOf(argbInt),
      name,
      cssName)

  /**
   *
   *
   * @param argbInt
   * @param name
   *
   * @return
   */
  def apply(argbInt: Int, name: Option[String]): RGBAColor = apply(argbInt, name, None)

}




/**
 *
 *
 * @param red
 * @param green
 * @param blue
 * @param opacity
 * @param name
 * @param cssName
 *
 * @author Aleksi Lukkarinen
 */
class PresetRGBAColor private[colors](
    override val red: Int,
    override val green: Int,
    override val blue: Int,
    override val opacity: Int,
    override val name: Option[String] = None,
    override val cssName: Option[String] = None) extends {

  /** Returns `true` if this [[PresetRGBAColor]] is provided by SMCL, otherwise `false`. */
  override val isPreset: Boolean = true

} with RGBAColor(red, green, blue, opacity, name) with Immutable {

  /**
   * Returns a string representation of this [[PresetRGBAColor]].
   */
  override def toString: String =
    s"""${super.toString}
       |CSS name: ${cssName getOrElse StrEmpty}
       |This is a preset color.
    """.stripMargin

}
