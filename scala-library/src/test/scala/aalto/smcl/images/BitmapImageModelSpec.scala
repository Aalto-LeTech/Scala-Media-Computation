package aalto.smcl

import org.scalatest._
import aalto.smcl.images._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class BitmapImageModelSpec extends ImageSpecBase {

  "Class BitmapImageModel" - {
    "when created for a BitmapImage of size 8 x 9 pixels, must have 72 pixels in total" in {
      val (width, height) = (8, 9)
      val numOfPixels = width * height
      val m = BitmapImage(width, height).model.get

      assert(m.numberOfPixels === numOfPixels)
    }

    "when created for an image without giving a background color, must have all its pixels of fully opaque black" in {
      val m = newDefaultSmallTestImage.model.get

      for (y <- m.heightRange; x <- m.widthRange) {
        // -- DEBUG -- info(s"(${x},${y})")

        assert(m.pixelIntAt(x, y) === 0)
      }
    }

    "when created for an image with a given background color, must have all its pixels of that colour" in {
      val m = BitmapImage(initialBackgroundColor=Option[Int](TEST_PIXEL_INT)).model.get

      for (y <- m.heightRange; x <- m.widthRange) {
        // -- DEBUG -- info(s"(${x},${y})")

        assert(m.pixelIntAt(x, y) === TEST_PIXEL_INT)
      }
    }
  }

}
