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

package smcl.pictures


import scala.collection.mutable

import smcl.colors.ColorValidator
import smcl.infrastructure.{BitmapBufferAdapter, DoubleWrapper, DrawingSurfaceAdapter, InjectablesRegistry, PRF}
import smcl.modeling.d2.BoundaryCalculator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object RenderingController
    extends InjectablesRegistry {

  /** The ColorValidator instance to be used by this object. */
  protected
  lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The BitmapValidator instance to be used by this object. */
  protected
  lazy val bitmapValidator: BitmapValidator = {
    injectable(InjectablesRegistry.IIdBitmapValidator).asInstanceOf[BitmapValidator]
  }

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  def createBitmapFrom(elements: PictureElement*): Bitmap = {
    if (elements.isEmpty)
      return Bitmap(0, 0)

    val bounds =
      if (containsOnlyAnImageThatDefinesViewport(elements))
        elements.head.toPicture.viewport.get.boundary
      else
        BoundaryCalculator.fromBoundaries(elements)

    val flooredWidth = bounds.width.floor
    val flooredHeight = bounds.height.floor

    if (flooredWidth < 1 || flooredHeight < 1)
      return Bitmap(0, 0)

    bitmapValidator.validateBitmapSize(flooredWidth, flooredHeight)

    val buffer: BitmapBufferAdapter =
      PRF.createPlatformBitmapBuffer(flooredWidth, flooredHeight)

    val (xOffsetToOrigoInPixels, yOffsetToOrigoInPixels) = {
      val upperLeftCorner = bounds.upperLeftCorner

      val xPrime = -upperLeftCorner.xInPixels.truncate
      val yPrime = -upperLeftCorner.yInPixels.truncate

      val xOffset = if (xPrime % 2 == 1) xPrime - 0 else xPrime
      val yOffset = if (yPrime % 2 == 1) yPrime - 0 else yPrime

      (xOffset, yOffset)
    }

    renderElements(
      elements,
      buffer.drawingSurface,
      xOffsetToOrigoInPixels, yOffsetToOrigoInPixels)

    Bitmap(buffer)
  }

  private
  def containsOnlyAnImageThatDefinesViewport(
      elements: Seq[PictureElement]): Boolean = {

    elements.lengthCompare(1) == 0 &&
        elements.head.isPicture &&
        elements.head.toPicture.viewport.isDefined
  }

  /**
   *
   *
   * @param content
   * @param targetDrawingSurface
   * @param xOffsetToOrigoInPixels
   * @param yOffsetToOrigoInPixels
   */
  private
  def renderElements(
      content: Seq[PictureElement],
      targetDrawingSurface: DrawingSurfaceAdapter,
      xOffsetToOrigoInPixels: Double,
      yOffsetToOrigoInPixels: Double): Unit = {

    val renderingQueue = mutable.Queue() ++ content.reverse
    while (renderingQueue.nonEmpty) {
      val contentItem = renderingQueue.dequeue()

      if (contentItem.isPicture) {
        renderingQueue ++= contentItem.toPicture.elements.reverse
      }
      else if (contentItem.isArc) {
        val arc = contentItem.asInstanceOf[Arc]

        targetDrawingSurface.drawArc(
          xOffsetToOrigoInPixels, yOffsetToOrigoInPixels,
          arc.position.xInPixels, arc.position.yInPixels,
          arc.untransformedWidthInPixels,
          arc.untransformedHeightInPixels,
          arc.startAngleInDegrees,
          arc.arcAngleInDegrees,
          0,
          arc.currentTransformation.alpha,
          arc.currentTransformation.beta,
          arc.hasBorder, arc.hasFilling,
          arc.color, arc.fillColor)
      }
      else if (contentItem.isBitmap) {
        val bmp = contentItem.asInstanceOf[Bitmap]
        if (bmp.buffer.isEmpty)
          return

        val topLeft = bmp.boundary.upperLeftCorner
        val topLeftX = xOffsetToOrigoInPixels + topLeft.xInPixels
        val topLeftY = yOffsetToOrigoInPixels + topLeft.yInPixels

        targetDrawingSurface.drawBitmap(
          bmp.buffer.get, topLeftX, topLeftY, ColorValidator.MaximumOpacity)
      }
      else if (contentItem.isPoint) {
        val pnt = contentItem.asInstanceOf[Point]
        val topLeftX = xOffsetToOrigoInPixels + pnt.position.xInPixels
        val topLeftY = yOffsetToOrigoInPixels + pnt.position.yInPixels

        targetDrawingSurface.drawPoint(topLeftX, topLeftY, pnt.color)
      }
      else if (contentItem.isPolygon) {
        val pgon = contentItem.asInstanceOf[Polygon]
        if (pgon.points.isEmpty)
          return

        val points = pgon.points

        if (points.lengthCompare(1) == 0) {
          val p = points.head
          val x = xOffsetToOrigoInPixels + p.xInPixels
          val y = yOffsetToOrigoInPixels + p.yInPixels

          targetDrawingSurface.drawPoint(x, y, pgon.color)
        }
        else if (points.lengthCompare(2) == 0) {
          val start = points.head
          val end = points.tail.head
          val xStart = xOffsetToOrigoInPixels + start.xInPixels
          val yStart = yOffsetToOrigoInPixels + start.yInPixels
          val xEnd = xOffsetToOrigoInPixels + end.xInPixels
          val yEnd = yOffsetToOrigoInPixels + end.yInPixels

          targetDrawingSurface.drawLine(xStart, yStart, xEnd, yEnd, pgon.color)
        }
        else {
          val (rawXs, rawYs) = points.unzip[Double, Double]
          val xs = rawXs.map(xOffsetToOrigoInPixels + _)
          val ys = rawYs.map(yOffsetToOrigoInPixels + _)

          targetDrawingSurface.drawPolygon(
            xs, ys, points.length,
            pgon.hasBorder,
            pgon.hasFilling,
            pgon.color,
            pgon.fillColor)
        }
      }
      else {
        throw new IllegalStateException("Unknown image element")
      }
    }
  }

}
