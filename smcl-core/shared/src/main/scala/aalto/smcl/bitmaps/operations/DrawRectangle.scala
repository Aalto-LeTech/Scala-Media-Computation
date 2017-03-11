package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps._
import aalto.smcl.colors.{RGBAColor, _}
import aalto.smcl.infrastructure._




/**
 * Operation to draw a rectangle with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[aalto.smcl.GS]].
 *
 * @param upperLeftCornerXInPixels
 * @param upperLeftCornerYInPixels
 * @param widthInPixels
 * @param heightInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class DrawRectangle(
  upperLeftCornerXInPixels: Int,
  upperLeftCornerYInPixels: Int,
  widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
  heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
  hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
  hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
  color: RGBAColor = GS.colorFor(DefaultPrimary),
  fillColor: RGBAColor = GS.colorFor(DefaultSecondary))
  extends AbstractOperation
  with Renderable
  with Immutable {

  require(widthInPixels > 0, s"The width argument must be greater than zero (was $widthInPixels).")
  require(heightInPixels > 0, s"The height argument must be greater than zero (was $heightInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "upperLeftX" -> Option(s"$upperLeftCornerXInPixels px"),
    "upperLeftY" -> Option(s"$upperLeftCornerYInPixels px"),
    "width" -> Option(s"$widthInPixels px"),
    "height" -> Option(s"$heightInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.toArgbInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.toArgbInt.toArgbHexColorString}")))

  /**
   * Draws a rectangle onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit = {
    destination.drawingSurface.drawRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
