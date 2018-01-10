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
import smcl.modeling.d2.{HasPos, Pos}
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
  def apply(): Pic = {
    createAndInitialize(image = None)
  }

  /**
   *
   *
   * @param imagePath
   * @param anchor
   *
   * @return
   */
  def apply(imagePath: String, anchor: Anchor = Anchor.Center): Pic = {
    createAndInitialize(image = None)
  }

  /**
   * Creates a new empty [[Pic]] instance.
   *
   * @param image
   *
   * @return
   */
  private[pictures]
  def createAndInitialize(image: Option[Image]): Pic = {
    val content = image.getOrElse[Image](Image())

    new Pic(content)
  }

  private[smcl] def leftToRight(pics: Seq[Pic]) = {
    //pics.reduceLeft((res, next) => Pic(res.leftOf(next), res.anchor))
    this
  } // XXX pwa ja voi laittaa alaviivalla}

  private[smcl] def topToBottom(pics: Seq[Pic]) = {
    //pics.reduceLeft((res, next) => Pic(res.above(next), res.anchor))
    this
  } // XXX pwa ja voi laittaa alaviivalla}

  def show(pic: Pic, backgroundColor: Color, borderWidth: Int): Unit = {}

  def hide(pic: Pic): Unit = {}

  def hideAll(): Unit = {}

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
    private[pictures] val content: Image)
    extends HasPos {

  /**
   *
   *
   * @return
   */
  @inline
  def height: Double = content.height.inPixels

  /**
   *
   *
   * @return
   */
  @inline
  def width: Double = content.width.inPixels

  /** Position of this object. */
  override
  def position: Pos = content.position

  //@inline
  def anchor: Anchor = Anchor.Center

  //@inline
  //def dimensions = content.

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

  //def anchorAt(anchor: Anchor) = this.self.copy(anchor = anchor)

  def scaleBy(factor: Double): Pic = this // PicWithAnchor(self.pic.scale(factor), self.anchor) // XXX pwa

  // def absoluteAnchor = this.anchor.toAbsoluteWithin(self)

  def scaleTo(targetWidth: Double, targetHeight: Double): Pic = {
    //val scaled = self.pic.scale(targetWidth / self.width, targetHeight / self.height, resizeCanvasBasedOnTransformation = true) // XXX should use: java.awt.Image.SCALE_AREA_AVERAGING ??
    //Pic(scaled, self.anchor) // XXX pwa
    this
  }

  def scaleTo(targetSize: Double): Pic = this.scaleTo(targetSize, targetSize)

  def show(background: Color = White, border: Int = 1): Unit = {
    //Pic.show(self, background, border)
  }

  def hide(): Unit = {
    //Pic.hide(self)
  }

  def cropToSizeOf(background: Pic, from: Pos): Pic = {
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

  def onto(backPic: Pic, my: Anchor, at: Pos): Pic = {
    /*
    val myTopLeftRelativeToBG = at - my.internalPosWithin(self)
    Pic(self.overlayOn(backPic, myTopLeftRelativeToBG.xInt, myTopLeftRelativeToBG.yInt), backPic.anchor) // XXX pwa
    */
    this
  }

  def onto(backPic: Pic, my: Anchor, atIts: Anchor): Pic = onto(backPic, my, atIts.internalPosWithin(backPic.content))

  def onto(backPic: Pic, at: Pos): Pic = onto(backPic, anchor, at)

  def onto(backPic: Pic, atIts: Anchor = Anchor.Center): Pic = onto(backPic, anchor, atIts)

  // XXX document here and elsewhere: at (etc.) are local coordinates within the background
  def against(background: Pic, my: Anchor, at: Pos): Pic = { // XXX wasteful, and creates potentially ridiculously-sized Pics as an intermediate result
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

  def against(background: Pic, my: Anchor, atIts: Anchor): Pic = against(background, my, atIts.internalPosWithin(background.content))

  def against(background: Pic, at: Pos): Pic = against(background, anchor, at)

  def against(background: Pic, atIts: Anchor = Anchor.Center): Pic = against(background, anchor, atIts)


  def place(foreground: Pic, its: Anchor, at: Pos): Pic = {
    val foregroundAnchor = its
    foreground.against(this, foregroundAnchor, at)
  }

  def place(foreground: Pic, its: Anchor, atMy: Anchor): Pic = foreground.against(this, its, atMy.internalPosWithin(content))

  def place(foreground: Pic, at: Pos): Pic = place(foreground, foreground.anchor, at)

  def place(foreground: Pic, atMy: Anchor): Pic = place(foreground, foreground.anchor, atMy)

  def place(foregroundPicAndPos: (Pic, Pos)): Pic = place(foregroundPicAndPos._1, foregroundPicAndPos._2)

  def place(foregroundPics: Traversable[(Pic, Pos)]): Pic = foregroundPics.foldLeft(this)(_.place(_))

  def place(foregroundPics: (Pic, Pos)*): Pic = this.place(foregroundPics)

  def placeCopies(foregroundPic: Pic, at: Iterable[Pos]): Pic = this.place(at.map(_ => foregroundPic) zip at)


  def cropXXXRemoveThis(topLeft: Pos, width: Double, height: Double): Pic = {
    //Pic(self.crop(topLeft.xInt, topLeft.yInt, (topLeft.x + width - 1).round.toInt, (topLeft.y + height - 1).round.toInt), Center) // XXX pwa // XXX anchor
    this
  }

  // XXX allow retaining of original anchor (pohjustava osin toimiva esimerkki alla) and customization of anchoring
  def leftOf(rightPic: Pic, retainAnchor: Boolean = false): Pic = {
    //Pic(self.appendOnRight(rightPic)(Vertical.Middle, paddingInPixels = 0, backgroundColor = Transparent), self.anchor) // XXX pwa
    this
  }

  def rightOf(leftPic: Pic, retainAnchor: Boolean = false): Pic = {
    //Pic(self.appendOnLeft(leftPic)(Vertical.Middle, paddingInPixels = 0, backgroundColor = Transparent), self.anchor) // XXX pwa
    this
  }

  def below(abovePic: Pic, retainAnchor: Boolean = false): Pic = {
    //Pic(self.appendOnTop(abovePic)(Horizontal.Center, paddingInPixels = 0, backgroundColor = Transparent), self.anchor) // XXX pwa
    this
  }

  def above(lowerPic: Pic, retainAnchor: Boolean = false): Pic = {
    /*
    val smclPic = self.appendOnBottom(lowerPic)(Horizontal.Center, paddingInPixels = 0, backgroundColor = Transparent)
    if (retainAnchor)
      Pic(smclPic, self.absoluteAnchor)
    else
      Pic(smclPic, self.anchor) // XXX pwa
    */
    this
  }

  def rowOf(number: Int): Pic = this // Pic.leftToRight(Seq.fill(number)(this))

  def columnOf(number: Int): Pic = this // Pic.topToBottom(Seq.fill(number)(this))

  def alternatingRow(another: Pic, number: Int): Pic = {
    // Pic.leftToRight(Seq.tabulate(number)(index => if (index.isEven) this else another))
    this
  }

  def alternatingColumn(another: Pic, number: Int): Pic = {
    // Pic.topToBottom(Seq.tabulate(number)(index => if (index.isEven) this else another))
    this
  }

  def flipVertical: Pic = this // Pic(self.flipVertically(), self.anchor) // XXX pwa // XXX improper anchor
  def flipHorizontal: Pic = this // Pic(self.flipHorizontally(), self.anchor)  // XXX pwa // XXX improper anchor

  def rotate(degrees: Double, background: Color = Transparent): Pic = this.clockwise(degrees, background)

  def clockwise(degrees: Double = 90.0, background: Color = Transparent): Pic = {  // XXX pwa // XXX should rotare around pin
    /*
    if (degrees == 90.0) Pic(self.rotate90DegsCw(true, background), self.anchor)
    else Pic(self.rotateDegs(-degrees, true, background), self.anchor)
    */
    this
  }

  def counterclockwise(degrees: Double = 90.0, background: Color = Transparent): Pic = { // XXX pwa // XXX should rotare around pin
    /*
    if (degrees == 90.0) Pic(self.rotate90DegsCcw(true, background), self.anchor)
    else Pic(self.rotateDegs(degrees, true, background), self.anchor)
    */
    this
  }

  def isWiderThan(another: Pic): Boolean = this.width > another.width

  def isTallerThan(another: Pic): Boolean = this.height > another.height

  def fitsOnto(another: Pic): Boolean = !this.isWiderThan(another) && !this.isTallerThan(another)

  def shiftLeft(offset: Double): Pic = {
    val leftBit = this.cropXXXRemoveThis(Pos(0, 0), offset, height)
    val rightBit = this.cropXXXRemoveThis(Pos(offset, 0), width - offset, height)
    leftBit.rightOf(rightBit)
  }
  // XXX muihin suuntiin myös

  def slidingHorizontally(step: Double): Stream[Pic] = {
    slidingHorizontally(step, windowSize = width.floor.toInt, wrap = true)
  }

  def slidingHorizontally(step: Double, windowSize: Int, wrap: Boolean = true): Stream[Pic] = {
    val slide = if (step >= 0) new Slide.Right(step, windowSize) else new Slide.Left(step, windowSize)
    if (wrap) slide.forever else slide.once
  }

  def slidingVertically(step: Double): Stream[Pic] = {
    slidingVertically(step, windowSize = height.floor.toInt, wrap = true)
  }

  def slidingVertically(step: Double, windowSize: Int, wrap: Boolean = true): Stream[Pic] = {
    val slide = if (step >= 0) new Slide.Down(step, windowSize) else new Slide.Up(step, windowSize)
    if (wrap) slide.forever else slide.once
  }




  private object Slide {




    // XXX optional cache näille
    // XXX toteutus meni vähän turhan abstraktiokikkailuksi; voisi selkiyttää?
    abstract class Slide(val step: Double, val windowSize: Int, val attachExtension: Pic => Pic, val farEdge: Int, val sliceWidth: Int,
        val sliceHeight: Int, val slicePosition: Int => (Int, Int), val applyOnceTo: Pic => Stream[Pic]) {

      final def once: Stream[Pic] = {
        def sliceStarts(from: Double): Stream[Int] = {
          if (this.isPastLastOffset(from.floor.toInt)) Stream.empty else from.floor.toInt #:: sliceStarts(from + this.step)
        }

        def createSliceAt(start: Int): Pic = {
          val (x, y) = this.slicePosition(start) // XXX myöhemmin Pos
          //Pic(crop(x, y, x + this.sliceWidth - 1, y + this.sliceHeight - 1), self.anchor) // XXX -1 ei kiva // XXX pwa
          Pic()
        }

        sliceStarts(from = this.firstOffset).map(createSliceAt)
      }

      final def forever: Stream[Pic] = {
        val onceAcross = this.applyOnceTo(this.extendedForWraparound)

        def wrappingStream: Stream[Pic] = onceAcross #::: wrappingStream

        wrappingStream
      }

      private lazy val extendedForWraparound = {
        val (x, y, w, h) = this.extraForWraparound
        val extension = Pic() // Pic(crop(x, y, x + w - 1, y + h - 1), self.anchor) // XXX pwa // XXX -1 ei kiva
        this.attachExtension(extension)
      }

      protected lazy val highestOffset: Int = this.farEdge - this.windowSize

      protected def firstOffset: Int

      protected def isPastLastOffset(offset: Int): Boolean

      protected def extraForWraparound: (Int, Int, Int, Int)
    }




    abstract class Horizontal(step: Double, windowSize: Int, attachExtension: Pic => Pic) extends Slide(step, windowSize, attachExtension, farEdge = width.floor.toInt,
      sliceWidth = windowSize, sliceHeight = height.floor.toInt, slicePosition = (_, 0), applyOnceTo = _.slidingHorizontally(step, windowSize, wrap = false))




    abstract class Vertical(step: Double, windowSize: Int, attachExtension: Pic => Pic) extends Slide(step, windowSize, attachExtension, farEdge = height.floor.toInt,
      sliceWidth = width.floor.toInt, sliceHeight = windowSize, slicePosition = (0, _), applyOnceTo = _.slidingVertically(step, windowSize, wrap = false))




    class Right(step: Double, windowSize: Int) extends Horizontal(step, windowSize, leftOf(_)) with Forward {

      protected def extraForWraparound: (Int, Int, Int, Int) = (0, 0, this.sliceWidth - step.floor.toInt, this.sliceHeight)
    }




    class Down(step: Double, windowSize: Int) extends Vertical(step, windowSize, above(_)) with Forward {

      protected def extraForWraparound: (Int, Int, Int, Int) = (0, 0, this.sliceWidth, this.sliceHeight - step.floor.toInt)
    }




    class Up(step: Double, windowSize: Int) extends Vertical(step, windowSize, below(_)) with Backward {

      protected def extraForWraparound: (Int, Int, Int, Int) = (0, this.highestOffset, this.sliceWidth, this.windowSize + step.floor.toInt)
    }




    class Left(step: Double, windowSize: Int) extends Horizontal(step, windowSize, rightOf(_)) with Backward {

      protected def extraForWraparound: (Int, Int, Int, Int) = (this.highestOffset, 0, this.windowSize + step.floor.toInt, this.sliceHeight)
    }




    trait Forward extends Slide {

      assert(this.step >= 0)
      protected lazy val firstOffset: Int = 0

      protected def isPastLastOffset(offset: Int): Boolean = offset > this.highestOffset
    }




    trait Backward extends Slide {

      assert(this.step < 0)
      protected lazy val firstOffset: Int = this.highestOffset

      protected def isPastLastOffset(offset: Int): Boolean = offset < 0
    }




  }

}
