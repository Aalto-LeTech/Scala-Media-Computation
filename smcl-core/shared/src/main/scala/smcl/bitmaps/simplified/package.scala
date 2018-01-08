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

package smcl.bitmaps


import scala.language.implicitConversions

import smcl.colors.rgb.Color
import smcl.settings._


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object simplified
    extends BitmapOperationAPI {

  /**
   * Creates a new empty [[Bitmap]] instance with a circle drawn on it.
   *
   * @param diameterInPixels diameter of the circle in pixels
   * @param color            color ot the circle
   * @param backgroundColor  color of the background
   *
   * @return a bitmap containing the circle
   */
  def circle(
      diameterInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    ShapeCreator.circle(diameterInPixels, color, backgroundColor)
  }

  /**
   * Creates an array of [[Bitmap]] instances with a circle drawn on each bitmap.
   *
   * @param collectionSize   number of elements to create into the collection
   * @param diameterInPixels diameter of the circle in pixels
   * @param color            color ot the circle
   * @param backgroundColor  color of the background
   *
   * @return an array of bitmaps, each of which contains the circle
   */
  def circleArray(
      collectionSize: Int = 5,
      diameterInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    ShapeCreator.circleArray(collectionSize, diameterInPixels, color, backgroundColor)
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a circle drawn on each bitmap.
   *
   * @param collectionSize   number of elements to create into the collection
   * @param diameterInPixels diameter of the circle in pixels
   * @param color            color ot the circle
   * @param backgroundColor  color of the background
   *
   * @return a sequence of bitmaps, each of which contains the circle
   */
  def circleSeq(
      collectionSize: Int = 5,
      diameterInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Seq[Bitmap] = {

    ShapeCreator.circleSeq(collectionSize, diameterInPixels, color, backgroundColor)
  }

  /**
   * Creates a list of [[Bitmap]] instances with a circle drawn on each bitmap.
   *
   * @param collectionSize   number of elements to create into the collection
   * @param diameterInPixels diameter of the circle in pixels
   * @param color            color ot the circle
   * @param backgroundColor  color of the background
   *
   * @return a list of bitmaps, each of which contains the circle
   */
  def circleList(
      collectionSize: Int = 5,
      diameterInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): List[Bitmap] = {

    ShapeCreator.circleList(collectionSize, diameterInPixels, color, backgroundColor)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with an ellipse drawn on it.
   *
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color ot the ellipse
   * @param backgroundColor color of the background
   *
   * @return a bitmap containing the ellipse
   */
  def ellipse(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    ShapeCreator.ellipse(widthInPixels, heightInPixels, color, backgroundColor)
  }

  /**
   * Creates an array of [[Bitmap]] instances with an ellipse drawn on each bitmap.
   *
   * @param collectionSize  number of elements to create into the collection
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color ot the ellipse
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

    ShapeCreator.ellipseArray(
      collectionSize,
      widthInPixels, heightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with an ellipse drawn on each bitmap.
   *
   * @param collectionSize  number of elements to create into the collection
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color ot the ellipse
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

    ShapeCreator.ellipseSeq(
      collectionSize,
      widthInPixels, heightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a list of [[Bitmap]] instances with an ellipse drawn on each bitmap.
   *
   * @param collectionSize  number of elements to create into the collection
   * @param widthInPixels   width of the ellipse in pixels
   * @param heightInPixels  height of the ellipse in pixels
   * @param color           color ot the ellipse
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

    ShapeCreator.ellipseList(
      collectionSize,
      widthInPixels, heightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a horizontal line drawn on it.
   *
   * @param lengthInPixels length of the line in pixels
   * @param color          color ot the square
   *
   * @return a bitmap containing the line
   */
  def hLine(
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Bitmap = {

    ShapeCreator.hLine(lengthInPixels, color)
  }

  /**
   * Creates an array of [[Bitmap]] instances with a horizontal line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color ot the line
   *
   * @return an array of bitmaps, each of which contains the line
   */
  def hLineArray(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Array[Bitmap] = {

    ShapeCreator.hLineArray(collectionSize, lengthInPixels, color)
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a horizontal line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color ot the line
   *
   * @return a sequence of bitmaps, each of which contains the line
   */
  def hLineSeq(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Seq[Bitmap] = {

    ShapeCreator.hLineSeq(collectionSize, lengthInPixels, color)
  }

  /**
   * Creates a list of [[Bitmap]] instances with a horizontal line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color ot the line
   *
   * @return a list of bitmaps, each of which contains the line
   */
  def hLineList(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): List[Bitmap] = {

    ShapeCreator.hLineList(collectionSize, lengthInPixels, color)
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
   * @param color           color ot the line
   * @param backgroundColor color of the background
   *
   * @return a bitmap containing the line
   */
  def line(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    ShapeCreator.line(
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels,
      color, backgroundColor)
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
   * @param color           color ot the line
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

    ShapeCreator.lineArray(
      collectionSize,
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels,
      color, backgroundColor)
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
   * @param color           color ot the line
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

    ShapeCreator.lineSeq(
      collectionSize,
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels,
      color, backgroundColor)
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
   * @param color           color ot the line
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

    ShapeCreator.lineList(
      collectionSize,
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a rectangle drawn on it.
   *
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color ot the rectangle
   *
   * @return a bitmap containing the rectangle
   */
  def rectangle(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): Bitmap = {

    ShapeCreator.rectangle(
      widthInPixels, heightInPixels, color)
  }

  /**
   * Creates an array of [[Bitmap]] instances with a rectangle drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color ot the rectangle
   *
   * @return an array of bitmaps, each of which contains the rectangle
   */
  def rectangleArray(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): Array[Bitmap] = {

    ShapeCreator.rectangleArray(
      collectionSize,
      widthInPixels, heightInPixels, color)
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a rectangle drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color ot the rectangle
   *
   * @return a sequence of bitmaps, each of which contains the rectangle
   */
  def rectangleSeq(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): Seq[Bitmap] = {

    ShapeCreator.rectangleSeq(
      collectionSize,
      widthInPixels, heightInPixels, color)
  }

  /**
   * Creates a list of [[Bitmap]] instances with a rectangle drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param widthInPixels  width of the rectangle in pixels
   * @param heightInPixels height of the rectangle in pixels
   * @param color          color ot the rectangle
   *
   * @return a list of bitmaps, each of which contains the rectangle
   */
  def rectangleList(
      collectionSize: Int = 5,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): List[Bitmap] = {

    ShapeCreator.rectangleList(
      collectionSize,
      widthInPixels, heightInPixels, color)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a rounded-corner rectangle drawn on it.
   *
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color ot the rectangle
   * @param backgroundColor        color of the background
   *
   * @return a bitmap containing the rectangle
   */
  def rRectangle(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    ShapeCreator.rRectangle(
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates an array of [[Bitmap]] instances with a rounded-corner rectangle drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color ot the rectangle
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

    ShapeCreator.rRectangleArray(
      collectionSize,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a rounded-corner rectangle drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color ot the rectangle
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

    ShapeCreator.rRectangleSeq(
      collectionSize,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a list of [[Bitmap]] instances with a rounded-corner rectangle drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param widthInPixels          width of the rectangle in pixels
   * @param heightInPixels         height of the rectangle in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the rectangle in pixels
   * @param roundingHeightInPixels height of the corner rounding of the rectangle in pixels
   * @param color                  color ot the rectangle
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

    ShapeCreator.rRectangleList(
      collectionSize,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a rounded-corner square drawn on it.
   *
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color ot the rectangle
   * @param backgroundColor        color of the background
   *
   * @return a bitmap containing the square
   */
  def rSquare(
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    ShapeCreator.rSquare(
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates an array of [[Bitmap]] instances with a rounded-corner square drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color ot the square
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

    ShapeCreator.rSquareArray(
      collectionSize,
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a rounded-corner square drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color ot the square
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

    ShapeCreator.rSquareSeq(
      collectionSize,
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a list of [[Bitmap]] instances with a rounded-corner square drawn on each bitmap.
   *
   * @param collectionSize         number of elements to create into the collection
   * @param sideLengthInPixels     side length of the square in pixels
   * @param roundingWidthInPixels  width of the corner rounding of the square in pixels
   * @param roundingHeightInPixels height of the corner rounding of the square in pixels
   * @param color                  color ot the square
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

    ShapeCreator.rSquareList(
      collectionSize,
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      color, backgroundColor)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a square drawn on it.
   *
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color ot the square
   *
   * @return a bitmap containing the square
   */
  def square(
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Bitmap = {

    ShapeCreator.square(sideLengthInPixels, color)
  }

  /**
   * Creates an array of [[Bitmap]] instances with a square drawn on each bitmap.
   *
   * @param collectionSize     number of elements to create into the collection
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color ot the square
   *
   * @return an array of bitmaps, each of which contains the square
   */
  def squareArray(
      collectionSize: Int = 5,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Array[Bitmap] = {

    ShapeCreator.squareArray(collectionSize, sideLengthInPixels, color)
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a square drawn on each bitmap.
   *
   * @param collectionSize     number of elements to create into the collection
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color ot the square
   *
   * @return a sequence of bitmaps, each of which contains the square
   */
  def squareSeq(
      collectionSize: Int = 5,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Seq[Bitmap] = {

    ShapeCreator.squareSeq(collectionSize, sideLengthInPixels, color)
  }

  /**
   * Creates a list of [[Bitmap]] instances with a square drawn on each bitmap.
   *
   * @param collectionSize     number of elements to create into the collection
   * @param sideLengthInPixels side length of the square in pixels
   * @param color              color ot the square
   *
   * @return a list of bitmaps, each of which contains the square
   */
  def squareList(
      collectionSize: Int = 5,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): List[Bitmap] = {

    ShapeCreator.squareList(collectionSize, sideLengthInPixels, color)
  }

  /**
   * Creates a new empty [[Bitmap]] instance with a vertical line drawn on it.
   *
   * @param lengthInPixels length of the line in pixels
   * @param color          color ot the line
   *
   * @return a bitmap containing the line
   */
  def vLine(
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Bitmap = {

    ShapeCreator.vLine(lengthInPixels, color)
  }

  /**
   * Creates an array of [[Bitmap]] instances with a vertical line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color ot the line
   *
   * @return an array of bitmaps, each of which contains the line
   */
  def vLineArray(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Array[Bitmap] = {

    ShapeCreator.vLineArray(collectionSize, lengthInPixels, color)
  }

  /**
   * Creates a sequence of [[Bitmap]] instances with a vertical line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color ot the line
   *
   * @return a sequence of bitmaps, each of which contains the line
   */
  def vLineSeq(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Seq[Bitmap] = {

    ShapeCreator.vLineSeq(collectionSize, lengthInPixels, color)
  }

  /**
   * Creates a list of [[Bitmap]] instances with a vertical line drawn on each bitmap.
   *
   * @param collectionSize number of elements to create into the collection
   * @param lengthInPixels length of the line in pixels
   * @param color          color ot the line
   *
   * @return a list of bitmaps, each of which contains the line
   */
  def vLineList(
      collectionSize: Int = 5,
      lengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): List[Bitmap] = {

    ShapeCreator.vLineList(collectionSize, lengthInPixels, color)
  }

  /**
   * Displays the given bitmap if, according to the corresponding
   * setting, new bitmaps should be displayed automatically.
   *
   * @param bitmap the bitmap to be displayed
   *
   * @return the bitmap to be displayed
   */
  private[simplified]
  def displayAsNewIfNecessary(bitmap: Bitmap): Bitmap = {
    if (NewBitmapsAreDisplayedAutomatically)
      bitmap.display()

    bitmap
  }

  /**
   * Application of the [[BitmapCreationStringInterpolator]] class.
   *
   * @param sc string context
   *
   * @return a new [[BitmapCreationStringInterpolator]] instance for the given [[StringContext]] instance
   */
  implicit def BitmapCreationStringContextWrapper(
      sc: StringContext): BitmapCreationStringInterpolator = {

    new BitmapCreationStringInterpolator(sc)
  }

}
