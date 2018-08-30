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
import smcl.infrastructure.{BitmapBufferAdapter, DoneStatus}




/**
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
  private[pictures] override final
  def reds: Array[Int] = _reds

  /** */
  @inline
  private[pictures] override final
  def greens: Array[Int] = _greens

  /** */
  @inline
  private[pictures] override final
  def blues: Array[Int] = _blues

  /** */
  @inline
  private[pictures] override final
  def opacities: Array[Int] = _opacities

  /**
    *
    * @return
    */
  @inline
  override final
  def redComponentArray: Array[Int] = {
    checkForInvalidation()
    _reds.clone
  }

  /**
    *
    * @return
    */
  @inline
  override final
  def greenComponentArray: Array[Int] = {
    checkForInvalidation()
    _greens.clone
  }

  /**
    *
    * @return
    */
  @inline
  override final
  def blueComponentArray: Array[Int] = {
    checkForInvalidation()
    _blues.clone
  }

  /**
    *
    * @return
    */
  @inline
  override final
  def opacityComponentArray: Array[Int] = {
    checkForInvalidation()
    _opacities.clone
  }

  /**
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
    * @return
    */
  @inline
  override final
  def setRedComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "red")

    _reds = array.clone
  }

  /**
    *
    * @return
    */
  @inline
  override final
  def setGreenComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "green")

    _greens = array.clone
  }

  /**
    *
    * @return
    */
  @inline
  override final
  def setBlueComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "blue")

    _blues = array.clone
  }

  /**
    *
    * @return
    */
  @inline
  override final
  def setOpacityComponentArray(array: Array[Int]): Unit = {
    checkForInvalidation()
    checkComponentArrayLength(array, "opacity")

    _opacities = array.clone
  }

  /**
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

    _reds = reds.clone
    _greens = greens.clone
    _blues = blues.clone
    _opacities = opacities.clone
  }

  /**
    *
    */
  @inline
  override final
  def toBitmap: Bitmap = {
    checkForInvalidation()
    buffer.setColorComponentArrays(_reds, _greens, _blues, _opacities)
    invalidation.setDone()
    copiedBitmap
  }

  /**
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

    require(xInPixels >= 0 && xInPixels < widthInPixels,
      s"X coordinate is out or range ($minXInPixels..$maxXInPixels)")

    require(yInPixels >= 0 && yInPixels < heightInPixels,
      s"Y coordinate is out or range ($minYInPixels..$maxYInPixels)")

    Pixel(
      this,
      minXInPixels, maxXInPixels,
      minYInPixels, maxYInPixels,
      xInPixels, yInPixels)
  }

  /**
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

    require(xInPixels >= 0 && xInPixels < widthInPixels,
      s"X coordinate is out or range ($minXInPixels..$maxXInPixels)")

    require(yInPixels >= 0 && yInPixels < heightInPixels,
      s"Y coordinate is out or range ($minYInPixels..$maxYInPixels)")

    getColorInternal(arrayPosition(xInPixels, yInPixels))
  }

}
