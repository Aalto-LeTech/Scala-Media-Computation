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


import scala.language.implicitConversions

import smcl.modeling.Angle
import smcl.modeling.d2.Pos


/**
  *
  * @author Aleksi Lukkarinen
  */
package object pictures {

  /** */
  val DefaultPosition: Pos = Pos.Origo

  /** */
  val DefaultRotationAngle: Angle = Angle.Zero

  /** */
  val DefaultRotationAngleInDegrees: Double = DefaultRotationAngle.inDegrees

  /** */
  val IdentityScalingFactor: Double = 1.0




  /** Base class for identifying edges. */
  sealed abstract class Side




  /** A constant for identifying a bottom side. */
  case object BottomSide extends Side




  /** A constant for identifying a top side. */
  case object TopSide extends Side




  /** A constant for identifying a left side. */
  case object LeftSide extends Side




  /** A constant for identifying a right side. */
  case object RightSide extends Side




}
