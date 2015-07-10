package aalto.smcl.images

import java.util.Calendar

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapImage {

  /** Default width of [[BitmapImage]] instances created without giving width. */
  val DEFAULT_WIDTH_IN_PIXELS: Int = 10

  /** Default height of [[BitmapImage]] instances created without giving height. */
  val DEFAULT_HEIGHT_IN_PIXELS: Int = 10

  /**
   *
   */
  def apply(
    widthInPixels: Int = BitmapImage.DEFAULT_WIDTH_IN_PIXELS,
    heightInPixels: Int = BitmapImage.DEFAULT_HEIGHT_IN_PIXELS,
    initialBackgroundColor: Option[Int] = None,
    title: Option[String] = None,
    description: Option[String] = None,
    courseName: Option[String] = None,
    assignment: Option[String] = None,
    creatorName: Option[String] = None): BitmapImage = {

    val imageController = new BitmapImage(title, description, courseName, assignment, creatorName)

    val imageModel = BitmapImageModel(
      imageController,
      widthInPixels,
      heightInPixels,
      initialBackgroundColor)

    imageController.model = Option[BitmapImageModel](imageModel)

    return imageController
  }
}

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class BitmapImage(
    var title: Option[String],
    var description: Option[String],
    var courseName: Option[String],
    var assignment: Option[String],
    var creatorName: Option[String]) {

  private val EMPTY_STRING = ""

  // Messages
  private val MSG_MODEL_CAN_BE_SET_ONLY_ONCE = "Model can be set only once, and it was already set."

  /** Represents the pixels of this image via a [[BitmapImageModel]] instance. */
  private[this] var _model: Option[BitmapImageModel] = None

  /** Creation time and date of this image. */
  val created = Calendar.getInstance.getTime

  /**
   *  Returns the [[BitmapImageModel]] instance related to this image.
   */
  def model: BitmapImageModel = _model.get

  /**
   * Sets the [[BitmapImageModel]] instance related to this image.
   * It can be set only once and must be set during the instantiation
   * in the companion object's `apply()` method.
   */
  private def model_=(newModel: Option[BitmapImageModel]): Unit = {
    _model.foreach { m => throw new IllegalStateException(MSG_MODEL_CAN_BE_SET_ONLY_ONCE) }
    _model = newModel
  }

  /**
   *
   */
  override def toString() = {
    val p = model.pixelBuffer

    s"[BitmapImage ${p.getWidth}x${p.getWidth} px" +
      title.fold[String](EMPTY_STRING)(t => s"; Title: '${t}'") +
      s"; created: ${created}]"
  }

}
