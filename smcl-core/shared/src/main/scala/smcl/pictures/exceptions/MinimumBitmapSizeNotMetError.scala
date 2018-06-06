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

package smcl.pictures.exceptions


import smcl.infrastructure.exceptions.SMCLBaseError
import smcl.modeling.Len




/**
 *
 *
 * @param actualWidth
 * @param actualHeight
 * @param minimumBitmapWidth
 * @param minimumBitmapHeight
 * @param resourcePath
 * @param imageIndexInResource
 *
 * @author Aleksi Lukkarinen
 */
final case class MinimumBitmapSizeNotMetError private[smcl](
    actualWidth: Len,
    actualHeight: Len,
    minimumBitmapWidth: Len,
    minimumBitmapHeight: Len,
    resourcePath: Option[String] = None,
    imageIndexInResource: Option[Int] = None)
    extends SMCLBaseError({
      val sb = new StringBuilder(200)

      sb ++= s"The minimum image size of ${minimumBitmapWidth.inPixels} x " ++=
          s"${minimumBitmapHeight.inPixels} px has not been met " ++=
          s"(was ${actualWidth.inPixels} x ${actualHeight.inPixels})."

      resourcePath foreach {path => sb ++= s""" Resource: "$path"."""}
      imageIndexInResource foreach {index =>
        sb ++= s" Index of the image in the resource: $index."
      }

      sb.toString()
    }, null)
