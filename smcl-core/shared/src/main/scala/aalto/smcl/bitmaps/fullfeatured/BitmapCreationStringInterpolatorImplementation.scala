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

package aalto.smcl.bitmaps.fullfeatured


/**
 * A string interpolator implementation for creating bitmap instances.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
object BitmapCreationStringInterpolatorImplementation {

  /**
   * Loads the first bitmap from a file.
   *
   * @param args path to the image file to be loaded
   *
   * @return
   */
  def bmpf[BitmapType <: AbstractBitmap](
      sc: StringContext, args: Any*): BitmapType = {

    val pathStringCandidate = sc.standardInterpolator(StringContext.processEscapes, args)

    Bitmap(pathStringCandidate).asInstanceOf[BitmapType]
  }

  /**
   * Loads all bitmaps from a file.
   *
   * @param args path to the image file to be loaded
   *
   * @return
   */
  def bmpsf[BitmapType <: AbstractBitmap](
      sc: StringContext, args: Any*): Seq[BitmapType] = {

    val pathStringCandidate = sc.standardInterpolator(StringContext.processEscapes, args)

    Bitmaps(pathStringCandidate).map(_.asInstanceOf[BitmapType])
  }

}
