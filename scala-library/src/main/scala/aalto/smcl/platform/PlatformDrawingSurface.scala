package aalto.smcl.platform


import scala.swing.Graphics2D

import aalto.smcl.bitmaps.BitmapSettingKeys._
import aalto.smcl.common.{AffineTransformation, GS, RGBAColor}




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

  /**
   *
   *
   * @param color
   */
  def clearUsing(color: RGBAColor = GS.colorFor(DefaultBackground)): Unit =
    withDrawingSurface {ds =>
      ds.setColor(PlatformColor(color).awtColor)
      ds.fillRect(0, 0, owner.widthInPixels, owner.heightInPixels)
    }

  /**
   *
   *
   * @param bitmap
   * @return
   */
  def drawBitmap(bitmap: PlatformBitmapBuffer): Boolean =
    drawBitmap(bitmap, 0, 0)

  /**
   *
   *
   * @param bitmap
   * @param x
   * @param y
   * @return
   */
  def drawBitmap(bitmap: PlatformBitmapBuffer, x: Int, y: Int): Boolean =
    withDrawingSurface {ds =>
      ds.drawImage(bitmap.awtBufferedImage, x, y, null)
    }

  /**
   *
   *
   * @param bitmap
   * @param transformation
   * @return
   */
  def drawBitmap(
      bitmap: PlatformBitmapBuffer,
      transformation: AffineTransformation): Boolean = {

    withDrawingSurface {ds =>
      ds.drawImage(
        bitmap.awtBufferedImage,
        transformation.platformAffineTransform.awtAffineTransformation,
        null)
    }
  }

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

    withDrawingSurface {ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAwtColor)
        ds.fillOval(
          boundingBoxUpperLeftX, boundingBoxUpperLeftY,
          widthInPixels, heightInPixels)
      }

      if (hasBorder) {
        ds.setColor(color.toAwtColor)
        ds.drawOval(
          boundingBoxUpperLeftX, boundingBoxUpperLeftY,
          widthInPixels, heightInPixels)
      }
    }
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

    withDrawingSurface {ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAwtColor)
        ds.fillArc(
          upperLeftCornerXInPixels, upperLeftCornerYInPixels,
          widthInPixels, heightInPixels,
          startAngleInDegrees, arcAngleInDegrees)
      }

      if (hasBorder) {
        ds.setColor(color.toAwtColor)
        ds.drawArc(
          upperLeftCornerXInPixels, upperLeftCornerYInPixels,
          widthInPixels, heightInPixels,
          startAngleInDegrees, arcAngleInDegrees)
      }
    }
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

    withDrawingSurface {ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAwtColor)
        ds.fillRect(
          upperLeftCornerXInPixels, upperLeftCornerYInPixels,
          widthInPixels, heightInPixels)
      }

      if (hasBorder) {
        ds.setColor(color.toAwtColor)
        ds.drawRect(
          upperLeftCornerXInPixels, upperLeftCornerYInPixels,
          widthInPixels, heightInPixels)
      }
    }
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

    withDrawingSurface {ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAwtColor)
        ds.fillRoundRect(
          upperLeftCornerXInPixels, upperLeftCornerYInPixels,
          widthInPixels, heightInPixels,
          roundingWidthInPixels, roundingHeightInPixels)
      }

      if (hasBorder) {
        ds.setColor(color.toAwtColor)
        ds.drawRoundRect(
          upperLeftCornerXInPixels, upperLeftCornerYInPixels,
          widthInPixels, heightInPixels,
          roundingWidthInPixels, roundingHeightInPixels)
      }
    }
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

    withDrawingSurface {ds =>
      ds.setColor(color.toAwtColor)
      ds.drawPolyline(xCoordinates.toArray, yCoordinates.toArray, numberOfCoordinatesToDraw)
    }
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

    withDrawingSurface {ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAwtColor)
        ds.fillPolygon(xCoordinates.toArray, yCoordinates.toArray, numberOfCoordinatesToDraw)
      }

      if (hasBorder) {
        ds.setColor(color.toAwtColor)
        ds.drawPolygon(xCoordinates.toArray, yCoordinates.toArray, numberOfCoordinatesToDraw)
      }
    }
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

    withDrawingSurface {ds =>
      ds.setColor(color.toAwtColor)
      ds.drawLine(
        fromXInPixels, fromYInPixels,
        toXInPixels, toYInPixels)
    }
  }

  /**
   *
   *
   * @param workUnit
   * @tparam ResultType
   * @return
   */
  def use[ResultType](workUnit: Graphics2D => ResultType): ResultType =
    withDrawingSurface[ResultType](workUnit)

  /**
   *
   *
   * @param workUnit
   * @tparam ResultType
   * @return
   */
  protected def withDrawingSurface[ResultType](workUnit: Graphics2D => ResultType): ResultType = {
    var drawingSurface: Graphics2D = null
    var memorizedThrowable: Throwable = null

    try {
      drawingSurface = owner.awtBufferedImage.createGraphics()
      workUnit(drawingSurface)
    }
    catch {
      case caughtThrowable: Throwable =>
        memorizedThrowable = caughtThrowable
        throw caughtThrowable
    }
    finally {
      if (drawingSurface != null) {
        if (memorizedThrowable != null) {
          try {
            drawingSurface.dispose()
          }
          catch {
            case caughtThrowable: Throwable =>
              memorizedThrowable.addSuppressed(caughtThrowable)
          }
        }
        else {
          drawingSurface.dispose()
        }
      }
    }
  }

}
