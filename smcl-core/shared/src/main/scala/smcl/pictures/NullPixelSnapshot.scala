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


import smcl.infrastructure.DoneStatus




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class NullPixelSnapshot protected[pictures](bitmap: Bitmap)
    extends AbstractPixelSnapshot {

  private
  val NullIterator: AbstractPixelSnapshotIterator = NullPixelSnapshotIterator(this)

  /** */
  private
  val ZeroColorArray: Array[Int] = Array.empty

  /** */
  override
  val widthInPixels: Int = 0

  /** */
  override
  val heightInPixels: Int = 0

  /** */
  override
  val areaInPixels: Int = 0

  /** */
  override
  val minXInPixels: Int = -1

  /** */
  override
  val maxXInPixels: Int = -1

  /** */
  override
  val minYInPixels: Int = -1

  /** */
  override
  val maxYInPixels: Int = -1

  /** */
  override
  val invalidation: DoneStatus = DoneStatus()

  /**
   *
   *
   * @return
   */
  override
  val redComponentArray: Array[Int] = {
    checkForInvalidation()
    ZeroColorArray.clone
  }

  /**
   *
   *
   * @return
   */
  override
  val greenComponentArray: Array[Int] = {
    checkForInvalidation()
    ZeroColorArray.clone
  }

  /**
   *
   *
   * @return
   */
  override
  val blueComponentArray: Array[Int] = {
    checkForInvalidation()
    ZeroColorArray.clone
  }

  /**
   *
   *
   * @return
   */
  override
  val opacityComponentArray: Array[Int] = {
    checkForInvalidation()
    ZeroColorArray.clone
  }

  /**
   *
   *
   * @return
   */
  override
  def componentArrays: (Array[Int], Array[Int], Array[Int], Array[Int]) = {
    checkForInvalidation()
    (redComponentArray, greenComponentArray, blueComponentArray, opacityComponentArray)
  }

  /**
   *
   *
   * @return
   */
  override
  def setRedComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "red")
  }

  /**
   *
   *
   * @return
   */
  override
  def setGreenComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "green")
  }

  /**
   *
   *
   * @return
   */
  override
  def setBlueComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "blue")
  }

  /**
   *
   *
   * @return
   */
  override
  def setOpacityComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "opacity")
  }

  /**
   *
   *
   * @return
   */
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
  }

  /**
   *
   */
  override
  def toBitmap: Bitmap = {
    checkForInvalidation()
    invalidation.setDone()

    bitmap
  }

  /**
   *
   *
   * @param x
   * @param y
   *
   * @return
   */
  override
  def pixel(
      x: Int,
      y: Int): Pixel = {

    checkForInvalidation()

    throw new IllegalStateException(
      "This pixel snapshot has no pixels to modify")
  }

  /**
   *
   * @return
   */
  override
  def iterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    NullIterator
  }

  /**
   *
   * @return
   */
  override
  def downwardsLeftwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    NullIterator
  }

  /**
   *
   * @return
   */
  override
  def leftwardsDownwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    NullIterator
  }

  /**
   *
   * @return
   */
  override
  def leftwardsUpwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    NullIterator
  }

  /**
   *
   * @return
   */
  override
  def downwardsRightwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    NullIterator
  }

  /**
   *
   * @return
   */
  override
  def rightwardsUpwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    NullIterator
  }

  /**
   *
   * @return
   */
  override
  def upwardsLeftwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    NullIterator
  }

  /**
   *
   * @return
   */
  override
  def upwardsRightwardsIterator: AbstractPixelSnapshotIterator = {
    checkForInvalidation()
    NullIterator
  }

  /**
   *
   *
   * @return
   */
  override
  def toString: String = s"PixelSnapshot (no pixels)"

}
