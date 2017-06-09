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

package aalto.smcl.colors.metadata


import aalto.smcl.bitmaps.fullfeatured
import aalto.smcl.colors.rgb.Color
import aalto.smcl.settings.{ColorVisualizationTileSideLengthInPixels, PreventViewerUpdates}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object ColorTileCreator {

  /**
   *
   * @return
   */
  def createSingleColorTile(
      colorToIllustrate: Color): fullfeatured.Bitmap = {

    fullfeatured.Bitmap(
      widthInPixels = ColorVisualizationTileSideLengthInPixels,
      heightInPixels = ColorVisualizationTileSideLengthInPixels,
      viewerHandling = PreventViewerUpdates) map {tile =>

      tile.drawRectangle(
        0,
        0,
        tile.widthInPixels - 1,
        tile.heightInPixels - 1,
        hasBorder = false,
        hasFilling = true,
        fillColor = colorToIllustrate,
        viewerHandling = PreventViewerUpdates)
    }
  }

  /**
   *
   *
   * @param backgroundColor
   *
   * @return
   */
  def createDoubleColorTile(
      colorToIllustrate: Color,
      backgroundColor: Color): fullfeatured.Bitmap = {

    fullfeatured.Bitmap(
      widthInPixels = ColorVisualizationTileSideLengthInPixels,
      heightInPixels = ColorVisualizationTileSideLengthInPixels,
      viewerHandling = PreventViewerUpdates) map {tile =>

      tile.drawRectangle(
        0,
        0,
        tile.widthInPixels,
        tile.heightInPixels,
        hasBorder = false,
        hasFilling = true,
        fillColor = backgroundColor,
        viewerHandling = PreventViewerUpdates) map {tile =>

        tile.drawRectangle(
          upperLeftCornerXInPixels = (0.25 * tile.widthInPixels.toDouble).toInt,
          upperLeftCornerYInPixels = (0.25 * tile.heightInPixels.toDouble).toInt,
          widthInPixels = (0.5 * tile.widthInPixels.toDouble).toInt,
          heightInPixels = (0.5 * tile.heightInPixels.toDouble).toInt,
          hasBorder = false,
          hasFilling = true,
          fillColor = colorToIllustrate,
          viewerHandling = PreventViewerUpdates)
      }
    }
  }

}
