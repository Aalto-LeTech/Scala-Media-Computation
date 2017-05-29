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

package aalto.smcl.colors.rgb


/**
 * Constants for RGBA sample bands.
 *
 * @author Aleksi Lukkarinen
 */
object SampleBands {




  /**
   * Base class for RGBA sample band constants.
   */
  sealed abstract class ColorSampleBand(val ordinal: Int)




  /**
   * A constant representing the sample band for red color.
   */
  case object Red extends ColorSampleBand(ordinal = 0)




  /**
   * A constant representing the sample band for green color.
   */
  case object Green extends ColorSampleBand(ordinal = 1)




  /**
   * A constant representing the sample band for blue color.
   */
  case object Blue extends ColorSampleBand(ordinal = 2)




  /**
   * A constant representing the sample band for opacity.
   */
  case object Opacity extends ColorSampleBand(ordinal = 3)




}
