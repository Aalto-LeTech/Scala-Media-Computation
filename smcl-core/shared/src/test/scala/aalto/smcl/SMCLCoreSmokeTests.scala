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

package aalto.smcl


import org.scalatest.DoNotDiscover

import aalto.smcl.bitmaps.circle
import aalto.smcl.bitmaps.fullfeatured.Bitmap
import aalto.smcl.colors.LightBlue
import aalto.smcl.colors.rgb.Color
import aalto.smcl.infrastructure.tests.SharedIntegrationSpecBase




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@DoNotDiscover
class SMCLCoreSmokeTests extends SharedIntegrationSpecBase {

  "SMCL must be able to" - {
    "list all settings" in {aalto.smcl.settings.Settings.list()}
    "create a bitmap" in {Bitmap(widthInPixels = 15)}
    "create a circle" in {circle(50)}
    "create a new RGBA color" in {Color(1, 2, 3, 4)}
    "recall a preset RGBA color" in LightBlue
  }

}
