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

import aalto.smcl.bitmaps.operations.DrawRoundedRectangle
import aalto.smcl.colors.rgb.Color
import aalto.smcl.settings._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class RoundedRectangleCreator private[bitmaps]() {

  /**
   * Creates a new empty [[Bitmap]] instance with a rounded-corner rectangle drawn on it.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def createOne(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    val newCircle = createArrayOf(1,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)(0)

    if (NewBitmapsAreDisplayedAutomatically)
      newCircle.display()

    newCircle
  }

  /**
   * Creates an array of [[Bitmap]] instances with a rounded-corner rectangle drawn on each bitmap.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def createArrayOf(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    require(collectionSize >= 0, s"Size of the collection cannot be negative (was $collectionSize)")
    require(widthInPixels >= 5, s"Width of the rectangle must be at least 5 pixels (was $widthInPixels)")
    require(heightInPixels >= 5, s"Height of the rectangle must be at least 5 pixels (was $heightInPixels)")
    require(roundingWidthInPixels > 0, s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")
    require(roundingHeightInPixels > 0, s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")
    require(color != null, "The ellipse color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val newCollection = mutable.ArrayBuffer.empty[Bitmap]

    def postCreationProcessor(bmp: Bitmap): Bitmap = {
      bmp.applyInitialization(
        DrawRoundedRectangle(
          0, 0,
          widthInPixels - 1, heightInPixels - 1,
          roundingWidthInPixels, roundingHeightInPixels,
          hasBorder = true,
          hasFilling = true,
          color = color,
          fillColor = color
        ))
    }

    for (i <- 1 to collectionSize) {
      newCollection += Bitmap(
        widthInPixels,
        heightInPixels,
        backgroundColor,
        Some(postCreationProcessor _))
    }

    newCollection.toArray
  }

}
