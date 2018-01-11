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


import java.awt.Point

import scala.math.hypot

import smcl.infrastructure._




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
object Pos {

  /**
   *
   *
   * @param point
   *
   * @return
   */
  def apply(point: Point) =
    new Pos(point.getX, point.getY)

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
  def xInt: Int = x.floor.toInt

  /**
   *
   *
   * @return
   */
  def yInt: Int = this.y.floor.toInt

  /**
   *
   *
   * @param yMax
   *
   * @return
   */
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
  def clamp(xMin: Double, xMax: Double, yMin: Double, yMax: Double): Pos =
    Pos(
      x atLeast xMin atMost xMax,
      y atLeast yMin atMost yMax)

  def offset(dx: Double, dy: Double): Pos = this.add(dx, dy)

  def offset(another: Pos): Pos = this.add(another)

  def add(dx: Double, dy: Double): Pos = Pos(this.x + dx, this.y + dy)

  def add(pos: Pos): Pos = this.add(pos.x, pos.y)

  def unary_- : Pos = Pos(-this.x, -this.y)

  def xDiff(another: Pos): Double = another.x - this.x

  def yDiff(another: Pos): Double = another.y - this.y

  def + (another: Pos): Pos = this.add(another)

  def - (another: Pos): Pos = this + -another

  def * (factor: Double): Pos = this.multiply(factor)

  def / (divisor: Double): Pos = this.divide(divisor)

  def multiply(factor: Double): Pos = Pos(this.x * factor, this.y * factor)

  def divide(divisor: Double): Pos = Pos(this.x / divisor, this.y / divisor)

  def setX(newX: Double): Pos = this.copy(x = newX)

  def setY(newY: Double): Pos = this.copy(y = newY)

  def addX(dx: Double): Pos = this.setX(this.x + dx)

  def addY(dy: Double): Pos = this.setY(this.y + dy)

  def subtract(another: Pos): Pos = this + -another

  def subtractX(dx: Double): Pos = this.setX(this.x - dx)

  def subtractY(dy: Double): Pos = this.setY(this.y - dy)

  def withXY(computeNew: (Double, Double) => (Double, Double)): Pos = computeNew(this.x, this.y) match {case (newX, newY) => Pos(newX, newY)}

  def withX(computeNew: Double => Double): Pos = this.setX(computeNew(this.x))

  def withY(computeNew: Double => Double): Pos = this.setY(computeNew(this.y))

  //def directionOf(destination: Pos) = Direction.fromDeltas(this.xDiff(destination), this.yDiff(destination))

  def vectorTo(destination: Pos): Pos = destination - this

  def distance(another: Pos): Double = hypot(this.xDiff(another), this.yDiff(another))

  def roughly: String = f"$x%1.2f,$y%1.2f"

  override
  def toString: String = s"($x,$y)"

}
