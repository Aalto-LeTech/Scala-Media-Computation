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

package aalto.smcl


import scala.language.implicitConversions

import aalto.smcl.colors.rgb.Color
import aalto.smcl.settings._


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object bitmaps
    extends simplified.BitmapOperationAPI {

  /** A type alias for default bitmap class. */
  type Bitmap = simplified.Bitmap

  /** A companion object alias for default bitmap. */
  val Bitmap = simplified.Bitmap

  /** A type alias for . */
  type Bmp = fullfeatured.Bmp

  /** A companion object alias for . */
  val Bmp = fullfeatured.Bmp

  /** A type alias for . */
  type Image = fullfeatured.Image

  /** A companion object alias for . */
  val Image = fullfeatured.Image

  /** A type alias for . */
  type Point = fullfeatured.Point

  /** A companion object alias for . */
  val Point = fullfeatured.Point

  /** A type alias for . */
  type Line = fullfeatured.Line

  /** A companion object alias for . */
  val Line = fullfeatured.Line

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

    simplified.circle(diameterInPixels, color, backgroundColor)
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

    simplified.circleArray(collectionSize, diameterInPixels, color, backgroundColor)
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

    simplified.circleSeq(collectionSize, diameterInPixels, color, backgroundColor)
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

    simplified.circleList(collectionSize, diameterInPixels, color, backgroundColor)
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

    simplified.ellipse(widthInPixels, heightInPixels, color, backgroundColor)
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

    simplified.ellipseArray(
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

    simplified.ellipseSeq(
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

    simplified.ellipseList(
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

    simplified.hLine(lengthInPixels, color)
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

    simplified.hLineArray(collectionSize, lengthInPixels, color)
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

    simplified.hLineSeq(collectionSize, lengthInPixels, color)
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

    simplified.hLineList(collectionSize, lengthInPixels, color)
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

    simplified.line(
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

    simplified.lineArray(
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

    simplified.lineSeq(
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

    simplified.lineList(
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

    simplified.rectangle(
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

    simplified.rectangleArray(
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

    simplified.rectangleSeq(
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

    simplified.rectangleList(
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

    simplified.rRectangle(
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

    simplified.rRectangleArray(
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

    simplified.rRectangleSeq(
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

    simplified.rRectangleList(
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

    simplified.rSquare(
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

    simplified.rSquareArray(
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

    simplified.rSquareSeq(
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

    simplified.rSquareList(
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

    simplified.square(sideLengthInPixels, color)
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

    simplified.squareArray(collectionSize, sideLengthInPixels, color)
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

    simplified.squareSeq(collectionSize, sideLengthInPixels, color)
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

    simplified.squareList(collectionSize, sideLengthInPixels, color)
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

    simplified.vLine(lengthInPixels, color)
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

    simplified.vLineArray(collectionSize, lengthInPixels, color)
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

    simplified.vLineSeq(collectionSize, lengthInPixels, color)
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

    simplified.vLineList(collectionSize, lengthInPixels, color)
  }

  /**
   * Application of the [[simplified.BitmapCreationStringInterpolator]] class.
   *
   * @param sc string context
   *
   * @return a new [[simplified.BitmapCreationStringInterpolator]]
   *         instance for the given [[StringContext]] instance
   */
  implicit def BitmapCreationStringContextWrapper(
      sc: StringContext): simplified.BitmapCreationStringInterpolator = {

    simplified.BitmapCreationStringContextWrapper(sc)
  }

}
