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


/**
 * Companion object for the [[Len]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Len {

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
case class Len private(
    inPixels: Double)
    extends AbstractMagnitude[Len](inPixels) {

  /**
   *
   *
   * @param f
   *
   * @return
   */
  override
  def map(f: (Double) => Double): Len = {
    Len(f(inPixels))
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
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
  def / (divider: Double): Len = {
    Len(inPixels / divider)
  }

}
