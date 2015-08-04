package aalto.smcl.bitmaps.operations


import aalto.smcl.common.ColorOps.RichPixelInt
import aalto.smcl.common.{Color, GS, MetaInformationMap}
import aalto.smcl.bitmaps.SettingKeys._
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to draw a rounded-corner rectangle with given colors. If a color is not
 * given, the default primary/secondary colors will be used, as defined in the [[GS]].
 *
 * @param upperLeftCornerXInPixels
 * @param upperLeftCornerYInPixels
 * @param widthInPixels
 * @param heightInPixels
 * @param roundingWidthInPixels
 * @param roundingHeightInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class DrawRoundedRectangle(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
    roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: Color = GS.colorFor(DefaultPrimary),
    fillColor: Color = GS.colorFor(DefaultSecondary))
    extends AbstractSingleSourceOperation with Immutable {

  require(widthInPixels > 0, s"The width argument must be greater than zero (was $widthInPixels).")
  require(heightInPixels > 0, s"The height argument must be greater than zero (was $heightInPixels).")
  require(roundingWidthInPixels > 0, s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")
  require(roundingHeightInPixels > 0, s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "upperLeftX" -> Option(s"$upperLeftCornerXInPixels px"),
    "upperLeftY" -> Option(s"$upperLeftCornerYInPixels px"),
    "width" -> Option(s"$widthInPixels px"),
    "height" -> Option(s"$heightInPixels px"),
    "roundingWidth" -> Option(s"$roundingWidthInPixels px"),
    "roundingHeight" -> Option(s"$roundingHeightInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.asPixelInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.asPixelInt.toArgbHexColorString}")))

  /**
   * Draws a rounded-corner rectangle onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: PlatformBitmapBuffer): Unit = {
    destination.drawingSurface().drawRoundedRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
