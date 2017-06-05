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
class VerticalLineCreator private[bitmaps]() {

  /**
   * Creates a new empty [[Bitmap]] instance with a vertical line drawn on it.
   *
   * @param lengthInPixels
   * @param color
   *
   * @return
   */
  def createOne(
      lengthInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): Bitmap = {

    val newShape = createArrayOf(1, lengthInPixels, color)(0)

    if (NewBitmapsAreDisplayedAutomatically)
      newShape.display()

    newShape
  }

  /**
   * Creates an array of [[Bitmap]] instances with a vertical line drawn on each bitmap.
   *
   * @param collectionSize
   * @param lengthInPixels
   * @param color
   *
   * @return
   */
  def createArrayOf(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): Array[Bitmap] = {

    require(collectionSize >= 0, s"Size of the collection cannot be negative (was $collectionSize)")
    require(lengthInPixels > 0, s"Length of the line must be at least 1 pixel (was $lengthInPixels)")
    require(color != null, "The color argument has to be a Color instance (was null).")

    val newCollection = mutable.ArrayBuffer.empty[Bitmap]

    for (_ <- 1 to collectionSize) {
      newCollection += Bitmap(1, lengthInPixels, color)
    }

    newCollection.toArray
  }

}
