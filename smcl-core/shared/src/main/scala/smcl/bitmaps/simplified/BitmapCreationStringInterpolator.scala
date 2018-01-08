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

package smcl.bitmaps.simplified


/**
 * A string interpolator for creating [[Bitmap]] instances.
 *
 * @param sc
 *
 * @author Aleksi Lukkarinen
 */
class BitmapCreationStringInterpolator(
    val sc: StringContext)
    extends AnyVal {

  /**
   * Loads the first bitmap from a file.
   *
   * @param args path to the image file to be loaded
   *
   * @return
   */
  def bmpf(args: Any*): Bitmap = {
    val pathStringCandidate =
      sc.standardInterpolator(
        StringContext.processEscapes, args)

    Bitmap(pathStringCandidate)
  }

  /**
   * Loads all bitmaps from a file.
   *
   * @param args path to the image file to be loaded
   *
   * @return
   */
  def bmpsf(args: Any*): Seq[Bitmap] = {
    val pathStringCandidate =
      sc.standardInterpolator(
        StringContext.processEscapes, args)

    Bitmaps(pathStringCandidate)
  }

}
