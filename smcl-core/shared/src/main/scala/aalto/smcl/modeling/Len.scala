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


import aalto.smcl.modeling.misc.AbstractMagnitude




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
  def apply(valueInPixels: Double): Len = {
    require(
      valueInPixels >= 0,
      s"Length cannot be negative (was $valueInPixels)")

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
    extends AbstractMagnitude[Len](inPixels) {

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
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
  @inline
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
  def center: d1.Pos = d1.Pos(inPixels / 2.0)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
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
  def / (divider: Double): Len = {
    Len(inPixels / divider)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def square: Area = Area(inPixels * inPixels)

  /**
   *
   *
   * @return
   */
  @inline
  def cube: Vol = Vol(inPixels * inPixels * inPixels)

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  @inline
  override
  def min(others: Len*): Len = (this +: others).min

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  @inline
  override
  def max(others: Len*): Len = (this +: others).max

}
