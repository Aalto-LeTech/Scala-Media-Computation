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


import java.awt.geom.{AffineTransform, Arc2D, Ellipse2D}
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

  /** A ``BasicStroke`` instance to represent as thin line as possible (i.e., width = 1 pixel). */
  private val HairlineStroke = new BasicStroke(0)

  /** A static ``Arc2D.Double`` instance to minimize creation of new objects during rendering. */
  private val StaticArc = new Arc2D.Double()

  /** A static ``Ellipse2D.Double`` instance to minimize creation of new objects during rendering. */
  private val StaticEllipse = new Ellipse2D.Double()

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

    val x = xInPixels.truncatedInt
    val y = yInPixels.truncatedInt

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

    val x = xInPixels.truncatedInt
    val y = yInPixels.truncatedInt

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

    val upperLeftX: Int = boundingBoxUpperLeftX.truncatedInt
    val upperLeftY: Int = boundingBoxUpperLeftY.truncatedInt
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
   * @param xPositionOfCenterInPixels
   * @param yPositionOfCenterInPixels
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
      xPositionOfCenterInPixels: Double,
      yPositionOfCenterInPixels: Double,
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

    val upperLeftXOffsetFromCenter = (scaledWidth / 2.0).truncate
    val upperLeftYOffsetFromCenter = (scaledHeight / 2.0).truncate

    val representsCompleteCycle = arcAngleInDegrees.abs >= 360

    owner.withGraphics2D{g =>
      g.setTransform(AffineTransform.getTranslateInstance(
        xOffsetToOrigoInPixels.truncate + xPositionOfCenterInPixels.truncate,
        yOffsetToOrigoInPixels.truncate + yPositionOfCenterInPixels.truncate))

      g.setStroke(HairlineStroke)

      // If drawing a complete cycle, check if the cycle represents a small circle
      if (representsCompleteCycle &&
          ((scaledWidth == 1.0 && scaledHeight == 1.0)
              || (scaledWidth == 2.0 && scaledHeight == 2.0))) {

        //-------------------------------------------------------------------------
        // Special case for small circles
        //

        if (hasBorder || hasFilling) {
          if (hasBorder) {
            g.setColor(color.toAWTColor)
          }
          else if (hasFilling) {
            g.setColor(fillColor.toAWTColor)
          }

          if (scaledWidth == 1.0) {
            g.fillRect(
              upperLeftXOffsetFromCenter.toInt,
              upperLeftYOffsetFromCenter.toInt,
              1, 1)
          }
          else {
            g.fillRect(
              upperLeftXOffsetFromCenter.toInt - 2,
              upperLeftYOffsetFromCenter.toInt - 2,
              2, 2)
          }
        }
      }
      else {
        //-------------------------------------------------------------------------
        // General case for all arcs
        //

        if (rotationAngleInDegrees != 0.0)
          g.rotate(rotationAngleInDegrees)

        if (hasFilling && !hasBorder) {

          g.setColor(fillColor.toAWTColor)

          val ulX =
            if (xOffsetToOrigoInPixels + xPositionOfCenterInPixels - upperLeftXOffsetFromCenter > 0)
              -upperLeftXOffsetFromCenter + 0.5
            else
              -upperLeftXOffsetFromCenter - 0.5

          val ulY =
            if (yOffsetToOrigoInPixels + yPositionOfCenterInPixels - upperLeftYOffsetFromCenter > 0)
              -upperLeftYOffsetFromCenter + 0.5
            else
              -upperLeftYOffsetFromCenter - 0.5

          if (representsCompleteCycle) {
            // The arc represents a circle or an ellipse

            /*
            g.fillOval(
              -upperLeftXOffsetFromCenter.toInt - 1,
              -upperLeftYOffsetFromCenter.toInt - 1,
              (scaledWidth + 1).toInt,
              (scaledHeight + 1).toInt)
            // */

            //*
            StaticEllipse.setFrame(
              ulX,
              ulY,
              scaledWidth,
              scaledHeight)

            g.fill(StaticEllipse)
            // */
          }
          else {
            // The arc does not represent a circle or an ellipse

            StaticArc.setArc(
              ulX,
              ulY,
              scaledWidth,
              scaledHeight,
              startAngleInDegrees,
              arcAngleInDegrees,
              Arc2D.OPEN)

            g.fill(StaticArc)
          }
        }
        else if (hasBorder && !hasFilling) {
          g.setColor(color.toAWTColor)

          if (representsCompleteCycle) {
            // The arc represents a circle or an ellipse

            /*
            g.drawOval(
              -upperLeftXOffsetFromCenter.toInt,
              -upperLeftYOffsetFromCenter.toInt,
              (scaledWidth - 1).toInt,
              (scaledHeight - 1).toInt)
            // */

            //*
            StaticEllipse.setFrame(
              -upperLeftXOffsetFromCenter,
              -upperLeftYOffsetFromCenter,
              scaledWidth - 1,
              scaledHeight - 1)

            g.draw(StaticEllipse)
            // */
          }
          else {
            // The arc does not represent a circle or an ellipse

            StaticArc.setArc(
              -upperLeftXOffsetFromCenter,
              -upperLeftYOffsetFromCenter,
              scaledWidth - 1,
              scaledHeight - 1,
              startAngleInDegrees,
              arcAngleInDegrees,
              Arc2D.OPEN)

            g.draw(StaticArc)
          }
        }
        else {
          // Has both border and filling

          val fillingUpperLeftX =
            if (xOffsetToOrigoInPixels + xPositionOfCenterInPixels - upperLeftXOffsetFromCenter > 0)
              -upperLeftXOffsetFromCenter + 0.5
            else
              -upperLeftXOffsetFromCenter

          val fillingUpperLeftY =
            if (yOffsetToOrigoInPixels + yPositionOfCenterInPixels - upperLeftYOffsetFromCenter > 0)
              -upperLeftYOffsetFromCenter + 0.5
            else
              -upperLeftYOffsetFromCenter

          g.setColor(fillColor.toAWTColor)

          if (representsCompleteCycle) {
            // The arc represents a circle or an ellipse

            /*
            g.fillOval(
              -upperLeftXOffsetFromCenter.toInt,
              -upperLeftYOffsetFromCenter.toInt,
              (scaledWidth - 1).toInt,
              (scaledHeight - 1).toInt)
            // */

            //*
            StaticEllipse.setFrame(
              fillingUpperLeftX,
              fillingUpperLeftY,
              scaledWidth - 1,
              scaledHeight - 1)

            g.fill(StaticEllipse)
            // */
          }
          else {
            // The arc does not represent a circle or an ellipse

            StaticArc.setArc(
              fillingUpperLeftX,
              fillingUpperLeftY,
              scaledWidth - 1,
              scaledHeight - 1,
              startAngleInDegrees,
              arcAngleInDegrees,
              Arc2D.OPEN)

            g.fill(StaticArc)
          }

          g.setColor(color.toAWTColor)

          if (representsCompleteCycle) {
            // The arc represents a circle or an ellipse

            /*
            g.drawOval(
              -upperLeftXOffsetFromCenter.toInt,
              -upperLeftYOffsetFromCenter.toInt,
              (scaledWidth - 1).toInt,
              (scaledHeight - 1).toInt)
            // */

            //*
            StaticEllipse.setFrame(
              -upperLeftXOffsetFromCenter,
              -upperLeftYOffsetFromCenter,
              scaledWidth - 1,
              scaledHeight - 1)

            g.draw(StaticEllipse)
            // */
          }
          else {
            // The arc does not represent a circle or an ellipse

            StaticArc.setArc(
              -upperLeftXOffsetFromCenter,
              -upperLeftYOffsetFromCenter,
              scaledWidth - 1,
              scaledHeight - 1,
              startAngleInDegrees,
              arcAngleInDegrees,
              Arc2D.OPEN)

            g.draw(StaticArc)
          }
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

    val upperLeftX: Int = upperLeftCornerXInPixels.truncatedInt
    val upperLeftY: Int = upperLeftCornerYInPixels.truncatedInt
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

    val upperLeftX: Int = upperLeftCornerXInPixels.truncatedInt
    val upperLeftY: Int = upperLeftCornerYInPixels.truncatedInt
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

    val xs = xCoordinates.map(_.truncatedInt).toArray
    val ys = yCoordinates.map(_.truncatedInt).toArray

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

    val xs = xCoordinates.map(_.truncatedInt).toArray
    val ys = yCoordinates.map(_.truncatedInt).toArray

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

    val startX = fromXInPixels.truncatedInt
    val startY = fromYInPixels.truncatedInt
    val endX = toXInPixels.truncatedInt
    val endY = toYInPixels.truncatedInt

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
