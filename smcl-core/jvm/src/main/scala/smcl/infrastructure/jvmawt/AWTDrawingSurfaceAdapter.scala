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
      xOffsetToOrigoInPixels: Double,
      yOffsetToOrigoInPixels: Double,
      widthInPixels: Double,
      heightInPixels: Double,
      startAngleInDegrees: Double,
      arcAngleInDegrees: Double,
      transformation: AffineTransformation,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit = {

    //----------------------------------------------------------------------------------------------
    // Special cases for small circles
    //
    // Are we drawing a small circle (i.e., full cycle and width == height == (1|2))?
    if (arcAngleInDegrees >= 360 &&
        ((owner.widthInPixels == 1 && owner.heightInPixels == 1)
            || (owner.widthInPixels == 2 && owner.heightInPixels == 2))) {

      owner.withGraphics2D{g =>
        g.transform(transformation.toAWTAffineTransform)
        g.setStroke(HairlineStroke)

        if (hasBorder) {
          g.setColor(color.toAWTColor)
        }
        else if (hasFilling) {
          g.setColor(fillColor.toAWTColor)
        }

        if (owner.widthInPixels == 2) {
          // 2 x 2 px full cycle = 2 x 2 px "circle"
          //println("2 x 2 px circle")
          g.fillRect(-1, -1, 2, 2)
        }
        else {
          // 1 x 1 px full cycle = 1 x 1 px "circle"
          //println("1 x 1 px circle")
          g.fillRect(-1, -1, 1, 1)
        }
      }
      return
    }

    //----------------------------------------------------------------------------------------------
    // The general case
    //

    val flooredWidth = widthInPixels.floor
    val flooredHeight = heightInPixels.floor
    val isOddWidth = flooredWidth % 2 == 1
    val isOddHeight = flooredHeight % 2 == 1

    val upperLeftCornerXInPixels = -(widthInPixels / 2.0)
    val upperLeftCornerYInPixels = -(heightInPixels / 2.0)

    // Adjust X position and width to produce a symmetrical and right-sized circle
    val (adjustedUpperLeftX, adjustedWidth, adjustedTransformationM02) =
      adjustOneDimensionOfArc(finalTransformation.tauX, upperLeftCornerXInPixels,
        flooredWidth, hasBorder, isOddWidth)

    // Adjust Y position and height to produce a symmetrical and right-sized circle
    val (adjustedUpperLeftY, adjustedHeight, adjustedTransformationM12) =
      adjustOneDimensionOfArc(finalTransformation.tauY, upperLeftCornerYInPixels,
        flooredHeight, hasBorder, isOddHeight)

    // Create a new transformationTranslationComponent matrix with previous position-wise adjustments
    val transformationM00 = finalTransformation.alpha
    val transformationM10 = finalTransformation.delta
    val transformationM01 = finalTransformation.gamma
    val transformationM11 = finalTransformation.beta

    val adjustedTransformation =
      new AffineTransform(
        transformationM00, transformationM10, transformationM01,
        transformationM11, adjustedTransformationM02, adjustedTransformationM12)

    //*
    println(s"($adjustedUpperLeftX,$adjustedUpperLeftY); " +
        s"w = $adjustedWidth & h = $adjustedHeight, " +
        s"Tx=${adjustedTransformation.getTranslateX}, " +
        s"Ty=${adjustedTransformation.getTranslateY}")
    // */

    /*
    println(
      s""""X scale" = $transformationM00,
         |"Y scale" = $transformationM11
         | (including possible rotation)""".stripMargin.replaceAll("\n", " "))
    // */

    // Create and draw the shape
    val shape = new Arc2D.Double(
      adjustedUpperLeftX,
      adjustedUpperLeftY,
      adjustedWidth,
      adjustedHeight,
      startAngleInDegrees,
      arcAngleInDegrees,
      Arc2D.OPEN)

    owner.withGraphics2D{g =>
      g.setColor(Color(255, 0, 0, 255).toAWTColor)
      g.fillRect(0, 0, 1, 1)

      /*
      val curTx = g.getTransform
      println(
        s"""  -- current in G2D: "X scale" = ${curTx.getScaleX},
           |"Y scale" = ${curTx.getScaleY}
           | (including possible rotation)""".stripMargin.replaceAll("\n", " "))
      // */

      g.translate(xOffsetToOrigoInPixels, yOffsetToOrigoInPixels)
      g.transform(adjustedTransformation)
      g.setStroke(HairlineStroke)

      if (hasFilling) {
        g.setColor(fillColor.toAWTColor)
        g.fill(shape)
      }

      if (hasBorder) {
        g.setColor(color.toAWTColor)
        g.draw(shape)
      }

      g.setTransform(g.getDeviceConfiguration.getDefaultTransform)
      g.setColor(Color(0, 0, 0, 255).toAWTColor)
      val w = owner.widthInPixels
      val h = owner.heightInPixels
      val centerX = (w / 2.0).truncatedInt
      val centerY = (h / 2.0).truncatedInt

      for (x <- 0 to w)
        g.fillRect(x, centerY, 1, 1)

      for (y <- 0 to h)
        g.fillRect(centerX, y, 1, 1)

      g.translate(xOffsetToOrigoInPixels, yOffsetToOrigoInPixels)
      g.transform(adjustedTransformation)
      val trans = AffineTransform.getTranslateInstance(
        adjustedTransformation.getTranslateX, adjustedTransformation.getTranslateY)
      g.setTransform(trans)
      g.setStroke(HairlineStroke)
      g.setColor(Color(0, 200, 0, 255).toAWTColor)

      g.fillRect(0, 0, 1, 1)
      for (c <- -10 to 10) {
        g.fillRect(0, c, 1, 1)
        g.fillRect(c, 0, 1, 1)
      }
    }
  }

  private
  def adjustOneDimensionOfArc(
      transformationTranslationComponent: Double,
      upperLeftCoordinateInPixels: Double,
      flooredSize: Double,
      hasBorder: Boolean,
      isOddSize: Boolean): (Double, Double, Double) = {

    /*
    return (
        transformationTranslationComponent.truncate,
        flooredSize,
        upperLeftCoordinateInPixels.truncate)
    // **/

    val truncTranslComp = transformationTranslationComponent.truncate + 0.5
    val truncULCoord = upperLeftCoordinateInPixels.truncate - 0.5

    if (hasBorder) {
      // For arcs having a border
      if (isOddSize) {
        (truncULCoord - 0.5, flooredSize - 1, truncTranslComp + 0.5)
      } else {
        (truncULCoord, flooredSize - 1, truncTranslComp)
      }
    }
    else {
      // For arcs without a border, the coordinate has to be adjusted by one more
      // pixel and the size to be increased by two pixels to fill the whole bitmap
      if (isOddSize) {
        (truncULCoord - 1.5, flooredSize + 1, truncTranslComp + 0.5)
      } else {
        (truncULCoord - 1, flooredSize + 1, truncTranslComp)
      }
    }
    /*
    val truncTranslComp = transformationTranslationComponent.truncate + 0.5
    val truncULCoord = upperLeftCoordinateInPixels.truncate - 0.5

    if (hasBorder) {
      // For arcs having a border
      if (isOddSize) {
        (truncULCoord - 0.5, flooredSize - 1, truncTranslComp + 0.5)
      } else {
        (truncULCoord, flooredSize - 1, truncTranslComp)
      }
    }
    else {
      // For arcs without a border, the coordinate has to be adjusted by one more
      // pixel and the size to be increased by two pixels to fill the whole bitmap
      if (isOddSize) {
        (truncULCoord - 1.5, flooredSize + 1, truncTranslComp + 0.5)
      } else {
        (truncULCoord - 1, flooredSize + 1, truncTranslComp)
      }
    }
    */
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
  //override
  def drawArc2(
      upperLeftCornerXInPixels: Double,
      upperLeftCornerYInPixels: Double,
      widthInPixels: Double,
      heightInPixels: Double,
      startAngleInDegrees: Double,
      arcAngleInDegrees: Double,
      transformation: AffineTransformation,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit = {

    //----------------------------------------------------------------------------------------------
    // Special cases for small circles
    //
    // Are we drawing a small circle (i.e., full cycle and width == height == (1|2))?
    if (arcAngleInDegrees >= 360 &&
        ((owner.widthInPixels == 1 && owner.heightInPixels == 1)
            || (owner.widthInPixels == 2 && owner.heightInPixels == 2))) {

      owner.withGraphics2D{g =>
        g.transform(transformation.toAWTAffineTransform)
        g.setStroke(HairlineStroke)

        if (hasBorder) {
          g.setColor(color.toAWTColor)
        }
        else if (hasFilling) {
          g.setColor(fillColor.toAWTColor)
        }

        if (owner.widthInPixels == 2) {
          // 2 x 2 px full cycle = 2 x 2 px "circle"
          //println("2 x 2 px circle")
          g.fillRect(-1, -1, 2, 2)
        }
        else {
          // 1 x 1 px full cycle = 1 x 1 px "circle"
          //println("1 x 1 px circle")
          g.fillRect(-1, -1, 1, 1)
        }
      }
      return
    }

    //----------------------------------------------------------------------------------------------
    // The general case
    //
    val flooredWidth = widthInPixels.floor
    val flooredHeight = heightInPixels.floor
    val isOddWidth = flooredWidth % 2 == 1
    val isOddHeight = flooredHeight % 2 == 1

    // Adjust X position and width to produce a symmetrical and right-sized circle
    val (adjustedUpperLeftX, adjustedWidth, adjustedTransformationM02) =
      adjustOneDimensionOfArc(transformation.tauX, upperLeftCornerXInPixels,
        flooredWidth, hasBorder, isOddWidth)

    // Adjust Y position and height to produce a symmetrical and right-sized circle
    val (adjustedUpperLeftY, adjustedHeight, adjustedTransformationM12) =
      adjustOneDimensionOfArc(transformation.tauY, upperLeftCornerYInPixels,
        flooredHeight, hasBorder, isOddHeight)

    // Create a new transformationTranslationComponent matrix with previous position-wise adjustments
    val transformationM00 = transformation.alpha
    val transformationM10 = transformation.delta
    val transformationM01 = transformation.gamma
    val transformationM11 = transformation.beta

    val adjustedTransformation =
      new AffineTransform(
        transformationM00, transformationM10, transformationM01,
        transformationM11, adjustedTransformationM02, adjustedTransformationM12)

    //*
    println(s"($adjustedUpperLeftX,$adjustedUpperLeftY); " +
        s"w = $adjustedWidth & h = $adjustedHeight, " +
        s"Tx=${adjustedTransformation.getTranslateX}, " +
        s"Ty=${adjustedTransformation.getTranslateY}")
    // */

    /*
    println(
      s""""X scale" = $transformationM00,
         |"Y scale" = $transformationM11
         | (including possible rotation)""".stripMargin.replaceAll("\n", " "))
    // */

    // Create and draw the shape
    val shape = new Arc2D.Double(
      adjustedUpperLeftX,
      adjustedUpperLeftY,
      adjustedWidth,
      adjustedHeight,
      startAngleInDegrees,
      arcAngleInDegrees,
      Arc2D.OPEN)

    owner.withGraphics2D{g =>
      /*
      val curTx = g.getTransform
      println(
        s"""  -- current in G2D: "X scale" = ${curTx.getScaleX},
           |"Y scale" = ${curTx.getScaleY}
           | (including possible rotation)""".stripMargin.replaceAll("\n", " "))
      // */

      g.setTransform(adjustedTransformation)
      g.setStroke(HairlineStroke)

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
