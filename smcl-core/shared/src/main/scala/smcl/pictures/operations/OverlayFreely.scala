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


import smcl.colors.ColorValidator
import smcl.colors.rgb._
import smcl.infrastructure._
import smcl.modeling.Len
import smcl.pictures.fullfeatured.AbstractBitmap
import smcl.settings.DefaultBackgroundColor




/**
 * Operation to overlay one bitmap over another bitmap.
 *
 * @param bottomBitmap
 * @param topBitmap
 * @param topBitmapUpperLeftX
 * @param topBitmapUpperLeftY
 * @param backgroundColor
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
case class OverlayFreely(
    bottomBitmap: AbstractBitmap,
    topBitmap: AbstractBitmap,
    topBitmapUpperLeftX: Int,
    topBitmapUpperLeftY: Int,
    topBitmapOpacity: Int = ColorValidator.MaximumOpacity,
    backgroundColor: Color = DefaultBackgroundColor,
    private val colorValidator: ColorValidator)
    extends AbstractOperation
        with BufferProvider
        with Immutable {

  require(bottomBitmap != null, "The lower bitmap argument has to be a Bitmap instance (was null).")
  require(topBitmap != null, "The upper bitmap argument has to be a Bitmap instance (was null).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")
  require(colorValidator != null, "The color validator argument has to be a ColorValidator instance (was null).")

  colorValidator.validateOpacityComponent(topBitmapOpacity)

  /** The [[BitmapOperationList]] instances resulting the bitmaps to be combined. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(bottomBitmap.operations, topBitmap.operations))

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "OverlayFreely"

  /** Information about this [[BufferProvider]] instance */
  lazy val describedProperties = Map(
    "topBitmapUpperLeftX" -> Option(s"$topBitmapUpperLeftX px"),
    "topBitmapUpperLeftY" -> Option(s"$topBitmapUpperLeftY px"),
    "topBitmapOpacity" -> Option(topBitmapOpacity.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toARGBInt.toARGBHexColorString}")
  )

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int =
    if (topBitmapUpperLeftX < 0)
      (bottomBitmap.widthInPixels - topBitmapUpperLeftX).max(topBitmap.widthInPixels).floor.toInt
    else
      bottomBitmap.widthInPixels.max(topBitmapUpperLeftX + topBitmap.widthInPixels).floor.toInt

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int =
    if (topBitmapUpperLeftY < 0)
      (bottomBitmap.heightInPixels - topBitmapUpperLeftY).max(topBitmap.heightInPixels).floor.toInt
    else
      bottomBitmap.heightInPixels.max(topBitmapUpperLeftY + topBitmap.heightInPixels).floor.toInt

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    var bottomX, bottomY, topX, topY: Int = 0

    if (topBitmapUpperLeftX < 0) {
      bottomX = -topBitmapUpperLeftX
      topX = 0
    }
    else {
      bottomX = 0
      topX = topBitmapUpperLeftX
    }

    if (topBitmapUpperLeftY < 0) {
      bottomY = -topBitmapUpperLeftY
      topY = 0
    }
    else {
      bottomY = 0
      topY = topBitmapUpperLeftY
    }

    val newBuffer = PRF.createPlatformBitmapBuffer(Len(widthInPixels), Len(heightInPixels))
    val drawingSurface = newBuffer.drawingSurface

    drawingSurface.clearUsing(backgroundColor, true)

    val bottomBuffer = bottomBitmap.toRenderedRepresentation
    val topBuffer = topBitmap.toRenderedRepresentation

    drawingSurface.drawBitmap(bottomBuffer, bottomX, bottomY, ColorValidator.MaximumOpacity)
    drawingSurface.drawBitmap(topBuffer, topX, topY, topBitmapOpacity)

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
