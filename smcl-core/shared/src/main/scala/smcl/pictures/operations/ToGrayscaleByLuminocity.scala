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


import smcl.infrastructure.BitmapBufferAdapter




/**
 * Operation to convert bitmap's colors into grayscale by luminocity.
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
case class ToGrayscaleByLuminocity()
    extends AbstractOperation
        with OneSourceFilter
        with Immutable {

  /** */
  private val StandardRedWeight: Double = 0.21

  /** */
  private val StandardGreenWeight: Double = 0.72

  /** */
  private val StandardBlueWeight: Double = 0.07

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "ToGrayscaleByLuminocity"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "redWeight" -> Option(StandardRedWeight.toString),
    "greenWeight" -> Option(StandardGreenWeight.toString),
    "blueWeight" -> Option(StandardBlueWeight.toString)
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
      s"Grayscale conversion requires exactly one source image (provided: ${sources.length}).")

    sources(0).iteratePixelsWith{(red, green, blue, opacity) =>
      val intensity = (
          StandardRedWeight * red +
              StandardGreenWeight * green +
              StandardBlueWeight * blue).toInt

      (intensity, intensity, intensity, opacity)
    }
  }

}
