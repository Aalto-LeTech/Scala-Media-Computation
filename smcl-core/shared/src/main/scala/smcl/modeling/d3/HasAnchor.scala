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

package smcl.modeling.d3


import smcl.modeling.Len




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
trait HasAnchor {

  self: HasDims =>


  /** */
  def anchor: Anchor[HasAnchor]

  /** */
  def width: Len = self.width

  /** */
  def height: Len = self.height

  /** */
  def depth: Len = self.depth

  /**
   *
   *
   * @return
   */
  def internalAnchorPos: Pos = {
    anchor.internalPosWithin(self)
  }

  /**
   *
   *
   * @return
   */
  def internalAnchorDims: Dims = {
    anchor.internalDimsWithin(self)
  }

  /**
   *
   *
   * @return
   */
  def internalAnchorX: Double = {
    anchor.internalXWithin(self)
  }

  /**
   *
   *
   * @return
   */
  def internalAnchorY: Double = {
    anchor.internalYWithin(self)
  }

  /**
   *
   *
   * @return
   */
  def internalAnchorZ: Double = {
    anchor.internalZWithin(self)
  }

}
