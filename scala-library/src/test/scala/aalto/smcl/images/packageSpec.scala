package aalto.smcl.images

import org.scalatest._
import aalto.smcl.images._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class packageSpec extends ImageSpecBase {

  "the right color component must be extracted from an Int representing a pixel value by" - {
    "redComponentFrom()" in {
      assert(redComponentFrom(TEST_PIXEL_INT) === TEST_RED_VALUE)
    }

    "greenComponentFrom()" in {
      assert(greenComponentFrom(TEST_PIXEL_INT) === TEST_GREEN_VALUE)
    }

    "blueComponentFrom()" in {
      assert(blueComponentFrom(TEST_PIXEL_INT) === TEST_BLUE_VALUE)
    }

    "transparencyComponentFrom()" in {
      assert(transparencyComponentFrom(TEST_PIXEL_INT) === TEST_TRANSPARENCY_VALUE)
    }
  }

  "withNewRedComponent() must" - {
    "return an Int representing a pixel value with the right color component updated" in {
      assert(withNewRedComponent(TEST_PIXEL_INT, 0) === TEST_PIXEL_INT_WITH_ZEROED_RED)
    }
    "throw an IllegalArgumentException when color component is" - {
      "less than MIN_RED" in {
        intercept[IllegalArgumentException] { withNewRedComponent(TEST_PIXEL_INT, MIN_RED - 1) }
      }
      "greater than MAX_RED" in {
        intercept[IllegalArgumentException] { withNewRedComponent(TEST_PIXEL_INT, MAX_RED + 1) }
      }
    }
  }

  "withNewGreenComponent() must" - {
    "return an Int representing a pixel value with the right color component updated" in {
      assert(withNewGreenComponent(TEST_PIXEL_INT, 0) === TEST_PIXEL_INT_WITH_ZEROED_GREEN)
    }
    "throw an IllegalArgumentException when color component is" - {
      "less than MIN_GREEN" in {
        intercept[IllegalArgumentException] { withNewGreenComponent(TEST_PIXEL_INT, MIN_GREEN - 1) }
      }
      "greater than MAX_GREEN" in {
        intercept[IllegalArgumentException] { withNewGreenComponent(TEST_PIXEL_INT, MAX_GREEN + 1) }
      }
    }
  }

  "withNewBlueComponent() must" - {
    "return an Int representing a pixel value with the right color component updated" in {
      assert(withNewBlueComponent(TEST_PIXEL_INT, 0) === TEST_PIXEL_INT_WITH_ZEROED_BLUE)
    }
    "throw an IllegalArgumentException when color component is" - {
      "less than MIN_BLUE" in {
        intercept[IllegalArgumentException] { withNewBlueComponent(TEST_PIXEL_INT, MIN_BLUE - 1) }
      }
      "greater than MAX_BLUE" in {
        intercept[IllegalArgumentException] { withNewBlueComponent(TEST_PIXEL_INT, MAX_BLUE + 1) }
      }
    }
  }

  "withNewTransparencyComponent() must" - {
    "return an Int representing a pixel value with the right color component updated" in {
      assert(withNewTransparencyComponent(TEST_PIXEL_INT, 0) === TEST_PIXEL_INT_WITH_ZEROED_TRANSPARENCY)
    }
    "throw an IllegalArgumentException when color component is" - {
      "less than MIN_TRANSPARENCY" in {
        intercept[IllegalArgumentException] { withNewTransparencyComponent(TEST_PIXEL_INT, MIN_TRANSPARENCY - 1) }
      }
      "greater than MAX_TRANSPARENCY" in {
        intercept[IllegalArgumentException] { withNewTransparencyComponent(TEST_PIXEL_INT, MAX_TRANSPARENCY + 1) }
      }
    }
  }

  "colorComponentsFrom() must" - {
    "return a map with the right color components of an Int representing a pixel value" in {
      assert(colorComponentsFrom(TEST_PIXEL_INT) ===
        Map[Symbol, Int](
          'red -> TEST_RED_VALUE,
          'green -> TEST_GREEN_VALUE,
          'blue -> TEST_BLUE_VALUE,
          'transparency -> TEST_TRANSPARENCY_VALUE))
    }
  }

  "pixelIntFrom() must" - {
    "compose the right pixel Int value based on given color components" in {
      assert(pixelIntFrom(
        TEST_RED_VALUE,
        TEST_GREEN_VALUE,
        TEST_BLUE_VALUE,
        TEST_TRANSPARENCY_VALUE) === TEST_PIXEL_INT)
    }

    "throw an IllegalArgumentException when color component" - {
      "'red' is less than MIN_RED" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(red = (MIN_RED - 1))
        }
      }
      "'red' is greater than MAX_RED" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(red = (MAX_RED + 1))
        }
      }
      "'green' is less than MIN_GREEN" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(green = (MIN_GREEN - 1))
        }
      }
      "'green' is greater than MAX_GREEN" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(green = (MAX_GREEN + 1))
        }
      }
      "'blue' is less than MIN_BLUE" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(blue = (MIN_BLUE - 1))
        }
      }
      "'blue' is greater than MAX_BLUE" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(blue = (MAX_BLUE + 1))
        }
      }
      "transparency is less than MIN_TRANSPARENCY" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(transparency = (MIN_TRANSPARENCY - 1))
        }
      }
      "transparency is greater than MAX_TRANSPARENCY" in {
        intercept[IllegalArgumentException] {
          pixelIntFrom(transparency = (MAX_TRANSPARENCY + 1))
        }
      }
    }
  }

}
