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


import java.awt.image.BufferedImage

import smcl.pictures.Bmp




/**
 * Enables instances of the `java.awt.image.BufferedImage` class to converted into SMCL's
 * [[Bmp]] instances. This is to enable collaboration with Java's AWT and Swing libraries.
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 */
class RichBufferedImage(val self: BufferedImage) {

  /**
   * Returns a [[Bmp]] instance that contains the content of a given
   * `java.awt.image.BufferedImage` instance.
   * <br />
   * Note: A deep copy is made of the original `BufferedImage`, so the
   * [[Bmp]] cannot be modified via manipulating the original. Also,
   * the memory consumption will be a doubled (original + copy).
   *
   * @return
   */
  def toSMCLBitmap: Option[Bmp] = {
    if (self == null)
      return None

    val copiedBuffer = BitmapUtils.deepCopy(self)
    val bitmap = Bmp(AWTBitmapBufferAdapter(copiedBuffer))

    Some(bitmap)
  }

}
