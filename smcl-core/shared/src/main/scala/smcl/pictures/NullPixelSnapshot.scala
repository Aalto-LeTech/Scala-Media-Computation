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




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class NullPixelSnapshot protected[pictures](bitmap: Bitmap)
    extends PixelSnapshot {

  /** */
  private
  val EmptyColorArray: Array[Int] = Array.empty

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

  /** */
  @inline
  private[pictures] final
  def reds: Array[Int] = EmptyColorArray

  /** */
  @inline
  private[pictures] final
  def greens: Array[Int] = EmptyColorArray

  /** */
  @inline
  private[pictures] final
  def blues: Array[Int] = EmptyColorArray

  /** */
  @inline
  private[pictures] final
  def opacities: Array[Int] = EmptyColorArray

  /**
   *
   *
   * @return
   */
  override
  val redComponentArray: Array[Int] = {
    checkForInvalidation()
    EmptyColorArray.clone
  }

  /**
   *
   *
   * @return
   */
  override
  val greenComponentArray: Array[Int] = {
    checkForInvalidation()
    EmptyColorArray.clone
  }

  /**
   *
   *
   * @return
   */
  override
  val blueComponentArray: Array[Int] = {
    checkForInvalidation()
    EmptyColorArray.clone
  }

  /**
   *
   *
   * @return
   */
  override
  val opacityComponentArray: Array[Int] = {
    checkForInvalidation()
    EmptyColorArray.clone
  }

  /**
   *
   *
   * @return
   */
  @inline
  override final
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
  override final
  def setRedComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "red")
  }

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def setGreenComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "green")
  }

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def setBlueComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "blue")
  }

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def setOpacityComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "opacity")
  }

  /**
   *
   *
   * @return
   */
  @inline
  override final
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
  @inline
  override final
  def toBitmap: Bitmap = {
    checkForInvalidation()
    invalidation.setDone()

    bitmap
  }

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   *
   * @return
   */
  @inline
  override final
  def pixel(
      xInPixels: Int,
      yInPixels: Int): Pixel = {

    checkForInvalidation()

    throw new IllegalStateException("This pixel snapshot has no pixels")
  }

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   *
   * @return
   */
  @inline
  override final
  def color(
      xInPixels: Int,
      yInPixels: Int): Color = {

    checkForInvalidation()

    throw new IllegalStateException("This pixel snapshot has no pixels")
  }

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def toString: String = s"PixelSnapshot (0 x 0 pixels)"

}
