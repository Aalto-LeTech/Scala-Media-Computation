package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps.DefaultBackground
import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure._




/**
 * Operation to clear a bitmap with a given color. If a color is not given,
 * the default background color will be used, as defined in the [[aalto.smcl.GS]].
 *
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class Clear(
  color: RGBAColor = GS.colorFor(DefaultBackground))
  extends AbstractOperation
  with Renderable
  with Immutable {

  require(color != null, "The color argument has to be a Color instance (was null).")

  /** Information about this operation instance */
  lazy val metaInformation = MetaInformationMap("Clear", Map(
    "background-color" -> Option("0x${_color.asArgbInt.toArgbHexColorString}")))

  /**
   * Clears the given bitmap with the given color.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit =
    destination.drawingSurface clearUsing color

}
