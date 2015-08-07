package aalto.smcl.bitmaps


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLMaximumBitmapSizeExceededError private[smcl](
    realWidth: Option[Int] = None,
    realHeight: Option[Int]= None,
    resourcePath: Option[String]= None,
    imageIndexInResourceOption: Option[Int]= None) extends {

  /** */
  val maximumWidthInPixels = BitmapValidator.MaximumBitmapWidthInPixels

  /** */
  val maximumHeightInPixels = BitmapValidator.MaximumBitmapHeightInPixels

  /** */
  private[this] val message: String = {
    val sb = new StringBuilder(200)

    sb ++= s"The maximum image size of $maximumWidthInPixels x $maximumHeightInPixels px has been exceeded"

    if (realWidth.isDefined && realHeight.isDefined)
      sb ++= s"(was ${realWidth.get} x ${realHeight.get}} )"

    sb ++= "."

    resourcePath.foreach {path => sb ++=  s""" Resource: "$path"."""}
    imageIndexInResourceOption.foreach {index => sb ++= s""" Index of the image in the resource: $index."""}

    sb.toString()
  }

} with RuntimeException(message) {

}
