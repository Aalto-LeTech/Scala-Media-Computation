package aalto.smcl.bitmaps.immutable.collections

import aalto.smcl.bitmaps.immutable.primitives.{Bitmap, Circle}

import aalto.smcl.common.{Color, GS}
import aalto.smcl.bitmaps.SettingKeys.{DefaultBackground, DefaultBitmapWidthInPixels, DefaultPrimary}
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle.PreventViewerUpdates

import scala.collection.mutable


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object CirclesA {

    aalto.smcl.bitmaps.SettingsInitializer.perform()

    /**
     * Creates an array of [[Bitmap]] instances with a circle drawn on each bitmap.
     *
     * @param diameter
     * @param color
     * @param backgroundColor
     * @return
     */
    def apply(
      collectionSize: Int = 5,
      diameter: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: Color = GS.colorFor(DefaultPrimary),
      backgroundColor: Color = GS.colorFor(DefaultBackground)): Array[Bitmap] = {

      require(collectionSize >= 0, s"Size of the collection cannot be negative (was $collectionSize)")

      val newCollection = mutable.ArrayBuffer.empty[Bitmap]

      var item = 0
      for (item <- 1 to collectionSize) {
        newCollection += Circle(diameter, color, backgroundColor, PreventViewerUpdates)
      }

      newCollection.toArray
   }

 }
