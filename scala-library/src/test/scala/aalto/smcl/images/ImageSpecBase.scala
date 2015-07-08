package aalto.smcl.images

import aalto.smcl.UnitBaseSpec

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
abstract class ImageSpecBase extends UnitBaseSpec {

  val TEST_PIXEL_INT = 0xFEDCBA98
  val TEST_RED_VALUE = 0xDC
  val TEST_GREEN_VALUE = 0xBA
  val TEST_BLUE_VALUE = 0x98
  val TEST_TRANSPARENCY_VALUE = 0xFE
  val TEST_PIXEL_INT_WITH_ZEROED_RED = 0xFE00BA98
  val TEST_PIXEL_INT_WITH_ZEROED_GREEN = 0xFEDC0098
  val TEST_PIXEL_INT_WITH_ZEROED_BLUE = 0xFEDCBA00
  val TEST_PIXEL_INT_WITH_ZEROED_TRANSPARENCY = 0x00DCBA98

  def newDefaultSmallTestImage = BitmapImage(10, 10)

}
