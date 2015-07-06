package aalto.smcl.images

/**
 * @author Aleksi Lukkarinen
 */
class Pixels(val image: BitmapImage) {

  /**  */
  private val MAX_X = image.widthInPixels - 1

  /**  */
  private val MAX_Y = image.heightInPixels - 1

  /**  */
  private val MAX_PIXELS = (MAX_X + 1) * (MAX_Y + 1)

  /**  */
  private var _currentX: Int = 0

  /**  */
  private var _currentY: Int = 0


}
