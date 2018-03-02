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

package smcl.infrastructure


import java.awt.image.BufferedImage
import java.awt.{Toolkit, Color => LowLevelColor}

import scala.language.implicitConversions

import smcl.colors.rgb.Color
import smcl.modeling.AffineTransformation
import smcl.pictures.Bitmap


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
  implicit def SMCLAffineTransformationWrapper(self: AffineTransformation): RichAffineTransformation =
    new RichAffineTransformation(self)

  /**
   *
   */
  implicit def AWTColorWrapper(self: LowLevelColor): RichAWTColor =
    new RichAWTColor(self)

  /**
   *
   */
  implicit def SMCLColorWrapper(self: Color): RicherColor =
    new RicherColor(self)

  /**
   *
   */
  implicit def SMCLBitmapWrapper(self: Bitmap): RichBitmap =
    new RichBitmap(self)

  /**
   *
   */
  implicit def AWTBufferedImageWrapper(self: BufferedImage): RichBufferedImage =
    new RichBufferedImage(self)

}
