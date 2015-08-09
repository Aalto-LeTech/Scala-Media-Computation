package aalto.smcl.platform


import java.awt.geom.AffineTransform

import scala.swing.Graphics2D

import aalto.smcl.bitmaps.BitmapSettingKeys._
import aalto.smcl.common.{GS, RGBAColor}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformDrawingSurface {

  /**
   *
   *
   * @param owner
   * @return
   */
  def apply(owner: PlatformBitmapBuffer): PlatformDrawingSurface = {
    new PlatformDrawingSurface(owner)
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class PlatformDrawingSurface private(val owner: PlatformBitmapBuffer) {

  /** */
  private[platform] val awtDrawingSurface: Graphics2D =
    owner.awtBufferedImage.createGraphics()

  /**
   *
   *
   * @param color
   */
  def clearUsing(color: RGBAColor = GS.colorFor(DefaultBackground)): Unit = {
    val oldColor = awtDrawingSurface.getColor

    awtDrawingSurface.setColor(PlatformColor(color).awtColor)
    awtDrawingSurface.fillRect(0, 0, owner.widthInPixels, owner.heightInPixels)
    awtDrawingSurface.setColor(oldColor)
  }


  /**
   *
   *
   * @param bitmap
   * @param x
   * @param y
   * @return
   */
  def drawBitmap(bitmap: PlatformBitmapBuffer, x: Int, y: Int): Boolean =
    awtDrawingSurface.drawImage(bitmap.awtBufferedImage, x, y, null)

  /**
   *
   *
   * @param bitmap
   * @param affineTransform
   * @return
   */
  def drawBitmap(bitmap: PlatformBitmapBuffer, affineTransform: AffineTransform): Boolean =
    awtDrawingSurface.drawImage(bitmap.awtBufferedImage, affineTransform, null)


  /**
   *
   *
   * @param boundingBoxUpperLeftX
   * @param boundingBoxUpperLeftY
   * @param widthInPixels
   * @param heightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   */
  def drawEllipse(
    boundingBoxUpperLeftX: Int,
    boundingBoxUpperLeftY: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary)): Unit = {

    val oldColor = this.color

    if (hasFilling) {
      this.color = fillColor

      awtDrawingSurface.fillOval(
        boundingBoxUpperLeftX, boundingBoxUpperLeftY,
        widthInPixels, heightInPixels)
    }

    if (hasBorder) {
      this.color = color

      awtDrawingSurface.drawOval(
        boundingBoxUpperLeftX, boundingBoxUpperLeftY,
        widthInPixels, heightInPixels)
    }

    this.color = oldColor
  }


  /**
   *
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
   */
  def drawArc(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    startAngleInDegrees: Int = GS.intFor(DefaultArcStartAngleInDegrees),
    arcAngleInDegrees: Int = GS.intFor(DefaultArcAngleInDgrees),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary)): Unit = {

    val oldColor = this.color

    if (hasFilling) {
      this.color = color

      awtDrawingSurface.fillArc(
        upperLeftCornerXInPixels, upperLeftCornerYInPixels,
        widthInPixels, heightInPixels,
        startAngleInDegrees, arcAngleInDegrees)
    }

    if (hasBorder) {
      this.color = fillColor

      awtDrawingSurface.drawArc(
        upperLeftCornerXInPixels, upperLeftCornerYInPixels,
        widthInPixels, heightInPixels,
        startAngleInDegrees, arcAngleInDegrees)
    }

    this.color = oldColor
  }

  /**
   *
   *
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   */
  def drawRectangle(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary)): Unit = {

    val oldColor = this.color

    if (hasFilling) {
      this.color = fillColor

      awtDrawingSurface.fillRect(
        upperLeftCornerXInPixels, upperLeftCornerYInPixels,
        widthInPixels, heightInPixels)
    }

    if (hasBorder) {
      this.color = color

      awtDrawingSurface.drawRect(
        upperLeftCornerXInPixels, upperLeftCornerYInPixels,
        widthInPixels, heightInPixels)
    }

    this.color = oldColor
  }

  /**
   *
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
   */
  def drawRoundedRectangle(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
    roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary)): Unit = {

    val oldColor = this.color

    if (hasFilling) {
      this.color = fillColor

      awtDrawingSurface.fillRoundRect(
        upperLeftCornerXInPixels, upperLeftCornerYInPixels,
        widthInPixels, heightInPixels,
        roundingWidthInPixels, roundingHeightInPixels)
    }

    if (hasBorder) {
      this.color = color

      awtDrawingSurface.drawRoundRect(
        upperLeftCornerXInPixels, upperLeftCornerYInPixels,
        widthInPixels, heightInPixels,
        roundingWidthInPixels, roundingHeightInPixels)
    }

    this.color = oldColor
  }

  /**
   *
   *
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param color
   */
  def drawPolyline(
    xCoordinates: Seq[Int],
    yCoordinates: Seq[Int],
    numberOfCoordinatesToDraw: Int,
    color: RGBAColor = GS.colorFor(DefaultPrimary)): Unit = {

    val oldColor = this.color

    this.color = color

    awtDrawingSurface.drawPolyline(xCoordinates.toArray, yCoordinates.toArray, numberOfCoordinatesToDraw)

    this.color = oldColor
  }

  /**
   *
   *
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   */
  def drawPolygon(
    xCoordinates: Seq[Int],
    yCoordinates: Seq[Int],
    numberOfCoordinatesToDraw: Int,
    hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
    hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    fillColor: RGBAColor = GS.colorFor(DefaultSecondary)): Unit = {

    val oldColor = this.color

    if (hasFilling) {
      this.color = fillColor

      awtDrawingSurface.fillPolygon(xCoordinates.toArray, yCoordinates.toArray, numberOfCoordinatesToDraw)
    }

    if (hasBorder) {
      this.color = color

      awtDrawingSurface.drawPolygon(xCoordinates.toArray, yCoordinates.toArray, numberOfCoordinatesToDraw)
    }

    this.color = oldColor
  }

  /**
   *
   *
   * @param fromXInPixels
   * @param fromYInPixels
   * @param toXInPixels
   * @param toYInPixels
   * @param color
   */
  def drawLine(
    fromXInPixels: Int,
    fromYInPixels: Int,
    toXInPixels: Int,
    toYInPixels: Int,
    color: RGBAColor = GS.colorFor(DefaultPrimary)): Unit = {

    val oldColor = this.color

    this.color = color
    awtDrawingSurface.drawLine(
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels)

    this.color = oldColor
  }

  /**
   *
   *
   * @return
   */
  def color: RGBAColor = awtDrawingSurface.getColor.toAapplicationColor

  /**
   *
   *
   * @param value
   */
  def color_=(value: RGBAColor): Unit = awtDrawingSurface.setColor(value.toAwtColor)

}
