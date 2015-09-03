package aalto.smcl.bitmaps


import aalto.smcl.GS
import aalto.smcl.bitmaps.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.colors.RGBAColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] trait ShapeCreationAPI {

  /** */
  private[this] lazy val _circleCreator: CircleCreator = new CircleCreator()

  /**
   *
   *
   * @param diameter
   * @param color
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def circle(
      diameter: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    _circleCreator.createOne(diameter, color, backgroundColor, viewerHandling)
  }

  /**
   *
   *
   * @param collectionSize
   * @param diameter
   * @param color
   * @param backgroundColor
   * @return
   */
  def circleArray(
      collectionSize: Int = 5,
      diameter: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground)): Array[Bitmap] = {

    _circleCreator.createArrayOf(collectionSize, diameter, color, backgroundColor)
  }

  /**
   *
   *
   * @param collectionSize
   * @param diameter
   * @param color
   * @param backgroundColor
   * @return
   */
  def circleSeq(
      collectionSize: Int = 5,
      diameter: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground)): Seq[Bitmap] = {

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
   * @param viewerHandling
   * @return
   */
  def ellipse(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    _ellipseCreator.createOne(
      widthInPixels,
      heightInPixels,
      color,
      backgroundColor,
      viewerHandling)
  }


  /** */
  private[this] lazy val _horizontalLineCreator: HorizontalLineCreator = new HorizontalLineCreator()

  /**
   *
   *
   * @param widthInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def hLine(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    _horizontalLineCreator.createOne(widthInPixels, color, viewerHandling)
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
   * @param viewerHandling
   * @return
   */
  def line(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    _lineCreator.createOne(
      fromXInPixels,
      fromYInPixels,
      toXInPixels,
      toYInPixels,
      color,
      backgroundColor,
      viewerHandling
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
   * @param viewerHandling
   * @return
   */
  def rectangle(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    _rectangleCreator.createOne(widthInPixels, heightInPixels, color, viewerHandling)
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
   * @param viewerHandling
   * @return
   */
  def rRectangle(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
      roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    _roundedRectangleCreator.createOne(
      widthInPixels,
      heightInPixels,
      roundingWidthInPixels,
      roundingHeightInPixels,
      color,
      backgroundColor,
      viewerHandling
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
   * @param viewerHandling
   * @return
   */
  def rSquare(
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
      roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    _roundedSquareCreator.createOne(
      sideLengthInPixels,
      roundingWidthInPixels,
      roundingHeightInPixels,
      color,
      backgroundColor,
      viewerHandling
    )
  }


  /** */
  private[this] lazy val _squareCreator: SquareCreator = new SquareCreator()

  /**
   *
   *
   * @param sideLengthInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def square(
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    _squareCreator.createOne(sideLengthInPixels, color, viewerHandling)
  }


  /** */
  private[this] lazy val _verticalLineCreator: VerticalLineCreator = new VerticalLineCreator()

  /**
   *
   *
   * @param heightInPixels
   * @param color
   * @param viewerHandling
   * @return
   */
  def vLine(
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    _verticalLineCreator.createOne(heightInPixels, color, viewerHandling)
  }

}
