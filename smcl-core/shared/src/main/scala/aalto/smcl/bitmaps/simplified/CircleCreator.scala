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

import aalto.smcl.colors.rgb.Color
import aalto.smcl.settings._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class CircleCreator {

  /**
   * Creates a new empty [[Bitmap]] instance with a circle drawn on it.
   *
   * @param diameter
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def createOne(
      diameter: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(diameter > 0, s"Diameter of the circle must be at least 1 pixel (was $diameter)")
    require(color != null, "The circle color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val imageSide = if (diameter % 2 == 0) diameter + 1 else diameter

    val newBitmap = Bitmap(
      widthInPixels = imageSide,
      heightInPixels = imageSide,
      initialBackgroundColor = backgroundColor)

    val radius = (imageSide - 2) / 2

    val oldViewerUpdatePolicy = NewBitmapsAreDisplayedAutomatically
    try {
      DoNotDisplayBitmapsAutomaticallyAfterOperations()

      val newCircle = newBitmap.drawCircle(
        centerXInPixels = radius + 1,
        centerYInPixels = radius + 1,
        radiusInPixels = radius,
        hasBorder = true,
        hasFilling = true,
        color = color,
        fillColor = color)
    }
    finally {
      NewBitmapsAreDisplayedAutomatically = oldViewerUpdatePolicy
    }

    if (NewBitmapsAreDisplayedAutomatically)
      newCircle.display()

    newCircle
  }

  /**
   * Creates an array of [[Bitmap]]
   * instances with a circle drawn on each bitmap.
   *
   * @param diameter
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def createArrayOf(
      collectionSize: Int = 5,
      diameter: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    require(collectionSize >= 0, s"Size of the collection cannot be negative (was $collectionSize)")

    val newCollection = mutable.ArrayBuffer.empty[Bitmap]

    var item = 0
    for (item <- 1 to collectionSize) {
      newCollection += createOne(diameter, color, backgroundColor, PreventViewerUpdates)
    }

    newCollection.toArray
  }

}
