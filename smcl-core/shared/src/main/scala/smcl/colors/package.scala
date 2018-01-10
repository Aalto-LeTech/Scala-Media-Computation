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

package smcl


import scala.language.implicitConversions


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object colors
    extends rgb.PresetColors
        with ColorOperationsAPI {

  /** A type alias for RGB color class. */
  type Color = rgb.Color

  /** A companion object alias for RGB color. */
  val Color: rgb.Color.type = rgb.Color

  /** An alias for RGB color components object. */
  val ColorComponents: rgb.ColorComponents.type = rgb.ColorComponents

  /** A type alias for RGB color component translation table class. */
  type ColorComponentTranslationTable =
    rgb.ColorComponentTranslationTable

  /** A companion object alias for RGB color component translation table. */
  val ColorComponentTranslationTable: rgb.ColorComponentTranslationTable.type =
    rgb.ColorComponentTranslationTable

  /** A type alias for RGB color translator trait. */
  type ColorTranslator = rgb.ColorTranslator

  /** An alias for RGB sample bands object. */
  val SampleBands: rgb.SampleBands.type = rgb.SampleBands

  /** An alias for the RichARGBInt application. */
  implicit def ARGBIntWrapper(self: Int): rgb.RichARGBInt =
    rgb.ARGBIntWrapper(self)

  /** An alias for the RichRGBAColor application. */
  implicit def ColorWrapper(self: Color): rgb.RichColor =
    rgb.ColorWrapper(self)

}
