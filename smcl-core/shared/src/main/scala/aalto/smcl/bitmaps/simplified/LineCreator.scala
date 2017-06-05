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

import aalto.smcl.bitmaps.operations.DrawLine
import aalto.smcl.colors.rgb.Color
import aalto.smcl.settings._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class LineCreator private[bitmaps]() {

  /**
   * Creates a new empty [[Bitmap]] instance with a line drawn on it. The line can be freely located in
   * the Cartesian coordinate system, but the bitmap will always contain the minimum amount of empty space.
   * That is, the line will always be drawn from one corner of the bitmap to the opposite corner.
   *
   * @param fromXInPixels
   * @param fromYInPixels
   * @param toXInPixels
   * @param toYInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def createOne(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    val newCircle = createArrayOf(1,
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels,
      color, backgroundColor)(0)

    if (NewBitmapsAreDisplayedAutomatically)
      newCircle.display()

    newCircle
  }

  /**
   * Creates an array of [[Bitmap]] instances with a line drawn on each bitmap. The lines can be
   * freely located in the Cartesian coordinate system, but the bitmap will always contain the
   * minimum amount of empty space. That is, the line will always be drawn from one corner of
   * the bitmap to the opposite corner.
   *
   * @param fromXInPixels
   * @param fromYInPixels
   * @param toXInPixels
   * @param toYInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def createArrayOf(
      collectionSize: Int = 5,
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    require(collectionSize >= 0, s"Size of the collection cannot be negative (was $collectionSize)")
    require(color != null, "The line color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val newCollection = mutable.ArrayBuffer.empty[Bitmap]

    val differenceX = toXInPixels - fromXInPixels
    val differenceY = toYInPixels - fromYInPixels
    val bitmapWidth = Math.abs(differenceX)
    val bitmapHeight = Math.abs(differenceY)

    require(bitmapWidth > 0, s"Difference of the x coordinates must be at least 1 pixel (was $bitmapWidth)")
    require(bitmapHeight > 0, s"Difference of the y coordinates must be at least 1 pixel (was $bitmapHeight)")

    def postCreationProcessor(bmp: Bitmap): Bitmap = {
      val slopeSign = Math.signum(differenceY.toDouble / differenceX)
      val (x0, y0, x1, y1) =
        if (slopeSign < 0)
          (0, 0, bitmapWidth - 1, bitmapHeight - 1)
        else
          (0, bitmapHeight - 1, bitmapWidth - 1, 0)

      bmp.applyInitialization(DrawLine(x0, y0, x1, y1, color))
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
