package aalto.smcl.bitmaps.operations


import aalto.smcl.GS
import aalto.smcl.bitmaps._
import aalto.smcl.colors.{RGBAColor, _}
import aalto.smcl.infrastructure.{MetaInformationMap, PlatformBitmapBuffer}




/**
 * Operation to draw an ellipse with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[aalto.smcl.GS]].
 *
 * @param centerXInPixels
 * @param centerYInPixels
 * @param widthInPixels
 * @param heightInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class DrawEllipse(
    centerXInPixels: Int,
    centerYInPixels: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary))
    extends AbstractOperation with Renderable with Immutable {

  require(widthInPixels > 0, s"The width argument must be greater than zero (was $widthInPixels).")
  require(heightInPixels > 0, s"The height argument must be greater than zero (was $heightInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** X coordinate of the upper-left corner of the bounding box of the circle to be drawn. */
  val boundingBoxUpperLeftX: Int = centerXInPixels - (widthInPixels / 2)

  /** Y coordinate of the upper-left corner of the bounding box of the circle to be drawn. */
  val boundingBoxUpperLeftY: Int = centerYInPixels - (heightInPixels / 2)

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "centerX" -> Option(s"$centerXInPixels px"),
    "centerY" -> Option(s"$centerYInPixels px"),
    "width" -> Option(s"$widthInPixels px"),
    "height" -> Option(s"$heightInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.toArgbInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.toArgbInt.toArgbHexColorString}")))

  /**
   * Draws an ellipse onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: PlatformBitmapBuffer): Unit = {
    destination.drawingSurface().drawEllipse(
      boundingBoxUpperLeftX, boundingBoxUpperLeftY,
      widthInPixels, heightInPixels,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
