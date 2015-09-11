package aalto.smcl.bitmaps.operations


import aalto.smcl.GS
import aalto.smcl.bitmaps.DefaultPrimary
import aalto.smcl.colors.{RGBAColor, _}
import aalto.smcl.infrastructure.{MetaInformationMap, PlatformBitmapBuffer}




/**
 * Operation to draw a line with given color. If the color is not given, the default
 * primary color will be used, as defined in the [[aalto.smcl.GS]].
 *
 * @param fromXInPixels
 * @param fromYInPixels
 * @param toXInPixels
 * @param toYInPixels
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class DrawLine(
    fromXInPixels: Int,
    fromYInPixels: Int,
    toXInPixels: Int,
    toYInPixels: Int,
    color: RGBAColor = GS.colorFor(DefaultPrimary))
    extends AbstractOperation with Renderable with Immutable {

  require(color != null, "The color argument has to be a Color instance (was null).")

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "fromX" -> Option(s"$fromXInPixels px"),
    "fromY" -> Option(s"$fromYInPixels px"),
    "toX" -> Option(s"$toXInPixels px"),
    "toY" -> Option(s"$toYInPixels px"),
    "color" -> Option(s"0x${color.toArgbInt.toArgbHexColorString}")))

  /**
   * Draws a line onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: PlatformBitmapBuffer): Unit = {
    destination.drawingSurface().drawLine(
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels,
      color)
  }

}
