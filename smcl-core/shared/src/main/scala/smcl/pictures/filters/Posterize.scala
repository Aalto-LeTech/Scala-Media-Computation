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

package smcl.pictures.filters


import smcl.colors.rgb.ColorComponentTranslationTable
import smcl.infrastructure.{CommonValidators, InjectablesRegistry}
import smcl.pictures.PictureElement




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Posterize
    extends InjectablesRegistry {

  /** The CommonValidators instance to be used by this object. */
  private
  lazy val commonValidators: CommonValidators = {
    injectable(InjectablesRegistry.IIdCommonValidators).asInstanceOf[CommonValidators]
  }

  /**
   *
   *
   * @param strengthAsPercentage
   *
   * @return
   */
  @inline
  def apply(strengthAsPercentage: Int): Filter = {
    commonValidators.validatePercentage(strengthAsPercentage, Some("strength"))

    new Posterize(strengthAsPercentage)
  }

  /**
   *
   *
   * @param target
   * @param strengthAsPercentage
   *
   * @return
   */
  @inline
  def apply(
      target: PictureElement,
      strengthAsPercentage: Int): PictureElement = {

    val filter = apply(strengthAsPercentage)

    filter(target.toBitmap)
  }

}




/**
 *
 *
 * @param strengthAsPercentage
 *
 * @author Aleksi Lukkarinen
 */
class Posterize private(val strengthAsPercentage: Int)
    extends Filter {

  /** */
  private
  lazy val translationTable: ColorComponentTranslationTable =
    ColorComponentTranslationTable.forPosterization(strengthAsPercentage)

  /**
   *
   *
   * @param target
   *
   * @return
   */
  override
  def apply(target: PictureElement): PictureElement = {
    target.toBitmap.translateColorsWith(translationTable)
  }

}
