package aalto.smcl.images.immutable

import aalto.smcl._
import aalto.smcl.images._
import aalto.smcl.images.immutable._
import java.util.{Calendar => JCalendar}

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class BitmapImage(
  private val widthInPixelsOption: Option[Int] = None,
  private val heightInPixelsOption: Option[Int] = None,
  private val initialBackgroundColorOption: Option[Int] = None,
  titleOption: Option[String] = None,
  descriptionOption: Option[String] = None,
  courseNameOption: Option[String] = None,
  assignmentOption: Option[String] = None,
  creatorNameOption: Option[String] = None) extends {

  /** Width of this [[BitmapImage]]. */
  override val widthInPixels = widthInPixelsOption.fold(DEFAULT_IMAGE_WIDTH_IN_PIXELS) { width =>
    require(width > 0, s"Width of the image must be greater than zero (was $width)")
    width
  }

  /** Height of this [[BitmapImage]]. */
  override val heightInPixels = heightInPixelsOption.fold(DEFAULT_IMAGE_HEIGHT_IN_PIXELS) { height =>
    require(height > 0, s"Height of the image must be greater than zero (was $height)")
    height
  }

  /** Initial background color of this [[BitmapImage]] (may not be the actual background color at a later time). */
  override val initialBackgroundColor = initialBackgroundColorOption getOrElse 0xFF000000

  /** Creation time and date of this image. */
  val created = JCalendar.getInstance.getTime

} with Bitmap with PixelRectangle with ColorableBackground with Immutable {

  /**
   *
   */
  override def toString() = {
    s"[BitmapImage ${widthInPixels}x${heightInPixels} px" +
      titleOption.fold[String](STR_EMPTY)(t => s"; Title: '${t}'") +
      s"; created: ${created}]"
  }

}
