package aalto.smcl.bitmaps


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLMinimumBitmapSizeNotMetError private[smcl](
  realWidth: Option[Int] = None,
  realHeight: Option[Int] = None,
  resourcePath: Option[String] = None,
  imageIndexInResourceOption: Option[Int] = None) extends RuntimeException({

  val sb = new StringBuilder(200)

  sb ++= s"The minimum image size of ${BitmapValidator.MinimumBitmapHeightInPixels} x " +
    s"${BitmapValidator.MinimumBitmapHeightInPixels} px has not been met"

  if (realWidth.isDefined && realHeight.isDefined)
    sb ++= s" (was ${realWidth.get} x ${realHeight.get})"

  sb ++= "."

  resourcePath.foreach { path => sb ++= s""" Resource: "$path".""" }
  imageIndexInResourceOption.foreach { index => sb ++= s""" Index of the image in the resource: $index.""" }

  sb.toString()
}) {

}
