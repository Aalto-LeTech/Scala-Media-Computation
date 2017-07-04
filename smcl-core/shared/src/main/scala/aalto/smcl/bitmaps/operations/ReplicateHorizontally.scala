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


import aalto.smcl.bitmaps._
import aalto.smcl.bitmaps.fullfeatured.AbstractBitmap
import aalto.smcl.colors.rgb._
import aalto.smcl.infrastructure._
import aalto.smcl.modeling.Len
import aalto.smcl.settings.{DefaultBackgroundColor, DefaultPaddingInPixels}




/**
 * Operation to replicate one bitmap horizontally a specified number of times.
 *
 * @param bitmapToReplicate
 * @param numberOfReplicas
 * @param paddingInPixels
 * @param backgroundColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class ReplicateHorizontally(
    bitmapToReplicate: AbstractBitmap,
    numberOfReplicas: Int,
    paddingInPixels: Int = DefaultPaddingInPixels,
    backgroundColor: Color = DefaultBackgroundColor,
    private val bitmapValidator: BitmapValidator)
    extends AbstractOperation
            with BufferProvider
            with Immutable {

  require(bitmapToReplicate != null,
    "Replicate operation must be given a non-empty Bitmap instance to replicate.")

  require(numberOfReplicas > 0, s"Number of replicas must be 1 or larger (was $numberOfReplicas)")
  require(paddingInPixels >= 0, s"The padding argument cannot be negative (was $paddingInPixels).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  /** The [[BitmapOperationList]] instance resulting the bitmap to be replicated. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(bitmapToReplicate.operations))

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "ReplicateHorizontally"

  /** Information about this [[BufferProvider]] instance */
  lazy val describedProperties = Map(
    "numberOfReplicas" -> Option(numberOfReplicas.toString),
    "padding" -> Option(s"$paddingInPixels px"),
    "backgroundColor" -> Option(s"0x${backgroundColor.toARGBInt.toARGBHexColorString}")
  )

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int = bitmapToReplicate.heightInPixels

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int =
    (numberOfReplicas + 1) * bitmapToReplicate.widthInPixels +
        numberOfReplicas * paddingInPixels

  bitmapValidator.validateBitmapSize(Len(widthInPixels), Len(heightInPixels))

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    val bufferToReplicate = childOperationListsOption.get.head.render()

    val newBuffer = PRF.createPlatformBitmapBuffer(Len(widthInPixels), Len(heightInPixels))
    val drawingSurface = newBuffer.drawingSurface

    drawingSurface.clearUsing(backgroundColor)

    var xPosition = 0
    for (itemNumber <- 0 to numberOfReplicas) {
      drawingSurface.drawBitmap(bufferToReplicate, xPosition, 0)

      xPosition += bufferToReplicate.widthInPixels + paddingInPixels
    }

    newBuffer
  }

  /**
   * Returns the buffer from which the provided buffer copies are made.
   * Users of this trait must provide an implementation, which returns
   * a [[BitmapBufferAdapter]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return bitmap buffer to be made copies of for providees
   */
  override protected def provideNewBufferToBeCopiedForProvidees(): BitmapBufferAdapter = {
    getOrCreateStaticBuffer()
  }

}
