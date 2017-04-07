package aalto.smcl


import org.scalatest.DoNotDiscover

import aalto.smcl.bitmaps.Bitmap
import aalto.smcl.infrastructure.tests.SharedUnitSpecBase




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@DoNotDiscover
class SmclCoreSmokeTests extends SharedUnitSpecBase {

  println("SmclCoreSmokeTests initializing...")

  "Savustusta" - {
    "Testitesti" in {
      val b = Bitmap(widthInPixels = 15)
    }

    "Testitesti2" in {
      val b = Bitmap(widthInPixels = 20)
    }
  }

}
