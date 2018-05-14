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
import java.awt.{AlphaComposite, BasicStroke, Graphics2D}

import smcl.colors.ColorValidator
import smcl.colors.rgb.Color
import smcl.infrastructure.{BitmapBufferAdapter, DoubleWrapper, DrawingSurfaceAdapter}
import smcl.modeling.AffineTransformation




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

  private val HairlineStroke = new BasicStroke(0)

  /**
   *
   *
   * @param color
   */
  override
  def clearUsing(
      color: Color,
      useSourceColorLiterally: Boolean): Unit = {

    owner.withGraphics2D{g =>
      if (useSourceColorLiterally)
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC))

      g.setColor(AWTColorAdapter(color).awtColor)
      g.fillRect(0, 0, owner.widthInPixels, owner.heightInPixels)
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
    drawBitmap(bitmap, 0.0, 0.0, ColorValidator.MaximumOpacity)
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
      opacity: Int): Boolean = {

    val x = xInPixels.floor.toInt
    val y = yInPixels.floor.toInt

    val normalizedOpacity: Float = opacity.toFloat / ColorValidator.MaximumOpacity

    owner.withGraphics2D{g =>
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, normalizedOpacity))

      g.drawImage(bitmap.asInstanceOf[AWTBitmapBufferAdapter].awtBufferedImage, x, y, null)
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

    owner.withGraphics2D{g =>
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, normalizedOpacity))

      g.drawImage(
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

    owner.withGraphics2D{g =>
      g.setColor(color.toAWTColor)
      g.drawLine(x, y, x, y)
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
  //@deprecated(message="Use drawArc() instead.", since="0.0.4")
  override
  def drawEllipse(
      boundingBoxUpperLeftX: Double,
      boundingBoxUpperLeftY: Double,
      widthInPixels: Double,
      heightInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit = {

    val upperLeftX: Int = boundingBoxUpperLeftX.floor.toInt
    val upperLeftY: Int = boundingBoxUpperLeftY.floor.toInt
    val width: Int = widthInPixels.floor.toInt
    val height: Int = heightInPixels.floor.toInt

    owner.withGraphics2D{g =>
      if (hasFilling) {
        g.setColor(fillColor.toAWTColor)
        g.fillOval(upperLeftX, upperLeftY, width, height)
      }

      if (hasBorder) {
        g.setColor(color.toAWTColor)
        g.drawOval(upperLeftX, upperLeftY, width, height)
      }
    }
  }

  /**
   *
   *
   * @param xOffsetToOrigoInPixels
   * @param yOffsetToOrigoInPixels
   * @param xPosition
   * @param yPosition
   * @param widthInPixels
   * @param heightInPixels
   * @param startAngleInDegrees
   * @param arcAngleInDegrees
   * @param rotationAngleInDegrees
   * @param scaleFactorX
   * @param scaleFactorY
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   */
  override
  def drawArc(
      xOffsetToOrigoInPixels: Double,
      yOffsetToOrigoInPixels: Double,
      xPosition: Double,
      yPosition: Double,
      widthInPixels: Double,
      heightInPixels: Double,
      startAngleInDegrees: Double,
      arcAngleInDegrees: Double,
      rotationAngleInDegrees: Double,
      scaleFactorX: Double,
      scaleFactorY: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit = {


    val scaledWidth = (scaleFactorX * widthInPixels.floor).truncate
    val scaledHeight = (scaleFactorY * heightInPixels.floor).truncate

    val upperLeftX = xOffsetToOrigoInPixels + xPosition - (scaledWidth / 2.0).truncate
    val upperLeftY = yOffsetToOrigoInPixels + yPosition - (scaledHeight / 2.0).truncate

    owner.withGraphics2D{g =>
      g.translate(-0.5, -0.5)
      g.setStroke(HairlineStroke)

      if (arcAngleInDegrees >= 360 &&
          ((scaledWidth == 1.0 && scaledHeight == 1.0)
              || (scaledWidth == 2.0 && scaledHeight == 2.0))) {

        //-------------------------------------------------------------------------
        // Special case for small circles
        //

        if (hasBorder) {
          g.setColor(color.toAWTColor)
        }
        else if (hasFilling) {
          g.setColor(fillColor.toAWTColor)
        }

        if (scaledWidth == 1.0) {
          g.fillRect(upperLeftX.truncatedInt, upperLeftY.truncatedInt, 1, 1)
        }
        else {
          g.fillRect(upperLeftX.truncatedInt, upperLeftY.truncatedInt, 2, 2)
        }
      }
      else {
        //-------------------------------------------------------------------------
        // General case for all arcs
        //

        val shape = new Arc2D.Double(
          upperLeftX + 0.5,
          upperLeftY + 0.5,
          scaledWidth - 1,
          scaledHeight - 1,
          startAngleInDegrees,
          arcAngleInDegrees,
          Arc2D.OPEN)

        if (hasFilling) {
          g.setColor(fillColor.toAWTColor)
          g.fill(shape)
        }

        if (hasBorder) {
          g.setColor(color.toAWTColor)
          g.draw(shape)
        }
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
  //@deprecated(message="Use polygons/polylines instead.", since="0.0.4")
  override
  def drawRectangle(
      upperLeftCornerXInPixels: Double,
      upperLeftCornerYInPixels: Double,
      widthInPixels: Double,
      heightInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit = {

    val upperLeftX: Int = upperLeftCornerXInPixels.floor.toInt
    val upperLeftY: Int = upperLeftCornerYInPixels.floor.toInt
    val width: Int = widthInPixels.floor.toInt
    val height: Int = heightInPixels.floor.toInt

    owner.withGraphics2D{g =>
      if (hasFilling) {
        g.setColor(fillColor.toAWTColor)
        g.fillRect(upperLeftX, upperLeftY, width, height)
      }

      if (hasBorder) {
        g.setColor(color.toAWTColor)
        g.drawRect(upperLeftX, upperLeftY, width, height)
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
      widthInPixels: Double,
      heightInPixels: Double,
      roundingWidthInPixels: Double,
      roundingHeightInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit = {

    val upperLeftX: Int = upperLeftCornerXInPixels.floor.toInt
    val upperLeftY: Int = upperLeftCornerYInPixels.floor.toInt
    val width: Int = widthInPixels.floor.toInt
    val height: Int = heightInPixels.floor.toInt
    val roundingWidth: Int = roundingWidthInPixels.floor.toInt
    val roundingHeight: Int = roundingHeightInPixels.floor.toInt

    owner.withGraphics2D{g =>
      if (hasFilling) {
        g.setColor(fillColor.toAWTColor)
        g.fillRoundRect(upperLeftX, upperLeftY, width, height, roundingWidth, roundingHeight)
      }

      if (hasBorder) {
        g.setColor(color.toAWTColor)
        g.drawRoundRect(upperLeftX, upperLeftY, width, height, roundingWidth, roundingHeight)
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
      color: Color): Unit = {

    val xs = xCoordinates.map(_.floor.toInt).toArray
    val ys = yCoordinates.map(_.floor.toInt).toArray

    owner.withGraphics2D{g =>
      g.setColor(color.toAWTColor)
      g.drawPolyline(xs, ys, numberOfCoordinatesToDraw)
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
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit = {

    val xs = xCoordinates.map(_.floor.toInt).toArray
    val ys = yCoordinates.map(_.floor.toInt).toArray

    owner.withGraphics2D{g =>
      if (hasFilling) {
        g.setColor(fillColor.toAWTColor)
        g.fillPolygon(xs, ys, numberOfCoordinatesToDraw)
      }

      if (hasBorder) {
        g.setColor(color.toAWTColor)
        g.drawPolygon(xs, ys, numberOfCoordinatesToDraw)
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
      color: Color): Unit = {

    val startX = fromXInPixels.floor.toInt
    val startY = fromYInPixels.floor.toInt
    val endX = toXInPixels.floor.toInt
    val endY = toYInPixels.floor.toInt

    owner.withGraphics2D{g =>
      g.setColor(color.toAWTColor)
      g.drawLine(startX, startY, endX, endY)
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
    owner.withGraphics2D[ResultType](workUnit)
  }

}
