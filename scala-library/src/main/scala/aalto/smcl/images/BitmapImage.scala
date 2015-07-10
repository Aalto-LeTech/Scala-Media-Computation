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
    initialBackgroundColorOption: Option[Int] = None,
    titleOption: Option[String] = None,
    descriptionOption: Option[String] = None,
    courseNameOption: Option[String] = None,
    assignmentOption: Option[String] = None,
    creatorNameOption: Option[String] = None): BitmapImage = {

    val imageController = new BitmapImage(
      titleOption,
      descriptionOption,
      courseNameOption,
      assignmentOption,
      creatorNameOption)

    val imageModel = BitmapImageModel(
      imageController,
      widthInPixels,
      heightInPixels,
      initialBackgroundColorOption)

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
    var titleOption: Option[String],
    var descriptionOption: Option[String],
    var courseNameOption: Option[String],
    var assignmentOption: Option[String],
    var creatorNameOption: Option[String]) {

  private val EMPTY_STRING = ""

  // Messages
  private val MSG_MODEL_CAN_BE_SET_ONLY_ONCE = "Model can be set only once, and it was already set."

  /** Represents the pixels of this image via a [[BitmapImageModel]] instance. */
  private[this] var _modelOption: Option[BitmapImageModel] = None

  /** Creation time and date of this image. */
  val created = Calendar.getInstance.getTime

  /**
   *  Returns the [[BitmapImageModel]] instance related to this image.
   */
  def model: BitmapImageModel = _modelOption.get

  /**
   * Sets the [[BitmapImageModel]] instance related to this image.
   * It can be set only once and must be set during the instantiation
   * in the companion object's `apply()` method.
   */
  private def model_=(newModel: Option[BitmapImageModel]): Unit = {
    _modelOption.foreach { m => throw new IllegalStateException(MSG_MODEL_CAN_BE_SET_ONLY_ONCE) }
    _modelOption = newModel
  }

  /**
   *
   */
  override def toString() = {
    val p = model.pixelBuffer

    s"[BitmapImage ${p.getWidth}x${p.getWidth} px" +
      titleOption.fold[String](EMPTY_STRING)(t => s"; Title: '${t}'") +
      s"; created: ${created}]"
  }

}
