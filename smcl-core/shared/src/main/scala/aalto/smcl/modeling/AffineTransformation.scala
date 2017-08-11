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

package aalto.smcl.modeling


import aalto.smcl.infrastructure.MathUtils




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object AffineTransformation {

  private val Zero = 0.0

  private val One = 1.0

  /** Identity transformation. */
  lazy val Identity: AffineTransformation = {
    AffineTransformation(
      One, Zero, Zero,
      Zero, One, Zero)
  }

  /**
   *
   *
   * @param horizontalSize
   *
   * @return
   */
  @inline
  def forYAxisRelativeHorizontalFlipOf(
      horizontalSize: Len): AffineTransformation = {

    forYAxisRelativeHorizontalFlipOf(horizontalSize.inPixels)
  }

  /**
   *
   *
   * @param horizontalSizeInPixels
   *
   * @return
   */
  @inline
  def forYAxisRelativeHorizontalFlipOf(
      horizontalSizeInPixels: Double): AffineTransformation = {

    AffineTransformation(
      -One, Zero, -horizontalSizeInPixels,
      Zero, One, Zero)
  }

  /**
   *
   *
   * @param verticalSize
   *
   * @return
   */
  @inline
  def forXAxisRelativeVerticalFlipOf(
      verticalSize: Len): AffineTransformation = {

    forXAxisRelativeVerticalFlipOf(verticalSize.inPixels)
  }

  /**
   *
   *
   * @param verticalSizeInPixels
   *
   * @return
   */
  @inline
  def forXAxisRelativeVerticalFlipOf(
      verticalSizeInPixels: Double): AffineTransformation = {

    AffineTransformation(
      One, Zero, Zero,
      Zero, -One, -verticalSizeInPixels)
  }

  /**
   *
   *
   * @param sizes
   *
   * @return
   */
  @inline
  def forOrigoRelativeDiagonalFlipOf(
      sizes: d2.Dims): AffineTransformation = {

    forOrigoRelativeDiagonalFlipOf(
      sizes.width.inPixels,
      sizes.height.inPixels)
  }

  /**
   *
   *
   * @param horizontalSize
   * @param verticalSize
   *
   * @return
   */
  @inline
  def forOrigoRelativeDiagonalFlipOf(
      horizontalSize: Len,
      verticalSize: Len): AffineTransformation = {

    forOrigoRelativeDiagonalFlipOf(
      horizontalSize.inPixels,
      verticalSize.inPixels)
  }

  /**
   *
   *
   * @param horizontalSizeInPixels
   * @param verticalSizeInPixels
   *
   * @return
   */
  @inline
  def forOrigoRelativeDiagonalFlipOf(
      horizontalSizeInPixels: Double,
      verticalSizeInPixels: Double): AffineTransformation = {

    AffineTransformation(
      -One, Zero, -horizontalSizeInPixels,
      Zero, -One, -verticalSizeInPixels)
  }

  /**
   *
   *
   * @param lengthX
   * @param lengthY
   *
   * @return
   */
  @inline
  def forTranslationOf(
      lengthX: Len,
      lengthY: Len): AffineTransformation = {

    forTranslationOf(
      lengthX.inPixels,
      lengthY.inPixels)
  }

  /**
   *
   *
   * @param lengthXInPixels
   * @param lengthYInPixels
   *
   * @return
   */
  @inline
  def forTranslationOf(
      lengthXInPixels: Double,
      lengthYInPixels: Double): AffineTransformation = {

    AffineTransformation(
      One, Zero, lengthXInPixels,
      Zero, One, lengthYInPixels)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   *
   * @return
   */
  @inline
  def forOrigoRelativeScalingOf(
      factorX: Double,
      factorY: Double): AffineTransformation = {

    AffineTransformation(
      factorX, Zero, Zero,
      Zero, factorY, Zero)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   *
   * @return
   */
  @inline
  def forOrigoRelativeShearingOf(
      factorX: Double,
      factorY: Double): AffineTransformation = {

    AffineTransformation(
      Zero, factorX, Zero,
      factorY, Zero, Zero)
  }

  /**
   *
   *
   * @param angle
   *
   * @return
   */
  @inline
  def forOrigoCentredRotationOf(
      angle: Angle): AffineTransformation = {

    forOrigoCentredRotationOf(
      angle.cos,
      angle.sin)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def forOrigoCentredRotationOf(
      angleInDegrees: Double): AffineTransformation = {

    forOrigoCentredRotationOf(
      MathUtils.cos(angleInDegrees),
      MathUtils.sin(angleInDegrees))
  }

  /**
   *
   *
   * @param sin
   *
   * @return
   */
  @inline
  private
  def forOrigoCentredRotationOf(
      cos: Double,
      sin: Double): AffineTransformation = {

    AffineTransformation(
      cos, -sin, Zero,
      sin, cos, Zero)
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def forPointCentredRotation(
      angleInDegrees: Double,
      pX: Double,
      pY: Double): AffineTransformation = {

    val cos = MathUtils.cos(angleInDegrees)
    val sin = MathUtils.sin(angleInDegrees)

    forPointCentredRotation(cos, sin, pX, pY)
  }

  /**
   *
   *
   * @param angle
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def forPointCentredRotation(
      angle: Angle,
      pX: Double,
      pY: Double): AffineTransformation = {

    forPointCentredRotation(
      angle.cos,
      angle.sin,
      pX,
      pY)
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param point
   *
   * @return
   */
  @inline
  def forPointCentredRotation(
      angleInDegrees: Double,
      point: Pos): AffineTransformation = {

    val cos = MathUtils.cos(angleInDegrees)
    val sin = MathUtils.sin(angleInDegrees)

    forPointCentredRotation(
      cos,
      sin,
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param angle
   * @param point
   *
   * @return
   */
  @inline
  def forPointCentredRotation(
      angle: Angle,
      point: Pos): AffineTransformation = {

    forPointCentredRotation(
      angle.cos,
      angle.sin,
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param cos
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def forPointCentredRotation(
      cos: Double,
      sin: Double,
      pX: Double,
      pY: Double): AffineTransformation = {

    AffineTransformation(
      cos, -sin, pX + sin * pY - cos * pX,
      sin, cos, pY - sin * pX - cos * pY)
  }

  /** */
  lazy val forOrigoCentredRotationOf90DegsCW: AffineTransformation = {
    AffineTransformation(
      Zero, One, Zero,
      -One, Zero, Zero)
  }

  /**
   *
   *
   * @param point
   *
   * @return
   */
  @inline
  def forPointCentredRotationOf90DegsCW(
      point: Pos): AffineTransformation = {

    forPointCentredRotationOf90DegsCW(
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def forPointCentredRotationOf90DegsCW(
      pX: Double,
      pY: Double): AffineTransformation = {

    AffineTransformation(
      Zero, One, pX - pY,
      -One, Zero, pX + pY)
  }

  /** */
  lazy val forOrigoCentredRotationOf90DegsCCW: AffineTransformation = {
    AffineTransformation(
      Zero, -One, Zero,
      One, Zero, Zero)
  }

  /**
   *
   *
   * @param point
   *
   * @return
   */
  @inline
  def forPointCentredRotationOf90DegsCCW(
      point: Pos): AffineTransformation = {

    forPointCentredRotationOf90DegsCCW(
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def forPointCentredRotationOf90DegsCCW(
      pX: Double,
      pY: Double): AffineTransformation = {

    AffineTransformation(
      Zero, -One, pX + pY,
      One, Zero, pY - pX)
  }

  /** */
  lazy val forOrigoCentredRotationOf180Degs: AffineTransformation = {
    AffineTransformation(
      One, Zero, Zero,
      Zero, One, Zero)
  }

  /**
   *
   *
   * @param point
   *
   * @return
   */
  @inline
  def forPointCentredRotationOf180Degs(
      point: Pos): AffineTransformation = {

    forPointCentredRotationOf180Degs(
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def forPointCentredRotationOf180Degs(
      pX: Double,
      pY: Double): AffineTransformation = {

    AffineTransformation(
      One, Zero, 2 * pX,
      Zero, One, 2 * pY)
  }

}




/**
 *
 *
 * @param alpha
 * @param beta
 * @param gamma
 * @param delta
 * @param tauX
 * @param tauY
 *
 * @author Aleksi Lukkarinen
 */
case class AffineTransformation(
    alpha: Double, gamma: Double, tauX: Double,
    delta: Double, beta: Double, tauY: Double) {

  /**
   *
   *
   * @param dimensions
   *
   * @return
   */
  @inline
  def translate(
      dimensions: d2.Dims): AffineTransformation = {

    translate(
      dimensions.width.inPixels,
      dimensions.height.inPixels)
  }

  /**
   *
   *
   * @param lengthX
   * @param lengthY
   *
   * @return
   */
  @inline
  def translate(
      lengthX: Len,
      lengthY: Len): AffineTransformation = {

    translate(lengthX.inPixels, lengthY.inPixels)
  }

  /**
   *
   *
   * @param lengthXInPixels
   * @param lengthYInPixels
   *
   * @return
   */
  @inline
  def translate(
      lengthXInPixels: Double,
      lengthYInPixels: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = alpha,
      beta = beta,
      gamma = gamma,
      delta = delta,
      tauX = tauX + lengthXInPixels,
      tauY = tauY + lengthYInPixels)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   *
   * @return
   */
  @inline
  def scaleRelativeToOrigo(
      factorX: Double,
      factorY: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = factorX * alpha,
      beta = factorY * beta,
      gamma = factorX * gamma,
      delta = factorY * delta,
      tauX = factorX * tauX,
      tauY = factorY * tauY)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @param point
   *
   * @return
   */
  @inline
  def scaleRelativeToPoint(
      factorX: Double,
      factorY: Double,
      point: Pos): AffineTransformation = {

    scaleRelativeToPoint(
      factorX,
      factorY,
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def scaleRelativeToPoint(
      factorX: Double,
      factorY: Double,
      pX: Double,
      pY: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = factorX * alpha,
      beta = factorY * beta,
      gamma = factorX * gamma,
      delta = factorY * delta,
      tauX = pX + factorX * (tauX - pX),
      tauY = pY + factorY * (tauY - pY))
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @param objectsLowerLeftCorner
   *
   * @return
   */
  @inline
  def shearRelativeToOrigo(
      factorX: Double,
      factorY: Double,
      objectsLowerLeftCorner: Pos): AffineTransformation = {

    shearRelativeToOrigo(
      factorX,
      factorY,
      objectsLowerLeftCorner.xInPixels,
      objectsLowerLeftCorner.yInPixels)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @param objectsLowerLeftCornerX
   * @param objectsLowerLeftCornerY
   *
   * @return
   */
  @inline
  def shearRelativeToOrigo(
      factorX: Double,
      factorY: Double,
      objectsLowerLeftCornerX: Double,
      objectsLowerLeftCornerY: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = alpha + factorX * delta,
      beta = beta + factorY * gamma,
      gamma = gamma + factorX * beta,
      delta = delta + factorY * alpha,
      tauX = tauX + factorX * (tauY - objectsLowerLeftCornerY),
      tauY = tauY + factorY * (tauX - objectsLowerLeftCornerX))
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @param objectsLowerLeftCorner
   * @param point
   *
   * @return
   */
  @inline
  def shearRelativeToPoint(
      factorX: Double,
      factorY: Double,
      objectsLowerLeftCorner: Pos,
      point: Pos): AffineTransformation = {

    shearRelativeToPoint(
      factorX,
      factorY,
      objectsLowerLeftCorner.xInPixels,
      objectsLowerLeftCorner.yInPixels,
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @param objectsLowerLeftCornerX
   * @param objectsLowerLeftCornerY
   * @param point
   *
   * @return
   */
  @inline
  def shearRelativeToPoint(
      factorX: Double,
      factorY: Double,
      objectsLowerLeftCornerX: Double,
      objectsLowerLeftCornerY: Double,
      point: Pos): AffineTransformation = {

    shearRelativeToPoint(
      factorX,
      factorY,
      objectsLowerLeftCornerX,
      objectsLowerLeftCornerY,
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @param objectsLowerLeftCorner
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def shearRelativeToPoint(
      factorX: Double,
      factorY: Double,
      objectsLowerLeftCorner: Pos,
      pX: Double,
      pY: Double): AffineTransformation = {

    shearRelativeToPoint(
      factorX,
      factorY,
      objectsLowerLeftCorner.xInPixels,
      objectsLowerLeftCorner.yInPixels,
      pX,
      pY)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @param objectsLowerLeftCornerX
   * @param objectsLowerLeftCornerY
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def shearRelativeToPoint(
      factorX: Double,
      factorY: Double,
      objectsLowerLeftCornerX: Double,
      objectsLowerLeftCornerY: Double,
      pX: Double,
      pY: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = alpha + factorX * delta,
      beta = beta + factorY * gamma,
      gamma = gamma + factorX * beta,
      delta = delta + factorY * alpha,
      tauX = tauX + factorX * (tauY - objectsLowerLeftCornerY - pY),
      tauY = tauY + factorY * (tauX - objectsLowerLeftCornerX - pX))
  }

  /**
   *
   *
   * @param angle
   *
   * @return
   */
  @inline
  def rotateAroundOrigo(angle: Angle): AffineTransformation = {
    rotateDegsAroundOrigo(angle.cos, angle.sin)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def rotateDegsAroundOrigo(angleInDegrees: Double): AffineTransformation = {
    rotateDegsAroundOrigo(
      MathUtils.cos(angleInDegrees),
      MathUtils.sin(angleInDegrees))
  }

  /**
   *
   *
   * @param cos
   * @param sin
   *
   * @return
   */
  @inline
  private
  def rotateDegsAroundOrigo(cos: Double, sin: Double): AffineTransformation = {
    new AffineTransformation(
      alpha = cos * alpha - sin * delta,
      beta = cos * beta + sin * gamma,
      gamma = cos * gamma - sin * beta,
      delta = cos * delta + sin * alpha,
      tauX = cos * tauX - sin * tauY,
      tauY = cos * tauY + sin * tauX)
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def rotateDegsAroundPoint(
      angleInDegrees: Double,
      pX: Double,
      pY: Double): AffineTransformation = {

    val cos = MathUtils.cos(angleInDegrees)
    val sin = MathUtils.sin(angleInDegrees)

    rotateDegsAroundPoint(cos, sin, pX, pY)
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param point
   *
   * @return
   */
  @inline
  def rotateDegsAroundPoint(
      angleInDegrees: Double,
      point: Pos): AffineTransformation = {

    val cos = MathUtils.cos(angleInDegrees)
    val sin = MathUtils.sin(angleInDegrees)

    rotateDegsAroundPoint(
      cos,
      sin,
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param angle
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def rotateAroundPoint(
      angle: Angle,
      pX: Double,
      pY: Double): AffineTransformation = {

    rotateDegsAroundPoint(
      angle.cos,
      angle.sin,
      pX,
      pY)
  }

  /**
   *
   *
   * @param angle
   * @param point
   *
   * @return
   */
  @inline
  def rotateAroundPoint(
      angle: Angle,
      point: Pos): AffineTransformation = {

    rotateDegsAroundPoint(
      angle.cos,
      angle.sin,
      point.xInPixels,
      point.yInPixels)
  }

  /**
   *
   *
   * @param cos
   * @param sin
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  private
  def rotateDegsAroundPoint(
      cos: Double,
      sin: Double,
      pX: Double,
      pY: Double): AffineTransformation = {

    val offsetX = tauX - pX

    new AffineTransformation(
      alpha = cos * alpha - sin * delta,
      beta = cos * beta + sin * gamma,
      gamma = cos * gamma - sin * beta,
      delta = cos * delta + sin * alpha,
      tauX = pX + cos * offsetX + sin * (pY - tauY),
      tauY = pY + cos * (tauY - pY) + sin * offsetX)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def rotate90DegsCWAroundOrigo: AffineTransformation = {
    new AffineTransformation(
      alpha = delta,
      beta = -gamma,
      gamma = beta,
      delta = -alpha,
      tauX = tauY,
      tauY = tauX)
  }

  /**
   *
   *
   * @param point
   *
   * @return
   */
  @inline
  def rotate90DegsCWAroundPoint(
      point: Pos): AffineTransformation = {

    rotate90DegsCWAroundPoint(
      point.xInPixels, point.yInPixels)
  }

  /**
   *
   *
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def rotate90DegsCWAroundPoint(
      pX: Double,
      pY: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = delta,
      beta = -gamma,
      gamma = beta,
      delta = -alpha,
      tauX = pX - pY + tauY,
      tauY = pY - tauX + pX)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def rotate90DegsCCWAroundOrigo: AffineTransformation = {
    new AffineTransformation(
      alpha = -delta,
      beta = gamma,
      gamma = -beta,
      delta = alpha,
      tauX = -tauY,
      tauY = tauX)
  }

  /**
   *
   *
   * @param point
   *
   * @return
   */
  @inline
  def rotate90DegsCCWAroundPoint(
      point: Pos): AffineTransformation = {

    rotate90DegsCCWAroundPoint(
      point.xInPixels, point.yInPixels)
  }

  /**
   *
   *
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def rotate90DegsCCWAroundPoint(
      pX: Double,
      pY: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = -delta,
      beta = gamma,
      gamma = -beta,
      delta = alpha,
      tauX = pX + pY - tauY,
      tauY = pY + tauX - pX)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def rotate180DegsAroundOrigo: AffineTransformation = {
    new AffineTransformation(
      alpha = -alpha,
      beta = -beta,
      gamma = -gamma,
      delta = -delta,
      tauX = -tauX,
      tauY = -tauY)
  }

  /**
   *
   *
   * @param point
   *
   * @return
   */
  @inline
  def rotate180DegsAroundPoint(
      point: Pos): AffineTransformation = {

    rotate180DegsAroundPoint(
      point.xInPixels, point.yInPixels)
  }

  /**
   *
   *
   * @param pX
   * @param pY
   *
   * @return
   */
  @inline
  def rotate180DegsAroundPoint(
      pX: Double,
      pY: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = -alpha,
      beta = -beta,
      gamma = -gamma,
      delta = -delta,
      tauX = 2 * pX - tauX,
      tauY = 2 * pY - tauY)
  }

  /**
   *
   *
   * @param horizontalSize
   *
   * @return
   */
  @inline
  def flipHorizontallyAroundYAxis(
      horizontalSize: Len): AffineTransformation = {

    flipHorizontallyAroundYAxis(horizontalSize.inPixels)
  }

  /**
   *
   *
   * @param horizontalSizeInPixels
   *
   * @return
   */
  @inline
  def flipHorizontallyAroundYAxis(
      horizontalSizeInPixels: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = -alpha,
      beta = beta,
      gamma = -gamma,
      delta = delta,
      tauX = -tauX - horizontalSizeInPixels,
      tauY = tauY)
  }

  /**
   *
   *
   * @param verticalSize
   *
   * @return
   */
  @inline
  def flipVerticallyAroundXAxis(
      verticalSize: Len): AffineTransformation = {

    flipVerticallyAroundXAxis(verticalSize.inPixels)
  }

  /**
   *
   *
   * @param verticalSizeInPixels
   *
   * @return
   */
  @inline
  def flipVerticallyAroundXAxis(
      verticalSizeInPixels: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = alpha,
      beta = -beta,
      gamma = gamma,
      delta = -delta,
      tauX = tauX,
      tauY = -tauY - verticalSizeInPixels)
  }

  /**
   *
   *
   * @param sizes
   *
   * @return
   */
  @inline
  def flipDiagonallyAroundOrigo(
      sizes: d2.Dims): AffineTransformation = {

    flipDiagonallyAroundOrigo(
      sizes.width.inPixels,
      sizes.height.inPixels)
  }

  /**
   *
   *
   * @param horizontalSize
   * @param verticalSize
   *
   * @return
   */
  @inline
  def flipDiagonallyAroundOrigo(
      horizontalSize: Len,
      verticalSize: Len): AffineTransformation = {

    flipDiagonallyAroundOrigo(
      horizontalSize.inPixels,
      verticalSize.inPixels)
  }

  /**
   *
   *
   * @param horizontalSizeInPixels
   * @param verticalSizeInPixels
   *
   * @return
   */
  @inline
  def flipDiagonallyAroundOrigo(
      horizontalSizeInPixels: Double,
      verticalSizeInPixels: Double): AffineTransformation = {

    new AffineTransformation(
      alpha = -alpha,
      beta = -beta,
      gamma = -gamma,
      delta = -delta,
      tauX = -tauX - horizontalSizeInPixels,
      tauY = -tauY - verticalSizeInPixels)
  }

}
