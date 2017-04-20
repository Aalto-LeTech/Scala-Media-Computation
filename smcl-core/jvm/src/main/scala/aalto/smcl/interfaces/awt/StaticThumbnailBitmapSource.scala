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

package aalto.smcl.interfaces.awt


import java.awt.image.BufferedImage




/**
 * Interface for querying objects for thumbnail bitmap representation.
 *
 * @author Aleksi Lukkarinen
 */
trait StaticThumbnailBitmapSource {

  /**
   *
   *
   * @return
   */
  def numberOfThumbnailBitmaps(): Int

  /**
   *
   *
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   *
   * @return
   */
  def thumbnailBitmapsOption(
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Option[Seq[BufferedImage]]

  /**
   *
   *
   * @param thumbnailNumber
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   *
   * @return
   */
  def thumbnailBitmapOption(
      thumbnailNumber: Int = 0,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Option[BufferedImage]

}
