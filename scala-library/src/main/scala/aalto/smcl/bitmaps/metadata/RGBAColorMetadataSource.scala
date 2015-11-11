package aalto.smcl.bitmaps.metadata


import java.awt.image.BufferedImage
import java.util.Date

import aalto.smcl.bitmaps.Bitmap
import aalto.smcl.bitmaps.ViewerUpdateStyle.PreventViewerUpdates
import aalto.smcl.colors.{PresetColors, RGBAColor}
import aalto.smcl.infrastructure.{GS, MetaInterfaceBase}
import aalto.smcl.interfaces.{ResourceMetadataSource, StaticGeneralBitmapSource}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[metadata]
case class RGBAColorMetadataSource(relatedRGBAColor: RGBAColor)
  extends MetaInterfaceBase
  with ResourceMetadataSource
  with StaticGeneralBitmapSource {

  /** General bitmaps */
  private[this] val _generalBitmaps =
    Seq[BufferedImage](
      createSingleColorTile().toRenderedRepresentation.awtBufferedImage,
      createDoubleColorTile(PresetColors('black)).toRenderedRepresentation.awtBufferedImage,
      createDoubleColorTile(PresetColors('gray)).toRenderedRepresentation.awtBufferedImage,
      createDoubleColorTile(PresetColors('white)).toRenderedRepresentation.awtBufferedImage)

  /** Indices of general bitmaps. */
  val GeneralBitmapIndices: Range = _generalBitmaps.indices

  /** First possible bitmap index. */
  val FirstBitmapIndex = GeneralBitmapIndices.start

  /** Last possible bitmap index. */
  val LastBitmapIndex = GeneralBitmapIndices.last


  /**
   *
   *
   * @param bitmapNumber
   */
  def validateBitmapNumber(bitmapNumber: Int): Unit = {
    require(GeneralBitmapIndices.contains(bitmapNumber),
      s"This resource supports ${numberOfGeneralBitmaps()} bitmaps " +
        s"(indices $FirstBitmapIndex to $LastBitmapIndex).")
  }


  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def generalBitmapOption(bitmapNumber: Int): Option[BufferedImage] = {
    validateBitmapNumber(bitmapNumber)

    Option(_generalBitmaps(bitmapNumber))
  }

  /**
   *
   *
   * @return
   */
  override def numberOfGeneralBitmaps(): Int = _generalBitmaps.length

  /**
   *
   *
   * @return
   */
  override def generalBitmapsOption(): Option[Seq[BufferedImage]] =
    Option(_generalBitmaps)

  /**
   *
   * @return
   */
  def createSingleColorTile(): Bitmap = {
    val tileSideLength = GS.intFor(ColorVisualizationTileSideLengthInPixels)

    var colorTile = Bitmap(
      widthInPixels = tileSideLength,
      heightInPixels = tileSideLength,
      viewerHandling = PreventViewerUpdates)

    colorTile = colorTile.drawRectangle(
      0,
      0,
      colorTile.widthInPixels - 1,
      colorTile.heightInPixels - 1,
      hasBorder = false,
      hasFilling = true,
      fillColor = relatedRGBAColor,
      viewerHandling = PreventViewerUpdates)

    colorTile
  }

  /**
   *
   *
   * @param backgroundColor
   * @return
   */
  def createDoubleColorTile(backgroundColor: RGBAColor): Bitmap = {
    val tileSideLength = GS.intFor(ColorVisualizationTileSideLengthInPixels)

    var colorTile = Bitmap(
      widthInPixels = tileSideLength,
      heightInPixels = tileSideLength,
      viewerHandling = PreventViewerUpdates)

    colorTile = colorTile.drawRectangle(0, 0, colorTile.widthInPixels, colorTile.heightInPixels,
      hasBorder = false,
      hasFilling = true,
      fillColor = backgroundColor,
      viewerHandling = PreventViewerUpdates)

    colorTile = colorTile.drawRectangle(
      upperLeftCornerXInPixels = (0.25 * colorTile.widthInPixels.toDouble).toInt,
      upperLeftCornerYInPixels = (0.25 * colorTile.heightInPixels.toDouble).toInt,
      widthInPixels = (0.5 * colorTile.widthInPixels.toDouble).toInt,
      heightInPixels = (0.5 * colorTile.heightInPixels.toDouble).toInt,
      hasBorder = false,
      hasFilling = true,
      fillColor = relatedRGBAColor,
      viewerHandling = PreventViewerUpdates)

    colorTile
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceIdOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    Some(relatedRGBAColor.toHexString)
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceAuthorsOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceKeywordsOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceTimestampOption(bitmapNumber: Int = FirstBitmapIndex): Option[Date] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceDescriptionOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceTitleOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

}
