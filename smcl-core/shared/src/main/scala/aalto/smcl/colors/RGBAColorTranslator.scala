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

package aalto.smcl.colors




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait RGBAColorTranslator {

  /**
   *
   *
   * @param color
   *
   * @return
   */
  def translate(color: RGBAColor): RGBAColor =
    RGBAColor(translate(rgbaTupleFrom(color)))

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  def translate(argbInt: Int): Int =
    argbIntFrom(translate(rgbaTupleFrom(argbInt)))

  /**
   *
   *
   * @param rgbaTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def translate(rgbaTuple: (Int, Int, Int, Int)): (Int, Int, Int, Int) =
  (translate(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   *
   * @return
   */
  def translate(red: Int, green: Int, blue: Int, opacity: Int): (Int, Int, Int, Int)

}
