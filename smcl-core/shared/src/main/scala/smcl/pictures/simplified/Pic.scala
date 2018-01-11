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

package smcl.pictures.simplified


import scala.collection.{Iterable, Seq, Traversable}

import smcl.colors.rgb._
import smcl.infrastructure._
import smcl.modeling.d2.simplified.{Anchor, Bounds, HasAnchor, Pos}
import smcl.pictures.fullfeatured.Image




/**
 *
 *
 * @author Aleksi Lukkarinen
 * @author Juha Sorva
 */
object Pic {

  /**
   * Creates a new empty [[Pic]] instance.
   *
   * @return
   */
  @inline
  def apply(): Pic = {
    createAndInitialize(
      image = None,
      anchor = Anchor.Center,
      viewport = null)
  }

  /**
   * Creates a new empty [[Pic]] instance.
   *
   * @param anchor
   *
   * @return
   */
  @inline
  def apply(anchor: Anchor): Pic = {
    createAndInitialize(
      image = None,
      anchor = anchor,
      viewport = null)
  }

  /**
   * Creates a new empty [[Pic]] instance.
   *
   * @param anchor
   * @param viewport
   *
   * @return
   */
  @inline
  def apply(
      anchor: Anchor,
      viewport: Viewport): Pic = {

    createAndInitialize(
      image = None,
      anchor = anchor,
      viewport = viewport)
  }

  /**
   *
   *
   * @param imagePath
   * @param anchor
   *
   * @return
   */
  @inline
  def apply(
      imagePath: String,
      anchor: Anchor = Anchor.Center): Pic = {

    val loadedImage: Option[Image] = None

    val viewport =
      if (loadedImage.isDefined)
        Viewport(loadedImage.get.boundary.toSimplifiedBounds)
      else
        null

    createAndInitialize(
      image = loadedImage,
      anchor = anchor,
      viewport = viewport)
  }

  /**
   * Creates a new empty [[Pic]] instance.
   *
   * @param image
   * @param anchor
   * @param viewport
   *
   * @return
   */
  @inline
  private[pictures]
  def createAndInitialize(
      image: Option[Image],
      anchor: Anchor,
      viewport: Viewport): Pic = {

    val content = image.getOrElse[Image](Image())

    new Pic(
      content,
      anchor,
      viewport)
  }

  /**
   *
   *
   * @param pic
   * @param backgroundColor
   * @param borderWidth
   */
  @inline
  def show(
      pic: Pic,
      backgroundColor: Color, borderWidth: Int): Unit = {

    println("show... to be implemented")
  }

  /**
   *
   *
   * @param pic
   */
  @inline
  def hide(pic: Pic): Unit = {
    println("hide... to be implemented")
  }

  /**
   *
   *
   */
  @inline
  def hideAll(): Unit = {
    println("hideAll... to be implemented")
  }

  @inline
  private[smcl]
  def leftToRight(pics: Seq[Pic]): Pic = {
    //pics.reduceLeft((res, next) => Pic(res.leftOf(next), res.anchor))
    new Pic(Image(), Anchor.Center, null)
  } // XXX pwa ja voi laittaa alaviivalla

  @inline
  private[smcl]
  def topToBottom(pics: Seq[Pic]): Pic = {
    //pics.reduceLeft((res, next) => Pic(res.above(next), res.anchor))
    new Pic(Image(), Anchor.Center, null)
  } // XXX pwa ja voi laittaa alaviivalla

}




/**
 *
 *
 * @param content
 *
 * @author Aleksi Lukkarinen
 * @author Juha Sorva
 */
