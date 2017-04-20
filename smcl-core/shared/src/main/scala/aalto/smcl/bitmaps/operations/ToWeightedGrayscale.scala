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

package aalto.smcl.bitmaps.operations


import aalto.smcl.colors.ColorValidator
import aalto.smcl.infrastructure._




/**
 * Operation to convert bitmap's colors into grayscale using given component weights.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class ToWeightedGrayscale(
    redWeight: Double,
    greenWeight: Double,
    blueWeight: Double)
    extends AbstractOperation
            with OneSourceFilter
            with Immutable {

  val validators = new CommonValidators()
  validators.validateZeroToOneFactor(redWeight, Option("Red weight"))
  validators.validateZeroToOneFactor(greenWeight, Option("Green weight"))
  validators.validateZeroToOneFactor(blueWeight, Option("Blue weight"))

  new ColorValidator().validateRgbColorWeightCombination(redWeight, greenWeight, blueWeight)

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap("ToWeightedGrayscale", Map(
    "redWeight" -> Option(redWeight.toString),
    "greenWeight" -> Option(greenWeight.toString),
    "blueWeight" -> Option(blueWeight.toString)
  ))

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources possible [[BitmapBufferAdapter]] instances used as sources
   *
   * @return
   */
  override protected def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    require(sources.length == 1,
      s"Grayscale conversion requires exactly one source image (provided: ${sources.length}).")

    sources(0).iteratePixelsWith{(red, green, blue, opacity) =>
      val intensity = (redWeight * red + greenWeight * green + blueWeight * blue).toInt

      (intensity, intensity, intensity, opacity)
    }
  }

}
