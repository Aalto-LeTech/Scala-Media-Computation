package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common._
import aalto.smcl.images.SettingKeys.DefaultBackground




/**
 * Operation to clear a bitmap with a given color. If a color is not given, the default background color will be used,
 * as defined in the [[GS]].
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class Clear(
    color: Color = GS.colorFor(DefaultBackground))
    extends AbstractSingleSourceOperation with Immutable {

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this operation instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "background-color" -> Option("0x${_color.asPixelInt.toArgbHexColorString}")))

  /**
   * Clears the given bitmap with the given color.
   */
  override def render(destination: JBufferedImage): Unit = {
    val drawingSurface = destination.createGraphics()
    val oldColor = drawingSurface.getColor

    drawingSurface.setColor(color.toOpaqueAwtColor)
    drawingSurface.fillRect(0, 0, destination.getWidth, destination.getHeight)
    drawingSurface.setColor(oldColor)
  }

}
