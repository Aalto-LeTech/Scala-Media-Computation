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

package aalto.smcl.infrastructure.jvmawt


import java.awt.geom.{AffineTransform => AWTAffineTransform}

import aalto.smcl.modeling.AffineTransformation




/**
 *
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure]
class RichAffineTransformation(val self: AffineTransformation) {

  /** This [[AffineTransformation]] as a AWT's 'AffineTransform'. */
  final def toAWTAffineTransform: AWTAffineTransform = {
    val awtTransform = new AWTAffineTransform()

    awtTransform.setTransform(
      self.alpha,
      self.delta,
      self.gamma,
      self.beta,
      self.tauX,
      self.tauY
    )

    awtTransform
  }

}
