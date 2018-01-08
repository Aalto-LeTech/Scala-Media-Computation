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

package smcl


import org.scalatest.DoNotDiscover
import smcl.bitmaps.circle
import smcl.bitmaps.fullfeatured.Bitmap
import smcl.colors.LightBlue
import smcl.colors.rgb.Color
import smcl.infrastructure.tests.SharedIntegrationSpecBase




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@DoNotDiscover
class SMCLCoreSmokeTests extends SharedIntegrationSpecBase {

  "SMCL must be able to" - {
    "list all settings" in {smcl.settings.Settings.list()}
    "create a bitmap" in {Bitmap(widthInPixels = 15)}
    "create a circle" in {circle(50)}
    "create a new RGBA color" in {Color(1, 2, 3, 4)}
    "recall a preset RGBA color" in LightBlue
  }

}
