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

package smcl.modeling


import smcl.modeling.misc.Magnitude




/**
 * Companion object for the [[Len]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Len {

  /** */
  lazy val Zero = Len(0.0)

  /** */
  lazy val One = Len(1.0)

  /** */
  lazy val PositiveInfinity = Len(Double.PositiveInfinity)

  /** */
  lazy val NegativeInfinity = Len(Double.NegativeInfinity)

  /** */
  lazy val NaN = Len(Double.NaN)

  /**
   * Creates a new [[Len]] instance.
   *
   * @param valueInPixels
   *
   * @return
   */
  @inline
  def apply(valueInPixels: Double): Len = {
    new Len(valueInPixels)
  }

}




/**
 * Length of an object.
 *
 * @param inPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Len private(inPixels: Double)
    extends Magnitude[Len] {

  /**
   *
   *
   * @return
   */
  @inline
  protected final
  def value: Double = inPixels

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def map(f: (Double) => Double): Len = {
    Len(f(inPixels))
  }

  /**
   *
   *
   * @param that
   *
   * @return
   */
  override
  def compare(that: Len): Int = {
    inPixels.compare(that.inPixels)
  }

  /**
   *
   *
   * @return
   */
  @inline
  final
  def center: d1.Pos = d1.Pos(inPixels / 2.0)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def + (offset: Len): Len = {
    Len(inPixels + offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def - (offset: Len): Len = {
    Len(inPixels - offset.inPixels)
  }

  /**
   *
   *
   * @param factor
   *
   * @return
   */
  @inline
  final
  def * (factor: Len): Area = {
    Area(inPixels * factor.inPixels)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  @inline
  final
  def / (divider: Len): Double = {
    inPixels / divider.inPixels
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def + (offset: Double): Len = {
    Len(inPixels + offset)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def - (offset: Double): Len = {
    Len(inPixels - offset)
  }

  /**
   *
   *
   * @param factor
   *
   * @return
   */
  @inline
  final
  def * (factor: Double): Len = {
    Len(inPixels * factor)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  @inline
  final
  def / (divider: Double): Len = {
    Len(inPixels / divider)
  }

  /**
   *
   *
   * @return
   */
  @inline
  final
  def square: Area = Area(inPixels * inPixels)

  /**
   *
   *
   * @return
   */
  @inline
  final
  def cube: Vol = Vol(inPixels * inPixels * inPixels)

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  @inline
  override final
  def min(others: Len*): Len = (this +: others).min

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  @inline
  override final
  def max(others: Len*): Len = (this +: others).max

  /**
   *
   *
   * @return
   */
  @inline
  final
  def toDims: d1.Dims = d1.Dims(inPixels)

  /**
   *
   *
   * @return
   */
  @inline
  final
  def toDimsWith(height: Len): d2.Dims = {
    d2.Dims(inPixels, height.inPixels)
  }

  /**
   *
   *
   * @return
   */
  @inline
  final
  def toDimsWith(height: Len, depth: Len): d3.Dims = {
    d3.Dims(inPixels, height.inPixels, depth.inPixels)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def toString: String = {
    s"Len($inPixels px)"
  }

}
