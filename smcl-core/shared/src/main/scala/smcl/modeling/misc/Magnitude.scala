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

package smcl.modeling.misc


import smcl.infrastructure.{CommonDoubleMathOps, FlatMap, ItemItemMap, MinMaxOps, ToTuple}




/**
 * A magnitude, such as length, area, and volume.
 *
 * @author Aleksi Lukkarinen
 */
trait Magnitude[ElementType]
    extends Measurement
        with Equals
        with Ordered[ElementType]
        with ToTuple[Tuple1[Double]]
        with FlatMap[ElementType, Double]
        with ItemItemMap[ElementType, Double]
        with CommonDoubleMathOps[ElementType]
        with MinMaxOps[ElementType] {

  /**
   *
   *
   * @return
   */
  protected
  def value: Double

  /** */
  @inline
  final
  def isZero: Boolean = value == 0.0

  /** */
  @inline
  final
  def isPositive: Boolean = value > 0.0

  /** */
  @inline
  final
  def isNegative: Boolean = value < 0.0

  /** */
  @inline
  final
  def isInfinity: Boolean = value.isInfinity

  /** */
  @inline
  final
  def isPosInfinity: Boolean = value.isPosInfinity

  /** */
  @inline
  final
  def isNegInfinity: Boolean = value.isNegInfinity

  /** */
  @inline
  final
  def isNaN: Boolean = value.isNaN

  /** */
  @inline
  final
  def isWholeNumber: Boolean = value.isWhole

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  override final
  def toTuple: Tuple1[Double] = {
    Tuple1(value)
  }

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline final
  def toIntTuple: Tuple1[Int] = {
    Tuple1(value.toInt)
  }

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override final
  def flatMap(f: (Double) => ElementType): ElementType = {
    f(value)
  }

  /**
   *
   *
   * @return
   */
  override
  lazy val hashCode: Int = {
    val prime = 31

    prime + value.##
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  override
  def canEqual(other: Any): Boolean

  /**
   *
   *
   * @param other
   *
   * @return
   */
  override
  def equals(other: Any): Boolean = {
    other match {
      case that: Magnitude[ElementType] =>
        that.canEqual(this) &&
            that.value == this.value

      case _ => false
    }
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isBetweenExclExcl[E <: Magnitude[E]](
      rangeStart: E,
      rangeEnd: E): Boolean = {

    rangeStart.value < this.value &&
        this.value < rangeEnd.value
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isAbsBetweenExclExcl[E <: Magnitude[E]](
      rangeStart: E,
      rangeEnd: E): Boolean = {

    val a = math.abs(value)

    rangeStart.value < a && a < rangeEnd.value
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isBetweenExclExcl(
      rangeStart: Double,
      rangeEnd: Double): Boolean = {

    rangeStart < this.value &&
        this.value < rangeEnd
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isAbsBetweenExclExcl(
      rangeStart: Double,
      rangeEnd: Double): Boolean = {

    val a = math.abs(value)

    rangeStart < a && a < rangeEnd
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isBetweenExclIncl[E <: Magnitude[E]](
      rangeStart: E,
      rangeEnd: E): Boolean = {

    rangeStart.value < this.value &&
        this.value <= rangeEnd.value
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isAbsBetweenExclIncl[E <: Magnitude[E]](
      rangeStart: E,
      rangeEnd: E): Boolean = {

    val a = math.abs(value)

    rangeStart.value < a && a <= rangeEnd.value
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isBetweenExclIncl(
      rangeStart: Double,
      rangeEnd: Double): Boolean = {

    rangeStart < this.value &&
        this.value <= rangeEnd
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isAbsBetweenExclIncl(
      rangeStart: Double,
      rangeEnd: Double): Boolean = {

    val a = math.abs(value)

    rangeStart < a && a <= rangeEnd
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isBetweenInclExcl[E <: Magnitude[E]](
      rangeStart: E,
      rangeEnd: E): Boolean = {

    rangeStart.value <= this.value &&
        this.value < rangeEnd.value
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isAbsBetweenInclExcl[E <: Magnitude[E]](
      rangeStart: E,
      rangeEnd: E): Boolean = {

    val a = math.abs(value)

    rangeStart.value <= a && a < rangeEnd.value
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isBetweenInclExcl(
      rangeStart: Double,
      rangeEnd: Double): Boolean = {

    rangeStart <= this.value &&
        this.value < rangeEnd
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isAbsBetweenInclExcl(
      rangeStart: Double,
      rangeEnd: Double): Boolean = {

    val a = math.abs(value)

    rangeStart <= a && a < rangeEnd
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isBetweenInclIncl[E <: Magnitude[E]](
      rangeStart: E,
      rangeEnd: E): Boolean = {

    rangeStart.value <= this.value &&
        this.value <= rangeEnd.value
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isAbsBetweenInclIncl[E <: Magnitude[E]](
      rangeStart: E,
      rangeEnd: E): Boolean = {

    val a = math.abs(value)

    rangeStart.value <= a && a <= rangeEnd.value
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isBetweenInclIncl(
      rangeStart: Double,
      rangeEnd: Double): Boolean = {

    rangeStart <= this.value &&
        this.value <= rangeEnd
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isAbsBetweenInclIncl(
      rangeStart: Double,
      rangeEnd: Double): Boolean = {

    val a = math.abs(value)

    rangeStart <= a && a <= rangeEnd
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def <[E <: Magnitude[E]] (other: Double): Boolean = {
    this.value < other
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def <=[E <: Magnitude[E]] (other: Double): Boolean = {
    this.value <= other
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def >[E <: Magnitude[E]] (other: Double): Boolean = {
    this.value > other
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def >=[E <: Magnitude[E]] (other: Double): Boolean = {
    this.value >= other
  }

}
