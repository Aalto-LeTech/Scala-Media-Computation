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


import scala.collection.mutable.ArrayBuffer

import smcl.colors.rgb._
import smcl.infrastructure._
import smcl.modeling.Len
import smcl.pictures._
import smcl.pictures.fullfeatured.AbstractBitmap
import smcl.settings.{HorizontalAlignment, _}




/**
 * Operation to append bitmaps vertically next to each other.
 *
 * @param bitmapsToCombine
 * @param horizontalAlignment
 * @param paddingInPixels
 * @param backgroundColor
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
case class AppendVertically(
    bitmapsToCombine: Seq[AbstractBitmap])(
    horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
    paddingInPixels: Int = DefaultPaddingInPixels,
    backgroundColor: Color = DefaultBackgroundColor,
    private val bitmapValidator: BitmapValidator)
    extends AbstractOperation
        with BufferProvider
        with Immutable {

  require(bitmapsToCombine.nonEmpty,
    "Append operation must be given a non-empty Sequence of Bitmap instances to combine.")

  require(paddingInPixels >= 0, s"The padding argument cannot be negative (was $paddingInPixels).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  /** The [[BitmapOperationList]] instances resulting the bitmaps to be combined. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(bitmapsToCombine.map(_.operations))

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "AppendVertically"

  /** Information about this [[BufferProvider]] instance */
  lazy val describedProperties = Map(
    "padding" -> Option(s"$paddingInPixels px"),
    "horizontalAlignment" -> Option(horizontalAlignment.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toARGBInt.toARGBHexColorString}")
  )

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int = childOperationListsOption.get.maxBy({_.widthInPixels}).widthInPixels

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int =
    childOperationListsOption.get.foldLeft[Int](0)({_ + _.heightInPixels}) +
        (childOperationListsOption.get.length - 1) * paddingInPixels

  bitmapValidator.validateBitmapSize(Len(widthInPixels), Len(heightInPixels))

  /** Future vertical offsets of the bitmaps to be combined. */
  val horizontalOffsets: Seq[Int] = horizontalAlignment match {
    case HALeft =>
      ArrayBuffer.fill[Int](bitmapsToCombine.length)(0)

    case HARight =>
      bitmapsToCombine map {widthInPixels - _.widthInPixels}

    case HACenter =>
      bitmapsToCombine map {bmp =>
        (widthInPixels.toDouble / 2 - bmp.widthInPixels.toDouble / 2).floor.toInt
      }
  }

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    val newBuffer = PRF.createPlatformBitmapBuffer(Len(widthInPixels), Len(heightInPixels))
    val drawingSurface = newBuffer.drawingSurface

    drawingSurface.clearUsing(backgroundColor)

    var yPosition = 0
    var itemNumber = 0
    childOperationListsOption.get foreach {opList =>
      val sourceBuffer = opList.render()

      drawingSurface.drawBitmap(sourceBuffer, horizontalOffsets(itemNumber), yPosition)

      yPosition += sourceBuffer.heightInPixels + paddingInPixels
      itemNumber += 1
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
