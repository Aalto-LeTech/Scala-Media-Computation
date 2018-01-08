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

package smcl.bitmaps.metadata.jvmawt


import java.awt.image.BufferedImage

import smcl.bitmaps
import smcl.bitmaps.fullfeatured.AbstractBitmap
import smcl.infrastructure.jvmawt.AWTBitmapBufferAdapter
import smcl.interfaces.BitmapToMetaImageConverter




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait BitmapToAWTImageConverter
    extends BitmapToMetaImageConverter[AbstractBitmap, BufferedImage] {

  /**
   *
   *
   * @param bitmap
   *
   * @return
   */
  override protected
  def convertBitmapToMetaImage(
      bitmap: bitmaps.fullfeatured.AbstractBitmap): BufferedImage = {

    bitmap
        .toRenderedRepresentation
        .asInstanceOf[AWTBitmapBufferAdapter]
        .awtBufferedImage
  }

}
