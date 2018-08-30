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
import smcl.infrastructure.enumerators.{MESRightwardsDownwards, MatrixEnumerator2D}
import smcl.infrastructure.exceptions.InvalidColorComponentArrayLengthError
import smcl.pictures.exceptions.PixelSnapshotInvalidatedError
import smcl.pictures.iterators._




/**
  *
  * @author Aleksi Lukkarinen
  */
object PixelSnapshot {

  /**
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
  * @author Aleksi Lukkarinen
  */
trait PixelSnapshot
    extends Iterable[Pixel] {

  /** */
  private[pictures]
  def reds: Array[Int]

  /** */
  private[pictures]
  def greens: Array[Int]

  /** */
  private[pictures]
  def blues: Array[Int]

  /** */
  private[pictures]
  def opacities: Array[Int]

  /**
    *
    * @return
    */
  def widthInPixels: Int

  /**
    *
    * @return
    */
  def heightInPixels: Int

  /**
    *
    * @return
    */
  def areaInPixels: Int

  /**
    *
    * @return
    */
  def minXInPixels: Int

  /**
    *
    * @return
    */
  def maxXInPixels: Int

  /**
    *
    * @return
    */
  def minYInPixels: Int

  /**
    *
    * @return
    */
  def maxYInPixels: Int

  /**
    *
    * @return
    */
  def invalidation: DoneStatus

  /**
    *
    * @return
    */
  def redComponentArray: Array[Int]

  /**
    *
    * @return
    */
  def greenComponentArray: Array[Int]

  /**
    *
    * @return
    */
  def blueComponentArray: Array[Int]

  /**
    *
    * @return
    */
  def opacityComponentArray: Array[Int]

  /**
    *
    * @return
    */
  def componentArrays: (Array[Int], Array[Int], Array[Int], Array[Int])

  /**
    *
    * @return
    */
  def setRedComponentArray(array: Array[Int]): Unit

  /**
    *
    * @return
    */
  def setGreenComponentArray(array: Array[Int]): Unit

  /**
    *
    * @return
    */
  def setBlueComponentArray(array: Array[Int]): Unit

  /**
    *
    * @return
    */
  def setOpacityComponentArray(array: Array[Int]): Unit

  /**
    *
    * @return
    */
  def setComponentArrays(
      reds: Array[Int],
      greens: Array[Int],
      blues: Array[Int],
      opacities: Array[Int]): Unit

  /**
    *
    * @param another
    * @param pixelMerger
    *
    * @return
    */
  @inline
  final
  def mergeWith(
      another: PixelSnapshot,
      pixelMerger: (Color, Color) => Color): PixelSnapshot = {

    val width = widthInPixels.min(another.widthInPixels)
    val height = heightInPixels.min(another.heightInPixels)

    val resultSnap = Bitmap(width, height).toPixelSnapshot
    resultSnap.iterateLocations{(x, y) =>
      val firstColor = getColorInternal(arrayPosition(x, y))
      val secondColor = another.getColorInternal(another.arrayPosition(x, y))

      val newColor = pixelMerger(firstColor, secondColor)
      resultSnap.setColorInternal(resultSnap.arrayPosition(x, y), newColor)
    }

    resultSnap
  }

  /**
    *
    * @param generator
    *
    * @return
    */
  @inline
  final
  def setColorsByLocation(generator: (Int, Int) => Color): PixelSnapshot =
    iterateLocationsAndReturnSelf{(x, y) =>
      setColorInternal(arrayPosition(x, y), generator(x, y))
    }

  /**
    *
    * @param transformers
    *
    * @return
    */
  @inline
  final
  def transformColorToColor(transformers: Seq[Color => Color]): PixelSnapshot =
    transformColorToColor(transformers.reduceLeft(_ andThen _))

  /**
    *
    * @param transformer
    *
    * @return
    */
  @inline
  final
  def transformColorToColor(transformer: Color => Color): PixelSnapshot =
    iterateComponentArrayIndicesAndReturnSelf{i =>
      setColorInternal(i, transformer(getColorInternal(i)))
    }

  /**
    *
    * @param transformers
    *
    * @return
    */
  @inline
  final
  def transformLocationColorToColor(transformers: Seq[(Int, Int, Color) => Color]): PixelSnapshot = {
    transformers.foreach{t => transformLocationColorToColor(t)}
    this
  }

  /**
    *
    * @param transformer
    *
    * @return
    */
  @inline
  final
  def transformLocationColorToColor(transformer: (Int, Int, Color) => Color): PixelSnapshot =
    iterateLocationsAndReturnSelf{(x, y) =>
      val p = arrayPosition(x, y)
      setColorInternal(p, transformer(x, y, getColorInternal(p)))
    }

  /**
    *
    * @param transformers
    *
    * @return
    */
  @inline
  final
  def iteratePixels(transformers: Seq[Pixel => Unit]): PixelSnapshot = {
    transformers.foreach{t => iteratePixels(t)}
    this
  }

  /**
    *
    * @param transformer
    *
    * @return
    */
  @inline
  final
  def iteratePixels(transformer: Pixel => Unit): PixelSnapshot =
    iterateLocationsAndReturnSelf((x, y) => transformer(pixel(x, y)))

  /**
    *
    * @param callback
    *
    * @return
    */
  @inline
  private final
  def iterateLocationsAndReturnSelf(callback: (Int, Int) => Unit): PixelSnapshot = {
    iterateLocations(callback)
    this
  }

  /**
    *
    * @param callback
    *
    * @return
    */
  @inline
  private final
  def iterateComponentArrayValuesAndReturnSelf(callback: (Int, Int, Int, Int) => Unit): PixelSnapshot = {
    iterateComponentArrayValues(callback)
    this
  }

  /**
    *
    * @param callback
    *
    * @return
    */
  @inline
  private final
  def iterateComponentArrayIndicesAndReturnSelf(callback: Int => Unit): PixelSnapshot = {
    iterateComponentArrayIndices(callback)
    this
  }

  /**
    *
    * @param callback
    *
    * @return
    */
  @inline
  private final
  def iterateLocations(callback: (Int, Int) => Unit): Unit = {
    val e = MatrixEnumerator2D(0, 0, widthInPixels, heightInPixels, MESRightwardsDownwards)
    while (e.hasNextCell) {
      e.advance()
      callback(e.currentColumn, e.currentRow)
    }
  }

  /**
    *
    * @param callback
    *
    * @return
    */
  @inline
  final
  def iterateComponentArrayValues(callback: (Int, Int, Int, Int) => Unit): Unit =
    iterateComponentArrayIndices(i => callback(reds(i), greens(i), blues(i), opacities(i)))

  /**
    *
    * @param callback
    *
    * @return
    */
  @inline
  final
  def iterateComponentArrayIndices(callback: Int => Unit): Unit =
    for (i <- 0 until areaInPixels)
      callback(i)

  /**
    *
    */
  def toBitmap: Bitmap

  /**
    *
    * @param xInPixels
    * @param yInPixels
    *
    * @return
    */
  def pixel(
      xInPixels: Int,
      yInPixels: Int): Pixel

  /**
    *
    * @param xInPixels
    * @param yInPixels
    *
    * @return
    */
  def color(
      xInPixels: Int,
      yInPixels: Int): Color

  /**
    *
    * @return
    */
  @inline
  override final
  def iterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
    *
    * @return
    */
  @inline
  final
  def downwardsLeftwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
    *
    * @return
    */
  @inline
  final
  def leftwardsDownwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
    *
    * @return
    */
  @inline
  final
  def leftwardsUpwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
    *
    * @return
    */
  @inline
  final
  def downwardsRightwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
    *
    * @return
    */
  @inline
  final
  def rightwardsUpwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsDownwardsIterator(this)
  }

