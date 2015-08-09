package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps.BitmapSettingKeys.DefaultBackground
import aalto.smcl.common.{GS, MetaInformationMap, RGBAColor}
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to clear a bitmap with a given color. If a color is not given, the default background color will be used,
 * as defined in the [[aalto.smcl.common.GS]].
 *
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class Clear(
  color: RGBAColor = GS.colorFor(DefaultBackground))
  extends AbstractSingleSourceOperation with Immutable {

  require(color != null, "The color argument has to be a Color instance (was null).")

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] = None

  /** Information about this operation instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "background-color" -> Option("0x${_color.asPixelInt.toArgbHexColorString}")))

  /**
   * Clears the given bitmap with the given color.
   *
   * @param destination
   */
  override def render(destination: PlatformBitmapBuffer): Unit =
    destination.drawingSurface().clearUsing(color)

}
