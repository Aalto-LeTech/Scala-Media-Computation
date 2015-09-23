package aalto.smcl.bitmaps


import aalto.smcl.infrastructure.{PlatformBitmapBuffer, SMCLInvalidColorComponentArrayLengthError}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class PixelSnapshot private[smcl](relatedBitmap: Bitmap) {

  /** */
  val widthInPixels: Int = relatedBitmap.widthInPixels

  /** */
  val heightInPixels: Int = relatedBitmap.heightInPixels

  /** */
  val areaInPixels: Int = relatedBitmap.areaInPixels

  /** */
  private[this] val buffer: PlatformBitmapBuffer =
    relatedBitmap.toRenderedRepresentation.copyPortionXYWH(0, 0, widthInPixels, heightInPixels)

  private[this] var (_reds, _greens, _blues, _opacities) =
    buffer.colorComponentArrays()


  /**
   *
   *
   * @return
   */
  def redComponentArray(): Array[Int] = _reds.clone()

  /**
   *
   *
   * @return
   */
  def greenComponentArray(): Array[Int] = _greens.clone()

  /**
   *
   *
   * @return
   */
  def blueComponentArray(): Array[Int] = _blues.clone()

  /**
   *
   *
   * @return
   */
  def opacityComponentArray(): Array[Int] = _opacities.clone()

  /**
   *
   *
   * @return
   */
  def componentArrays(): (Array[Int], Array[Int], Array[Int], Array[Int]) =
    (redComponentArray(), greenComponentArray(), blueComponentArray(), opacityComponentArray())


  /**
   *
   *
   * @return
   */
  def setRedComponentArray(array: Array[Int]): Unit = _reds = array.clone()

  /**
   *
   *
   * @return
   */
  def setGreenComponentArray(array: Array[Int]): Unit = _greens = array.clone()

  /**
   *
   *
   * @return
   */
  def setBlueComponentArray(array: Array[Int]): Unit = _blues = array.clone()

  /**
   *
   *
   * @return
   */
  def setOpacityComponentArray(array: Array[Int]): Unit = _opacities = array.clone()

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
      throw new SMCLInvalidColorComponentArrayLengthError(
        "Expected length for the given red RGBA component array is " +
            s"$areaInPixels, but actually was ${reds.length}")

    if (greens.length != areaInPixels)
      throw new SMCLInvalidColorComponentArrayLengthError(
        "Expected length for the given green RGBA component array is " +
            s"$areaInPixels, but actually was ${greens.length}")

    if (blues.length != areaInPixels)
      throw new SMCLInvalidColorComponentArrayLengthError(
        "Expected length for the given blue RGBA component array is " +
            s"$areaInPixels, but actually was ${blues.length}")

    if (opacities.length != areaInPixels)
      throw new SMCLInvalidColorComponentArrayLengthError(
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
  def apply(): Unit = {
    buffer.setColorComponentArrays(_reds, _greens, _blues, _opacities)

    relatedBitmap.applyPixelSnapshot(buffer)
  }

  /**
   *
   *
   * @param x
   * @param y
   * @return
   */
  def pixel(x: Int, y: Int) = new {

    require(x >= 0 && x < widthInPixels, s"Given x coordinate $x is outside of the bitmap.")
    require(y >= 0 && y < heightInPixels, s"Given y coordinate $y is outside of the bitmap.")

    private[this] val linearPosition: Int = (y * widthInPixels + x) - 1

    def red(): Int = _reds(linearPosition)

    def red_=(value: Int): Unit = _reds(linearPosition) = value

    def green(): Int = _greens(linearPosition)

    def green_=(value: Int): Unit = _greens(linearPosition) = value

    def blue(): Int = _blues(linearPosition)

    def blue_=(value: Int): Unit = _blues(linearPosition) = value

    def opacity(): Int = _opacities(linearPosition)

    def opacity_=(value: Int): Unit = _opacities(linearPosition) = value

  }

}