  /**
    *
    * @return
    */
  @inline
  final
  def upwardsLeftwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotUpwardsLeftwardsIterator(this)
  }

  /**
    *
    * @return
    */
  @inline
  final
  def upwardsRightwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotUpwardsRightwardsIterator(this)
  }

  /**
    *
    * @return
    */
  override
  def toString: String =
    s"PixelSnapshot ($widthInPixels x $heightInPixels pixels)"

  /**
    *
    */
  @inline
  final
  def checkForInvalidation(): Unit = {
    if (invalidation.isDone)
      throw PixelSnapshotInvalidatedError()
  }

  /**
    *
    * @param array
    * @param colorOfArray
    */
  @inline
  final
  def checkComponentArrayLength(
      array: Array[Int],
      colorOfArray: String): Unit = {

    if (array.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        s"Expected length for the given ${colorOfArray.toLowerCase} RGBA component array is " +
            s"$areaInPixels, but actually was ${array.length}")
  }

  /**
    *
    * @param arrayPosition
    *
    * @return
    */
  @inline
  protected final
  def getColorInternal(arrayPosition: Int): Color = {
    Color(
      reds(arrayPosition),
      greens(arrayPosition),
      blues(arrayPosition),
      opacities(arrayPosition))
  }

  /**
    *
    * @param arrayPosition
    * @param newColor
    */
  @inline
  protected final
  def setColorInternal(
      arrayPosition: Int,
      newColor: Color): Unit = {

    reds(arrayPosition) = newColor.red
    greens(arrayPosition) = newColor.green
    blues(arrayPosition) = newColor.blue
    opacities(arrayPosition) = newColor.opacity
  }

  /**
    *
    * @param xInPixels
    * @param yInPixels
    *
    * @return
    */
  @inline
  protected final
  def arrayPosition(
      xInPixels: Int,
      yInPixels: Int): Int = {

    yInPixels * widthInPixels + xInPixels
  }

}
