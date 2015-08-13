package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps.BitmapSettingKeys._
import aalto.smcl.common._
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to draw a rounded-corner square with given colors. If a color is not
 * given, the default primary/secondary colors will be used, as defined in the [[GS]].
 *
 * @param upperLeftCornerXInPixels
 * @param upperLeftCornerYInPixels
 * @param sideLengthInPixels
 * @param roundingWidthInPixels
 * @param roundingHeightInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class DrawRoundedSquare(
  upperLeftCornerXInPixels: Int,
  upperLeftCornerYInPixels: Int,
  sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
  roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
  roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
  hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
  hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
  color: RGBAColor = GS.colorFor(DefaultPrimary),
  fillColor: RGBAColor = GS.colorFor(DefaultSecondary))
  extends AbstractOperation with RenderableOperation with Immutable {

  require(sideLengthInPixels > 0, s"The side length argument must be greater than zero (was $sideLengthInPixels).")
  require(roundingWidthInPixels > 0, s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")
  require(roundingHeightInPixels > 0, s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** Information about this [[RenderableOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "upperLeftX" -> Option(s"$upperLeftCornerXInPixels px"),
    "upperLeftY" -> Option(s"$upperLeftCornerYInPixels px"),
    "sideLength" -> Option(s"$sideLengthInPixels px"),
    "roundingWidth" -> Option(s"$roundingWidthInPixels px"),
    "roundingHeight" -> Option(s"$roundingHeightInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.toPixelInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.toPixelInt.toArgbHexColorString}")))

  /**
   * Draws a rounded-corner square onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: PlatformBitmapBuffer): Unit = {
    destination.drawingSurface().drawRoundedRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels, sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
