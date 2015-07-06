package aalto.smcl.images

import org.scalatest._
import aalto.smcl.UnitBaseSpec

/**
 * @author Aleksi Lukkarinen
 */
class BitmapImageSpec extends UnitBaseSpec {
  val EXPECTED_DEFAULT_WIDTH_IN_PIXELS = 10
  val EXPECTED_DEFAULT_HEIGHT_IN_PIXELS = 10

  "A BitmapImage" should haveItsDefault {
    s"width defined as $EXPECTED_DEFAULT_WIDTH_IN_PIXELS pixels" in {
      assert(BitmapImage.DEFAULT_WIDTH_IN_PIXELS === EXPECTED_DEFAULT_WIDTH_IN_PIXELS)
    }
    s"height defined as $EXPECTED_DEFAULT_HEIGHT_IN_PIXELS pixels" in {
      assert(BitmapImage.DEFAULT_HEIGHT_IN_PIXELS === EXPECTED_DEFAULT_HEIGHT_IN_PIXELS)
    }
  }

  it should {
    "throw an IllegalArgumentException" when triedToInstantiateWith {
      "zero width" in {
        intercept[IllegalArgumentException] { new BitmapImage(widthInPixels = 0) }
      }
      "zero height" in {
        intercept[IllegalArgumentException] { new BitmapImage(heightInPixels = 0) }
      }
      "negative width" in {
        intercept[IllegalArgumentException] { new BitmapImage(widthInPixels = (-1)) }
      }
      "negative height" in {
        intercept[IllegalArgumentException] { new BitmapImage(heightInPixels = (-1)) }
      }
    }
  }

  it when {
    "constructed without arguments" should {
      val b = new BitmapImage()

      s"be ${EXPECTED_DEFAULT_WIDTH_IN_PIXELS} pixels in width" in {
        assert(b.widthInPixels === EXPECTED_DEFAULT_WIDTH_IN_PIXELS)
      }
      s"be ${EXPECTED_DEFAULT_HEIGHT_IN_PIXELS} pixels in height" in {
        assert(b.heightInPixels === EXPECTED_DEFAULT_HEIGHT_IN_PIXELS)
      }
    }

    val TEST_WIDTH_IN_PIXELS = 23
    val TEST_HEIGHT_IN_PIXELS = 45

    s"constructed with an arbitrary size of ${TEST_WIDTH_IN_PIXELS} x ${TEST_HEIGHT_IN_PIXELS} pixels" should {
      val b = new BitmapImage(TEST_WIDTH_IN_PIXELS, TEST_HEIGHT_IN_PIXELS)

      s"be ${TEST_WIDTH_IN_PIXELS} pixels in width" in {
        assert(b.widthInPixels === TEST_WIDTH_IN_PIXELS)
      }
      s"be ${TEST_HEIGHT_IN_PIXELS} pixels in height" in {
        assert(b.heightInPixels === TEST_HEIGHT_IN_PIXELS)
      }
    }
  }

  val TEST_PIXEL_INT = 0xFEDCBA98
  val TEST_RED_VALUE = 0xDC
  val TEST_GREEN_VALUE = 0xBA
  val TEST_BLUE_VALUE = 0x98
  val TEST_TRANSPARENCY_VALUE = 0xFE

  it should {
    "extract the right color component from an Int representing a pixel value" when {
      "'red' is requested" in {
        assert(BitmapImage.red(TEST_PIXEL_INT) === TEST_RED_VALUE)
      }
      "'green' is requested" in {
        assert(BitmapImage.green(TEST_PIXEL_INT) === TEST_GREEN_VALUE)
      }
      "'blue' is requested" in {
        assert(BitmapImage.blue(TEST_PIXEL_INT) === TEST_BLUE_VALUE)
      }
      "transparency is requested" in {
        assert(BitmapImage.transparency(TEST_PIXEL_INT) === TEST_TRANSPARENCY_VALUE)
      }
    }
  }

  "BitmapImage.pixelInt()" should {
    "compose the right pixel Int value based on given color components" in {
      assert(BitmapImage.pixelInt(
          TEST_RED_VALUE,
          TEST_GREEN_VALUE,
          TEST_BLUE_VALUE,
          TEST_TRANSPARENCY_VALUE) === TEST_PIXEL_INT)
    }

    "throw an IllegalArgumentException" when afterWord("color component") {
      "'red' is less than BitmapImage.MIN_RED" in {
        intercept[IllegalArgumentException] {
          BitmapImage.pixelInt(red = (BitmapImage.MIN_RED - 1))
        }
      }
      "'red' is greater than BitmapImage.MAX_RED" in {
        intercept[IllegalArgumentException] {
          BitmapImage.pixelInt(red = (BitmapImage.MAX_RED + 1))
        }
      }
      "'green' is less than BitmapImage.MIN_GREEN" in {
        intercept[IllegalArgumentException] {
          BitmapImage.pixelInt(green = (BitmapImage.MIN_GREEN - 1))
        }
      }
      "'green' is greater than BitmapImage.MAX_GREEN" in {
        intercept[IllegalArgumentException] {
          BitmapImage.pixelInt(green = (BitmapImage.MAX_GREEN + 1))
        }
      }
      "'blue' is less than BitmapImage.MIN_BLUE" in {
        intercept[IllegalArgumentException] {
          BitmapImage.pixelInt(blue = (BitmapImage.MIN_BLUE - 1))
        }
      }
      "'blue' is greater than BitmapImage.MAX_BLUE" in {
        intercept[IllegalArgumentException] {
          BitmapImage.pixelInt(blue = (BitmapImage.MAX_BLUE + 1))
        }
      }
      "transparency is less than BitmapImage.MIN_TRANSPARENCY" in {
        intercept[IllegalArgumentException] {
          BitmapImage.pixelInt(transparency = (BitmapImage.MIN_TRANSPARENCY - 1))
        }
      }
      "transparency is greater than BitmapImage.MAX_TRANSPARENCY" in {
        intercept[IllegalArgumentException] {
          BitmapImage.pixelInt(transparency = (BitmapImage.MAX_TRANSPARENCY + 1))
        }
      }
    }
  }

}