class Pic private[pictures](
    private[pictures] val content: Image,
    val anchor: Anchor,
    val viewport: Viewport)
    extends HasAnchor
        with HasViewport {

  /**
   *
   *
   * @return
   */
  @inline
  def height: Double =
    content.height.inPixels

  /**
   *
   *
   * @return
   */
  @inline
  def width: Double =
    content.width.inPixels

  /**
   *
   *
   * @return
   */
  @inline
  lazy val boundary: Bounds =
    content.boundary.toSimplifiedBounds

  /**
   *
   */
  @inline
  def setViewportFrom(picture: Pic): Pic = {
    setViewport(picture.viewport)
  }

  /**
   *
   */
  @inline
  //noinspection MutatorLikeMethodIsParameterless
  def setViewportToContentBoundary: Pic = {
    setViewportToContentBoundaryOf(this)
  }

  /**
   *
   *
   * @param picture
   *
   * @return
   */
  @inline
  def setViewportToContentBoundaryOf(picture: Pic): Pic = {
    setViewport(picture.content.boundary.toSimplifiedBounds)
  }

  /**
   *
   *
   * @param upperLeft
   * @param lowerRight
   *
   * @return
   */
  @inline
  def setViewport(
      upperLeft: Pos,
      lowerRight: Pos): Pic = {

    setViewport(
      upperLeft.x,
      upperLeft.y,
      lowerRight.x,
      lowerRight.y)
  }

  /**
   *
   *
   * @param left
   * @param top
   * @param right
   * @param bottom
   *
   * @return
   */
  @inline
  def setViewport(
      left: Double,
      top: Double,
      right: Double,
      bottom: Double): Pic = {

    val bounds =
      Bounds(
        left.floor.toInt,
        top.floor.toInt,
        (right - left + 1).floor.toInt,
        (bottom - top + 1).floor.toInt)

    setViewport(bounds)
  }

  /**
   *
   *
   * @param boundary
   *
   * @return
   */
  @inline
  def setViewport(boundary: Bounds): Pic = {
    setViewport(Viewport(boundary))
  }

  /**
   *
   *
   * @param viewport
   *
   * @return
   */
  @inline
  def setViewport(viewport: Viewport): Pic = {
    copy(newViewport = viewport)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def hasViewport: Boolean =
    viewport != null

  /**
   *
   *
   * @return
   */
  //noinspection MutatorLikeMethodIsParameterless
  @inline
  def removeViewport: Pic = {
    copy(newViewport = null)
  }

  /**
   *
   *
   * @param filePath
   */
  @inline
  def save(filePath: String): Unit = {
    println("save... to be implemented")
  }

  /**
   *
   *
   * @param filePath
   */
  @inline
  def saveViewport(filePath: String): Unit = {
    val (x0: Double, y0: Double, x1: Double, y1: Double) =
      if (hasViewport) {
        val b = viewport.boundary

        (b.left, b.top, b.right, b.bottom)
      }
      else {
        val bounds = content.boundary
        val ul = bounds.upperLeftMarker
        val lr = bounds.lowerRightMarker

        (ul.xInPixels, ul.yInPixels, lr.xInPixels, lr.yInPixels)
      }

    saveArea(x0, y0, x1, y1, filePath)
  }

  /**
   *
   *
   * @param boundary
   * @param filePath
   */
  @inline
  def saveArea(
      boundary: Bounds,
      filePath: String): Unit = {

    saveArea(
      boundary.left,
      boundary.top,
      boundary.right,
      boundary.bottom,
      filePath)
  }

  /**
   *
   *
   * @param upperLeft
   * @param lowerRight
   * @param filePath
   */
  @inline
  def saveArea(
      upperLeft: Pos,
      lowerRight: Pos,
      filePath: String): Unit = {

    saveArea(
      upperLeft.x,
      upperLeft.y,
      lowerRight.x,
      lowerRight.y,
      filePath)
  }

  /**
   *
   *
   * @param upperLeftX
   * @param upperLeftY
   * @param lowerRightX
   * @param lowerRightY
   * @param filePath
   */
  @inline
  def saveArea(
      upperLeftX: Double,
      upperLeftY: Double,
      lowerRightX: Double,
      lowerRightY: Double,
      filePath: String): Unit = {

    println("save... to be implemented")
  }

  /**
   *
   *
   * @param background
   * @param from
   *
   * @return
   */
  @inline
  def cropToSizeOf(
      background: Pic,
      from: Pos): Pic = {
    /*
    if (self.dimensions == background.dimensions)
      self
    else {
      val (cropX, cropY) = (from.x.floor.toInt, from.y.floor.toInt) // XXX pyöristys vai floor?
      val bgWithCroppedPicOnTop = self.crop(cropX, cropY, cropX + background.width - 1, cropY + background.height - 1)  // XXX -1 ei kiva
      PicWithAnchor(bgWithCroppedPicOnTop, background.anchor)    // XXX pwa
    }
    */
    this
  }

  /**
   *
   *
   * @param boundary
   *
   * @return
   */
  @inline
  def crop(boundary: Bounds): Pic = {
    crop(
      boundary.left,
      boundary.top,
      boundary.right,
      boundary.bottom)
  }

  /**
   *
   *
   * @param upperLeft
   * @param lowerRight
   *
   * @return
   */
  @inline
  def crop(
      upperLeft: Pos,
      lowerRight: Pos): Pic = {

    crop(
      upperLeft.x,
      upperLeft.y,
      lowerRight.x,
      lowerRight.y)
  }

  /**
   *
   *
   * @param topLeft
   * @param width
   * @param height
   *
   * @return
   */
  @inline
  def crop(
      topLeft: Pos,
      width: Double,
      height: Double): Pic = {

    crop(
      topLeft.x,
      topLeft.y,
      topLeft.x + width - 1,
      topLeft.y + height - 1)
  }

  /**
   *
   *
   * @param upperLeftX
   * @param upperLeftY
   * @param lowerRightX
   * @param lowerRightY
   *
   * @return
   */
  @inline
  def crop(
      upperLeftX: Double,
      upperLeftY: Double,
      lowerRightX: Double,
      lowerRightY: Double): Pic = {

    println("crop... to be implemented")
    //Pic(self.crop(topLeft.xInt, topLeft.yInt, (topLeft.x + width - 1).round.toInt, (topLeft.y + height - 1).round.toInt), Center) // XXX pwa // XXX anchor
    this
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def shiftLeft(offset: Double): Pic = {
    val leftBit = crop(Pos(0, 0), offset, height)
    val rightBit = crop(Pos(offset, 0), width - offset, height)
    leftBit.rightOf(rightBit)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def shiftRight(offset: Double): Pic = {
    this
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def shiftUpwards(offset: Double): Pic = {
    this
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def shiftDownwards(offset: Double): Pic = {
    this
  }

  /**
   *
   *
   * @param step
   *
   * @return
   */
  @inline
  def slidingHorizontally(step: Double): Stream[Pic] = {
    //noinspection RedundantDefaultArgument
    slidingHorizontally(
      step,
      windowSize = width.floor.toInt,
      wrap = true)
  }

  /**
   *
   *
   * @param step
   *
   * @return
   */
  @inline
  def slidingHorizontally(
      step: Double,
      windowSize: Int,
      wrap: Boolean = true): Stream[Pic] = {

    val slide =
      if (step >= 0)
        new Slide.Right(step, windowSize)
      else
        new Slide.Left(step, windowSize)

    if (wrap)
      slide.forever
    else
      slide.once
  }

  /**
   *
   *
   * @param step
   *
   * @return
   */
  @inline
  def slidingVertically(step: Double): Stream[Pic] = {
    //noinspection RedundantDefaultArgument
    slidingVertically(
      step,
      windowSize = height.floor.toInt,
      wrap = true)
  }

  /**
   *
   *
   * @param step
   *
   * @return
   */
  @inline
  def slidingVertically(
      step: Double,
      windowSize: Int,
      wrap: Boolean = true): Stream[Pic] = {

    val slide =
      if (step >= 0)
        new Slide.Down(step, windowSize)
      else
        new Slide.Up(step, windowSize)

    if (wrap)
      slide.forever
    else
      slide.once
  }


  //def toImage = self.toRenderedRepresentation.awtBufferedImage

  /*
  def toIcon: javax.swing.Icon = {
    import java.awt.{Component, Graphics}
    import javax.swing.Icon
    new Icon {

      def paintIcon(target: Component, graphics: Graphics, x: Int, y: Int) = {
        graphics.drawImage(self.toImage, x, y, target)
      }

      def getIconWidth = self.width

      def getIconHeight = self.height
    }
  }
  */

  /**
   *
   *
   * @param anchor
   *
   * @return
   */
  @inline
  def anchorAt(anchor: Anchor): Pic = {
    copy(newAnchor = anchor)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def absoluteAnchor: Anchor = {
    anchor.toAbsoluteWithin(this)
  }

  /**
   *
   * @param factor
   *
   * @return
   */
  @inline
  def scaleBy(factor: Double): Pic = {
    this // PicWithAnchor(self.pic.scale(factor), self.anchor) // XXX pwa
  }

  /**
   *
   * @param targetSize
   *
   * @return
   */
  @inline
  def scaleTo(targetSize: Double): Pic = {
    scaleTo(targetSize, targetSize)
  }

  /**
   *
   * @param targetWidth
   * @param targetHeight
   *
   * @return
   */
  @inline
  def scaleTo(
      targetWidth: Double,
      targetHeight: Double): Pic = {

    //val scaled = self.pic.scale(targetWidth / self.width, targetHeight / self.height, resizeCanvasBasedOnTransformation = true) // XXX should use: java.awt.Image.SCALE_AREA_AVERAGING ??
    //Pic(scaled, self.anchor) // XXX pwa
    this
  }

  /**
   *
   * @param background
   * @param border
   */
  @inline
  def show(
      background: Color = White,
      border: Int = 1): Unit = {

    //Pic.show(self, background, border)
  }

  /**
   *
   */
  @inline
  def hide(): Unit = {
    //Pic.hide(self)
  }

  /**
   *
   *
   * @param backPic
   * @param my
   * @param at
   *
   * @return
   */
  @inline
  def onto(
      backPic: Pic,
      my: Anchor,
      at: Pos): Pic = {

    val myTopLeftRelativeToBG =
      at - my.internalPosWithin(this)

    /*
    Pic(self.overlayOn(backPic, myTopLeftRelativeToBG.xInt, myTopLeftRelativeToBG.yInt), backPic.anchor) // XXX pwa
    */
    this
  }

  /**
   *
   *
   * @param backPic
   * @param my
   * @param atIts
   *
   * @return
   */
  @inline
  def onto(
      backPic: Pic,
      my: Anchor,
      atIts: Anchor): Pic = {

    onto(
      backPic,
      my,
      atIts.internalPosWithin(backPic))
  }

  /**
   *
   *
   * @param backPic
   * @param at
   *
   * @return
   */
  @inline
  def onto(
      backPic: Pic,
      at: Pos): Pic = {

    onto(backPic, anchor, at)
  }

  /**
   *
   *
   * @param backPic
   * @param atIts
   *
   * @return
   */
  @inline
  def onto(
      backPic: Pic,
      atIts: Anchor = Anchor.Center): Pic = {

    onto(backPic, anchor, atIts)
  }


  /**
   *
   *
   * @param background
   * @param my
   * @param at
   *
   * @return
   */
  // XXX document here and elsewhere: at (etc.) are local coordinates within the background
  @inline
  def against(
      background: Pic,
      my: Anchor,
      at: Pos): Pic = {
    // XXX wasteful, and creates potentially ridiculously-sized Pics as an intermediate result

    /*
    val uncroppedCombination = self.onto(background, my, at)
    val myLeftRelativeToBG = at.x - my.internalXWithin(self)
    val myTopRelativeToBG = at.y - my.internalYWithin(self)
    val bgXWithinCombo = if (myLeftRelativeToBG < 0) -myLeftRelativeToBG else 0
    val bgYWithinCombo = if (myTopRelativeToBG < 0) -myTopRelativeToBG else 0
    uncroppedCombination.cropToSizeOf(background, Pos(bgXWithinCombo, bgYWithinCombo))
    */
    this
  }

  /**
   *
   *
   * @param background
   * @param my
   * @param atIts
   *
   * @return
   */
  @inline
  def against(
      background: Pic,
      my: Anchor,
      atIts: Anchor): Pic = {

    against(
      background,
      my,
      atIts.internalPosWithin(background))
  }

  /**
   *
   *
   * @param background
   * @param at
   *
   * @return
   */
  @inline
  def against(
      background: Pic,
      at: Pos): Pic = {

    against(background, anchor, at)
  }

  /**
   *
   *
   * @param background
   * @param atIts
   *
   * @return
   */
  @inline
  def against(
      background: Pic,
      atIts: Anchor = Anchor.Center): Pic = {

    against(background, anchor, atIts)
  }

  /**
   *
   *
   * @param foreground
   * @param its
   * @param at
   *
   * @return
   */
  @inline
  def place(
      foreground: Pic,
      its: Anchor,
      at: Pos): Pic = {

    val foregroundAnchor = its

    foreground.against(this, foregroundAnchor, at)
  }

  /**
   *
   *
   * @param foreground
   * @param its
   * @param atMy
   *
   * @return
   */
  @inline
  def place(
      foreground: Pic,
      its: Anchor,
      atMy: Anchor): Pic = {

    foreground.against(
      this,
      its,
      atMy.internalPosWithin(this))
  }

  /**
   *
   *
   * @param foreground
   * @param at
   *
   * @return
   */
  @inline
  def place(
      foreground: Pic,
      at: Pos): Pic = {

    place(
      foreground,
      foreground.anchor,
      at)
  }

  /**
   *
   *
   * @param foreground
   * @param atMy
   *
   * @return
   */
  @inline
  def place(
      foreground: Pic,
      atMy: Anchor): Pic = {

    place(
      foreground,
      foreground.anchor,
      atMy)
  }

  /**
   *
   *
   * @param foregroundPicAndPos
   *
   * @return
   */
  @inline
  def place(
      foregroundPicAndPos: (Pic, Pos)): Pic = {

    place(
      foregroundPicAndPos._1,
      foregroundPicAndPos._2)
  }

  /**
   *
   *
   * @param foregroundPics
   *
   * @return
   */
  @inline
  def place(
      foregroundPics: Traversable[(Pic, Pos)]): Pic = {

    foregroundPics.foldLeft(this)(_.place(_))
  }

  /**
   *
   *
   * @param foregroundPics
   *
   * @return
   */
  @inline
  def place(foregroundPics: (Pic, Pos)*): Pic = {
    place(foregroundPics)
  }

  /**
   *
   *
   * @param foregroundPic
   * @param at
   *
   * @return
   */
  @inline
  def placeCopies(
      foregroundPic: Pic,
      at: Iterable[Pos]): Pic = {

    place(at.map(_ => foregroundPic) zip at)
  }


  // XXX allow retaining of original anchor (pohjustava osin toimiva esimerkki alla) and customization of anchoring

  /**
   *
   *
   * @param rightPic
   * @param retainAnchor
   *
   * @return
   */
  @inline
  def leftOf(
      rightPic: Pic,
      retainAnchor: Boolean = false): Pic = {

    //Pic(self.appendOnRight(rightPic)(Vertical.Middle, paddingInPixels = 0, backgroundColor = Transparent), self.anchor) // XXX pwa
    this
  }

  /**
   *
   *
   * @param leftPic
   * @param retainAnchor
   *
   * @return
   */
  @inline
  def rightOf(
      leftPic: Pic,
      retainAnchor: Boolean = false): Pic = {

    //Pic(self.appendOnLeft(leftPic)(Vertical.Middle, paddingInPixels = 0, backgroundColor = Transparent), self.anchor) // XXX pwa
    this
  }

  /**
   *
   *
   * @param abovePic
   * @param retainAnchor
   *
   * @return
   */
  @inline
  def below(
      abovePic: Pic,
      retainAnchor: Boolean = false): Pic = {

    //Pic(self.appendOnTop(abovePic)(Horizontal.Center, paddingInPixels = 0, backgroundColor = Transparent), self.anchor) // XXX pwa
    this
  }

  /**
   *
   *
   * @param lowerPic
   * @param retainAnchor
   *
   * @return
   */
  @inline
  def above(
      lowerPic: Pic,
      retainAnchor: Boolean = false): Pic = {

    /*
    val smclPic = self.appendOnBottom(lowerPic)(Horizontal.Center, paddingInPixels = 0, backgroundColor = Transparent)
    if (retainAnchor)
      Pic(smclPic, self.absoluteAnchor)
    else
      Pic(smclPic, self.anchor) // XXX pwa
    */
    this
  }

  /**
   *
   *
   * @param number
   *
   * @return
   */
  @inline
  def rowOf(number: Int): Pic = {
    Pic.leftToRight(Seq.fill(number)(this))
  }

  /**
   *
   *
   * @param number
   *
   * @return
   */
  @inline
  def columnOf(number: Int): Pic = {
    Pic.topToBottom(Seq.fill(number)(this))
  }

  /**
   *
   *
   * @param another
   * @param number
   *
   * @return
   */
  @inline
  def alternatingRow(
      another: Pic,
      number: Int): Pic = {

    val pics = Seq.tabulate(number){index =>
      if (index.isEven)
        this
      else
        another
    }

    Pic.leftToRight(pics)
  }

  /**
   *
   *
   * @param another
   * @param number
   *
   * @return
   */
  @inline
  def alternatingColumn(
      another: Pic,
      number: Int): Pic = {

    val pics = Seq.tabulate(number){index =>
      if (index.isEven)
        this
      else
        another
    }

    Pic.topToBottom(pics)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def flipVertical: Pic = {
    this // Pic(self.flipVertically(), self.anchor) // XXX pwa // XXX improper anchor
  }

  /**
   *
   *
   * @return
   */
  @inline
  def flipHorizontal: Pic = {
    this // Pic(self.flipHorizontally(), self.anchor)  // XXX pwa // XXX improper anchor
  }

  /**
   *
   *
   * @param degrees
   * @param background
   *
   * @return
   */
  @inline
  def rotate(
      degrees: Double,
      background: Color = Transparent): Pic = {

    clockwise(degrees, background)
  }

  /**
   *
   *
   * @param degrees
   * @param background
   *
   * @return
   */
  // XXX pwa // XXX should rotare around pin
  @inline
  def clockwise(
      degrees: Double = 90.0,
      background: Color = Transparent): Pic = {

    /*
    if (degrees == 90.0) Pic(self.rotate90DegsCw(true, background), self.anchor)
    else Pic(self.rotateDegs(-degrees, true, background), self.anchor)
    */
    this
  }

  /**
   *
   *
   * @param degrees
   * @param background
   *
   * @return
   */
  // XXX pwa // XXX should rotare around pin
  @inline
  def counterclockwise(
      degrees: Double = 90.0,
      background: Color = Transparent): Pic = {

    /*
    if (degrees == 90.0) Pic(self.rotate90DegsCcw(true, background), self.anchor)
    else Pic(self.rotateDegs(degrees, true, background), self.anchor)
    */
    this
  }

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def isWiderThan(another: Pic): Boolean = {
    this.width > another.width
  }

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def isTallerThan(another: Pic): Boolean = {
    this.height > another.height
  }

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def fitsOnto(another: Pic): Boolean = {
    !this.isWiderThan(another) && !this.isTallerThan(another)
  }

  /**
   *
   *
   * @param newAnchor
   *
   * @return
   */
  def copy(
      newAnchor: Anchor = anchor,
      newViewport: Viewport = viewport): Pic = {

    if (newAnchor != anchor || newViewport != viewport) {
      return new Pic(content, newAnchor, newViewport)
    }

    this
  }




  /**
   *
   */
  private object Slide {

    // XXX optional cache näille
    // XXX toteutus meni vähän turhan abstraktiokikkailuksi; voisi selkiyttää?

    /**
     *
     *
     * @param step
     * @param windowSize
     * @param attachExtension
     * @param farEdge
     * @param sliceWidth
     * @param sliceHeight
     * @param slicePosition
     * @param applyOnceTo
     */
    abstract class Slide(
        val step: Double,
        val windowSize: Int,
        val attachExtension: Pic => Pic,
        val farEdge: Int,
        val sliceWidth: Int,
        val sliceHeight: Int,
        val slicePosition: Int => (Int, Int),
        val applyOnceTo: Pic => Stream[Pic]) {

      /** */
      protected
      lazy val highestOffset: Int = farEdge - windowSize

      /** */
      protected
      def firstOffset: Int

      /** */
      protected
      def isPastLastOffset(offset: Int): Boolean

      /** */
      protected
      def extraForWraparound: (Int, Int, Int, Int)

      /** */
      private
      lazy val extendedForWraparound = {
        val (x, y, w, h) = extraForWraparound
        //val extension = Pic(content.crop(x, y, x + w - 1, y + h - 1), anchor) // XXX pwa // XXX -1 ei kiva

        //attachExtension(extension)

        Pic()
      }

      /**
       *
       *
       * @return
       */
      @inline
      final def once: Stream[Pic] = {

        @inline
        def sliceStarts(from: Double): Stream[Int] = {
          if (isPastLastOffset(from.closestInt))
            Stream.empty
          else
            from.closestInt #:: sliceStarts(from + step)
        }

        @inline
        def createSliceAt(start: Int): Pic = {
          val (x, y) = slicePosition(start) // XXX myöhemmin Pos

          //Pic(content.crop(x, y, x + this.sliceWidth - 1, y + this.sliceHeight - 1), anchor) // XXX -1 ei kiva // XXX pwa
          Pic()
        }

        sliceStarts(from = firstOffset).map(createSliceAt)
      }

      /**
       *
       *
       * @return
       */
      @inline
      final def forever: Stream[Pic] = {
        val onceAcross = applyOnceTo(extendedForWraparound)

        def wrappingStream: Stream[Pic] = onceAcross #::: wrappingStream

        wrappingStream
      }
    }




    /**
     *
     *
     * @param step
     * @param windowSize
     * @param attachExtension
     */
    abstract class Horizontal(
        step: Double,
        windowSize: Int,
        attachExtension: Pic => Pic)
        extends Slide(
          step,
          windowSize,
          attachExtension,
          farEdge = width.floor.toInt,
          sliceWidth = windowSize,
          sliceHeight = height.floor.toInt,
          slicePosition = (_, 0),
          applyOnceTo = _.slidingHorizontally(step, windowSize, wrap = false))




    /**
     *
     *
     * @param step
     * @param windowSize
     * @param attachExtension
     */
    abstract class Vertical(
        step: Double,
        windowSize: Int,
        attachExtension: Pic => Pic)
        extends Slide(
          step,
          windowSize,
          attachExtension,
          farEdge = height.floor.toInt,
          sliceWidth = width.floor.toInt,
          sliceHeight = windowSize,
          slicePosition = (0, _),
          applyOnceTo = _.slidingVertically(step, windowSize, wrap = false))




    /**
     *
     */
    trait Forward extends Slide {

      assert(this.step >= 0)

      /** */
      protected
      lazy val firstOffset = 0

      /**
       *
       *
       * @param offset
       *
       * @return
       */
      @inline
      protected
      def isPastLastOffset(offset: Int): Boolean = offset > highestOffset
    }




    /**
     *
     */
    trait Backward extends Slide {

      assert(this.step < 0)

      /** */
      protected
      lazy val firstOffset: Int = this.highestOffset

      /**
       *
       *
       * @param offset
       *
       * @return
       */
      @inline
      protected
      def isPastLastOffset(offset: Int): Boolean = offset < 0
    }




    /**
     *
     *
     * @param step
     * @param windowSize
     */
    class Right(
        step: Double,
        windowSize: Int)
        extends Horizontal(
          step,
          windowSize,
          leftOf(_))
            with Forward {

      /**
       *
       *
       * @return
       */
      @inline
      protected
      def extraForWraparound: (Int, Int, Int, Int) = {
        (0,
            0,
            sliceWidth - step.closestInt,
            sliceHeight)
      }
    }




    /**
     *
     *
     * @param step
     * @param windowSize
     */
    class Down(
        step: Double,
        windowSize: Int)
        extends Vertical(
          step,
          windowSize,
          above(_))
            with Forward {

      /**
       *
       *
       * @return
       */
      @inline
      protected
      def extraForWraparound: (Int, Int, Int, Int) = {
        (0,
            0,
            sliceWidth,
            sliceHeight - step.closestInt)
      }
    }




    /**
     *
     *
     * @param step
     * @param windowSize
     */
    class Up(
        step: Double,
        windowSize: Int)
        extends Vertical(
          step,
          windowSize,
          below(_))
            with Backward {

      /**
       *
       *
       * @return
       */
      @inline
      protected
      def extraForWraparound: (Int, Int, Int, Int) = {
        (0,
            highestOffset,
            sliceWidth,
            windowSize + step.closestInt)
      }
    }




    /**
     *
     *
     * @param step
     * @param windowSize
     */
    class Left(
        step: Double,
        windowSize: Int)
        extends Horizontal(
          step,
          windowSize,
          rightOf(_))
            with Backward {

      /**
       *
       *
       * @return
       */
      @inline
      protected
      def extraForWraparound: (Int, Int, Int, Int) = {
        (highestOffset,
            0,
            windowSize + step.closestInt,
            sliceHeight)
      }
    }




  }




}
