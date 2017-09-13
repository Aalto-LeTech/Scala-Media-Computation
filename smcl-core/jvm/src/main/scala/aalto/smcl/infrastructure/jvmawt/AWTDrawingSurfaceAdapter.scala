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

package aalto.smcl.infrastructure.jvmawt


import java.awt.{AlphaComposite, Graphics2D}

import aalto.smcl.colors.ColorValidator
import aalto.smcl.colors.rgb.Color
import aalto.smcl.modeling.AffineTransformation
import aalto.smcl.infrastructure.{BitmapBufferAdapter, DrawingSurfaceAdapter}
import aalto.smcl.settings._




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
  override def clearUsing(
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
  override def drawBitmap(bitmap: BitmapBufferAdapter): Boolean = {
    drawBitmap(bitmap, 0, 0)
  }

  /**
   *
   *
   * @param bitmap
   * @param x
   * @param y
   * @param opacity
   *
   * @return
   */
  override def drawBitmap(
      bitmap: BitmapBufferAdapter,
      x: Int,
      y: Int,
      opacity: Int = ColorValidator.MaximumOpacity): Boolean = {

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
  override def drawBitmap(
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
  override def drawBitmap(
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
  override def drawPoint(xInPixels: Double, yInPixels: Double, color: Color): Unit = {
    val x = math.floor(xInPixels).toInt
    val y = math.floor(yInPixels).toInt

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
  override def drawEllipse(
      boundingBoxUpperLeftX: Int,
      boundingBoxUpperLeftY: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fillOval(
          boundingBoxUpperLeftX, boundingBoxUpperLeftY,
          widthInPixels, heightInPixels)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
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
  override def drawArc(
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      startAngleInDegrees: Int = DefaultArcStartAngleInDegrees,
      arcAngleInDegrees: Int = DefaultArcAngleInDegrees,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fillArc(
          upperLeftCornerXInPixels, upperLeftCornerYInPixels,
          widthInPixels, heightInPixels,
          startAngleInDegrees, arcAngleInDegrees)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
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
  override def drawRectangle(
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fillRect(
          upperLeftCornerXInPixels, upperLeftCornerYInPixels,
          widthInPixels, heightInPixels)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
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
  override def drawRoundedRectangle(
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fillRoundRect(
          upperLeftCornerXInPixels, upperLeftCornerYInPixels,
          widthInPixels, heightInPixels,
          roundingWidthInPixels, roundingHeightInPixels)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
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
  override def drawPolyline(
      xCoordinates: Seq[Int],
      yCoordinates: Seq[Int],
      numberOfCoordinatesToDraw: Int,
      color: Color = DefaultPrimaryColor): Unit = {

    withDrawingSurface{ds =>
      ds.setColor(color.toAWTColor)
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
  override def drawPolygon(
      xCoordinates: Seq[Int],
      yCoordinates: Seq[Int],
      numberOfCoordinatesToDraw: Int,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Unit = {

    withDrawingSurface{ds =>
      if (hasFilling) {
        ds.setColor(fillColor.toAWTColor)
        ds.fillPolygon(xCoordinates.toArray, yCoordinates.toArray, numberOfCoordinatesToDraw)
      }

      if (hasBorder) {
        ds.setColor(color.toAWTColor)
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
  override def drawLine(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor): Unit = {

    withDrawingSurface{ds =>
      ds.setColor(color.toAWTColor)
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
