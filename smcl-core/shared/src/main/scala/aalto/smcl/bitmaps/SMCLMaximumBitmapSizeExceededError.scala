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

package aalto.smcl.bitmaps


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLMaximumBitmapSizeExceededError private[smcl](
    realWidthOption: Option[Int] = None,
    realHeightOption: Option[Int] = None,
    resourcePathOption: Option[String] = None,
    imageIndexInResourceOption: Option[Int] = None,
    private val bitmapValidator: BitmapValidator) extends RuntimeException({

  val sb = new StringBuilder(200)

  sb ++= s"The maximum image size of ${bitmapValidator.MaximumBitmapWidthInPixels} x " +
      s"${bitmapValidator.MaximumBitmapHeightInPixels} px has been exceeded "

  if (realWidthOption.isDefined && realHeightOption.isDefined)
    sb ++= s"(was ${realWidthOption.get} x ${realHeightOption.get})"

  sb ++= "."

  resourcePathOption foreach {path => sb ++= s""" Resource: "$path"."""}
  imageIndexInResourceOption foreach {index => sb ++= s""" Index of the image in the resource: $index."""}

  sb.toString()
}) {

}
