package aalto.smcl.bitmaps.operations


import aalto.smcl.GS
import aalto.smcl.bitmaps._
import aalto.smcl.colors.{RGBAColor, _}
import aalto.smcl.infrastructure.{MetaInformationMap, PlatformBitmapBuffer}




/**
 * Operation to draw a square with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[aalto.smcl.GS]].
 *
 * @param upperLeftCornerXInPixels
 * @param upperLeftCornerYInPixels
 * @param sideLengthInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class DrawSquare(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary))
    extends AbstractOperation with Renderable with Immutable {

  require(sideLengthInPixels > 0, s"The side length argument must be greater than zero (was $sideLengthInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "upperLeftX" -> Option(s"$upperLeftCornerXInPixels px"),
    "upperLeftY" -> Option(s"$upperLeftCornerYInPixels px"),
    "side" -> Option(s"$sideLengthInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.toArgbInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.toArgbInt.toArgbHexColorString}")))

  /**
   * Draws a square onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: PlatformBitmapBuffer): Unit = {
    destination.drawingSurface().drawRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels, sideLengthInPixels,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
