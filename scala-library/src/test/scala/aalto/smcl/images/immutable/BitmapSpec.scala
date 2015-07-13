package aalto.smcl.images.immutable

import java.awt.{ Graphics2D => JGraphics2D }
import org.scalatest._
import org.scalatest.prop.TableDrivenPropertyChecks._
import aalto.smcl.images._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class BitmapSpec extends ImageSpecBase {

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
      "zero width" in { intercept[IllegalArgumentException] { Bitmap(widthInPixelsOption = Option(0)) } }
      "zero height" in { intercept[IllegalArgumentException] { Bitmap(heightInPixelsOption = Option(0)) } }
      "negative width" in { intercept[IllegalArgumentException] { Bitmap(widthInPixelsOption = Option(-1)) } }
      "negative height" in { intercept[IllegalArgumentException] { Bitmap(heightInPixelsOption = Option(-1)) } }
    }

    //    "when constructed without arguments, must be" - {
    //      val b = Bitmap().buffer
    //
    //      s"${EXPECTED_DEFAULT_WIDTH_IN_PIXELS} pixels in width" in {
    //        assert(b.getWidth === EXPECTED_DEFAULT_WIDTH_IN_PIXELS)
    //      }
    //      s"${EXPECTED_DEFAULT_HEIGHT_IN_PIXELS} pixels in height" in {
    //        assert(b.getHeight === EXPECTED_DEFAULT_HEIGHT_IN_PIXELS)
    //      }
    //    }

    val TEST_WIDTH_IN_PIXELS = 23
    val TEST_HEIGHT_IN_PIXELS = 45

    s"when constructed with an arbitrary size of " +
      s"${TEST_WIDTH_IN_PIXELS} x ${TEST_HEIGHT_IN_PIXELS} pixels, must" - {

        val (width, height) = (TEST_WIDTH_IN_PIXELS, TEST_HEIGHT_IN_PIXELS)
        val numOfPixels = width * height
        val b = Bitmap(Option(width), Option(height))

        //        s"be ${width} pixels by width" in { assert(b.buffer.getWidth === width) }
        //        s"be ${height} pixels by height" in { assert(b.buffer.getHeight === height) }
        "have a widthRange" - {
          "starting from 0" in { assert(b.widthRange.start === 0) }
          s"ending to ${width - 1}" in { assert(b.widthRange.end === (width - 1)) }
        }
        "have a heightRange" - {
          "starting from 0" in { assert(b.heightRange.start === 0) }
          s"ending to ${height - 1}" in { assert(b.heightRange.end === (height - 1)) }
        }
        s"have ${numOfPixels} pixels in total" in { assert(b.pixelCount === numOfPixels) }
      }

    "must get timestamped and be able to tell the time of creation" in {
      val b = Bitmap()
      assert(b.created.isInstanceOf[java.util.Date])
      info(s"Timestamp: ${b.created.toString()}")
    }

    "must be able to tell its" - {
      val testValue = Option("Koe")

      "title" in { assert(Bitmap(titleOption = testValue).titleOption === testValue) }
      "description" in { assert(Bitmap(descriptionOption = testValue).descriptionOption === testValue) }
      "courseName" in { assert(Bitmap(courseNameOption = testValue).courseNameOption === testValue) }
      "assignmentOption" in { assert(Bitmap(assignmentOption = testValue).assignmentOption === testValue) }
      "creatorName" in { assert(Bitmap(creatorNameOption = testValue).creatorNameOption === testValue) }
    }

    //    "when created for an image without giving a background color, must have all its pixels of pure white" in {
    //      val b = newSmallDefaultImmutableTestImage
    //
    //      for (y <- b.heightRange; x <- b.widthRange) { // -- DEBUG -- info(s"(${x},${y})")
    //        assert(b.pixelIntAt(x, y) === 0xFFFFFFFF)
    //      }
    //    }
    //
    //    "when created for an image with a given opaque background color, must have all its pixels of that colour" in {
    //      val b = Bitmap(initialBackgroundColorOption = Option[Int](TEST_PIXEL_INT))
    //
    //      for (y <- b.heightRange; x <- b.widthRange) { // -- DEBUG -- info(s"(${x},${y})")
    //        assert(b.pixelIntAt(x, y) === TEST_PIXEL_INT)
    //      }
    //    }
    //
    //    "must be able to give a Graphics2D instance" in {
    //      assert(Bitmap().graphics2D.isInstanceOf[JGraphics2D])
    //    }
    //
    //    "must be able to clear() the image with a given opaque color" in {
    //      val testColors = Table("c", 0xFF9EADBC, 0xFF000000, 0xFF123456)
    //
    //      forAll(testColors) { c =>
    //        info(s"testing pixelInt value: 0x${c.toArgbHexColorString}  (${c})")
    //
    //        val b = Bitmap()
    //
    //        b.clear(Option(c))
    //
    //        for (y <- b.heightRange; x <- b.widthRange) { // -- DEBUG -- info(s"(${x},${y})")
    //          b.pixelIntAt(x, y) shouldEqual c
    //        }
    //      }
    //    }
    //
    //    "must be able to clear() the image with the default (opaque) background color" in {
    //      val testColors = Table("c", 0xFF9EADBC, 0xFF000000, 0xFF123456)
    //
    //      forAll(testColors) { c =>
    //        info(s"testing pixelInt value: 0x${c.toArgbHexColorString}  (${c})")
    //
    //        val b = Bitmap(initialBackgroundColorOption = Option(c))
    //
    //        b.clear()
    //
    //        for (y <- b.heightRange; x <- b.widthRange) { // -- DEBUG -- info(s"(${x},${y})")
    //          b.pixelIntAt(x, y) shouldEqual c
    //        }
    //      }
    //    }
  }

}
