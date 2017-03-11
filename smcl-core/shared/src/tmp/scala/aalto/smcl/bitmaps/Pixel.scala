package aalto.smcl.bitmaps


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @param relatedPixelSnapshot
 * @param currentXInPixels
 * @param currentYInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Pixel private[bitmaps](
  relatedPixelSnapshot: PixelSnapshot,
  MinXInPixels: Int,
  MaxXInPixels: Int,
  MinYInPixels: Int,
  MaxYInPixels: Int,
  currentXInPixels: Int,
  currentYInPixels: Int) extends SMCLInitializationInvoker {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null


  /** */
  private[this] lazy val linearPosition: Int =
    currentYInPixels * (MaxXInPixels - MinXInPixels + 1) + currentXInPixels


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

  /**
   *
   *
   * @return
   */
  override def toString: String =
    s"Pixel($currentXInPixels, $currentYInPixels): $red - $green - $blue - $opacity"

}
