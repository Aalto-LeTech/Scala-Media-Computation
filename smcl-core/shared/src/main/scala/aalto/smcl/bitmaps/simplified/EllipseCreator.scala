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

package aalto.smcl.bitmaps.simplified


import scala.collection.mutable

import aalto.smcl.bitmaps.operations.DrawEllipse
import aalto.smcl.colors.rgb.Color
import aalto.smcl.settings._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class EllipseCreator private[bitmaps]() {

  /**
   * Creates a new empty [[Bitmap]] instance with an ellipse drawn on it.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def createOne(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    val newCircle = createArrayOf(1, widthInPixels, heightInPixels, color, backgroundColor)(0)

    if (NewBitmapsAreDisplayedAutomatically)
      newCircle.display()

    newCircle
  }

  /**
   * Creates an array of [[Bitmap]]
   * instances with an ellipse drawn on each bitmap.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def createArrayOf(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    require(collectionSize >= 0, s"Size of the collection cannot be negative (was $collectionSize)")
    require(widthInPixels > 0, s"Width of the ellipse must be at least 1 pixel (was $widthInPixels)")
    require(heightInPixels > 0, s"Height of the ellipse must be at least 1 pixel (was $heightInPixels)")
    require(color != null, "The ellipse color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val newCollection = mutable.ArrayBuffer.empty[Bitmap]
    val bitmapWidth = if (widthInPixels % 2 == 0) widthInPixels + 1 else widthInPixels
    val bitmapHeight = if (heightInPixels % 2 == 0) heightInPixels + 1 else heightInPixels

    def postCreationProcessor(bmp: Bitmap): Bitmap = {
      val ellipseWidth = bitmapWidth - 3
      val ellipseHeight = bitmapHeight - 3
      val ellipseCenterX = (ellipseWidth / 2) + 1
      val ellipseCenterY = (ellipseHeight / 2) + 1

      bmp.applyInitialization(
        DrawEllipse(
          centerXInPixels = ellipseCenterX,
          centerYInPixels = ellipseCenterY,
          widthInPixels = ellipseWidth,
          heightInPixels = ellipseHeight,
          hasBorder = true,
          hasFilling = true,
          color = color,
          fillColor = color
        ))
    }

    for (i <- 1 to collectionSize) {
      newCollection += Bitmap(
        bitmapWidth,
        bitmapHeight,
        backgroundColor,
        Some(postCreationProcessor _))
    }

    newCollection.toArray
  }

}
