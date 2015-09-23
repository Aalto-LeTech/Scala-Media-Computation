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

  /** */
  private[bitmaps] def reds(): Array[Int] = _reds

  /** */
  private[bitmaps] def greens(): Array[Int] = _greens

  /** */
  private[bitmaps] def blues(): Array[Int] = _blues

  /** */
  private[bitmaps] def opacities(): Array[Int] = _opacities


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
  def apply(): ImmutableBitmap = {
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
  def pixel(x: Int, y: Int) = new Pixel(this, x, y)

}
