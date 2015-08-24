package aalto.smcl.bitmaps.immutable.collections


import scala.collection.mutable

import aalto.smcl.SMCL
import aalto.smcl.bitmaps.ViewerUpdateStyle.PreventViewerUpdates
import aalto.smcl.bitmaps.immutable.primitives.{Bitmap, Circle}
import aalto.smcl.bitmaps.{DefaultBackground, DefaultBitmapWidthInPixels, DefaultPrimary}
import aalto.smcl.common.{GS, RGBAColor}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object CirclesA {

  SMCL.performInitialization()


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
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground)): Array[Bitmap] = {

    require(collectionSize >= 0, s"Size of the collection cannot be negative (was $collectionSize)")

    val newCollection = mutable.ArrayBuffer.empty[Bitmap]

    var item = 0
    for (item <- 1 to collectionSize) {
      newCollection += Circle(diameter, color, backgroundColor, PreventViewerUpdates)
    }

    newCollection.toArray
  }

}
