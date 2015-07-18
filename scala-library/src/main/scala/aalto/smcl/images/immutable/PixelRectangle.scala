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

  /** The range of numbers from zero to the width of this rectangle. */
  lazy val widthRange: Range.Inclusive = 0 to (widthInPixels - 1)

  /** The range of numbers from zero to the height of this rectangle. */
  lazy val heightRange: Range.Inclusive = 0 to (heightInPixels - 1)

  /** Total number of pixels occupied by the area of this rectangle. */
  lazy val pixelCount: Int = widthInPixels * heightInPixels

}
