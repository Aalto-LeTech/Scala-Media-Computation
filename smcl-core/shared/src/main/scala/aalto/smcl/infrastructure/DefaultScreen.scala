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


import aalto.smcl.geometry.Dimension




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
  def dimensionsInPixels: Dimension = _informationProvider.dimensionsInPixels

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
  def widthInPixels: Int = dimensionsInPixels.widthInPixels

  /**
   * Height of the screen.
   *
   * @return
   */
  def heightInPixels: Int = dimensionsInPixels.heightInPixels

  /**
   * Area of the screen.
   *
   * @return
   */
  def areaInPixels: Int = widthInPixels * heightInPixels

}
