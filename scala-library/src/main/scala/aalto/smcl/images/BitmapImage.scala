package aalto.smcl.images

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapImage {

  /** Default width of <code>BitmapImage</code> instances created without giving width. */
  val DEFAULT_WIDTH_IN_PIXELS: Int = 10

  /** Default height of <code>BitmapImage</code> instances created without giving height. */
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

    imageController.setModel(imageModel)

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

  /** Represents the pixels of this image. */
  private var _model: Option[BitmapImageModel] = None

  /**  */
  def model = _model

  /**  */
  private def setModel(model: BitmapImageModel) = _model = Option[BitmapImageModel](model)

}
