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




/**
 * Operation to convert bitmap's colors into grayscale by lightness.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class ToGrayscaleByLightness()
    extends AbstractOperation
        with OneSourceFilter
        with Immutable {

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "ToGrayscaleByLightness"

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
      s"Grayscale conversion requires exactly one source image (provided: ${sources.length}).")

    sources(0).iteratePixelsWith{(red, green, blue, opacity) =>
      val intensity = ((Math.max(red, green).max(blue) + Math.min(red, green).min(blue)) / 2.0).toInt

      (intensity, intensity, intensity, opacity)
    }
  }

}
