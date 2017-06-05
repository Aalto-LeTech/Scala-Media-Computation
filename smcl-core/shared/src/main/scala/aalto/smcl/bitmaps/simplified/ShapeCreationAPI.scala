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

package aalto.smcl.bitmaps.simplified

import aalto.smcl.colors.rgb.Color
import aalto.smcl.settings._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
trait ShapeCreationAPI {


  /** */
  private[this] lazy val _circleCreator: CircleCreator = new CircleCreator()

  /**
   *
   *
   * @param diameter
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def circle(
      diameter: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    _circleCreator.createOne(diameter, color, backgroundColor)
  }

  /**
   *
   *
   * @param collectionSize
   * @param diameter
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def circleArray(
      collectionSize: Int = 5,
      diameter: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Array[Bitmap] = {

    _circleCreator.createArrayOf(collectionSize, diameter, color, backgroundColor)
  }

  /**
   *
   *
   * @param collectionSize
   * @param diameter
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def circleSeq(
      collectionSize: Int = 5,
      diameter: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Seq[Bitmap] = {

    _circleCreator.createArrayOf(collectionSize, diameter, color, backgroundColor).toSeq
  }


  /** */
  private[this] lazy val _ellipseCreator: EllipseCreator = new EllipseCreator()

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def ellipse(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    _ellipseCreator.createOne(
      widthInPixels,
      heightInPixels,
      color,
      backgroundColor)
  }


  /** */
  private[this] lazy val _horizontalLineCreator: HorizontalLineCreator = new HorizontalLineCreator()

  /**
   *
   *
   * @param widthInPixels
   * @param color
   *
   * @return
   */
  def hLine(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Bitmap = {

    _horizontalLineCreator.createOne(widthInPixels, color)
  }


  /** */
  private[this] lazy val _lineCreator: LineCreator = new LineCreator()

  /**
   *
   *
   * @param fromXInPixels
   * @param fromYInPixels
   * @param toXInPixels
   * @param toYInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def line(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    _lineCreator.createOne(
      fromXInPixels,
      fromYInPixels,
      toXInPixels,
      toYInPixels,
      color,
      backgroundColor
    )
  }


  /** */
  private[this] lazy val _rectangleCreator: RectangleCreator = new RectangleCreator()

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param color
   *
   * @return
   */
  def rectangle(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): Bitmap = {

    _rectangleCreator.createOne(widthInPixels, heightInPixels, color)
  }


  /** */
  private[this] lazy val _roundedRectangleCreator: RoundedRectangleCreator = new RoundedRectangleCreator()

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def rRectangle(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    _roundedRectangleCreator.createOne(
      widthInPixels,
      heightInPixels,
      roundingWidthInPixels,
      roundingHeightInPixels,
      color,
      backgroundColor
    )
  }


  /** */
  private[this] lazy val _roundedSquareCreator: RoundedSquareCreator = new RoundedSquareCreator()

  /**
   *
   *
   * @param sideLengthInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def rSquare(
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      color: Color = DefaultPrimaryColor,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    _roundedSquareCreator.createOne(
      sideLengthInPixels,
      roundingWidthInPixels,
      roundingHeightInPixels,
      color,
      backgroundColor
    )
  }


  /** */
  private[this] lazy val _squareCreator: SquareCreator = new SquareCreator()

  /**
   *
   *
   * @param sideLengthInPixels
   * @param color
   *
   * @return
   */
  def square(
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      color: Color = DefaultPrimaryColor): Bitmap = {

    _squareCreator.createOne(sideLengthInPixels, color)
  }


  /** */
  private[this] lazy val _verticalLineCreator: VerticalLineCreator = new VerticalLineCreator()

  /**
   *
   *
   * @param heightInPixels
   * @param color
   *
   * @return
   */
  def vLine(
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: Color = DefaultPrimaryColor): Bitmap = {

    _verticalLineCreator.createOne(heightInPixels, color)
  }

}
