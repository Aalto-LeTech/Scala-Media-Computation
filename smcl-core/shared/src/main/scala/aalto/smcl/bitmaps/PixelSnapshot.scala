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

package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.fullfeatured.AbstractBitmap
import aalto.smcl.infrastructure.BitmapBufferAdapter
import aalto.smcl.infrastructure.exceptions.InvalidColorComponentArrayLengthError




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class PixelSnapshot[BitmapType <: AbstractBitmap] private[bitmaps](
    val widthInPixels: Int,
    val heightInPixels: Int,
    val relatedBitmap: BitmapType,
    private[this] val buffer: BitmapBufferAdapter,
    private[this] val receiver: PixelSnapshotReceiver[BitmapType])
    extends Iterable[Pixel[BitmapType]]
            with PixelRectangle {

  private[this]
  var (_reds, _greens, _blues, _opacities) =
    buffer.colorComponentArrays

  /** */
  private[bitmaps]
  def reds: Array[Int] = _reds

  /** */
  private[bitmaps]
  def greens: Array[Int] = _greens

  /** */
  private[bitmaps]
  def blues: Array[Int] = _blues

  /** */
  private[bitmaps]
  def opacities: Array[Int] = _opacities


  /**
   *
   *
   * @return
   */
  def redComponentArray: Array[Int] = _reds.clone

  /**
   *
   *
   * @return
   */
  def greenComponentArray: Array[Int] = _greens.clone

  /**
   *
   *
   * @return
   */
  def blueComponentArray: Array[Int] = _blues.clone

  /**
   *
   *
   * @return
   */
  def opacityComponentArray: Array[Int] = _opacities.clone

  /**
   *
   *
   * @return
   */
  def componentArrays: (Array[Int], Array[Int], Array[Int], Array[Int]) =
    (redComponentArray, greenComponentArray, blueComponentArray, opacityComponentArray)


  /**
   *
   *
   * @return
   */
  def setRedComponentArray(array: Array[Int]): Unit = _reds = array.clone

  /**
   *
   *
   * @return
   */
  def setGreenComponentArray(array: Array[Int]): Unit = _greens = array.clone

  /**
   *
   *
   * @return
   */
  def setBlueComponentArray(array: Array[Int]): Unit = _blues = array.clone

  /**
   *
   *
   * @return
   */
  def setOpacityComponentArray(array: Array[Int]): Unit = _opacities = array.clone

  /**
   *
   *
   * @return
   */
  def setComponentArrays(
      reds: Array[Int],
      greens: Array[Int],
      blues: Array[Int],
      opacities: Array[Int]): Unit = {

    if (reds.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        "Expected length for the given red RGBA component array is " +
            s"$areaInPixels, but actually was ${reds.length}")

    if (greens.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        "Expected length for the given green RGBA component array is " +
            s"$areaInPixels, but actually was ${greens.length}")

    if (blues.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        "Expected length for the given blue RGBA component array is " +
            s"$areaInPixels, but actually was ${blues.length}")

    if (opacities.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        "Expected length for the given opacity RGBA component array is " +
            s"$areaInPixels, but actually was ${opacities.length}")

    setRedComponentArray(reds)
    setGreenComponentArray(greens)
    setBlueComponentArray(blues)
    setOpacityComponentArray(opacities)
  }

  /**
   *
   */
  def toBitmap: BitmapType = {
    buffer.setColorComponentArrays(_reds, _greens, _blues, _opacities)

    receiver.applyPixelSnapshot(buffer)
  }

  /**
   *
   *
   * @param x
   * @param y
   *
   * @return
   */
  def pixel(x: Int, y: Int): Pixel[BitmapType] = {
    Pixel(
      this,
      0, widthInPixels - 1,
      0, heightInPixels - 1,
      x, y)
  }

  /**
   *
   * @return
   */
  override def iterator: PixelSnapshotRightwardsDownwardsIterator[BitmapType] = {
    PixelSnapshotRightwardsDownwardsIterator[BitmapType](this)
  }

  /**
   *
   * @return
   */
  def downwardsLeftwardsIterator: PixelSnapshotDownwardsLeftwardsIterator[BitmapType] = {
    PixelSnapshotDownwardsLeftwardsIterator[BitmapType](this)
  }

  /**
   *
   * @return
   */
  def leftwardsDownwardsIterator: PixelSnapshotLeftwardsDownwardsIterator[BitmapType] = {
    PixelSnapshotLeftwardsDownwardsIterator[BitmapType](this)
  }

  /**
   *
   * @return
   */
  def leftwardsUpwardsIterator: PixelSnapshotLeftwardsUpwardsIterator[BitmapType] = {
    PixelSnapshotLeftwardsUpwardsIterator[BitmapType](this)
  }

  /**
   *
   * @return
   */
  def downwardsRightwardsIterator: PixelSnapshotDownwardsRightwardsIterator[BitmapType] = {
    PixelSnapshotDownwardsRightwardsIterator[BitmapType](this)
  }

  /**
   *
   * @return
   */
  def rightwardsUpwardsIterator: PixelSnapshotRightwardsUpwardsIterator[BitmapType] = {
    PixelSnapshotRightwardsUpwardsIterator[BitmapType](this)
  }

  /**
   *
   * @return
   */
  def upwardsLeftwardsIterator: PixelSnapshotUpwardsLeftwardsIterator[BitmapType] = {
    PixelSnapshotUpwardsLeftwardsIterator[BitmapType](this)
  }

  /**
   *
   * @return
   */
  def upwardsRightwardsIterator: PixelSnapshotUpwardsRightwardsIterator[BitmapType] = {
    PixelSnapshotUpwardsRightwardsIterator[BitmapType](this)
  }

  /**
   *
   *
   * @return
   */
  override def toString: String =
    s"PixelSnapshot $widthInPixels x $heightInPixels"

}
