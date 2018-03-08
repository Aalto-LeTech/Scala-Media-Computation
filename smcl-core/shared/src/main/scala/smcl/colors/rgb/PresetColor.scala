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


import smcl.colors.{ColorValidator, rgb}
import smcl.infrastructure.{CommonValidators, InjectablesRegistry}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object PresetColor
    extends InjectablesRegistry {

  /** The CommonValidators instance to be used by this object. */
  private
  lazy val commonValidators: CommonValidators = {
    injectable(InjectablesRegistry.IIdCommonValidators).asInstanceOf[CommonValidators]
  }

  /** The ColorValidator instance to be used by this object. */
  private
  lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /**
   *
   *
   * @param rgbaInt
   * @param canonicalName
   * @param cssName
   *
   * @return
   */
  def apply(
      rgbaInt: Int,
      canonicalName: String,
      cssName: String): PresetColor = {

    val color = new PresetColor(
      rgbaInt,
      Option(canonicalName),
      Option(cssName),
      commonValidators,
      colorValidator)

    rgb._allPresetColors = _allPresetColors + (color.presetMapKey -> color)

    color
  }

  /**
   *
   *
   * @param color
   * @param canonicalName
   * @param cssName
   *
   * @return
   */
  def apply(
      color: Color,
      canonicalName: String,
      cssName: String): PresetColor = {

    apply(color.toARGBInt, canonicalName, cssName)
  }

  /**
   *
   *
   * @param color
   * @param canonicalName
   *
   * @return
   */
  def apply(
      color: Color,
      canonicalName: String): PresetColor = {

    apply(color, canonicalName, null)
  }

}




/**
 * A preset RGBA color.
 *
 * @param argbInt
 * @param canonicalName
 * @param cssName
 * @param commonValidators
 * @param colorValidator
 */
class PresetColor private(
    argbInt: Int,
    override val canonicalName: Option[String],
    override val cssName: Option[String],
    private val commonValidators: CommonValidators,
    private val colorValidator: ColorValidator) extends {

  /** Returns `true` if this [[PresetColor]] is provided by SMCL, otherwise `false`. */
  override val isPreset: Boolean = true

} with
    Color(
      redComponentOf(argbInt),
      greenComponentOf(argbInt),
      blueComponentOf(argbInt),
      opacityComponentOf(argbInt),
      canonicalName,
      commonValidators,
      colorValidator)
    with Immutable {

  /**
   *
   */
  private
  val tidiedCSSName: Option[String] = {
    val trimmedLowerCase = cssName map (_.trim.toLowerCase)

    if (trimmedLowerCase.nonEmpty && trimmedLowerCase.get.nonEmpty)
      trimmedLowerCase
    else
      None
  }

  /** */
  private[colors]
  val presetMapKey = tidiedCSSName getOrElse canonicalName.get

  /**
   *
   *
   * @return
   */
  override
  def toString: String =
    if (canonicalName.isDefined)
      canonicalName.get
    else
      super.toString

}
