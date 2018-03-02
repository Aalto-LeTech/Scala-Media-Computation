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
class RichAWTColor(val self: LowLevelColor) {

  /**
   * This `java.awt.Color` as a [[Color]].
   *
   * @return
   */
  final
  def toSMCLColor: Color = AWTColorAdapter.smclColorFrom(self)

  /**
   * This `java.awt.Color` as a [[Color]] with full opacity.
   *
   * @return
   */
  final
  def toOpaqueSMCLColor: Color =
    AWTColorAdapter.smclColorFrom(self).withFullOpacity

}
