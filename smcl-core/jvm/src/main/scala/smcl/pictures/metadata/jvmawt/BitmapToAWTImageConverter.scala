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

package smcl.pictures.metadata.jvmawt


import java.awt.image.BufferedImage

import smcl.infrastructure.jvmawt.AWTBitmapBufferAdapter
import smcl.interfaces.BitmapToMetaImageConverter
import smcl.pictures
import smcl.pictures.fullfeatured.AbstractBitmap




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
      bitmap: pictures.fullfeatured.AbstractBitmap): BufferedImage = {

    bitmap
        .toRenderedRepresentation
        .asInstanceOf[AWTBitmapBufferAdapter]
        .awtBufferedImage
  }

}
