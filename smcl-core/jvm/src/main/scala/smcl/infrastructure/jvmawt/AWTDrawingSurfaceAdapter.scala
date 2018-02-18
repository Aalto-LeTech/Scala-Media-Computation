/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.infrastructure.jvmawt


import java.awt.geom.Arc2D
import java.awt.{AlphaComposite, Graphics2D}

import smcl.colors.ColorValidator
import smcl.colors.rgb.Color
import smcl.infrastructure.{BitmapBufferAdapter, DrawingSurfaceAdapter}
import smcl.modeling.AffineTransformation
import smcl.settings._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object AWTDrawingSurfaceAdapter {

  /**
   *
   *
   * @param owner
   *
   * @return
   */
  def apply(owner: AWTBitmapBufferAdapter): AWTDrawingSurfaceAdapter = {
    new AWTDrawingSurfaceAdapter(owner)
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class AWTDrawingSurfaceAdapter private(val owner: AWTBitmapBufferAdapter)
    extends DrawingSurfaceAdapter {

  /**
   *
   *
   * @param color
   */
  override
  def clearUsing(
      color: Color = DefaultBackgroundColor,
      useSourceColorLiterally: Boolean = false): Unit = {

    withDrawingSurface{ds =>
      if (useSourceColorLiterally)
        ds.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC))

      ds.setColor(AWTColorAdapter(color).awtColor)
      ds.fillRect(0, 0, owner.widthInPixels, owner.heightInPixels)
    }
  }

  /**
   *
   *
   * @param bitmap
   *
   * @return
   */
  override
  def drawBitmap(bitmap: BitmapBufferAdapter): Boolean = {
    drawBitmap(bitmap, 0.0, 0.0)
  }

  /**
   *
   *
   * @param bitmap
   * @param xInPixels
   * @param yInPixels
   * @param opacity
   *
   * @return
   */
  override
  def drawBitmap(
      bitmap: BitmapBufferAdapter,
      xInPixels: Double,
      yInPixels: Double,
      opacity: Int = ColorValidator.MaximumOpacity): Boolean = {

    val x = xInPixels.floor.toInt
    val y = yInPixels.floor.toInt

    val normalizedOpacity: Float = opacity.toFloat / ColorValidator.MaximumOpacity

    withDrawingSurface{ds =>
      ds.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, normalizedOpacity))

      ds.drawImage(bitmap.asInstanceOf[AWTBitmapBufferAdapter].awtBufferedImage, x, y, null)
    }
  }

  /**
   *
   *
   * @param bitmap
   * @param transformation
   * @param opacity
   *
   * @return
   */
  override
  def drawBitmap(
      bitmap: BitmapBufferAdapter,
      transformation: AffineTransformation,
      opacity: Int): Boolean = {

    val normalizedOpacity: Float = opacity.toFloat / ColorValidator.MaximumOpacity

    withDrawingSurface{ds =>
      ds.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, normalizedOpacity))

      ds.drawImage(
        bitmap.asInstanceOf[AWTBitmapBufferAdapter].awtBufferedImage,
        transformation.toAWTAffineTransform,
        null)
    }
  }

  /**
   *
   *
   * @param bitmap
   * @param transformation
   *
   * @return
   */
  override
  def drawBitmap(
      bitmap: BitmapBufferAdapter,
      transformation: AffineTransformation): Boolean = {

    drawBitmap(bitmap, transformation, ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   * @param color
   */
  override
  def drawPoint(
      xInPixels: Double,
      yInPixels: Double,
      color: Color): Unit = {

    val x = xInPixels.floor.toInt
    val y = yInPixels.floor.toInt

    withDrawingSurface{ds =>
      ds.setColor(color.toAWTColor)
      ds.drawLine(x, y, x, y)
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
  override
  def drawEllipse(
      boundingBoxUpperLeftX: Double,
      boundingBoxUpperLeftY: Double,
      widthInPixels: Double = DefaultBitmapWidthInPixels,
      heightInPixels: Double = DefaultBitmapHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    val upperLeftX: Int = boundingBoxUpperLeftX.floor.toInt
    val upperLeftY: Int = boundingBoxUpperLeftY.floor.toInt
    val width: Int = widthInPixels.floor.toInt
    val height: Int = heightInPixels.floor.toInt

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fillOval(upperLeftX, upperLeftY, width, height)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
        ds.drawOval(upperLeftX, upperLeftY, width, height)
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
  override
  def drawArc(
      upperLeftCornerXInPixels: Double,
      upperLeftCornerYInPixels: Double,
      widthInPixels: Double = DefaultBitmapWidthInPixels,
      heightInPixels: Double = DefaultBitmapHeightInPixels,
      startAngleInDegrees: Double = DefaultArcStartAngleInDegrees,
      arcAngleInDegrees: Double = DefaultArcAngleInDegrees,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    val shape = new Arc2D.Double(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      0D.max(widthInPixels - 1.0),
      0D.max(heightInPixels - 1.0),
      startAngleInDegrees,
      arcAngleInDegrees,
      Arc2D.OPEN)

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fill(shape)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
        ds.draw(shape)
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
  override
  def drawRectangle(
      upperLeftCornerXInPixels: Double,
      upperLeftCornerYInPixels: Double,
      widthInPixels: Double = DefaultBitmapWidthInPixels,
      heightInPixels: Double = DefaultBitmapHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    val upperLeftX: Int = upperLeftCornerXInPixels.floor.toInt
    val upperLeftY: Int = upperLeftCornerYInPixels.floor.toInt
    val width: Int = widthInPixels.floor.toInt
    val height: Int = heightInPixels.floor.toInt

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fillRect(upperLeftX, upperLeftY, width, height)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
        ds.drawRect(upperLeftX, upperLeftY, width, height)
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
  override
  def drawRoundedRectangle(
      upperLeftCornerXInPixels: Double,
      upperLeftCornerYInPixels: Double,
      widthInPixels: Double = DefaultBitmapWidthInPixels,
      heightInPixels: Double = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Double = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Double = DefaultRoundingHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    val upperLeftX: Int = upperLeftCornerXInPixels.floor.toInt
    val upperLeftY: Int = upperLeftCornerYInPixels.floor.toInt
    val width: Int = widthInPixels.floor.toInt
    val height: Int = heightInPixels.floor.toInt
    val roundingWidth: Int = roundingWidthInPixels.floor.toInt
    val roundingHeight: Int = roundingHeightInPixels.floor.toInt

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fillRoundRect(upperLeftX, upperLeftY, width, height, roundingWidth, roundingHeight)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
        ds.drawRoundRect(upperLeftX, upperLeftY, width, height, roundingWidth, roundingHeight)
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
  override
  def drawPolyline(
      xCoordinates: Seq[Double],
      yCoordinates: Seq[Double],
      numberOfCoordinatesToDraw: Int,
      color: Color = DefaultPrimaryColor): Unit = {

    val xs = xCoordinates.map(_.floor.toInt).toArray
    val ys = yCoordinates.map(_.floor.toInt).toArray

    withDrawingSurface{ds =>
      ds.setColor(color.toAWTColor)
      ds.drawPolyline(xs, ys, numberOfCoordinatesToDraw)
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
  override
  def drawPolygon(
      xCoordinates: Seq[Double],
      yCoordinates: Seq[Double],
      numberOfCoordinatesToDraw: Int,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    val xs = xCoordinates.map(_.floor.toInt).toArray
    val ys = yCoordinates.map(_.floor.toInt).toArray

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fillPolygon(xs, ys, numberOfCoordinatesToDraw)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
        ds.drawPolygon(xs, ys, numberOfCoordinatesToDraw)
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
  override
  def drawLine(
      fromXInPixels: Double,
      fromYInPixels: Double,
      toXInPixels: Double,
      toYInPixels: Double,
      color: Color = DefaultPrimaryColor): Unit = {

    val startX = fromXInPixels.floor.toInt
    val startY = fromYInPixels.floor.toInt
    val endX = toXInPixels.floor.toInt
    val endY = toYInPixels.floor.toInt

    withDrawingSurface{ds =>
      ds.setColor(color.toAWTColor)
      ds.drawLine(startX, startY, endX, endY)
    }
  }

  /**
   *
   *
   * @param workUnit
   * @tparam ResultType
   *
   * @return
   */
  def use[ResultType](workUnit: Graphics2D => ResultType): ResultType = {
    withDrawingSurface[ResultType](workUnit)
  }

  /**
   *
   *
   * @param workUnit
   * @tparam ResultType
   *
   * @return
   */
  protected
  def withDrawingSurface[ResultType](workUnit: Graphics2D => ResultType): ResultType = {
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
