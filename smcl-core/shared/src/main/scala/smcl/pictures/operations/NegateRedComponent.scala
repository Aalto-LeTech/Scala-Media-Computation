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
import smcl.infrastructure.BitmapBufferAdapter




/**
 * Operation to negate the red component of a bitmap.
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
case class NegateRedComponent()
    extends AbstractOperation
        with OneSourceFilter
        with Immutable {

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "NegateRedComponent"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map()

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

    sources(0).createFilteredVersionWith(ColorComponentTranslationTable.forNegatingRed)
  }

}
