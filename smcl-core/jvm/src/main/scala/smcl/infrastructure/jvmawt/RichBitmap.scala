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


import java.awt.image.{BufferedImage, ImageObserver}
import java.awt.{Component, Graphics}
import javax.swing.Icon

import smcl.pictures.fullfeatured.Bmp




/**
 * Enables instances of the [[Bmp]] class to be drawn on platform-dependent drawing surfaces
 * as well as to be converted into platform-dependent representations. This is to enable
 * collaboration with Java's AWT and Swing libraries.
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 */
class RichBitmap(val self: Bmp) {

  /**
   * Draws this [[Bmp]] onto a `java.awt.Graphics`.
   *
   * @param drawingSurface
   * @param x
   * @param y
   */
  def drawOnto(drawingSurface: Graphics, x: Int, y: Int): Unit =
    drawOnto(drawingSurface, x, y, null)

  /**
   * Draws this [[Bmp]] onto a `java.awt.Graphics`.
   *
   * @param drawingSurface
   * @param x
   * @param y
   * @param observer
   */
  def drawOnto(drawingSurface: Graphics, x: Int, y: Int, observer: ImageObserver): Unit =
    internalBufferedImage foreach {image =>
      drawingSurface.drawImage(image, x, y, observer)
    }

  /**
   * Returns the content of this [[Bmp]] as a `java.awt.image.BufferedImage`.
   * <br/>
   * Note: The internal `BufferedImage` instance will not be returned; instead,
   * a deep copy will be made and returned. This makes calling this method
   * a slower option than directly drawing the internal image. However, if
   * the image is desired to be modified outside SMCL, calling this metehod
   * is the only option to retrieve a copy of the image's content.
   *
   * @return a deep copy of the internal `BufferedImage` instance, if that instance exists
   */
  def toAWTImage: Option[BufferedImage] =
    internalBufferedImage map BitmapUtils.deepCopy

  /**
   * Returns a `javax.swing.Icon` that is able to draw the content of this
   * [[Bmp]] onto itself.
   * <br/>
   * NOTE: The icon will keep a reference to the internal
   * `java.awt.image.BufferedImage` instance, so the memory reserved by it
   * will not be released until this Icon instance is destroyed.
   *
   * @return a `javax.swing.Icon` instance, if an internal `BufferedImage` instance exists
   */
  def toSwingIcon: Option[Icon] = {
    internalBufferedImage map {image =>
      new Icon {

        /**
         * Paints the content of the [[Bmp]] instance onto the place of this icon.
         *
         * @param target
         * @param graphics
         * @param x
         * @param y
         */
        def paintIcon(
            target: Component,
            graphics: Graphics,
            x: Int,
            y: Int): Unit = {

          graphics.drawImage(image, x, y, target)
        }

        /**
         * Returns the width of this icon.
         *
         * @return width in pixels
         */
        def getIconWidth: Int = image.getWidth

        /**
         * Returns the height of this icon.
         *
         * @return height in pixels
         */
        def getIconHeight: Int = image.getHeight

      }

    }
  }

  /**
   * Returns the internal `java.awt.image.BufferedImage`
   * instance of this [[Bmp]].
   *
   * @return a `java.awt.image.BufferedImage` instance, if it exists
   */
  private
  def internalBufferedImage: Option[BufferedImage] = {
    if (self == null || self.buffer.isEmpty)
      return None

    val adapter = self.buffer.get
    val internalImage =
      adapter.asInstanceOf[AWTBitmapBufferAdapter].awtBufferedImage

    Some(internalImage)
  }

}
