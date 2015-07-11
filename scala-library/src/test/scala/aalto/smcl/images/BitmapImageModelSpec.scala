package aalto.smcl.images

import java.awt.Graphics2D
import org.scalatest._
import org.scalatest.prop.TableDrivenPropertyChecks._
import aalto.smcl.images._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class BitmapImageModelSpec extends ImageSpecBase {

  "Class BitmapImageModel" - {
    "when created for a BitmapImage of size 8 x 9 pixels" - {
      val (width, height) = (8, 9)
      val numOfPixels = width * height
      val m = BitmapImage(width, height).imageModel

      "must have a width of 8 pixels" in { assert(m.pixelBuffer.getWidth === 8) }
      "must have a width of 9 pixels" in { assert(m.pixelBuffer.getHeight === 9) }
      "must have a widthRange" - {
        "starting from 0" in { assert(m.widthRange.start === 0) }
        "ending to 7" in { assert(m.widthRange.end === 7) }
      }
      "must have a heightRange" - {
        "starting from 0" in { assert(m.heightRange.start === 0) }
        "ending to 8" in { assert(m.heightRange.end === 8) }
      }
      "must have 72 pixels in total" in { assert(m.numberOfPixels === numOfPixels) }
    }

    "when created for an image without giving a background color, must have all its pixels of fully opaque black" in {
      val m = newDefaultSmallTestImage.imageModel

      for (y <- m.heightRange; x <- m.widthRange) { // -- DEBUG -- info(s"(${x},${y})")
        assert(m.pixelIntAt(x, y) === 0)
      }
    }

    "when created for an image with a given opaque background color, must have all its pixels of that colour" in {
      val m = BitmapImage(initialBackgroundColorOption = Option[Int](TEST_PIXEL_INT)).imageModel

      for (y <- m.heightRange; x <- m.widthRange) { // -- DEBUG -- info(s"(${x},${y})")
        assert(m.pixelIntAt(x, y) === TEST_PIXEL_INT)
      }
    }

    "must be able to give a Graphics2D instance" in {
      assert(BitmapImage().imageModel.graphics2D.isInstanceOf[Graphics2D])
    }

    "must be able to clear() the image with a given opaque color" in {
      val testColors = Table("c", 0xFF9EADBC, 0xFF000000, 0xFF123456)

      forAll(testColors) { c =>
        info(s"testing pixelInt value: 0x${c.toArgbHexColorString}  (${c})")

        val m = BitmapImage().imageModel

        m.clear(Option(c))

        for (y <- m.heightRange; x <- m.widthRange) { // -- DEBUG -- info(s"(${x},${y})")
          m.pixelIntAt(x, y) shouldEqual c
        }
      }
    }

    "must be able to clear() the image with the default (opaque) background color" in {
      val testColors = Table("c", 0xFF9EADBC, 0xFF000000, 0xFF123456)

      forAll(testColors) { c =>
        info(s"testing pixelInt value: 0x${c.toArgbHexColorString}  (${c})")

        val m = BitmapImage(initialBackgroundColorOption = Option(c)).imageModel

        m.clear()

        for (y <- m.heightRange; x <- m.widthRange) { // -- DEBUG -- info(s"(${x},${y})")
          m.pixelIntAt(x, y) shouldEqual c
        }
      }
    }

  }

}
