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

package aalto.smcl.geometry


import aalto.smcl.infrastructure.{AffineTransformationAdapter, PRF}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object AffineTransformation {

  /**
   *
   *
   * @return
   */
  def apply(): AffineTransformation = {
    new AffineTransformation(PRF.createPlatformAffineTransformation)
  }


  /**
   *
   *
   * @param bitmapWidth
   *
   * @return
   */
  def forHorizontalFlipOf(bitmapWidth: Int): AffineTransformation = {
    AffineTransformation()
        .scale(-1, 1)
        .translate(-bitmapWidth, 0)
  }

  /**
   *
   *
   * @param bitmapHeight
   *
   * @return
   */
  def forVerticalFlipOf(bitmapHeight: Int): AffineTransformation = {
    AffineTransformation()
        .scale(1, -1)
        .translate(0, -bitmapHeight)
  }

  /**
   *
   *
   * @param bitmapHeight
   *
   * @return
   */
  def forDiagonalFlipOf(
      bitmapWidth: Int,
      bitmapHeight: Int): AffineTransformation = {

    AffineTransformation()
        .scale(-1, -1)
        .translate(-bitmapWidth, -bitmapHeight)
  }

  /**
   *
   *
   * @param factorX
   * @param factorY
   *
   * @return
   */
  def forFreeScalingOf(
      factorX: Double,
      factorY: Double): AffineTransformation = {

    AffineTransformation()
        .scale(factorX, factorY)
  }

  /**
   *
   *
   * @param amountX
   * @param amountY
   *
   * @return
   */
  def forFreeShearingOf(
      amountX: Double,
      amountY: Double): AffineTransformation = {

    AffineTransformation()
        .shear(amountX, amountY)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  def forFreeRotationOf(
      angleInDegrees: Double): AffineTransformation = {

    AffineTransformation()
        .rotateDegs(angleInDegrees)
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param anchorX
   * @param anchorY
   *
   * @return
   */
  def forFreeRotationOfAround(
      angleInDegrees: Double,
      anchorX: Double,
      anchorY: Double): AffineTransformation = {

    AffineTransformation()
        .rotateDegsAround(angleInDegrees, anchorX, anchorY)
  }

  /**
   *
   *
   * @return
   */
  def forRotationOf90DegsCW: AffineTransformation = {
    AffineTransformation()
        .rotate90DegsCW
  }

  /**
   *
   *
   * @param anchorX
   * @param anchorY
   *
   * @return
   */
  def forRotationOf90DegsCWAround(
      anchorX: Double,
      anchorY: Double): AffineTransformation = {

    AffineTransformation()
        .rotate90DegsCWAround(anchorX, anchorY)
  }

  /**
   *
   *
   * @return
   */
  def forRotationOf90DegsCCW: AffineTransformation = {
    AffineTransformation()
        .rotate90DegsCCW
  }

  /**
   *
   *
   * @param anchorX
   * @param anchorY
   *
   * @return
   */
  def forRotationOf90DegsCCWAround(
      anchorX: Double,
      anchorY: Double): AffineTransformation = {

    AffineTransformation()
        .rotate90DegsCCWAround(anchorX, anchorY)
  }

  /**
   *
   *
   * @return
   */
  def forRotationOf180Degs: AffineTransformation = {
    AffineTransformation()
        .rotate180Degs
  }

  /**
   *
   *
   * @param anchorX
   * @param anchorY
   *
   * @return
   */
  def forRotationOf180DegsAround(
      anchorX: Double,
      anchorY: Double): AffineTransformation = {

    AffineTransformation()
        .rotate180DegsAround(anchorX, anchorY)
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class AffineTransformation private(
    private[smcl] val platformAffineTransform: AffineTransformationAdapter) {

  require(platformAffineTransform != null,
    "Platform transformation argument cannot be null.")


  /**
   *
   *
   * @param factorX
   * @param factorY
   *
   * @return
   */
  def scale(
      factorX: Double,
      factorY: Double): AffineTransformation = {

    AffineTransformation(
      platformAffineTransform.copy.scale(
        factorX, factorY))
  }

  /**
   *
   *
   * @param amountXInPixels
   * @param amountYInPixels
   *
   * @return
   */
  def translate(
      amountXInPixels: Double,
      amountYInPixels: Double): AffineTransformation = {

    AffineTransformation(
      platformAffineTransform.copy.translate(
        amountXInPixels, amountYInPixels))
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  def rotateDegs(angleInDegrees: Double): AffineTransformation = {
    AffineTransformation(
      platformAffineTransform.copy.rotateDegs(angleInDegrees))
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param anchorXInPixels
   * @param anchorYInPixels
   *
   * @return
   */
  def rotateDegsAround(
      angleInDegrees: Double,
      anchorXInPixels: Double,
      anchorYInPixels: Double): AffineTransformation = {

    AffineTransformation(
      platformAffineTransform.copy.rotateDegsAround(
        angleInDegrees, anchorXInPixels, anchorYInPixels))
  }

  /**
   *
   *
   * @return
   */
  def rotate90DegsCW: AffineTransformation = {
    AffineTransformation(
      platformAffineTransform.copy.rotate90DegsCW)
  }

  /**
   *
   *
   * @param anchorXInPixels
   * @param anchorYInPixels
   *
   * @return
   */
  def rotate90DegsCWAround(
      anchorXInPixels: Double,
      anchorYInPixels: Double): AffineTransformation = {

    AffineTransformation(
      platformAffineTransform.copy.rotate90DegsCWAround(
        anchorXInPixels, anchorYInPixels))
  }

  /**
   *
   *
   * @return
   */
  def rotate90DegsCCW: AffineTransformation = {
    AffineTransformation(
      platformAffineTransform.copy.rotate90DegsCCW)
  }

  /**
   *
   *
   * @param anchorXInPixels
   * @param anchorYInPixels
   *
   * @return
   */
  def rotate90DegsCCWAround(
      anchorXInPixels: Double,
      anchorYInPixels: Double): AffineTransformation = {

    AffineTransformation(
      platformAffineTransform.copy.rotate90DegsCCWAround(
        anchorXInPixels, anchorYInPixels))
  }

  /**
   *
   *
   * @return
   */
  def rotate180Degs: AffineTransformation = {
    AffineTransformation(
      platformAffineTransform.copy.rotate180Degs)
  }

  /**
   *
   *
   * @param anchorXInPixels
   * @param anchorYInPixels
   *
   * @return
   */
  def rotate180DegsAround(
      anchorXInPixels: Double,
      anchorYInPixels: Double): AffineTransformation = {

    AffineTransformation(
      platformAffineTransform.copy.rotate180DegsAround(
        anchorXInPixels, anchorYInPixels))
  }

  /**
   *
   *
   * @param amountX
   * @param amountY
   */
  def shear(amountX: Double, amountY: Double): AffineTransformation = {
    AffineTransformation(
      platformAffineTransform.copy.shear(
        amountX, amountY))
  }

  /**
   *
   *
   * @return
   */
  def copy: AffineTransformation = {
    AffineTransformation(platformAffineTransform.copy)
  }

}
