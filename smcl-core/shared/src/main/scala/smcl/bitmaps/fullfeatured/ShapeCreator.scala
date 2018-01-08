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

package smcl.bitmaps.fullfeatured


import smcl.bitmaps.operations.{DrawCircle, DrawEllipse, DrawLine, DrawRoundedRectangle, DrawRoundedSquare}
import smcl.colors.rgb.Color
import smcl.infrastructure.{CollectionCreator, InjectablesRegistry}
import smcl.settings._




/**
 * Functionality for creating single shapes and shape collections.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object ShapeCreator
    extends InjectablesRegistry {

  /** The [[smcl.infrastructure.CollectionCreator]] instance to be used by this object. */
  private lazy val collectionCreator: CollectionCreator = {
    injectable(InjectablesRegistry.IIdCollectionCreator)
        .asInstanceOf[CollectionCreator]
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a circle drawn on it.
   *
   * @param diameterInPixels diameter of the circle in pixels
   * @param color            color of the circle
   * @param backgroundColor  color of the background
   * @param viewerHandling   whether the bitmap should be displayed per settings or not displayed at all
   *
   * @return a bitmap containing the circle
   */
  def circle(
      diameterInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    validateCircleParameters(diameterInPixels, color, backgroundColor)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateCircle(diameterInPixels, color, backgroundColor))
  }

  /**
   * Creates an array of [[Bitmap]] instances with a circle drawn on each bitmap.
   *
   * @param collectionSize   number of elements to create into the collection
   * @param diameterInPixels diameter of the circle in pixels
   * @param color            color of the circle
   * @param backgroundColor  color of the background
   *
   * @return an array of bitmaps, each of which contains the circle
   */
  def circleArray(
      collectionSize: Int = 5,
      diameterInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    validateCircleParameters(diameterInPixels, color, backgroundColor)

    collectionCreator.array[Bitmap](collectionSize){
      instantiateCircle(diameterInPixels, color, backgroundColor)
    }
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a circle drawn on each bitmap.
   *
   * @param collectionSize   number of elements to create into the collection
   * @param diameterInPixels diameter of the circle in pixels
   * @param color            color of the circle
   * @param backgroundColor  color of the background
   *
   * @return a sequence of bitmaps, each of which contains the circle
   */
  def circleSeq(
      collectionSize: Int = 5,
      diameterInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Seq[Bitmap] = {

    validateCircleParameters(diameterInPixels, color, backgroundColor)

    collectionCreator.sequence[Bitmap](collectionSize){
      instantiateCircle(diameterInPixels, color, backgroundColor)
    }
  }

  /**
   * Creates a list of [[Bitmap]] instances with a circle drawn on each bitmap.
   *
   * @param collectionSize   number of elements to create into the collection
   * @param diameterInPixels diameter of the circle in pixels
   * @param color            color of the circle
   * @param backgroundColor  color of the background
   *
   * @return a list of bitmaps, each of which contains the circle
   */
  def circleList(
      collectionSize: Int = 5,
      diameterInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): List[Bitmap] = {

    validateCircleParameters(diameterInPixels, color, backgroundColor)

    collectionCreator.list[Bitmap](collectionSize){
      instantiateCircle(diameterInPixels, color, backgroundColor)
    }
  }

  /**
   * Validates parameters for a circle.
   *
   * @param diameterInPixels diameter of the circle
   * @param color            color of the circle
   * @param backgroundColor  color of the background
   */
  private
  def validateCircleParameters(
      diameterInPixels: Int,
      color: Color,
      backgroundColor: Color): Unit = {

    require(
      diameterInPixels > 0,
      s"Diameter of the circle must be at least 1 pixel (was $diameterInPixels)")

    validateColors(color, backgroundColor)
  }

  /**
   * Instantiates a circle.
   *
   * @param diameterInPixels diameter of the circle
   * @param color            color of the circle
   * @param backgroundColor  color of the background
   *
   * @return a bitmap containing the circle
   */
  private
  def instantiateCircle(
      diameterInPixels: Int,
      color: Color,
      backgroundColor: Color): Bitmap = {

    val imageSide =
      if (diameterInPixels % 2 == 0)
        diameterInPixels + 1
      else
        diameterInPixels

    def postCreationProcessor(bmp: Bitmap): Bitmap = {
      val radius = (imageSide - 2) / 2

      bmp.applyInitialization(
        DrawCircle(
          centerXInPixels = radius + 1,
          centerYInPixels = radius + 1,
          radiusInPixels = radius,
          hasBorder = true,
          hasFilling = true,
          color = color,
          fillColor = color
        ))
    }

    Bitmap(
      imageSide,
      imageSide,
      backgroundColor,
      Some(postCreationProcessor _))
  }

  /**
   * Creates a new empty [[Bitmap]] instance with an ellipse drawn on it.
   *
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color of the ellipse
   * @param backgroundColor color of the background
   * @param viewerHandling  whether the bitmap should be displayed per settings or not displayed at all
   *
   * @return a bitmap containing the ellipse
   */
  def ellipse(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    validateEllipseParameters(widthInPixels, heightInPixels, color, backgroundColor)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateEllipse(
        widthInPixels, heightInPixels,
        color, backgroundColor))
  }

  /**
   * Creates an array of [[Bitmap]] instances with an ellipse drawn on each bitmap.
   *
   * @param collectionSize  number of elements to create into the collection
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color of the ellipse
   * @param backgroundColor color of the background
   *
   * @return an array of bitmaps, each of which contains the ellipse
   */
  def ellipseArray(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    validateEllipseParameters(widthInPixels, heightInPixels, color, backgroundColor)

    collectionCreator.array[Bitmap](collectionSize){
      instantiateEllipse(
        widthInPixels, heightInPixels,
        color, backgroundColor)
    }
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with an ellipse drawn on each bitmap.
   *
   * @param collectionSize  number of elements to create into the collection
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color of the ellipse
   * @param backgroundColor color of the background
   *
   * @return a sequence of bitmaps, each of which contains the ellipse
   */
  def ellipseSeq(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Seq[Bitmap] = {

    validateEllipseParameters(widthInPixels, heightInPixels, color, backgroundColor)

    collectionCreator.sequence[Bitmap](collectionSize){
      instantiateEllipse(
        widthInPixels, heightInPixels,
        color, backgroundColor)
    }
  }

  /**
   * Creates a list of [[Bitmap]] instances with an ellipse drawn on each bitmap.
   *
   * @param collectionSize  number of elements to create into the collection
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color of the ellipse
   * @param backgroundColor color of the background
   *
   * @return a list of bitmaps, each of which contains the ellipse
   */
  def ellipseList(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): List[Bitmap] = {

    validateEllipseParameters(widthInPixels, heightInPixels, color, backgroundColor)

    collectionCreator.list[Bitmap](collectionSize){
      instantiateEllipse(
        widthInPixels, heightInPixels,
        color, backgroundColor)
    }
  }

  /**
   * Validates parameters for an ellipse.
   *
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color of the ellipse
   * @param backgroundColor color of the background
   */
  private
  def validateEllipseParameters(
      widthInPixels: Int,
      heightInPixels: Int,
      color: Color,
      backgroundColor: Color): Unit = {

    require(
      widthInPixels > 0,
      s"Width of the ellipse must be at least 1 pixel (was $widthInPixels)")

    require(
      heightInPixels > 0,
      s"Height of the ellipse must be at least 1 pixel (was $heightInPixels)")

    validateColors(color, backgroundColor)
  }

  /**
   * Instantiates an ellipse.
   *
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color of the ellipse
   * @param backgroundColor color of the background
   *
   * @return a bitmap containing the ellipse
   */
  private
  def instantiateEllipse(
      widthInPixels: Int,
      heightInPixels: Int,
      color: Color,
      backgroundColor: Color): Bitmap = {

    val bitmapWidth = if (widthInPixels % 2 == 0) widthInPixels + 1 else widthInPixels
    val bitmapHeight = if (heightInPixels % 2 == 0) heightInPixels + 1 else heightInPixels

    def postCreationProcessor(bmp: Bitmap): Bitmap = {
      val ellipseWidth = bitmapWidth - 3
      val ellipseHeight = bitmapHeight - 3
      val ellipseCenterX = (ellipseWidth / 2) + 1
      val ellipseCenterY = (ellipseHeight / 2) + 1

      bmp.applyInitialization(
        DrawEllipse(
          centerXInPixels = ellipseCenterX,
          centerYInPixels = ellipseCenterY,
          widthInPixels = ellipseWidth,
          heightInPixels = ellipseHeight,
          hasBorder = true,
          hasFilling = true,
          color = color,
          fillColor = color
        ))
    }

    Bitmap(
      bitmapWidth,
      bitmapHeight,
      backgroundColor,
      Some(postCreationProcessor _))
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a horizontal line drawn on it.
   *
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the square
   * @param viewerHandling whether the bitmap should be displayed per settings or not displayed at all
   *
   * @return a bitmap containing the line
   */
  def hLine(
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    validateHorizontalLineParameters(lengthInPixels, color)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateHorizontalLine(
        lengthInPixels, color))
  }

  /**
   * Creates an array of [[Bitmap]] instances with a horizontal line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   *
   * @return an array of bitmaps, each of which contains the line
   */
  def hLineArray(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Array[Bitmap] = {

    validateHorizontalLineParameters(lengthInPixels, color)

    collectionCreator.array[Bitmap](collectionSize){
      instantiateHorizontalLine(
        lengthInPixels, color)
    }
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a horizontal line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   *
   * @return a sequence of bitmaps, each of which contains the line
   */
  def hLineSeq(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Seq[Bitmap] = {

    validateHorizontalLineParameters(lengthInPixels, color)

    collectionCreator.sequence[Bitmap](collectionSize){
      instantiateHorizontalLine(
        lengthInPixels, color)
    }
  }

  /**
   * Creates a list of [[Bitmap]] instances with a horizontal line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   *
   * @return a list of bitmaps, each of which contains the line
   */
  def hLineList(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): List[Bitmap] = {

    validateHorizontalLineParameters(lengthInPixels, color)

    collectionCreator.list[Bitmap](collectionSize){
      instantiateHorizontalLine(
        lengthInPixels, color)
    }
  }

  /**
   * Validates parameters for a horizontal line.
   *
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   */
  private
  def validateHorizontalLineParameters(lengthInPixels: Int, color: Color): Unit = {
    require(
      lengthInPixels > 0,
      s"Length of the line must be at least 1 pixel (was $lengthInPixels)")

    validateShapeColor(color)
  }

  /**
   * Instantiates a horizontal line.
   *
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   *
   * @return a bitmap containing the line
   */
  private
  def instantiateHorizontalLine(
      lengthInPixels: Int,
      color: Color): Bitmap = {

    Bitmap(lengthInPixels, 1, color)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a line drawn on it. The line can be freely located in
   * the Cartesian coordinate system, but the bitmap will always contain the minimum amount of empty space.
   * That is, the line will always be drawn from one corner of the bitmap to the opposite corner.
   *
   * @param fromXInPixels   the X coordinate of the starting point of the line
   * @param fromYInPixels   the Y coordinate of the starting point of the line
   * @param toXInPixels     the X coordinate of the ending point of the line
   * @param toYInPixels     the Y coordinate of the ending point of the line
   * @param color           color of the line
   * @param backgroundColor color of the background
   * @param viewerHandling  whether the bitmap should be displayed per settings or not displayed at all
   *
   * @return a bitmap containing the line
   */
  def line(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    val (bitmapWidth, bitmapHeight, isAscending) =
      calculateAndValidateLineParameters(
        fromXInPixels, fromYInPixels,
        toXInPixels, toYInPixels,
        color, backgroundColor)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateLine(
        bitmapWidth, bitmapHeight, isAscending,
        color, backgroundColor))
  }

  /**
   * Creates an array of [[Bitmap]] instances with a line drawn on each bitmap. The lines can be
   * freely located in the Cartesian coordinate system, but the bitmap will always contain the
   * minimum amount of empty space. That is, the line will always be drawn from one corner of
   * the bitmap to the opposite corner.
   *
   * @param collectionSize  number of elements to create into the collection
   * @param fromXInPixels   the X coordinate of the starting point of the line
   * @param fromYInPixels   the Y coordinate of the starting point of the line
   * @param toXInPixels     the X coordinate of the ending point of the line
   * @param toYInPixels     the Y coordinate of the ending point of the line
   * @param color           color of the line
   * @param backgroundColor color of the background
   *
   * @return an array of bitmaps, each of which contains the line
   */
  def lineArray(
      collectionSize: Int = 5,
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    val (bitmapWidth, bitmapHeight, isAscending) =
      calculateAndValidateLineParameters(
        fromXInPixels, fromYInPixels,
        toXInPixels, toYInPixels,
        color, backgroundColor)

    collectionCreator.array[Bitmap](collectionSize){
      instantiateLine(
        bitmapWidth, bitmapHeight, isAscending,
        color, backgroundColor)
    }
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a line drawn on each bitmap. The lines can be
   * freely located in the Cartesian coordinate system, but the bitmap will always contain the
   * minimum amount of empty space. That is, the line will always be drawn from one corner of
   * the bitmap to the opposite corner.
   *
   * @param collectionSize  number of elements to create into the collection
   * @param fromXInPixels   the X coordinate of the starting point of the line
   * @param fromYInPixels   the Y coordinate of the starting point of the line
   * @param toXInPixels     the X coordinate of the ending point of the line
   * @param toYInPixels     the Y coordinate of the ending point of the line
   * @param color           color of the line
   * @param backgroundColor color of the background
   *
   * @return a sequence of bitmaps, each of which contains the line
   */
  def lineSeq(
      collectionSize: Int = 5,
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Seq[Bitmap] = {

    val (bitmapWidth, bitmapHeight, isAscending) =
      calculateAndValidateLineParameters(
        fromXInPixels, fromYInPixels,
        toXInPixels, toYInPixels,
        color, backgroundColor)

    collectionCreator.sequence[Bitmap](collectionSize){
      instantiateLine(
        bitmapWidth, bitmapHeight, isAscending,
        color, backgroundColor)
    }
  }

  /**
   * Creates a list of [[Bitmap]] instances with a line drawn on each bitmap. The lines can be
   * freely located in the Cartesian coordinate system, but the bitmap will always contain the
   * minimum amount of empty space. That is, the line will always be drawn from one corner of
   * the bitmap to the opposite corner.
   *
   * @param collectionSize  number of elements to create into the collection
   * @param fromXInPixels   the X coordinate of the starting point of the line
   * @param fromYInPixels   the Y coordinate of the starting point of the line
   * @param toXInPixels     the X coordinate of the ending point of the line
   * @param toYInPixels     the Y coordinate of the ending point of the line
   * @param color           color of the line
   * @param backgroundColor color of the background
   *
   * @return a list of bitmaps, each of which contains the line
   */
  def lineList(
      collectionSize: Int = 5,
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): List[Bitmap] = {

    val (bitmapWidth, bitmapHeight, isAscending) =
      calculateAndValidateLineParameters(
        fromXInPixels, fromYInPixels,
        toXInPixels, toYInPixels,
        color, backgroundColor)

    collectionCreator.list[Bitmap](collectionSize){
      instantiateLine(
        bitmapWidth, bitmapHeight, isAscending,
        color, backgroundColor)
    }
  }

  /**
   * Calculates and validates parameters for an arbitrary straight line.
   *
   * @param fromXInPixels   the X coordinate of the starting point of the line
   * @param fromYInPixels   the Y coordinate of the starting point of the line
   * @param toXInPixels     the X coordinate of the ending point of the line
   * @param toYInPixels     the Y coordinate of the ending point of the line
   * @param color           color of the line
   * @param backgroundColor color of the background
   */
  private
  def calculateAndValidateLineParameters(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color,
      backgroundColor: Color): (Int, Int, Boolean) = {

    val differenceX = toXInPixels - fromXInPixels
    val differenceY = toYInPixels - fromYInPixels
    val bitmapWidth = Math.abs(differenceX)
    val bitmapHeight = Math.abs(differenceY)
    val slopeSign = Math.signum(differenceY.toDouble / differenceX)
    val isAscending = slopeSign > 0

    validateLineParameters(bitmapWidth, bitmapHeight, color, backgroundColor)

    (bitmapWidth, bitmapHeight, isAscending)
  }

  /**
   * Validates parameters for an arbitrary straight line.
   *
   * @param bitmapWidth     width of the bitmap containing the line
   * @param bitmapHeight    height of the bitmap containing the line
   * @param color           color of the line
   * @param backgroundColor color of the background
   */
  private
  def validateLineParameters(
      bitmapWidth: Int,
      bitmapHeight: Int,
      color: Color,
      backgroundColor: Color): Unit = {

    require(
      bitmapWidth > 0,
      s"Difference of the x coordinates must be at least 1 pixel (was $bitmapWidth)")

    require(
      bitmapHeight > 0,
      s"Difference of the y coordinates must be at least 1 pixel (was $bitmapHeight)")

    validateColors(color, backgroundColor)
  }

  /**
   * Instantiates an arbitrary straight line.
   *
   * @param bitmapWidth     width of the bitmap containing the line
   * @param bitmapHeight    height of the bitmap containing the line
   * @param isAscending     <code>true</code> if the line is ascending; otherwise <code>false</code>
   * @param color           color of the line
   * @param backgroundColor color of the background
   *
   * @return a bitmap containing the line
   */
  private
  def instantiateLine(
      bitmapWidth: Int,
      bitmapHeight: Int,
      isAscending: Boolean,
      color: Color,
      backgroundColor: Color): Bitmap = {

    def postCreationProcessor(bmp: Bitmap): Bitmap = {
      val (x0, y0, x1, y1) =
        if (isAscending)
          (0, 0, bitmapWidth - 1, bitmapHeight - 1)
        else
          (0, bitmapHeight - 1, bitmapWidth - 1, 0)

      bmp.applyInitialization(DrawLine(x0, y0, x1, y1, color))
    }

    Bitmap(
      bitmapWidth,
      bitmapHeight,
      backgroundColor,
      Some(postCreationProcessor _))
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a rectangle drawn on it.
   *
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color of the rectangle
   * @param viewerHandling whether the bitmap should be displayed per settings or not displayed at all
   *
   * @return a bitmap containing the rectangle
   */
  def rectangle(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    validateRectangleParameters(widthInPixels, heightInPixels, color)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateRectangle(
        widthInPixels, heightInPixels,
        color))
  }

  /**
   * Creates an array of [[Bitmap]] instances with a rectangle drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color of the rectangle
   *
   * @return an array of bitmaps, each of which contains the rectangle
   */
  def rectangleArray(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): Array[Bitmap] = {

    validateRectangleParameters(widthInPixels, heightInPixels, color)

    collectionCreator.array[Bitmap](collectionSize){
      instantiateRectangle(
        widthInPixels, heightInPixels,
        color)
    }
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a rectangle drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color of the rectangle
   *
   * @return a sequence of bitmaps, each of which contains the rectangle
   */
  def rectangleSeq(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): Seq[Bitmap] = {

    validateRectangleParameters(widthInPixels, heightInPixels, color)

    collectionCreator.sequence[Bitmap](collectionSize){
      instantiateRectangle(
        widthInPixels, heightInPixels,
        color)
    }
  }

  /**
   * Creates a list of [[Bitmap]] instances with a rectangle drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color of the rectangle
   *
   * @return a list of bitmaps, each of which contains the rectangle
   */
  def rectangleList(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): List[Bitmap] = {

    validateRectangleParameters(widthInPixels, heightInPixels, color)

    collectionCreator.list[Bitmap](collectionSize){
      instantiateRectangle(
        widthInPixels, heightInPixels,
        color)
    }
  }

  /**
   * Validates parameters for a rectangle.
   *
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color of the rectangle
   */
  private
  def validateRectangleParameters(
      widthInPixels: Int,
      heightInPixels: Int,
      color: Color): Unit = {

    require(
      widthInPixels > 0,
      s"Width of the rectangle must be at least 1 pixel (was $widthInPixels)")

    require(
      heightInPixels > 0,
      s"Height of the rectangle must be at least 1 pixel (was $heightInPixels)")

    validateShapeColor(color)
  }

  /**
   * Instantiates a rectangle.
   *
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color of the rectangle
   *
   * @return a bitmap containing the rectangle
   */
  private
  def instantiateRectangle(
      widthInPixels: Int,
      heightInPixels: Int,
      color: Color): Bitmap = {

    Bitmap(widthInPixels, heightInPixels, color)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a rounded-corner rectangle drawn on it.
   *
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color of the rectangle
   * @param backgroundColor        color of the background
   * @param viewerHandling         whether the bitmap should be displayed per settings or not displayed at all
   *
   * @return a bitmap containing the rectangle
   */
  def rRectangle(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    validateRoundedRectangleParameters(
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateRoundedRectangle(
        widthInPixels, heightInPixels,
        roundingWidthInPixels, roundingHeightInPixels,
        color, backgroundColor))
  }

  /**
   * Creates an array of [[Bitmap]] instances with a rounded-corner rectangle drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color of the rectangle
   * @param backgroundColor        color of the background
   *
   * @return an array of bitmaps, each of which contains the rectangle
   */
  def rRectangleArray(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    validateRoundedRectangleParameters(
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)

    collectionCreator.array[Bitmap](collectionSize){
      instantiateRoundedRectangle(
        widthInPixels, heightInPixels,
        roundingWidthInPixels, roundingHeightInPixels,
        color, backgroundColor)
    }
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a rounded-corner rectangle drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color of the rectangle
   * @param backgroundColor        color of the background
   *
   * @return a sequence of bitmaps, each of which contains the rectangle
   */
  def rRectangleSeq(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Seq[Bitmap] = {

    validateRoundedRectangleParameters(
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)

    collectionCreator.sequence[Bitmap](collectionSize){
      instantiateRoundedRectangle(
        widthInPixels, heightInPixels,
        roundingWidthInPixels, roundingHeightInPixels,
        color, backgroundColor)
    }
  }

  /**
   * Creates a list of [[Bitmap]] instances with a rounded-corner rectangle drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color of the rectangle
   * @param backgroundColor        color of the background
   *
   * @return a list of bitmaps, each of which contains the rectangle
   */
  def rRectangleList(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): List[Bitmap] = {

    validateRoundedRectangleParameters(
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)

    collectionCreator.list[Bitmap](collectionSize){
      instantiateRoundedRectangle(
        widthInPixels, heightInPixels,
        roundingWidthInPixels, roundingHeightInPixels,
        color, backgroundColor)
    }
  }

  /**
   * Validates parameters for a rounded-corner rectangle.
   *
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color of the rectangle
   * @param backgroundColor        color of the background
   */
  private
  def validateRoundedRectangleParameters(
      widthInPixels: Int,
      heightInPixels: Int,
      roundingWidthInPixels: Int,
      roundingHeightInPixels: Int,
      color: Color,
      backgroundColor: Color): Unit = {

    require(
      widthInPixels >= 5,
      s"Width of the rectangle must be at least 5 pixels (was $widthInPixels)")

    require(
      heightInPixels >= 5,
      s"Height of the rectangle must be at least 5 pixels (was $heightInPixels)")

    require(
      roundingWidthInPixels > 0,
      s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")

    require(
      roundingHeightInPixels > 0,
      s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")

    validateColors(color, backgroundColor)
  }

  /**
   * Instantiates a rounded-corner rectangle.
   *
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color of the rectangle
   * @param backgroundColor        color of the background
   *
   * @return a bitmap containing the rectangle
   */
  private
  def instantiateRoundedRectangle(
      widthInPixels: Int,
      heightInPixels: Int,
      roundingWidthInPixels: Int,
      roundingHeightInPixels: Int,
      color: Color,
      backgroundColor: Color): Bitmap = {

    def postCreationProcessor(bmp: Bitmap): Bitmap = {
      bmp.applyInitialization(
        DrawRoundedRectangle(
          0, 0,
          widthInPixels - 1, heightInPixels - 1,
          roundingWidthInPixels, roundingHeightInPixels,
          hasBorder = true,
          hasFilling = true,
          color = color,
          fillColor = color
        ))
    }

    Bitmap(
      widthInPixels,
      heightInPixels,
      backgroundColor,
      Some(postCreationProcessor _))
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a rounded-corner square drawn on it.
   *
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color of the rectangle
   * @param backgroundColor        color of the background
   * @param viewerHandling         whether the bitmap should be displayed per settings or not displayed at all
   *
   * @return a bitmap containing the square
   */
  def rSquare(
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    validateRoundedSquareParameters(
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateRoundedSquare(
        sideLengthInPixels,
        roundingWidthInPixels, roundingHeightInPixels,
        color, backgroundColor))
  }

  /**
   * Creates an array of [[Bitmap]] instances with a rounded-corner square drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color of the square
   * @param backgroundColor        color of the background
   *
   * @return an array of bitmaps, each of which contains the square
   */
  def rSquareArray(
      collectionSize: Int = 5,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    validateRoundedSquareParameters(
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)

    collectionCreator.array[Bitmap](collectionSize){
      instantiateRoundedSquare(
        sideLengthInPixels,
        roundingWidthInPixels, roundingHeightInPixels,
        color, backgroundColor)
    }
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a rounded-corner square drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color of the square
   * @param backgroundColor        color of the background
   *
   * @return a sequence of bitmaps, each of which contains the square
   */
  def rSquareSeq(
      collectionSize: Int = 5,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Seq[Bitmap] = {

    validateRoundedSquareParameters(
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)

    collectionCreator.sequence[Bitmap](collectionSize){
      instantiateRoundedSquare(
        sideLengthInPixels,
        roundingWidthInPixels, roundingHeightInPixels,
        color, backgroundColor)
    }
  }

  /**
   * Creates a list of [[Bitmap]] instances with a rounded-corner square drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color of the square
   * @param backgroundColor        color of the background
   *
   * @return a list of bitmaps, each of which contains the square
   */
  def rSquareList(
      collectionSize: Int = 5,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): List[Bitmap] = {

    validateRoundedSquareParameters(
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)

    collectionCreator.list[Bitmap](collectionSize){
      instantiateRoundedSquare(
        sideLengthInPixels,
        roundingWidthInPixels, roundingHeightInPixels,
        color, backgroundColor)
    }
  }

  /**
   * Validates parameters for a rounded-corner square.
   *
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color of the square
   * @param backgroundColor        color of the background
   */
  private
  def validateRoundedSquareParameters(
      sideLengthInPixels: Int,
      roundingWidthInPixels: Int,
      roundingHeightInPixels: Int,
      color: Color,
      backgroundColor: Color): Unit = {

    require(
      sideLengthInPixels >= 5,
      s"A side of the square must be at least 5 pixels (was $sideLengthInPixels)")

    require(
      roundingWidthInPixels > 0,
      s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")

    require(
      roundingHeightInPixels > 0,
      s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")

    validateColors(color, backgroundColor)
  }

  /**
   * Instantiates a rounded-corner square.
   *
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color of the square
   * @param backgroundColor        color of the background
   *
   * @return a bitmap containing the square
   */
  private
  def instantiateRoundedSquare(
      sideLengthInPixels: Int,
      roundingWidthInPixels: Int,
      roundingHeightInPixels: Int,
      color: Color,
      backgroundColor: Color): Bitmap = {

    def postCreationProcessor(bmp: Bitmap): Bitmap = {
      bmp.applyInitialization(
        DrawRoundedSquare(
          0, 0,
          sideLengthInPixels - 1,
          roundingWidthInPixels, roundingHeightInPixels,
          hasBorder = true,
          hasFilling = true,
          color = color,
          fillColor = color
        ))
    }

    Bitmap(
      sideLengthInPixels,
      sideLengthInPixels,
      backgroundColor,
      Some(postCreationProcessor _))
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a square drawn on it.
   *
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color of the square
   * @param viewerHandling     whether the bitmap should be displayed per settings or not displayed at all
   *
   * @return a bitmap containing the square
   */
  def square(
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    validateSquareParameters(sideLengthInPixels, color)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateSquare(sideLengthInPixels, color))
  }

  /**
   * Creates an array of [[Bitmap]] instances with a square drawn on each bitmap.
   *
   * @param collectionSize     number of elements to create into the collection
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color of the square
   *
   * @return an array of bitmaps, each of which contains the square
   */
  def squareArray(
      collectionSize: Int = 5,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Array[Bitmap] = {

    validateSquareParameters(sideLengthInPixels, color)

    collectionCreator.array[Bitmap](collectionSize){
      instantiateSquare(sideLengthInPixels, color)
    }
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a square drawn on each bitmap.
   *
   * @param collectionSize     number of elements to create into the collection
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color of the square
   *
   * @return a sequence of bitmaps, each of which contains the square
   */
  def squareSeq(
      collectionSize: Int = 5,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Seq[Bitmap] = {

    validateSquareParameters(sideLengthInPixels, color)

    collectionCreator.sequence[Bitmap](collectionSize){
      instantiateSquare(sideLengthInPixels, color)
    }
  }

  /**
   * Creates a list of [[Bitmap]] instances with a square drawn on each bitmap.
   *
   * @param collectionSize     number of elements to create into the collection
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color of the square
   *
   * @return a list of bitmaps, each of which contains the square
   */
  def squareList(
      collectionSize: Int = 5,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): List[Bitmap] = {

    validateSquareParameters(sideLengthInPixels, color)

    collectionCreator.list[Bitmap](collectionSize){
      instantiateSquare(sideLengthInPixels, color)
    }
  }

  /**
   * Validates parameters for a square.
   *
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color of the square
   */
  private
  def validateSquareParameters(
      sideLengthInPixels: Int,
      color: Color): Unit = {

    require(
      sideLengthInPixels > 0,
      s"Width of the square must be at least 1 pixel (was $sideLengthInPixels)")

    validateShapeColor(color)
  }

  /**
   * Instantiates a square.
   *
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color of the square
   *
   * @return a bitmap containing the square
   */
  private
  def instantiateSquare(
      sideLengthInPixels: Int,
      color: Color): Bitmap = {

    Bitmap(sideLengthInPixels, sideLengthInPixels, color)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a vertical line drawn on it.
   *
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   * @param viewerHandling whether the bitmap should be displayed per settings or not displayed at all
   *
   * @return a bitmap containing the line
   */
  def vLine(
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    validateVerticalLineParameters(lengthInPixels, color)

    displayAsNewIfNecessary(viewerHandling)(
      instantiateVerticalLine(lengthInPixels, color))
  }

  /**
   * Creates an array of [[Bitmap]] instances with a vertical line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   *
   * @return an array of bitmaps, each of which contains the line
   */
  def vLineArray(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Array[Bitmap] = {

    validateVerticalLineParameters(lengthInPixels, color)

    collectionCreator.array[Bitmap](collectionSize){
      instantiateVerticalLine(lengthInPixels, color)
    }
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a vertical line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   *
   * @return a sequence of bitmaps, each of which contains the line
   */
  def vLineSeq(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Seq[Bitmap] = {

    validateVerticalLineParameters(lengthInPixels, color)

    collectionCreator.sequence[Bitmap](collectionSize){
      instantiateVerticalLine(lengthInPixels, color)
    }
  }

  /**
   * Creates a list of [[Bitmap]] instances with a vertical line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   *
   * @return a list of bitmaps, each of which contains the line
   */
  def vLineList(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): List[Bitmap] = {

    validateVerticalLineParameters(lengthInPixels, color)

    collectionCreator.list[Bitmap](collectionSize){
      instantiateVerticalLine(lengthInPixels, color)
    }
  }

  /**
   * Validates parameters for a vertical line.
   *
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   */
  private
  def validateVerticalLineParameters(lengthInPixels: Int, color: Color): Unit = {
    require(
      lengthInPixels > 0,
      s"Length of the line must be at least 1 pixel (was $lengthInPixels)")

    validateShapeColor(color)
  }

  /**
   * Instantiates a vertical line.
   *
   * @param lengthInPixels length of the line in pixels
   * @param color          color of the line
   *
   * @return a bitmap containing the line
   */
  private
  def instantiateVerticalLine(
      lengthInPixels: Int,
      color: Color): Bitmap = {

    Bitmap(1, lengthInPixels, color)
  }

  /**
   * Validates foreground and background colors.
   *
   * @param color           color of the shape
   * @param backgroundColor color of the background
   */
  private
  def validateColors(color: Color, backgroundColor: Color): Unit = {
    validateShapeColor(color)
    validateBackgroundColor(color)
  }

  /**
   * Validates a foreground color.
   *
   * @param color color of the shape
   */
  private
  def validateShapeColor(color: Color): Unit = {
    require(color != null, "The shape color argument has to be a Color instance (was null).")
  }

  /**
   * Validates a background color.
   *
   * @param color color of the background
   */
  private
  def validateBackgroundColor(color: Color): Unit = {
    require(color != null, "The background color argument has to be a Color instance (was null).")
  }

}
