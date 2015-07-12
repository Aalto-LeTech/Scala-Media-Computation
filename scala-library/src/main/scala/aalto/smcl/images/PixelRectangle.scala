package aalto.smcl.images

/**
 * A rectangle having its dimensions measured in pixels.
 *
 * @author Aleksi Lukkarinen
 */
private[images] trait PixelRectangle {

  /**
   * Width of this rectangle in pixels.
   */
  def widthInPixels: Int
  
  /**
   * Height of this rectangle in pixels.
   */
  def heightInPixels: Int

}