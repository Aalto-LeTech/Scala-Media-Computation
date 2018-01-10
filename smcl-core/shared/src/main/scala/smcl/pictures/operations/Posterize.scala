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

package smcl.pictures.operations


import smcl.colors.rgb.ColorComponentTranslationTable
import smcl.infrastructure.{BitmapBufferAdapter, CommonValidators}




/**
 * Operation to posterize a bitmap, i.e. to reduce the amount of its colors.
 *
 * @param strengthAsPercentage
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
case class Posterize(strengthAsPercentage: Int)
    extends AbstractOperation
        with OneSourceFilter
        with Immutable {

  new CommonValidators().validatePercentage(strengthAsPercentage, Option("Strength"))

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "Posterize"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "strengthInPercents" -> Option(strengthAsPercentage.toString)
  )


  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources possible [[smcl.infrastructure.BitmapBufferAdapter]] instances used as sources
   *
   * @return
   */
  override protected def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    require(sources.length == 1,
      s"Negative creation requires exactly one source image (provided: ${sources.length}).")

    sources(0).createFilteredVersionWith(
      ColorComponentTranslationTable.forPosterization(strengthAsPercentage))
  }

}
