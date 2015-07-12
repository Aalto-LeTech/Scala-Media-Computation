package aalto.smcl.images

import java.util.{ Calendar => JCalendar }
import aalto.smcl._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class BitmapImage(
  widthInPixelsOption: Option[Int] = None,
  heightInPixelsOption: Option[Int] = None,
  initialBackgroundColorOption: Option[Int] = None,
  titleOption: Option[String] = None,
  descriptionOption: Option[String] = None,
  courseNameOption: Option[String] = None,
  assignmentOption: Option[String] = None,
  creatorNameOption: Option[String] = None)
    extends PixelRectangle {

  /** Width of this [[BitmapImage]]. */  
  val widthInPixels = widthInPixelsOption getOrElse DEFAULT_IMAGE_WIDTH_IN_PIXELS
  
  /** Height of this [[BitmapImage]]. */  
  val heightInPixels = heightInPixelsOption getOrElse DEFAULT_IMAGE_HEIGHT_IN_PIXELS
  
  val imageModel = new BitmapImageModel(this, widthInPixels, heightInPixels, initialBackgroundColorOption)

  /** Creation time and date of this image. */
  val created = JCalendar.getInstance.getTime

  /**
   *
   */
  override def toString() = {
    val p = imageModel.pixelBuffer

    s"[BitmapImage ${widthInPixels}x${heightInPixels} px" +
      titleOption.fold[String](STR_EMPTY)(t => s"; Title: '${t}'") +
      s"; created: ${created}]"
  }

}
