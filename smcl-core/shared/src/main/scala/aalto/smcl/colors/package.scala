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

package aalto.smcl


import scala.language.implicitConversions


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object colors
    extends PresetColors with ColorOperationsAPI {

  /** Color component value representing maximal opacity. */
  val FullyOpaque: Int = ColorValidator.MaximumRGBAOpacity

  /** Color component value representing minimal opacity. */
  val FullyTransparent: Int = ColorValidator.MinimumRGBAOpacity


  /** Application of the RichARGBInt class. */
  implicit def ARGBIntWrapper(self: Int): RichARGBInt = new RichARGBInt(self)

  /** Application of the RichRGBAColor class. */
  implicit def RGBAColorWrapper(self: RGBAColor): RichRGBAColor = RichRGBAColor(self)

}
