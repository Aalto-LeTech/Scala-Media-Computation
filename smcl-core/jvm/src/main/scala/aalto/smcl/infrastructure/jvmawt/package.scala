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

import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure.BaseSettingKeys.EnumSettingKey


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object jvmawt
    extends AwtSettingKeys with Constants {

  /** */
  private[infrastructure]
  lazy val UIProvider: AwtSwingUIProvider = new AwtSwingUIProvider()

  /** */
  private[infrastructure]
  lazy val AWTToolkit: Toolkit = UIProvider.awtToolkit




  /** */
  case object AffineTransformationInterpolationMethod extends
      EnumSettingKey[AwtAffineTransformationInterpolationMethod.Value]




  /**
   *
   */
  private[infrastructure]
  implicit def AwtColorWrapper(self: LowLevelColor): RichAwtColor = new RichAwtColor(self)

  /**
   *
   */
  private[infrastructure]
  implicit def RGBAColorExtendedWrapper(self: RGBAColor): ExtendedRichRGBAColor =
    new ExtendedRichRGBAColor(self)

}
