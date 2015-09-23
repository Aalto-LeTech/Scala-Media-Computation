package aalto.smcl.bitmaps


/**
 *
 *
 * @param relatedPixelSnapshot
 * @param x
 * @param y
 *
 * @author Aleksi Lukkarinen
 */
case class Pixel private[bitmaps](relatedPixelSnapshot: PixelSnapshot, x: Int, y: Int) {

  require(x >= 0 && x < relatedPixelSnapshot.widthInPixels, s"Given x coordinate $x is outside of the bitmap.")
  require(y >= 0 && y < relatedPixelSnapshot.heightInPixels, s"Given y coordinate $y is outside of the bitmap.")

  private[this] val linearPosition: Int = y * relatedPixelSnapshot.widthInPixels + x


  /**
   *
   *
   * @return
   */
  def red: Int = relatedPixelSnapshot.reds()(linearPosition)

  /**
   *
   *
   * @param value
   */
  def red_=(value: Int): Unit =
    relatedPixelSnapshot.reds()(linearPosition) = value

  /**
   *
   *
   * @return
   */
  def green: Int = relatedPixelSnapshot.greens()(linearPosition)

  /**
   *
   *
   * @param value
   */
  def green_=(value: Int): Unit =
    relatedPixelSnapshot.greens()(linearPosition) = value

  /**
   *
   *
   * @return
   */
  def blue: Int = relatedPixelSnapshot.blues()(linearPosition)

  /**
   *
   *
   * @param value
   */
  def blue_=(value: Int): Unit =
    relatedPixelSnapshot.blues()(linearPosition) = value

  /**
   *
   *
   * @return
   */
  def opacity: Int = relatedPixelSnapshot.opacities()(linearPosition)

  /**
   *
   *
   * @param value
   */
  def opacity_=(value: Int): Unit =
    relatedPixelSnapshot.opacities()(linearPosition) = value

}
