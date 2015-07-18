package aalto.smcl.images.immutable


/**
 * A rectangle having its dimensions measured in pixels.
 *
 * @author Aleksi Lukkarinen
 */
private[images] trait PixelRectangle {

  /** Width of this rectangle in pixels. */
  def widthInPixels: Int

  /** Height of this rectangle in pixels. */
  def heightInPixels: Int

  /** The range of numbers from zero until the width of this rectangle. */
  lazy val widthRange: Range = 0 until widthInPixels

  /** The range of numbers from zero until the height of this rectangle. */
  lazy val heightRange: Range = 0 until heightInPixels

  /** Total number of pixels occupied by the area of this rectangle. */
  lazy val pixelCount: Int = widthInPixels * heightInPixels

}
