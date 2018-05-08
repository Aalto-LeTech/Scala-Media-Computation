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


import java.awt.geom.{AffineTransform, Arc2D}
import java.awt.{AlphaComposite, BasicStroke, Graphics2D}

import smcl.colors.ColorValidator
import smcl.colors.rgb.Color
import smcl.infrastructure.{BitmapBufferAdapter, DoubleWrapper, DrawingSurfaceAdapter}
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
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param startAngleInDegrees
   * @param arcAngleInDegrees
   * @param transformation
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
      transformation: AffineTransformation = AffineTransformation.Identity,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    val flooredWidth = widthInPixels.floor
    val flooredHeight = heightInPixels.floor
    val isOddWidth = flooredWidth % 2 == 1
    val isOddHeight = flooredHeight % 2 == 1

    val (adjustedUpperLeftX, adjustedWidth, adjustedTransformationM02) = {
      val transformationM02 = transformation.tauX.truncate + 0.5
      val truncatedUpperLeftX = upperLeftCornerXInPixels.truncate - 0.5

      if (isOddWidth) {
        (truncatedUpperLeftX - 0.5, flooredWidth - 1, transformationM02 + 0.5)
      } else {
        (truncatedUpperLeftX, flooredWidth - 1, transformationM02)
      }
    }

    val (adjustedUpperLeftY, adjustedHeight, adjustedTransformationM12) = {
      val transformationM12 = transformation.tauY.truncate + 0.5
      val truncatedUpperLeftY = upperLeftCornerYInPixels.truncate - 0.5

      if (isOddHeight) {
        (truncatedUpperLeftY - 0.5, flooredHeight - 1, transformationM12 + 0.5)
      } else {
        (truncatedUpperLeftY, flooredHeight - 1, transformationM12)
      }
    }

    val transformationM00 = transformation.alpha
    val transformationM10 = transformation.delta
    val transformationM01 = transformation.gamma
    val transformationM11 = transformation.beta

    val adjustedTransformation =
      new AffineTransform(
        transformationM00, transformationM10, transformationM01,
        transformationM11, adjustedTransformationM02, adjustedTransformationM12)

    /*
    println(s"($adjustedUpperLeftX,$adjustedUpperLeftY); " +
        s"w = $adjustedWidth & h = $adjustedHeight, " +
        s"Tx=${adjustedTransformation.getTranslateX}, " +
        s"Ty=${adjustedTransformation.getTranslateY}")
    */

    val shape = new Arc2D.Double(
      adjustedUpperLeftX,
      adjustedUpperLeftY,
      adjustedWidth,
      adjustedHeight,
      startAngleInDegrees,
      arcAngleInDegrees,
      Arc2D.OPEN)

    owner.withGraphics2D{g =>
      g.transform(adjustedTransformation)
      g.setStroke(new BasicStroke(0))

      if (adjustedWidth < 2 && (hasBorder || hasFilling)) {
        if (hasBorder) {
          g.setColor(color.toAWTColor)
        }
        else if (hasFilling) {
          g.setColor(fillColor.toAWTColor)
        }

        if (adjustedWidth <= 1) {
          g.fillRect(-1, -1, 1, 1)
        }
        else {
          println("Width < 2")
          g.fillRect(-1, -1, 2, 2)
        }
      }
      else {
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
      color: Color = DefaultPrimaryColor): Unit = {

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
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

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
      color: Color = DefaultPrimaryColor): Unit = {

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
