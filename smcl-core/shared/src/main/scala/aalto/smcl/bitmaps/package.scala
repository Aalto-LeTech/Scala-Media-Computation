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

package aalto.smcl


import scala.language.implicitConversions

import aalto.smcl.bitmaps.fullfeatured.ShapeCreationAPI


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object bitmaps
    extends simplified.BitmapOperationAPI
            with ShapeCreationAPI {

  /** A type alias for default bitmap class. */
  type Bitmap = simplified.Bitmap

  /** A companion object alias for default bitmap. */
  val Bitmap = simplified.Bitmap


  /**
   *
   *
   * @param sc
   *
   * @return
   */
  implicit def BitmapCreationStringContextWrapper(sc: StringContext): simplified.BitmapCreationStringInterpolator =
    simplified.BitmapCreationStringContextWrapper(sc)

}
