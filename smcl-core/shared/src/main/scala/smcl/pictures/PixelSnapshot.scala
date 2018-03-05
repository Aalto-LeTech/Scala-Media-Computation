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

package smcl.pictures


import smcl.colors.rgb.Color
import smcl.infrastructure.DoneStatus
import smcl.infrastructure.exceptions.InvalidColorComponentArrayLengthError
import smcl.pictures.exceptions.PixelSnapshotInvalidatedError
import smcl.pictures.iterators._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object PixelSnapshot {

  /**
   *
   *
   * @param source
   *
   * @return
   */
  def apply(source: PictureElement): PixelSnapshot = {
    val bitmapCopy = source.toBitmapCopy

    if (bitmapCopy.buffer.isEmpty
        || bitmapCopy.buffer.get.widthInPixels == 0
        || bitmapCopy.buffer.get.heightInPixels == 0) {

      return new NullPixelSnapshot(bitmapCopy)
    }

    new DefaultPixelSnapshot(bitmapCopy)
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait PixelSnapshot
    extends Iterable[Pixel] {

  /** */
  @inline
  private[pictures]
  def reds: Array[Int]

  /** */
  @inline
  private[pictures]
  def greens: Array[Int]

  /** */
  @inline
  private[pictures]
  def blues: Array[Int]

  /** */
  @inline
  private[pictures]
  def opacities: Array[Int]

  /**
   *
   *
   * @return
   */
  @inline
  def widthInPixels: Int

  /**
   *
   *
   * @return
   */
  @inline
  def heightInPixels: Int

  /**
   *
   *
   * @return
   */
  def areaInPixels: Int

  /**
   *
   *
   * @return
   */
  def minXInPixels: Int

  /**
   *
   *
   * @return
   */
  def maxXInPixels: Int

  /**
   *
   *
   * @return
   */
  def minYInPixels: Int

  /**
   *
   *
   * @return
   */
  def maxYInPixels: Int

  /**
   *
   *
   * @return
   */
  def invalidation: DoneStatus

  /**
   *
   *
   * @return
   */
  @inline
  def redComponentArray: Array[Int]

  /**
   *
   *
   * @return
   */
  @inline
  def greenComponentArray: Array[Int]

  /**
   *
   *
   * @return
   */
  @inline
  def blueComponentArray: Array[Int]

  /**
   *
   *
   * @return
   */
  @inline
  def opacityComponentArray: Array[Int]

  /**
   *
   *
   * @return
   */
  @inline
  def componentArrays: (Array[Int], Array[Int], Array[Int], Array[Int])

  /**
   *
   *
   * @return
   */
  @inline
  def setRedComponentArray(array: Array[Int]): Unit

  /**
   *
   *
   * @return
   */
  @inline
  def setGreenComponentArray(array: Array[Int]): Unit

  /**
   *
   *
   * @return
   */
  @inline
  def setBlueComponentArray(array: Array[Int]): Unit

  /**
   *
   *
   * @return
   */
  @inline
  def setOpacityComponentArray(array: Array[Int]): Unit

  /**
   *
   *
   * @return
   */
  @inline
  def setComponentArrays(
      reds: Array[Int],
      greens: Array[Int],
      blues: Array[Int],
      opacities: Array[Int]): Unit

  /**
   *
   *
   * @param colorTranslator
   *
   * @return
   */
  @inline
  def iterateColorsByPixel(colorTranslator: Color => Color): PixelSnapshot =
    iterateColorsByPixel(Seq(colorTranslator))

  /**
   *
   *
   * @param colorTranslator
   *
   * @return
   */
  @inline
  def iterateColorsByPixel(colorTranslator: Seq[Color => Color]): PixelSnapshot = {
    colorTranslator.foreach{currentTranslator =>
      val i = iterator
      while (i.hasNext) {
        val pixel = i.next()
        pixel.color = currentTranslator(pixel.color)
      }
    }

    this
  }

  /**
   *
   *
   * @param pixelTranslator
   *
   * @return
   */
  @inline
  def iteratePixels(pixelTranslator: Pixel => Pixel): PixelSnapshot =
    iteratePixels(Seq(pixelTranslator))

  /**
   *
   *
   * @param pixelTranslator
   *
   * @return
   */
  @inline
  def iteratePixels(pixelTranslator: Seq[Pixel => Pixel]): PixelSnapshot = {
    pixelTranslator.foreach{currentTranslator =>
      val i = iterator
      while (i.hasNext) {
        val pixel = i.next()
        pixel.setFrom(currentTranslator(pixel))
      }
    }

    this
  }

  /**
   *
   */
  @inline
  def toBitmap: Bitmap

  /**
   *
   *
   * @param x
   * @param y
   *
   * @return
   */
  @inline
  def pixel(
      x: Int,
      y: Int): Pixel

  /**
   *
   * @return
   */
  @inline
  override
  def iterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def downwardsLeftwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def leftwardsDownwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def leftwardsUpwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def downwardsRightwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def rightwardsUpwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def upwardsLeftwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotUpwardsLeftwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def upwardsRightwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotUpwardsRightwardsIterator(this)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String =
    s"PixelSnapshot ($widthInPixels x $heightInPixels pixels)"

  /**
   *
   *
   */
  @inline
  def checkForInvalidation(): Unit = {
    if (invalidation.isDone)
      throw PixelSnapshotInvalidatedError()
  }

  /**
   *
   *
   * @param array
   * @param colorOfArray
   */
  @inline
  def checkComponentArrayLength(
      array: Array[Int],
      colorOfArray: String): Unit = {

    if (array.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        s"Expected length for the given ${colorOfArray.toLowerCase} RGBA component array is " +
            s"$areaInPixels, but actually was ${array.length}")
  }

}
