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

package aalto.smcl.infrastructure


import aalto.smcl.modeling.d2.Dims




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class DefaultScreen(private val _informationProvider: ScreenInformationProvider) {

  /**
   * Dimensions of the screen.
   *
   * @return
   */
  def dimensionsInPixels: Dims = _informationProvider.dimensionsInPixels

  /**
   *
   *
   * @return
   */
  def resolutionInDotsPerInch: Int = _informationProvider.resolutionInDotsPerInch

  /**
   *
   *
   * @return
   */
  def widthInPixels: Double = dimensionsInPixels.widthInPixels

  /**
   * Height of the screen.
   *
   * @return
   */
  def heightInPixels: Double = dimensionsInPixels.heightInPixels

  /**
   * Area of the screen.
   *
   * @return
   */
  def areaInPixels: Double = widthInPixels * heightInPixels

}
