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

package smcl.infrastructure.jvmawt


import java.awt.{Color => LowLevelColor}

import smcl.colors._
import smcl.colors.rgb.Color




/**
 *
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 */
class RichColor(val self: Color) {

  /**
   * This [[Color]] as a `java.awt.Color`.
   *
   * @return
   */
  final
  def toAWTColor: LowLevelColor = AWTColorAdapter.awtColorFrom(self)

  /**
   * This [[Color]] as a `java.awt.Color` with full opacity.
   *
   * @return
   */
  final
  def toOpaqueAWTColor: LowLevelColor =
    AWTColorAdapter.awtColorFrom(self.withFullOpacity)

}
