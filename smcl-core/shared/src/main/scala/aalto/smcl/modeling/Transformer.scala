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
object Transformer {

  /**
   *
   *
   * @param position
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def rotate(
      position: Pos,
      angleInDegrees: Double): Pos = {

    val (sin, cos) =
      MathUtils.sinCosRads(math.toRadians(angleInDegrees))

    val x = position.xInPixels
    val y = position.yInPixels

    val xNew = rotateX2D(x, y, sin, cos)
    val yNew = rotateY2D(x, y, sin, cos)

    Pos(xNew, yNew)
  }

  /**
   *
   *
   * @param position
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  def rotate(
      position: Pos,
      angleInDegrees: Double,
      centerOfRotation: Pos): Pos = {

    val (sin, cos) =
      MathUtils.sinCosRads(math.toRadians(angleInDegrees))

    val x = position.xInPixels - centerOfRotation.xInPixels
    val y = position.yInPixels - centerOfRotation.yInPixels

    val xNew = rotateX2D(x, y, sin, cos) + centerOfRotation.xInPixels
    val yNew = rotateY2D(x, y, sin, cos) + centerOfRotation.yInPixels

    Pos(xNew, yNew)
  }

  /**
   *
   *
   * @param positions
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def rotate(
      positions: Seq[Pos],
      angleInDegrees: Double): Seq[Pos] = {

    val (sin, cos) =
      MathUtils.sinCosRads(math.toRadians(angleInDegrees))

    for {
      p <- positions

      x = p.xInPixels
      y = p.yInPixels

      xNew = rotateX2D(x, y, sin, cos)
      yNew = rotateY2D(x, y, sin, cos)
    } yield Pos(xNew, yNew)
  }

  /**
   *
   *
   * @param positions
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  def rotate(
      positions: Seq[Pos],
      angleInDegrees: Double,
      centerOfRotation: Pos): Seq[Pos] = {

    val (sin, cos) =
      MathUtils.sinCosRads(math.toRadians(angleInDegrees))

    for {
      p <- positions

      xNorm = p.xInPixels - centerOfRotation.xInPixels
      yNorm = p.yInPixels - centerOfRotation.yInPixels

      xNew = rotateX2D(xNorm, yNorm, sin, cos) + centerOfRotation.xInPixels
      yNew = rotateY2D(xNorm, yNorm, sin, cos) + centerOfRotation.yInPixels
    } yield Pos(xNew, yNew)
  }

  /**
   *
   *
   * @param x
   * @param y
   * @param sinOfRotationAngle
   * @param cosOfRotationAngle
   *
   * @return
   */
  @inline
  def rotateX2D(
      x: Double,
      y: Double,
      sinOfRotationAngle: Double,
      cosOfRotationAngle: Double): Double = {

    println("X-koordinaatti:")
    println(s"  x: $x, y: $y, sin: $sinOfRotationAngle, cos: $cosOfRotationAngle")
    val result = cosOfRotationAngle * x + sinOfRotationAngle * y
    println(s"tulos: $result")

    result
  }

  /**
   *
   *
   * @param x
   * @param y
   * @param sinOfRotationAngle
   * @param cosOfRotationAngle
   *
   * @return
   */
  @inline
  def rotateY2D(
      x: Double,
      y: Double,
      sinOfRotationAngle: Double,
      cosOfRotationAngle: Double): Double = {

    println("Y-koordinaatti:")
    println(s"  x: $x, y: $y, sin: $sinOfRotationAngle, cos: $cosOfRotationAngle")
    val result = -sinOfRotationAngle * x + cosOfRotationAngle * y
    println(s"tulos: $result")

    result
  }

}
