package aalto.smcl.bitmaps.immutable

import scala.collection.mutable

import aalto.smcl.bitmaps.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}
import aalto.smcl.bitmaps._
import aalto.smcl.colors.RGBAColor
import aalto.smcl.{GS, bitmaps}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] class CircleCreator {

  /**
   * Creates a new empty [[Bitmap]] instance with a circle drawn on it.
   *
   * @param diameter
   * @param color
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def createOne(
      diameter: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(diameter > 0, s"Diameter of the circle must be at least 1 pixel (was $diameter)")
    require(color != null, "The circle color argument has to be a Color instance (was null).")
    require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val imageSide = if (diameter % 2 == 0) diameter + 1 else diameter

    val newBitmap = Bitmap(
      widthInPixels = imageSide,
      heightInPixels = imageSide,
      initialBackgroundColor = backgroundColor,
      viewerHandling = PreventViewerUpdates)

    val radius = (imageSide - 2) / 2

    val newCircle = newBitmap.drawCircle(
      centerXInPixels = radius + 1,
      centerYInPixels = radius + 1,
      radiusInPixels = radius,
      hasBorder = true,
      hasFilling = true,
      color = color,
      fillColor = color,
      viewerHandling = PreventViewerUpdates)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
        newCircle.display()
    }

    newCircle
  }

  /**
   * Creates an array of [[bitmaps.Bitmap]]
   * instances with a circle drawn on each bitmap.
   *
   * @param diameter
   * @param color
   * @param backgroundColor
   * @return
   */
  def createArrayOf(
      collectionSize: Int = 5,
      diameter: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground)): Array[Bitmap] = {

    require(collectionSize >= 0, s"Size of the collection cannot be negative (was $collectionSize)")

    val newCollection = mutable.ArrayBuffer.empty[Bitmap]

    var item = 0
    for (item <- 1 to collectionSize) {
      newCollection += createOne(diameter, color, backgroundColor, PreventViewerUpdates)
    }

    newCollection.toArray
  }

}
