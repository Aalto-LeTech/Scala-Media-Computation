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

package smcl.pictures.fullfeatured


import scala.collection.mutable

import smcl.colors.ColorValidator
import smcl.colors.rgb.{Color, ColorComponentTranslationTable}
import smcl.infrastructure._
import smcl.pictures.operations._
import smcl.pictures.{BitmapValidator, ConvolutionKernel}
import smcl.settings._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmap extends BitmapCompanion[Bitmap] {

  /**
   * Creates a new empty [[Bitmap]] instance.
   */
  override
  def apply(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      initialBackgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    super.apply(
      widthInPixels,
      heightInPixels,
      initialBackgroundColor,
      viewerHandling)
  }

  /**
   *
   *
   * @param sourceResourcePath
   * @param viewerHandling
   *
   * @return
   */
  override
  def apply(
      sourceResourcePath: String,
      viewerHandling: ViewerUpdateStyle): Bitmap = {

    super.apply(
      sourceResourcePath,
      viewerHandling)
  }

  /**
   * Creates a new empty [[Bitmap]] instance and applies a processing function to it.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param initialBackgroundColor
   * @param processor
   *
   * @return
   */
  def apply(
      widthInPixels: Int,
      heightInPixels: Int,
      initialBackgroundColor: Color,
      processor: Option[(Bitmap) => Bitmap]): Bitmap = {

    super.apply(
      widthInPixels,
      heightInPixels,
      initialBackgroundColor,
      processor,
      VUSPreventViewerUpdates)
  }

  /**
   * HACK HACK HACK HACK --> REMOVE WHEN UNNECESSARY! See BitmapCompanion as well.
   *
   * @param bmpBfrAdapter
   *
   * @return
   */
  private[smcl]
  def apply(bmpBfrAdapter: BitmapBufferAdapter): Bitmap = {
    super.fromBitmapBufferAdapter(bmpBfrAdapter)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def apply(sourceResourcePath: String): Bitmap = {
    apply(sourceResourcePath, VUSUpdateViewerPerDefaults)
  }

  /**
   *
   *
   * @param operations
   * @param bitmapValidator
   * @param colorValidator
   * @param uniqueIdentifier
   *
   * @return
   */
  override
  protected
  def instantiateBitmap(
      operations: BitmapOperationList,
      bitmapValidator: BitmapValidator,
      colorValidator: ColorValidator,
      uniqueIdentifier: Identity): Bitmap = {

    new Bitmap(
      operations,
      bitmapValidator,
      colorValidator,
      uniqueIdentifier)
  }

}




/**
 *
 *
 * @param operations
 * @param bitmapValidator
 * @param uniqueIdentifier
 *
 * @author Aleksi Lukkarinen
 */
class Bitmap private[pictures](
    override private[pictures] val operations: BitmapOperationList,
    private val bitmapValidator: BitmapValidator,
    private val colorValidator: ColorValidator,
    uniqueIdentifier: Identity)
    extends AbstractBitmap(
      operations,
      bitmapValidator,
      colorValidator,
      uniqueIdentifier) {

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def map(f: (Bitmap) => Bitmap): Bitmap = {
    f(this)
  }

  /**
   * Applies an [[Renderable]] to this [[Bitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[pictures]
  override def apply(
      newOperation: Renderable,
      viewerHandling: ViewerUpdateStyle): Bitmap = {

    super.apply(newOperation, viewerHandling).asInstanceOf[Bitmap]
  }

  /**
   * Applies an [[Renderable]] to this [[Bitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[pictures]
  override def applyInitialization(newOperation: Renderable): Bitmap = {
    super.apply(newOperation, VUSPreventViewerUpdates).asInstanceOf[Bitmap]
  }

  /**
   * Applies an [[BufferProvider]] to this [[Bitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[pictures]
  override def apply(
      newOperation: BufferProvider,
      viewerHandling: ViewerUpdateStyle): Bitmap = {

    super.apply(newOperation, viewerHandling).asInstanceOf[Bitmap]
  }

  /**
   * Applies an [[BufferProvider]] to this [[Bitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[pictures]
  override def applyInitialization(newOperation: BufferProvider): Bitmap = {
    super.apply(newOperation, VUSPreventViewerUpdates).asInstanceOf[Bitmap]
  }

  /**
   *
   *
   * @return
   */
  def aspectRatio: (Double, Double) = {
    if (widthInPixels == heightInPixels)
      return (1.0, 1.0)

    if (widthInPixels > heightInPixels)
      return (heightInPixels.toDouble / widthInPixels, 1.0)

    (1.0, widthInPixels.toDouble / heightInPixels)
  }

  /**
   *
   *
   * @param color
   * @param viewerHandling
   *
   * @return
   */
  def clear(
      color: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(Clear(color), viewerHandling)
  }

  /**
   *
   *
   * @param kernel
   * @param viewerHandling
   *
   * @return
   */
  def convolveWith(
      kernel: ConvolutionKernel,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(ConvolveWithCustomKernel(kernel), viewerHandling)
  }

  /**
   *
   *
   * @param translator
   * @param viewerHandling
   *
   * @return
   */
  def filterWith(
      translator: ColorComponentTranslationTable,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(FilterWithComponentTranslationTable(translator), viewerHandling)
  }

  /**
   *
   *
   * @param function
   * @param viewerHandling
   *
   * @return
   */
  def iteratePixelsWith(
      function: (Int, Int, Int, Int) => (Int, Int, Int, Int),
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(IteratePixels(function), viewerHandling)
  }

  /**
   *
   *
   * @param colorToTrim
   * @param viewerHandling
   *
   * @return
   */
  def trim(
      colorToTrim: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(Trim(this, colorToTrim), viewerHandling)
  }

  /**
   *
   *
   * @param extraPixelsOntoLeftEdge
   * @param extraPixelsOntoTopEdge
   * @param extraPixelsOntoRightEdge
   * @param extraPixelsOntoBottomEdge
   * @param color
   * @param viewerHandling
   *
   * @return
   */
  def augmentCanvas(
      extraPixelsOntoLeftEdge: Int = 0,
      extraPixelsOntoTopEdge: Int = 0,
      extraPixelsOntoRightEdge: Int = 0,
      extraPixelsOntoBottomEdge: Int = 0,
      color: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      AugmentCanvas(
        this,
        extraPixelsOntoLeftEdge,
        extraPixelsOntoTopEdge,
        extraPixelsOntoRightEdge,
        extraPixelsOntoBottomEdge,
        color,
        bitmapValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param topBitmap
   * @param topBitmapUpperLeftX
   * @param topBitmapUpperLeftY
   * @param backgroundColor
   */
  def underlayBehind(
      topBitmap: Bitmap,
      topBitmapUpperLeftX: Int,
      topBitmapUpperLeftY: Int,
      topBitmapOpacity: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      OverlayFreely(
        this,
        topBitmap,
        topBitmapUpperLeftX,
        topBitmapUpperLeftY,
        topBitmapOpacity,
        backgroundColor,
        colorValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param bottomBitmap
   * @param upperLeftX
   * @param upperLeftY
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def overlayOn(
      bottomBitmap: Bitmap,
      upperLeftX: Int,
      upperLeftY: Int,
      opacity: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      OverlayFreely(
        bottomBitmap,
        this,
        upperLeftX,
        upperLeftY,
        opacity,
        backgroundColor,
        colorValidator),
      viewerHandling)
  }


  // ----------------------------------------------------------------------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|++| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HALeft, VATop)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|+*| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HALeft, VAMiddle)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|+-| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HALeft, VABottom)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|*+| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HACenter, VATop)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|**| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HACenter, VAMiddle)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|*-| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HACenter, VABottom)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|-+| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HARight, VATop)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|-*| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HARight, VAMiddle)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|--| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HARight, VABottom)
  }

  // ----------------------------------------------------------------------------------------------


  /**
   *
   *
   * @param bitmapsToLayOverThisFromBottomToTop
   * @param horizontalAlignment
   * @param verticalAlignment
   * @param opacityForAllBitmaps
   * @param backgroundColor
   *
   * @return
   */
  def overlayPerAlignments(
      bitmapsToLayOverThisFromBottomToTop: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      opacityForAllBitmaps: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      OverlayPerAlignments(this +: bitmapsToLayOverThisFromBottomToTop)(
        horizontalAlignment,
        verticalAlignment,
        opacityForAllBitmaps,
        colorValidator = colorValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param verticalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendOnLeft(
      bitmapsToCombineWith: Bitmap*)(
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendHorizontally(bitmapsToCombineWith :+ this)(
        verticalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param verticalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendOnRight(
      bitmapsToCombineWith: Bitmap*)(
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendHorizontally(this +: bitmapsToCombineWith)(
        verticalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param bitmap
   *
   * @return
   */
  def sewOnLeftOf(bitmap: Bitmap): Bitmap = {
    appendOnRight(bitmap)(VAMiddle)
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param horizontalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendOnTop(
      bitmapsToCombineWith: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendVertically(bitmapsToCombineWith :+ this)(
        horizontalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param bitmap
   *
   * @return
   */
  def pileOnTopOf(bitmap: Bitmap): Bitmap = {
    appendOnBottom(bitmap)(HACenter)
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param horizontalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendOnBottom(
      bitmapsToCombineWith: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendVertically(this +: bitmapsToCombineWith)(
        horizontalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def flipHorizontally(
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(FlipHorizontally(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def flipVertically(
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(FlipVertically(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def flipDiagonally(
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(FlipDiagonally(), viewerHandling)
  }

  /**
   *
   *
   * @param shearingFactorHorizontal
   * @param shearingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def shear(
      shearingFactorHorizontal: Double = 0.0,
      shearingFactorVertical: Double = 0.0,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      Shear(
        this,
        shearingFactorHorizontal,
        shearingFactorVertical,
        resizeCanvasBasedOnTransformation,
        backgroundColor),
      viewerHandling)
  }

  /**
   *
   *
   * @param shearingFactor
   *
   * @return
   */
  def shear(shearingFactor: Double): Bitmap = {
    shear(shearingFactor, shearingFactor)
  }

  /**
   *
   *
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def shearHorizontally(
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    shear(
      shearingFactorHorizontal = shearingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation,
      backgroundColor = backgroundColor,
      viewerHandling = viewerHandling)
  }

  /**
   *
   *
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def shearVertically(
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    shear(
      shearingFactorVertical = shearingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation,
      backgroundColor = backgroundColor,
      viewerHandling = viewerHandling)
  }

  /**
   *
   *
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def replicateHorizontally(
      numberOfReplicas: Int,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      ReplicateHorizontally(
        this,
        numberOfReplicas,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def replicateVertically(
      numberOfReplicas: Int,
      paddingInPixels: Double = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = VUSUpdateViewerPerDefaults): Bitmap = {

    apply(
      ReplicateVertically(
        this,
        numberOfReplicas,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
  }


  // ----------------------------------------------------------------------------------------------

  //-------------------------------
  //
  //  :/\
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :/\ (other: Bitmap): Bitmap = appendOnTop(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :/\ (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnTop(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :/\ (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    :/\(other.toSeq)
  }

  //-------------------------------
  //
  //  /\:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def /\: (other: Bitmap): Bitmap = appendOnTop(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def /\: (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnTop(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def /\: (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    /\:(other.toSeq)
  }

  //-------------------------------
  //
  //  :\/
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :\/ (other: Bitmap): Bitmap = appendOnBottom(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :\/ (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnBottom(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :\/ (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    :\/(other.toSeq)
  }

  //-------------------------------
  //
  //  \/:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def \/: (other: Bitmap): Bitmap = appendOnBottom(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def \/: (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnBottom(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def \/: (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    \/:(other.toSeq)
  }

  //-------------------------------
  //
  //  :>>
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :>> (other: Bitmap): Bitmap = appendOnRight(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :>> (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnRight(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :>> (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    :>>(other.toSeq)
  }

  //-------------------------------
  //
  //  >>:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def >>: (other: Bitmap): Bitmap = appendOnRight(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def >>: (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnRight(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def >>: (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    >>:(other.toSeq)
  }

  //-------------------------------
  //
  //  :<<
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :<< (other: Bitmap): Bitmap = appendOnLeft(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :<< (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnLeft(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :<< (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    :<<(other.toSeq)
  }

  //-------------------------------
  //
  //  <<:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def <<: (other: Bitmap): Bitmap = appendOnLeft(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def <<: (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnLeft(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def <<: (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    <<:(other.toSeq)
  }

  // ----------------------------------------------------------------------------------------------


  /**
   * Returns a mutable `ArrayBuffer` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   *
   * @return
   */
  private def propagateToArrayBuffer(size: Int): mutable.ArrayBuffer[Bitmap] = {
    require(size >= 0, s"Size of the collection cannot be negative (was $size)")

    mutable.ArrayBuffer.fill[Bitmap](size)(this)
  }

  /**
   * Returns an `Array` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   *
   * @return
   */
  def propagateToArray(size: Int): Array[Bitmap] = {
    propagateToArrayBuffer(size).toArray
  }

  /**
   * Returns an `List` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   *
   * @return
   */
  def propagateToList(size: Int): List[Bitmap] = {
    propagateToArrayBuffer(size).toList
  }

  /**
   * Returns an `Seq` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   *
   * @return
   */
  def propagateToSeq(size: Int): Seq[Bitmap] = {
    propagateToArrayBuffer(size)
  }

  /**
   *
   *
   * @param operations
   * @param bitmapValidator
   * @param colorValidator
   * @param uniqueIdentifier
   *
   * @return
   */
  override
  protected
  def instantiateBitmap(
      operations: BitmapOperationList,
      bitmapValidator: BitmapValidator,
      colorValidator: ColorValidator,
      uniqueIdentifier: Identity): Bitmap = {

    Bitmap.instantiateBitmap(
      operations,
      bitmapValidator,
      colorValidator,
      uniqueIdentifier)
  }

}
