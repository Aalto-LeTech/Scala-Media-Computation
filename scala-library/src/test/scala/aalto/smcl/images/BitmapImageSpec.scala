package aalto.smcl.images

import org.scalatest._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class BitmapImageSpec extends ImageSpecBase {

  val EXPECTED_DEFAULT_WIDTH_IN_PIXELS = 10
  val EXPECTED_DEFAULT_HEIGHT_IN_PIXELS = 10

  "Class BitmapImage" - {
    "must have a default" - {
      s"width defined as ${EXPECTED_DEFAULT_WIDTH_IN_PIXELS} pixels" in {
        assert(DEFAULT_IMAGE_WIDTH_IN_PIXELS === EXPECTED_DEFAULT_WIDTH_IN_PIXELS)
      }
      s"height defined as ${EXPECTED_DEFAULT_HEIGHT_IN_PIXELS} pixels" in {
        assert(DEFAULT_IMAGE_HEIGHT_IN_PIXELS === EXPECTED_DEFAULT_HEIGHT_IN_PIXELS)
      }
    }

    "must throw an IllegalArgumentException when tried to instantiate with" - {
      "zero width" in { intercept[IllegalArgumentException] { BitmapImage(widthInPixelsOption = Option(0)) } }
      "zero height" in { intercept[IllegalArgumentException] { BitmapImage(heightInPixelsOption = Option(0)) } }
      "negative width" in { intercept[IllegalArgumentException] { BitmapImage(widthInPixelsOption = Option(-1)) } }
      "negative height" in { intercept[IllegalArgumentException] { BitmapImage(heightInPixelsOption = Option(-1)) } }
    }

    "when constructed without arguments, must be" - {
      val b = BitmapImage().imageModel.pixelBuffer

      s"${EXPECTED_DEFAULT_WIDTH_IN_PIXELS} pixels in width" in {
        assert(b.getWidth === EXPECTED_DEFAULT_WIDTH_IN_PIXELS)
      }
      s"${EXPECTED_DEFAULT_HEIGHT_IN_PIXELS} pixels in height" in {
        assert(b.getHeight === EXPECTED_DEFAULT_HEIGHT_IN_PIXELS)
      }
    }

    val TEST_WIDTH_IN_PIXELS = 23
    val TEST_HEIGHT_IN_PIXELS = 45

    s"when constructed with an arbitrary size of " +
      s"${TEST_WIDTH_IN_PIXELS} x ${TEST_HEIGHT_IN_PIXELS} pixels, must be" - {

        val b = BitmapImage(Option(TEST_WIDTH_IN_PIXELS), Option(TEST_HEIGHT_IN_PIXELS)).imageModel.pixelBuffer

        s"${TEST_WIDTH_IN_PIXELS} pixels in width" in { assert(b.getWidth === TEST_WIDTH_IN_PIXELS) }
        s"${TEST_HEIGHT_IN_PIXELS} pixels in height" in { assert(b.getHeight === TEST_HEIGHT_IN_PIXELS) }
      }

    "when constructed, must get timestamped and be able to tell the time of creation" in {
      val b = BitmapImage()
      assert(b.created.isInstanceOf[java.util.Date])
      info(s"Timestamp: ${b.created.toString()}")
    }

    "must be able to tell its" - {
      val testValue = Option("Koe")

      "title" in { assert(BitmapImage(titleOption = testValue).titleOption === testValue) }
      "description" in { assert(BitmapImage(descriptionOption = testValue).descriptionOption === testValue) }
      "courseName" in { assert(BitmapImage(courseNameOption = testValue).courseNameOption === testValue) }
      "assignmentOption" in { assert(BitmapImage(assignmentOption = testValue).assignmentOption === testValue) }
      "creatorName" in { assert(BitmapImage(creatorNameOption = testValue).creatorNameOption === testValue) }
    }
  }
}
