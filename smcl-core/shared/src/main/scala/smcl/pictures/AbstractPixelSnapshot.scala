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
import smcl.infrastructure.exceptions.InvalidColorComponentArrayLengthError
import smcl.pictures.exceptions.PixelSnapshotInvalidatedError




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractPixelSnapshot
    extends Iterable[Pixel] {

  /**
   *
   *
   * @return
   */
  def widthInPixels: Int

  /**
   *
   *
   * @return
   */
  def heightInPixels: Int

  /** */
  val areaInPixels: Int = heightInPixels * widthInPixels

  /** */
  val minXInPixels: Int = 0

  /** */
  val maxXInPixels: Int = widthInPixels - 1

  /** */
  val minYInPixels: Int = 0

  /** */
  val maxYInPixels: Int = heightInPixels - 1

  /** */
  val invalidation: DoneStatus = DoneStatus()

  /**
   *
   *
   * @return
   */
  def redComponentArray: Array[Int]

  /**
   *
   *
   * @return
   */
  def greenComponentArray: Array[Int]

  /**
   *
   *
   * @return
   */
  def blueComponentArray: Array[Int]

  /**
   *
   *
   * @return
   */
  def opacityComponentArray: Array[Int]

  /**
   *
   *
   * @return
   */
  def componentArrays: (Array[Int], Array[Int], Array[Int], Array[Int])

  /**
   *
   *
   * @return
   */
  def setRedComponentArray(array: Array[Int]): Unit

  /**
   *
   *
   * @return
   */
  def setGreenComponentArray(array: Array[Int]): Unit

  /**
   *
   *
   * @return
   */
  def setBlueComponentArray(array: Array[Int]): Unit

  /**
   *
   *
   * @return
   */
  def setOpacityComponentArray(array: Array[Int]): Unit

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

    checkForInvalidation()

    checkComponentArrayLength(reds, "red")
    checkComponentArrayLength(greens, "green")
    checkComponentArrayLength(blues, "blue")
    checkComponentArrayLength(opacities, "opacity")
  }

  /**
   *
   */
  def toBitmap: Bitmap

  /**
   *
   *
   * @param x
   * @param y
   *
   * @return
   */
  def pixel(
      x: Int,
      y: Int): Pixel

  /**
   *
   * @return
   */
  override
  def iterator: AbstractPixelSnapshotIterator

  /**
   *
   * @return
   */
  def downwardsLeftwardsIterator: AbstractPixelSnapshotIterator

  /**
   *
   * @return
   */
  def leftwardsDownwardsIterator: AbstractPixelSnapshotIterator

  /**
   *
   * @return
   */
  def leftwardsUpwardsIterator: AbstractPixelSnapshotIterator

  /**
   *
   * @return
   */
  def downwardsRightwardsIterator: AbstractPixelSnapshotIterator

  /**
   *
   * @return
   */
  def rightwardsUpwardsIterator: AbstractPixelSnapshotIterator

  /**
   *
   * @return
   */
  def upwardsLeftwardsIterator: AbstractPixelSnapshotIterator

  /**
   *
   * @return
   */
  def upwardsRightwardsIterator: AbstractPixelSnapshotIterator

  /**
   *
   *
   * @return
   */
  override
  def toString: String =
    s"PixelSnapshot $widthInPixels x $heightInPixels"

  /**
   *
   *
   */
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
  def checkComponentArrayLength(
      array: Array[Int],
      colorOfArray: String): Unit = {

    if (array.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        s"Expected length for the given ${colorOfArray.toLowerCase} RGBA component array is " +
            s"$areaInPixels, but actually was ${array.length}")
  }

}
