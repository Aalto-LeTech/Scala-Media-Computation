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
  @inline
  protected
  def value: Double

  /** */
  @inline
  def isZero: Boolean = value == 0.0

  /** */
  @inline
  def isPositive: Boolean = value > 0.0

  /** */
  @inline
  def isNegative: Boolean = value < 0.0

  /** */
  @inline
  def isInfinity: Boolean = value.isInfinity

  /** */
  @inline
  def isPosInfinity: Boolean = value.isPosInfinity

  /** */
  @inline
  def isNegInfinity: Boolean = value.isNegInfinity

  /** */
  @inline
  def isNaN: Boolean = value.isNaN

  /** */
  @inline
  def isWholeNumber: Boolean = value.isWhole

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  override
  def toTuple: Tuple1[Double] = {
    Tuple1(value)
  }

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
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
  override
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
  @inline
  override
  def canEqual(other: Any): Boolean

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
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
  @inline
  def >=[E <: Magnitude[E]] (other: Double): Boolean = {
    this.value >= other
  }

}