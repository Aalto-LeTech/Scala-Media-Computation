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

package smcl.bitmaps.operations


import smcl.infrastructure.BitmapBufferAdapter
import smcl.modeling.AffineTransformation




/**
 * Operation to flip a bitmap diagonally.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class FlipDiagonally()
    extends AbstractOperation
        with OneSourceFilter
        with Immutable {

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "FlipDiagonally"

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
  override
  protected
  def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    require(sources.length == 1, s"Flip requires exactly one source image (provided: ${sources.length}).")

    val transformation =
      AffineTransformation.forOrigoRelativeDiagonalFlipOf(
        sources(0).widthInPixels,
        sources(0).heightInPixels)

    sources(0).createTransformedVersionWith(transformation)._1
  }

}
