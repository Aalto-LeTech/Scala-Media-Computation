package aalto.smcl

import org.scalatest._
import aalto.smcl.images._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class PixelsSpec extends aalto.smcl.UnitBaseSpec {

  "A Pixels" - {
    "when created for an image of size 8 x 9 pixels, it must have 72 pixels in total" in {
      val b = BitmapImage(8, 9)

      assert(b.pixels.count === 72)
    }
  }

}
