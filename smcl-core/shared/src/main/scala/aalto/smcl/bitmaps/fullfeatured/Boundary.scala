package aalto.smcl.bitmaps.fullfeatured


/**
 *
 *
 *
 */
object Boundary {

  /** */
  val NumberOfCorners: Int = 4


  /**
   *
   *
   * @param upperLeftXInPixels
   * @param upperLeftYInPixels
   * @param lowerRightXInPixels
   * @param lowerRightYInPixels
   *
   * @return
   */
  def apply(
      upperLeftXInPixels: Int,
      upperLeftYInPixels: Int,
      lowerRightXInPixels: Int,
      lowerRightYInPixels: Int): Option[Boundary] = {

    if (upperLeftXInPixels == lowerRightXInPixels ||
        upperLeftYInPixels == lowerRightYInPixels)
      return None

    val (x0: Int, x1: Int) =
      if (upperLeftXInPixels < lowerRightXInPixels)
        (upperLeftXInPixels, lowerRightXInPixels)
      else
        (lowerRightXInPixels, upperLeftXInPixels)

    val (y0: Int, y1: Int) =
      if (upperLeftYInPixels < lowerRightYInPixels)
        (upperLeftYInPixels, lowerRightYInPixels)
      else
        (lowerRightYInPixels, upperLeftYInPixels)

    val widthInPixels: Int = x1 - x0 + 1

    val heightInPixels: Int = y1 - y0 + 1

    val length: Length =
      Length(2 * widthInPixels + 2 * heightInPixels - NumberOfCorners).get

    val boundary = new Boundary(
      Point(x0, y0),
      Point(x1, y1),
      widthInPixels,
      heightInPixels,
      length)

    Some(boundary)
  }

  /**
   *
   *
   * @param upperLeft
   * @param lowerRight
   *
   * @return
   */
  def apply(
      upperLeft: Point,
      lowerRight: Point): Option[Boundary] = {

    apply(upperLeft.xInPixels, upperLeft.yInPixels, lowerRight.xInPixels, lowerRight.yInPixels)
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class Boundary private(
    upperLeft: Point,
    lowerRight: Point,
    widthInPixels: Int,
    heightInPixels: Int,
    length: Length) {

}
