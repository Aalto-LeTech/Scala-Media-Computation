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

    // println(s"${bounds.width.inPixels} x ${bounds.height.inPixels}  ---> $flooredWidth x $flooredHeight")

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

      if (contentItem.isRenderable) {
        if (contentItem.isPicture) {
          renderingQueue ++= contentItem.toPicture.elements.reverse
        }
        else {
          renderElement(
            contentItem,
            targetDrawingSurface,
            xOffsetToOrigoInPixels,
            yOffsetToOrigoInPixels)
        }
      }
    }
  }

  private
  def renderElement(
      contentItem: PictureElement,
      targetDrawingSurface: DrawingSurfaceAdapter,
      xOffsetToOrigoInPixels: Double,
      yOffsetToOrigoInPixels: Double): Unit = {

    if (contentItem.isArc) {
      val arc = contentItem.asInstanceOf[Arc]

      targetDrawingSurface.drawArc(
        xOffsetToOrigoInPixels, yOffsetToOrigoInPixels,
        arc.position.xInPixels, arc.position.yInPixels,
        arc.untransformedWidthInPixels, arc.untransformedHeightInPixels,
        arc.startAngleInDegrees, arc.arcAngleInDegrees,
        arc.rotationAngleInDegrees,
        arc.horizontalScalingFactor, arc.verticalScalingFactor,
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

      targetDrawingSurface.drawPoint(
        xOffsetToOrigoInPixels, yOffsetToOrigoInPixels,
        pnt.position.xInPixels, pnt.position.yInPixels,
        pnt.color)
    }
    else if (contentItem.isPolygon) {
      val pgon = contentItem.asInstanceOf[Polygon]
      if (pgon.pointsRelativeToPosition.isEmpty)
        return

      val position = pgon.position
      val points = pgon.pointsRelativeToPosition

      val (xs, ys) = points.unzip[Double, Double]

      targetDrawingSurface.drawPolygon(
        xOffsetToOrigoInPixels, yOffsetToOrigoInPixels,
        position.xInPixels, position.yInPixels,
        xs, ys, points.length,
        pgon.hasBorder,
        pgon.hasFilling,
        pgon.color,
        pgon.fillColor)
    }
    else {
      throw new IllegalStateException("Unknown image element")
    }
  }

}
