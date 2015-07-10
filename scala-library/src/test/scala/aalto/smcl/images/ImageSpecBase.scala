package aalto.smcl.images

import aalto.smcl.UnitBaseSpec

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
abstract class ImageSpecBase extends UnitBaseSpec {

  val TEST_PIXEL_INT = 0xFFDCBA98
  val TEST_RED_VALUE = 0xDC
  val TEST_GREEN_VALUE = 0xBA
  val TEST_BLUE_VALUE = 0x98
  val TEST_TRANSPARENCY_VALUE = 0xFF
  val TEST_PIXEL_INT_WITH_ZEROED_RED = 0xFF00BA98
  val TEST_PIXEL_INT_WITH_ZEROED_GREEN = 0xFFDC0098
  val TEST_PIXEL_INT_WITH_ZEROED_BLUE = 0xFFDCBA00
  val TEST_PIXEL_INT_WITH_ZEROED_TRANSPARENCY = 0x00DCBA98

  def newDefaultSmallTestImage = BitmapImage(10, 10)

}
