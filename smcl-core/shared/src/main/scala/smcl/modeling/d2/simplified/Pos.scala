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


package smcl.modeling.d2.simplified


import scala.math.hypot

import smcl.infrastructure._




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
object Pos {
/*
  /**
   *
   *
   * @param point
   *
   * @return
   */
  def apply(point: Point) =
    new Pos(point.getX, point.getY)
*/
}




/**
 *
 *
 * @param x
 * @param y
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
case class Pos(
    x: Double,
    y: Double) { // XXX pos vai position; konsistenssi

  /**
   *
   *
   * @return
   */
  @inline
  def xInt: Int = x.floor.toInt

  /**
   *
   *
   * @return
   */
  @inline
  def yInt: Int = y.floor.toInt

  /**
   *
   *
   * @param yMax
   *
   * @return
   */
  @inline
  def noLowerThan(yMax: Double): Pos =
    Pos(
      x,
      y atMost yMax)

  /**
   *
   *
   * @param yMin
   *
   * @return
   */
  @inline
  def noHigherThan(yMin: Double): Pos =
    Pos(
      x,
      y atLeast yMin)

  /**
   *
   *
   * @param xMin
   *
   * @return
   */
  @inline
  def noFurtherLeftThan(xMin: Double): Pos =
    Pos(
      x atLeast xMin,
      y)

  /**
   *
   *
   * @param xMax
   *
   * @return
   */
  @inline
  def noFurtherRightThan(xMax: Double): Pos =
    Pos(
      x atMost xMax,
      y)

  /**
   *
   *
   * @param min
   * @param max
   *
   * @return
   */
  @inline
  def clampX(min: Double, max: Double): Pos =
    Pos(
      x atLeast min atMost max,
      y)

  /**
   *
   *
   * @param min
   * @param max
   *
   * @return
   */
  @inline
  def clampY(min: Double, max: Double): Pos =
    Pos(
      x,
      y atLeast min atMost max)

  /**
   *
   *
   * @param xMin
   * @param xMax
   * @param yMin
   * @param yMax
   *
   * @return
   */
  @inline
  def clamp(xMin: Double, xMax: Double, yMin: Double, yMax: Double): Pos =
    Pos(
      x atLeast xMin atMost xMax,
      y atLeast yMin atMost yMax)

  /**
   *
   *
   * @param dx
   * @param dy
   *
   * @return
   */
  @inline
  def offset(dx: Double, dy: Double): Pos = add(dx, dy)

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def offset(another: Pos): Pos = add(another)

  /**
   *
   *
   * @param dx
   * @param dy
   *
   * @return
   */
  @inline
  def add(dx: Double, dy: Double): Pos = Pos(x + dx, y + dy)

  /**
   *
   *
   * @param pos
   *
   * @return
   */
  @inline
  def add(pos: Pos): Pos = add(pos.x, pos.y)

  /**
   *
   *
   * @return
   */
  @inline
  def unary_- : Pos = Pos(-x, -y)

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def xDiff(another: Pos): Double = another.x - x

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def yDiff(another: Pos): Double = another.y - y

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def + (another: Pos): Pos = add(another)

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def - (another: Pos): Pos = this + -another

  /**
   *
   *
   * @param factor
   *
   * @return
   */
  @inline
  def * (factor: Double): Pos = multiply(factor)

  /**
   *
   *
   * @param divisor
   *
   * @return
   */
  @inline
  def / (divisor: Double): Pos = divide(divisor)

  /**
   *
   *
   * @param factor
   *
   * @return
   */
  @inline
  def multiply(factor: Double): Pos =
    Pos(x * factor, y * factor)

  /**
   *
   *
   * @param divisor
   *
   * @return
   */
  @inline
  def divide(divisor: Double): Pos =
    Pos(x / divisor, y / divisor)

  /**
   *
   *
   * @param newX
   *
   * @return
   */
  @inline
  def setX(newX: Double): Pos = copy(x = newX)

  /**
   *
   *
   * @param newY
   *
   * @return
   */
  @inline
  def setY(newY: Double): Pos = copy(y = newY)

  /**
   *
   *
   * @param dx
   *
   * @return
   */
  @inline
  def addX(dx: Double): Pos = setX(x + dx)

  /**
   *
   *
   * @param dy
   *
   * @return
   */
  @inline
  def addY(dy: Double): Pos = setY(y + dy)

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def subtract(another: Pos): Pos = this + -another

  /**
   *
   *
   * @param dx
   *
   * @return
   */
  @inline
  def subtractX(dx: Double): Pos = setX(x - dx)

  /**
   *
   *
   * @param dy
   *
   * @return
   */
  @inline
  def subtractY(dy: Double): Pos = setY(y - dy)

  /**
   *
   *
   * @param computeNew
   *
   * @return
   */
  @inline
  def withXY(computeNew: (Double, Double) => (Double, Double)): Pos =
    computeNew(x, y) match {
      case (newX, newY) => Pos(newX, newY)
    }

  /**
   *
   *
   * @param computeNew
   *
   * @return
   */
  @inline
  def withX(computeNew: Double => Double): Pos =
    setX(computeNew(x))

  /**
   *
   *
   * @param computeNew
   *
   * @return
   */
  @inline
  def withY(computeNew: Double => Double): Pos =
    setY(computeNew(y))

  /*
  /**
   *
   *
   * @param destination
   *
   * @return
   */
  def directionOf(destination: Pos) =
   Direction.fromDeltas(this.xDiff(destination), this.yDiff(destination))
  */

  /**
   *
   *
   * @param destination
   *
   * @return
   */
  @inline
  def vectorTo(destination: Pos): Pos = destination - this

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def distance(another: Pos): Double =
    hypot(xDiff(another), yDiff(another))

  /**
   *
   *
   * @return
   */
  @inline
  def roughly: String = f"$x%1.2f,$y%1.2f"

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = s"($x,$y)"

}
