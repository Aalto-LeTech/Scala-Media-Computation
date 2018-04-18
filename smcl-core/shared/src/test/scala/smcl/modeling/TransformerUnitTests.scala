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

package smcl.modeling


import org.scalatest.DoNotDiscover

import smcl.infrastructure.tests.SharedUnitSpecBase




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@DoNotDiscover
class TransformerUnitTests
    extends SharedUnitSpecBase {

  val original = Pos(5, 5)

  "Transformer must be able to" - {
    "scale a point's distance from the Y axis" in {
      val factor = 2

      val scaled = Transformer.scaleHorizontally(
        position = original,
        scalingFactor = factor)

      assert(scaled.xInPixels == factor * original.xInPixels, ", so the scaling is incorrect")
      assert(scaled.yInPixels == original.yInPixels, " but Y coordinate must not change")
    }

    "scale a point's distance from the X axis" in {
      val factor = 2

      val scaled = Transformer.scaleVertically(
        position = original,
        scalingFactor = factor)

      assert(scaled.xInPixels == original.xInPixels, " but X coordinate must not change")
      assert(scaled.yInPixels == factor * original.yInPixels, ", so the scaling is incorrect")
    }

    "scale a point in relation to the origo using a single factor" in {
      val factor = 3

      val scaled = Transformer.scale(
        position = original,
        scalingFactor = factor)

      assert(
        scaled.xInPixels == factor * original.xInPixels,
        ", so the horizontal scaling is incorrect")

      assert(
        scaled.yInPixels == factor * original.yInPixels,
        ", so the vertical scaling is incorrect")
    }

    "scale a point in relation to the origo using both horizontal and vertical factors" in {
      val (xFactor, yFactor) = (4, 5)

      val scaled = Transformer.scale(
        position = original,
        horizontalScalingFactor = xFactor,
        verticalScalingFactor = yFactor)

      assert(
        scaled.xInPixels == xFactor * original.xInPixels,
        ", so the horizontal scaling is incorrect")

      assert(
        scaled.yInPixels == yFactor * original.yInPixels,
        ", so the vertical scaling is incorrect")
    }
  }

}
