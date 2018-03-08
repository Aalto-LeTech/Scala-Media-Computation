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

package smcl.pictures.filters


import smcl.pictures.PictureElement




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ToGrayscaleByLuminocity
    extends Filter {

  /** */
  val StandardRedWeight: Double = 0.21

  /** */
  val StandardGreenWeight: Double = 0.72

  /** */
  val StandardBlueWeight: Double = 0.07

  /**
   *
   *
   * @param target
   *
   * @return
   */
  @inline
  override
  def apply(target: PictureElement): PictureElement = {
    target.toBitmapCopy.translateColorsWith{(red, green, blue, opacity) =>
      val intensity = (
          StandardRedWeight * red +
              StandardGreenWeight * green +
              StandardBlueWeight * blue).toInt

      (intensity, intensity, intensity, opacity)
    }
  }

}