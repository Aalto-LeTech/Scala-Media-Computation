package aalto.smcl.bitmaps.fullfeatured


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Length {

  /**
   *
   *
   * @param valueInPixels
   *
   * @return
   */
  def apply(valueInPixels: Int): Option[Length] = {
    if (valueInPixels < 1)
      return None

    Some(new Length(valueInPixels))
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class Length private(valueInPixels: Int) {

}
