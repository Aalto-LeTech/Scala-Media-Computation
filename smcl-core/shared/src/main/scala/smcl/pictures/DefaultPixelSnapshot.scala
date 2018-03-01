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


import smcl.infrastructure.{BitmapBufferAdapter, DoneStatus}




/**
 *
 *
 * @param copiedBitmap
 *
 * @author Aleksi Lukkarinen
 */
class DefaultPixelSnapshot protected[pictures](
    private val copiedBitmap: Bitmap)
    extends PixelSnapshot {

  /** */
  private[this]
  val buffer: BitmapBufferAdapter = copiedBitmap.buffer.get

  /** */
  override
  val widthInPixels: Int = buffer.widthInPixels

  /** */
  override
  val heightInPixels: Int = buffer.heightInPixels

  /** */
  override
  val areaInPixels: Int = heightInPixels * widthInPixels

  /** */
  override
  val minXInPixels: Int = 0

  /** */
  override
  val maxXInPixels: Int = widthInPixels - 1

  /** */
  override
  val minYInPixels: Int = 0

  /** */
  override
  val maxYInPixels: Int = heightInPixels - 1

  /** */
  override
  val invalidation: DoneStatus = DoneStatus()

  /** */
  private[this]
  var (_reds, _greens, _blues, _opacities) =
    buffer.colorComponentArrays

  /** */
  @inline
  private[pictures]
  def reds: Array[Int] = _reds

  /** */
  @inline
  private[pictures]
  def greens: Array[Int] = _greens

  /** */
  @inline
  private[pictures]
  def blues: Array[Int] = _blues

  /** */
  @inline
  private[pictures]
  def opacities: Array[Int] = _opacities

  /**
   *
   *
   * @return
   */
  @inline
  def redComponentArray: Array[Int] = {
    checkForInvalidation()
    _reds.clone
  }

  /**
   *
   *
   * @return
   */
  @inline
  def greenComponentArray: Array[Int] = {
    checkForInvalidation()
    _greens.clone
  }

  /**
   *
   *
   * @return
   */
  @inline
  def blueComponentArray: Array[Int] = {
    checkForInvalidation()
    _blues.clone
  }

  /**
   *
   *
   * @return
   */
  @inline
  def opacityComponentArray: Array[Int] = {
    checkForInvalidation()
    _opacities.clone
  }

  /**
   *
   *
   * @return
   */
  @inline
  def componentArrays: (Array[Int], Array[Int], Array[Int], Array[Int]) = {
    checkForInvalidation()
    (redComponentArray, greenComponentArray, blueComponentArray, opacityComponentArray)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def setRedComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "red")

    _reds = array.clone
  }

  /**
   *
   *
   * @return
   */
  @inline
  def setGreenComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "green")

    _greens = array.clone
  }

  /**
   *
   *
   * @return
   */
  @inline
  def setBlueComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "blue")

    _blues = array.clone
  }

  /**
   *
   *
   * @return
   */
  @inline
  def setOpacityComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "opacity")

    _opacities = array.clone
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def setComponentArrays(
      reds: Array[Int],
      greens: Array[Int],
      blues: Array[Int],
      opacities: Array[Int]): Unit = {

    checkForInvalidation()

    checkComponentArrayLength(reds, "red")
    checkComponentArrayLength(greens, "green")
    checkComponentArrayLength(blues, "blue")
    checkComponentArrayLength(opacities, "opacity")

    _reds = reds.clone
    _greens = greens.clone
    _blues = blues.clone
    _opacities = opacities.clone
  }

  /**
   *
   */
  @inline
  def toBitmap: Bitmap = {
    checkForInvalidation()
    buffer.setColorComponentArrays(_reds, _greens, _blues, _opacities)
    invalidation.setDone()
    copiedBitmap
  }

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
      y: Int): Pixel = {

    checkForInvalidation()

    require(x >= 0 && x < widthInPixels,
      s"X coordinate is out or range ($minXInPixels..$maxXInPixels)")

    require(x >= 0 && x < widthInPixels,
      s"Y coordinate is out or range ($minYInPixels..$maxYInPixels)")

    Pixel(
      this,
      minXInPixels, maxXInPixels,
      minYInPixels, maxYInPixels,
      x, y)
  }

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
    PixelSnapshotDownwardsLeftwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def leftwardsDownwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotLeftwardsDownwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def leftwardsUpwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotLeftwardsUpwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def downwardsRightwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotDownwardsRightwardsIterator(this)
  }

  /**
   *
   * @return
   */
  @inline
  def rightwardsUpwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    PixelSnapshotRightwardsUpwardsIterator(this)
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

}
