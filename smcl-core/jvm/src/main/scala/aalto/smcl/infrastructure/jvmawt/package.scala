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

package aalto.smcl.infrastructure


import java.awt.{Toolkit, Color => LowLevelColor}

import scala.language.implicitConversions

import aalto.smcl.colors.rgb.Color


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object jvmawt {

  /** */
  private[infrastructure]
  lazy val UIProvider: AWTSwingUIProvider = new AWTSwingUIProvider()

  /** */
  private[infrastructure]
  lazy val AWTToolkit: Toolkit = UIProvider.awtToolkit

  /**
   *
   */
  private[infrastructure]
  implicit def AWTColorWrapper(self: LowLevelColor): RichAWTColor =
    new RichAWTColor(self)

  /**
   *
   */
  private[infrastructure]
  implicit def ColorRicherWrapper(self: Color): RicherColor =
    new RicherColor(self)

}
