package aalto.smcl.bitmaps.immutable


import scala.swing.Dimension




/**
 * A rectangle having its dimensions measured in pixels.
 *
 * @author Aleksi Lukkarinen
 */
trait PixelRectangle {

  /** Width of this rectangle in pixels. */
  def widthInPixels: Int

  /** Height of this rectangle in pixels. */
  def heightInPixels: Int

  /** The range of numbers from zero until the width of this rectangle. */
  lazy val widthRangeInPixels: Range = 0 until widthInPixels

  /** The range of numbers from zero until the height of this rectangle. */
  lazy val heightRangeInPixels: Range = 0 until heightInPixels

  /** Dimensions (width and height) of this rectangle. */
  private[smcl] lazy val sizeInPixels: Dimension = new Dimension(widthInPixels, heightInPixels)

  /** Area of this rectangle in pixels (equals to `pixelCount`). */
  lazy val areaInPixels: Int = widthInPixels * heightInPixels

  /** Total number of pixels occupied by the area of this rectangle (equals to `areaInPixels`). */
  lazy val pixelCount: Int = areaInPixels

}
