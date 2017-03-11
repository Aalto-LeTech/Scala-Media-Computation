package aalto.smcl.bitmaps


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLMaximumBitmapSizeExceededError private[smcl](
  realWidthOption: Option[Int] = None,
  realHeightOption: Option[Int] = None,
  resourcePathOption: Option[String] = None,
  imageIndexInResourceOption: Option[Int] = None) extends RuntimeException({

  val sb = new StringBuilder(200)

  sb ++= s"The maximum image size of ${BitmapValidator.MaximumBitmapWidthInPixels} x " +
    s"${BitmapValidator.MaximumBitmapHeightInPixels} px has been exceeded "

  if (realWidthOption.isDefined && realHeightOption.isDefined)
    sb ++= s"(was ${realWidthOption.get} x ${realHeightOption.get})"

  sb ++= "."

  resourcePathOption foreach {path => sb ++= s""" Resource: "$path"."""}
  imageIndexInResourceOption foreach {index => sb ++= s""" Index of the image in the resource: $index."""}

  sb.toString()
}) with SMCLInitializationInvoker {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null

}
