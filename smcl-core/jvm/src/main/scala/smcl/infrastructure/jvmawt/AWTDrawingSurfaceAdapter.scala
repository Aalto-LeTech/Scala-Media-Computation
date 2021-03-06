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
   * @param xOffsetToOrigoInPixels
   * @param yOffsetToOrigoInPixels
   * @param xInPixels
   * @param yInPixels
   * @param color
   */
  override
  def drawPoint(
      xOffsetToOrigoInPixels: Double,
      yOffsetToOrigoInPixels: Double,
      xInPixels: Double,
      yInPixels: Double,
      color: Color): Unit = {

    val x = xOffsetToOrigoInPixels.floor.toInt + xInPixels.floor.toInt
    val y = yOffsetToOrigoInPixels.floor.toInt + yInPixels.floor.toInt

    owner.withGraphics2D{g =>
      g.setStroke(HairlineStroke)
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
            if (xOffsetToOrigoInPixels.truncate + xPositionOfCenterInPixels - upperLeftXOffsetFromCenter > 0)
              -upperLeftXOffsetFromCenter + 0.5
            else
              -upperLeftXOffsetFromCenter - 0.5

          val ulY =
            if (yOffsetToOrigoInPixels.truncate + yPositionOfCenterInPixels - upperLeftYOffsetFromCenter > 0)
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
            if (xOffsetToOrigoInPixels.truncate + xPositionOfCenterInPixels - upperLeftXOffsetFromCenter > 0)
              -upperLeftXOffsetFromCenter + 0.5
            else
              -upperLeftXOffsetFromCenter

          val fillingUpperLeftY =
            if (yOffsetToOrigoInPixels.truncate + yPositionOfCenterInPixels - upperLeftYOffsetFromCenter > 0)
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
   * @param xOffsetToOrigoInPixels
   * @param yOffsetToOrigoInPixels
   * @param xPositionInPixels
   * @param yPositionInPixels
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param leftEdgeInPixels
   * @param topEdgeInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   */
  override
  def drawPolygon(
      xOffsetToOrigoInPixels: Double,
      yOffsetToOrigoInPixels: Double,
      xPositionInPixels: Double,
      yPositionInPixels: Double,
      xCoordinates: Seq[Double],
      yCoordinates: Seq[Double],
      numberOfCoordinatesToDraw: Int,
      leftEdgeInPixels: Double,
      topEdgeInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit = {

    if (numberOfCoordinatesToDraw == 1) {
      drawPoint(
        xOffsetToOrigoInPixels,
        yOffsetToOrigoInPixels,
        xPositionInPixels + xCoordinates.head,
        yPositionInPixels + yCoordinates.head,
        color)
    }
    else if (numberOfCoordinatesToDraw > 1) {
      val leftEdgeOffset = leftEdgeInPixels - leftEdgeInPixels.floor
      val topEdgeOffset = topEdgeInPixels - topEdgeInPixels.floor

      val horizontalAdjustment = if (owner.widthInPixels % 2 == 1) 0.5 else 0
      val verticalAdjustment = if (owner.heightInPixels % 2 == 1) 0.5 else 0

      val xOffset = (xOffsetToOrigoInPixels + xPositionInPixels).floor + leftEdgeOffset - horizontalAdjustment
      val yOffset = (yOffsetToOrigoInPixels + yPositionInPixels).floor + topEdgeOffset - verticalAdjustment

      val xs = xCoordinates.map(x => (x - leftEdgeOffset).floor.toInt).toArray
      val ys = yCoordinates.map(y => (y - topEdgeOffset).floor.toInt).toArray

      /*
      if (numberOfCoordinatesToDraw == 2) {
        if (leftEdgeInPixels < -100) {
          println()
          val s = s"Line (${xCoordinates.head}, ${yCoordinates.head})-(${xCoordinates.tail.head}, ${yCoordinates.tail.head})"
          println(s + "\n" + "-" * s.length)
          println(s"Image's dimensions: ${owner.widthInPixels} x ${owner.heightInPixels}")
          println(s"Origo's position: ($xOffsetToOrigoInPixels, $yOffsetToOrigoInPixels)")
          println(s"Object's logical position: ($xPositionInPixels, $yPositionInPixels)")
          println(s"Object's physical position: ($xOffset, $yOffset)")
          println(s"Left edge: $leftEdgeInPixels  --> offset: $leftEdgeOffset")
          println(s"Top edge: $topEdgeInPixels  --> offset: $topEdgeOffset")
          println(s"Adjusted: (${xs.head}, ${ys.head})-(${xs.tail.head}, ${ys.tail.head})")
        }
      }
      else if (numberOfCoordinatesToDraw == 4) {
        println()
        val s = s"4 Lines (a rectangle?) " + xCoordinates.zip(yCoordinates).mkString(" -> ")
        println(s + "\n" + "-" * s.length)
        println(s"Image's dimensions: ${owner.widthInPixels} x ${owner.heightInPixels}")
        println(s"Origo's position: ($xOffsetToOrigoInPixels, $yOffsetToOrigoInPixels)")
        println(s"Object's logical position: ($xPositionInPixels, $yPositionInPixels)")
        println(s"Object's physical position: ($xOffset, $yOffset)")
        println(s"Left edge: $leftEdgeInPixels  -->  offset: $leftEdgeOffset")
        println(s"Top edge: $topEdgeInPixels  -->  offset: $topEdgeOffset")
        println("Adjusted: " + xs.zip(ys).mkString(" -> "))
      }
      // */

      owner.withGraphics2D{g =>
        g.setStroke(HairlineStroke)

        if (hasBorder && !hasFilling) {
          g.setTransform(AffineTransform.getTranslateInstance(xOffset, yOffset))

          g.setColor(color.toAWTColor)
          g.drawPolygon(xs, ys, numberOfCoordinatesToDraw)
        }
        else if (hasBorder && hasFilling) {
          g.setTransform(AffineTransform.getTranslateInstance(
            xOffset + horizontalAdjustment,
            yOffset + verticalAdjustment))

          g.setColor(fillColor.toAWTColor)
          g.fillPolygon(xs, ys, numberOfCoordinatesToDraw)

          g.setTransform(AffineTransform.getTranslateInstance(xOffset, yOffset))

          g.setColor(color.toAWTColor)
          g.drawPolygon(xs, ys, numberOfCoordinatesToDraw)
        }
        else if (!hasBorder && hasFilling) {
          g.setTransform(AffineTransform.getTranslateInstance(
            xOffset + horizontalAdjustment,
            yOffset + verticalAdjustment))

          g.setColor(fillColor.toAWTColor)
          g.fillPolygon(xs, ys, numberOfCoordinatesToDraw)
          g.drawPolygon(xs, ys, numberOfCoordinatesToDraw)
        }
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
