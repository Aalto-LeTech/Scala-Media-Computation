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

package smcl.infrastructure


import smcl.colors.rgb.Color
import smcl.modeling.AffineTransformation
import smcl.settings._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
trait DrawingSurfaceAdapter {

  /**
   *
   *
   * @param color
   */
  def clearUsing(
      color: Color,
      useSourceColorLiterally: Boolean): Unit

  /**
   *
   *
   * @param bitmap
   *
   * @return
   */
  def drawBitmap(bitmap: BitmapBufferAdapter): Boolean

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
  def drawBitmap(
      bitmap: BitmapBufferAdapter,
      xInPixels: Double,
      yInPixels: Double,
      opacity: Int): Boolean

  /**
   *
   *
   * @param bitmap
   * @param transformation
   * @param opacity
   *
   * @return
   */
  def drawBitmap(
      bitmap: BitmapBufferAdapter,
      transformation: AffineTransformation,
      opacity: Int): Boolean

  /**
   *
   *
   * @param bitmap
   * @param transformation
   *
   * @return
   */
  def drawBitmap(
      bitmap: BitmapBufferAdapter,
      transformation: AffineTransformation): Boolean

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   * @param color
   */
  def drawPoint(
      xInPixels: Double,
      yInPixels: Double,
      color: Color): Unit

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
  def drawEllipse(
      boundingBoxUpperLeftX: Double,
      boundingBoxUpperLeftY: Double,
      widthInPixels: Double,
      heightInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit

  /**
   *
   *
   * @param xOffsetToOrigoInPixels
   * @param yOffsetToOrigoInPixels
   * @param arcWidthInPixels
   * @param arcHeightInPixels
   * @param startAngleInDegrees
   * @param arcAngleInDegrees
   * @param transformation
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   */
  def drawArc(
      xOffsetToOrigoInPixels: Double,
      yOffsetToOrigoInPixels: Double,
      arcWidthInPixels: Double,
      arcHeightInPixels: Double,
      startAngleInDegrees: Double,
      arcAngleInDegrees: Double,
      transformation: AffineTransformation,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit

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
      fillColor: Color): Unit

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
  def drawRectangle(
      upperLeftCornerXInPixels: Double,
      upperLeftCornerYInPixels: Double,
      widthInPixels: Double,
      heightInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit

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
      upperLeftCornerXInPixels: Double,
      upperLeftCornerYInPixels: Double,
      widthInPixels: Double,
      heightInPixels: Double,
      roundingWidthInPixels: Double,
      roundingHeightInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit

  /**
   *
   *
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param color
   */
  def drawPolyline(
      xCoordinates: Seq[Double],
      yCoordinates: Seq[Double],
      numberOfCoordinatesToDraw: Int,
      color: Color = DefaultPrimaryColor): Unit

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
      xCoordinates: Seq[Double],
      yCoordinates: Seq[Double],
      numberOfCoordinatesToDraw: Int,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: Color,
      fillColor: Color): Unit

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
      fromXInPixels: Double,
      fromYInPixels: Double,
      toXInPixels: Double,
      toYInPixels: Double,
      color: Color): Unit

}
