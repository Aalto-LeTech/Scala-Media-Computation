package aalto.smcl.bitmaps.operations


import aalto.smcl.GS
import aalto.smcl.bitmaps._
import aalto.smcl.colors.{RGBAColor, _}
import aalto.smcl.infrastructure.{MetaInformationMap, PlatformBitmapBuffer}




/**
 * Operation to draw an arc with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[aalto.smcl.GS]].
 *
 * @param upperLeftCornerXInPixels
 * @param upperLeftCornerYInPixels
 * @param widthInPixels
 * @param heightInPixels
 * @param startAngleInDegrees
 * @param arcAngleInDegrees
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class DrawArc(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    startAngleInDegrees: Int = GS.intFor(DefaultArcStartAngleInDegrees),
    arcAngleInDegrees: Int = GS.intFor(DefaultArcAngleInDegrees),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary))
    extends AbstractOperation with Renderable with Immutable {

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
    "startAngle" -> Option(s"${startAngleInDegrees.toString} deg"),
    "arcAngle" -> Option(s"${arcAngleInDegrees.toString} deg"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.toArgbInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.toArgbInt.toArgbHexColorString}")))

  /**
   * Draws an arc onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: PlatformBitmapBuffer): Unit = {
    destination.drawingSurface().drawArc(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels, startAngleInDegrees, arcAngleInDegrees,
      hasBorder, hasFilling, color, fillColor)
  }

}
