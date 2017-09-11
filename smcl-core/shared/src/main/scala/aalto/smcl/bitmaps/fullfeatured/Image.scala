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

package aalto.smcl.bitmaps.fullfeatured


import scala.annotation.tailrec

import aalto.smcl.infrastructure.{DrawingSurfaceAdapter, Identity}
import aalto.smcl.modeling.Len
import aalto.smcl.modeling.d2.{Bounds, HasBounds, HasPos, Pos}




/**
 *
 */
object Image {

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  def apply(elements: ImageElement*): Image = {
    val identity: Identity = Identity()

    new Image(identity, elements)
  }

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  def calculateOuterBoundary(elements: Seq[HasBounds]): Option[Bounds] = {
    type RecursRetVal = Option[(Double, Double, Double, Double)]

    @tailrec
    def calculateOuterBoundaryRecursion(
        it: Iterator[HasBounds], foundOneBoundary: Boolean,
        x0: Double, y0: Double, x1: Double, y1: Double): RecursRetVal = {

      if (!it.hasNext) {
        if (foundOneBoundary)
          return Some((x0, y0, x1, y1))

        return None
      }

      val elementWithBoundary = it.next()

      if (elementWithBoundary.boundary.isEmpty) {
        calculateOuterBoundaryRecursion(
          it, foundOneBoundary, x0, y0, x1, y1)
      }
      else {
        val boundary = elementWithBoundary.boundary.get

        val ul = boundary.upperLeftMarker
        val x0New = math.min(ul.xInPixels, x0)
        val y0New = math.min(ul.yInPixels, y0)

        val lr = boundary.lowerRightMarker
        val x1New = math.max(lr.xInPixels, x1)
        val y1New = math.max(lr.yInPixels, y1)

        calculateOuterBoundaryRecursion(
          it, foundOneBoundary = true, x0New, y0New, x1New, y1New)
      }
    }

    val resolvedBoundaryValues =
      if (elements.isEmpty)
        None
      else
        calculateOuterBoundaryRecursion(
          elements.iterator, foundOneBoundary = false,
          Double.MaxValue, Double.MaxValue,
          Double.MinValue, Double.MinValue)

    resolvedBoundaryValues map[Bounds] {newBounds =>
      Bounds(newBounds._1, newBounds._2, newBounds._3, newBounds._4)
    }
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Image(
    override val identity: Identity,
    val elements: Seq[ImageElement])
    extends ImageElement
            with HasPos
            with HasBounds {

  // TODO: Tarkistukset

  /** */
  val boundary: Option[Bounds] =
    Image.calculateOuterBoundary(elements)

  /** */
  val position: Pos = boundary.get.upperLeftMarker

  /** */
  val width: Len = boundary.get.width

  /** */
  val height: Len = boundary.get.height

  /** */
  val isRenderable: Boolean = width > 0 && height > 0

  /**
   *
   *
   * @param drawingSurface
   */
  override def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      position: Pos): Unit = {

  }

  /**
   *
   *
   * @return
   */
  override def toBitmap: Bmp = {
    val bounds = boundary.get
    val bitmap = Bmp(bounds.width, bounds.height)

    bitmap
  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  def rotateBy(angleInDegrees: Double, centerOfRotation: Pos): ImageElement = {
    this
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  def moveBy(offsets: Double*): ImageElement = {
    this
  }

}
