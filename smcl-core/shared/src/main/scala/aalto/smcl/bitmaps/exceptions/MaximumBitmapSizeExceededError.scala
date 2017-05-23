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

package aalto.smcl.bitmaps.exceptions


import aalto.smcl.infrastructure.exceptions.SMCLBaseError




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final case class MaximumBitmapSizeExceededError private[smcl](
    actualWidthInPixels: Int,
    actualHeightInPixels: Int,
    maximumBitmapWidthInPixels: Int,
    maximumBitmapHeightInPixels: Int,
    resourcePath: Option[String] = None,
    imageIndexInResource: Option[Int] = None)
    extends SMCLBaseError({
      val sb = new StringBuilder(200)

      sb ++= s"The maximum image size of $maximumBitmapWidthInPixels x " ++=
          s"$maximumBitmapHeightInPixels px has been exceeded " ++=
          s"(was $actualWidthInPixels x $actualHeightInPixels)."

      resourcePath foreach {path => sb ++= s""" Resource: "$path"."""}
      imageIndexInResource foreach {index =>
        sb ++= s" Index of the image in the resource: $index."
      }

      sb.toString()
    }, null)
