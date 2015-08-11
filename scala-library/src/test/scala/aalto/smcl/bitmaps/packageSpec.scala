package aalto.smcl.bitmaps


import aalto.smcl.common._
import aalto.smcl.common.ColorOps._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class packageSpec extends ImageSpecBase {

  "the right color component must be extracted from an Int representing a pixel value by" - {
    "redComponentFrom()" in {
      assert(redComponentOf(TEST_PIXEL_INT) === TEST_RED_VALUE)
    }

    "greenComponentFrom()" in {
      assert(greenComponentOf(TEST_PIXEL_INT) === TEST_GREEN_VALUE)
    }

    "blueComponentFrom()" in {
      assert(blueComponentOf(TEST_PIXEL_INT) === TEST_BLUE_VALUE)
    }

    "opacityComponentFrom()" in {
      assert(opacityComponentOf(TEST_PIXEL_INT) === TEST_OPACITY_VALUE)
    }
  }

  "withNewRedComponent() must" - {
    "return an Int representing a pixel value with the right color component updated" in {
      assert(withNewRedComponent(TEST_PIXEL_INT, 0) === TEST_PIXEL_INT_WITH_ZEROED_RED)
    }
    "throw an IllegalArgumentException when color component is" - {
      "less than MIN_RED" in {
        intercept[IllegalArgumentException] {withNewRedComponent(TEST_PIXEL_INT, ColorValidator.MinimumRgbRed - 1)}
      }
      "greater than MAX_RED" in {
        intercept[IllegalArgumentException] {withNewRedComponent(TEST_PIXEL_INT, ColorValidator.MaximumRgbRed + 1)}
      }
    }
  }

  "withNewGreenComponent() must" - {
    "return an Int representing a pixel value with the right color component updated" in {
      assert(withNewGreenComponent(TEST_PIXEL_INT, 0) === TEST_PIXEL_INT_WITH_ZEROED_GREEN)
    }
    "throw an IllegalArgumentException when color component is" - {
      "less than MIN_GREEN" in {
        intercept[IllegalArgumentException] {withNewGreenComponent(TEST_PIXEL_INT, ColorValidator.MinimumRgbGreen - 1)}
      }
      "greater than MAX_GREEN" in {
        intercept[IllegalArgumentException] {withNewGreenComponent(TEST_PIXEL_INT, ColorValidator.MaximumRgbGreen + 1)}
      }
    }
  }

  "withNewBlueComponent() must" - {
    "return an Int representing a pixel value with the right color component updated" in {
      assert(withNewBlueComponent(TEST_PIXEL_INT, 0) === TEST_PIXEL_INT_WITH_ZEROED_BLUE)
    }
    "throw an IllegalArgumentException when color component is" - {
      "less than MIN_BLUE" in {
        intercept[IllegalArgumentException] {withNewBlueComponent(TEST_PIXEL_INT, ColorValidator.MinimumRgbBlue - 1)}
      }
      "greater than MAX_BLUE" in {
        intercept[IllegalArgumentException] {withNewBlueComponent(TEST_PIXEL_INT, ColorValidator.MaximumRgbBlue + 1)}
      }
    }
  }

  "withNewOpacityComponent() must" - {
    "return an Int representing a pixel value with the right color component updated" in {
      assert(withNewOpacityComponent(TEST_PIXEL_INT, 0) === TEST_PIXEL_INT_WITH_ZEROED_OPACITY)
    }
    "throw an IllegalArgumentException when color component is" - {
      "less than MinimumOpacity" in {
        intercept[IllegalArgumentException] {withNewOpacityComponent(TEST_PIXEL_INT, ColorValidator.MinimumRgbaOpacity - 1)}
      }
      "greater than MaximumOpacity" in {
        intercept[IllegalArgumentException] {withNewOpacityComponent(TEST_PIXEL_INT, ColorValidator.MaximumRgbaOpacity + 1)}
      }
    }
  }

  "colorComponentsFrom() must" - {
    "return a map with the right color components of an Int representing a pixel value" in {
      assert(colorComponentMapFrom(TEST_PIXEL_INT) ===
          Map[Symbol, Int](
            'red -> TEST_RED_VALUE,
            'green -> TEST_GREEN_VALUE,
            'blue -> TEST_BLUE_VALUE,
            'opacity -> TEST_OPACITY_VALUE))
    }
  }

  "pixelIntFrom() must" - {
    "compose the right pixel Int value based on given color components" in {
      assert(pixelIntFrom(
        TEST_RED_VALUE,
        TEST_GREEN_VALUE,
        TEST_BLUE_VALUE,
        TEST_OPACITY_VALUE) === TEST_PIXEL_INT)
    }

    "throw an IllegalArgumentException when color component" - {
      "'red' is less than MIN_RED" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(red = ColorValidator.MinimumRgbRed - 1)
        }
      }
      "'red' is greater than MAX_RED" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(red = ColorValidator.MaximumRgbRed + 1)
        }
      }
      "'green' is less than MIN_GREEN" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(green = ColorValidator.MinimumRgbGreen - 1)
        }
      }
      "'green' is greater than MAX_GREEN" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(green = ColorValidator.MaximumRgbGreen + 1)
        }
      }
      "'blue' is less than MIN_BLUE" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(blue = ColorValidator.MinimumRgbBlue - 1)
        }
      }
      "'blue' is greater than MAX_BLUE" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(blue = ColorValidator.MaximumRgbBlue + 1)
        }
      }
      "opacity is less than MinimumOpacity" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(opacity = ColorValidator.MinimumRgbaOpacity - 1)
        }
      }
      "opacity is greater than MaximumOpacity" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(opacity = ColorValidator.MaximumRgbaOpacity + 1)
        }
      }
    }
  }

  "implicit class PixelInt must return the correct" - {
    "color component map by invoking colorComponentInts()" in {
      assert(TEST_PIXEL_INT.colorComponentMap ===
          Map[Symbol, Int](
            'red -> TEST_RED_VALUE,
            'green -> TEST_GREEN_VALUE,
            'blue -> TEST_BLUE_VALUE,
            'opacity -> TEST_OPACITY_VALUE))
    }
    "red component by invoking redComponentInt()" in {
      assert(TEST_PIXEL_INT.redComponentInt === TEST_RED_VALUE)
    }
    "green component by invoking greenComponentInt()" in {
      assert(TEST_PIXEL_INT.greenComponentInt === TEST_GREEN_VALUE)
    }
    "blue component by invoking blueComponentInt()" in {
      assert(TEST_PIXEL_INT.blueComponentInt === TEST_BLUE_VALUE)
    }
    "opacity component by invoking opacityComponentInt()" in {
      assert(TEST_PIXEL_INT.opacityComponentInt === TEST_OPACITY_VALUE)
    }
    "hexadecimal representation by invoking toArgbHexColorString()" in {
      assert(TEST_PIXEL_INT.toArgbHexColorString === "ffdcba98")
    }
    "binary representation by invoking toArgbBinaryColorString()" in {
      assert(TEST_PIXEL_INT.toArgbBinaryColorString === "11111111 11011100 10111010 10011000")
    }
  }

}
