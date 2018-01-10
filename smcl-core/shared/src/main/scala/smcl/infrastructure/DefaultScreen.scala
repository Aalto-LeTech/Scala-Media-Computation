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

package smcl.infrastructure


import smcl.modeling.d2.Dims
import smcl.modeling.{Area, Len}




/**
 *
 *
 * @param _informationProvider
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class DefaultScreen(
    private val _informationProvider: ScreenInformationProvider) {

  /**
   * Dimensions of the screen.
   *
   * @return
   */
  def dimensions: Dims = _informationProvider.dimensions

  /**
   *
   *
   * @return
   */
  def resolutionInDotsPerInch: Int =
    _informationProvider.resolutionInDotsPerInch

  /**
   *
   *
   * @return
   */
  def width: Len = dimensions.width

  /**
   * Height of the screen.
   *
   * @return
   */
  def height: Len = dimensions.height

  /**
   * Area of the screen.
   *
   * @return
   */
  def area: Area = Area.forRectangle(width, height)

}
