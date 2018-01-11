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

package smcl.modeling.d2.simplified


import smcl.infrastructure._




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
trait HasEdges extends HasPosition with HasAnchor {

  def anchor: Anchor = Anchor.Center

  def positionXInside(container: HasEdges, desiredPosition: Pos = this.position): Pos =
    desiredPosition.clampX(container.left + this.internalAnchorX, container.right + this.internalAnchorX - this.width)

  def positionYInside(container: HasEdges, desiredPosition: Pos = this.position): Pos =
    desiredPosition.clampY(container.top + this.internalAnchorY, container.bottom + this.internalAnchorY - this.height)

  def positionInside(container: HasEdges, desiredPosition: Pos = this.position): Pos = {
    val anchorPos = this.internalAnchorPos
    desiredPosition.clamp(xMin = container.left + anchorPos.x, xMax = container.right + anchorPos.x - this.width,
      yMin = container.top + anchorPos.y, yMax = container.bottom + anchorPos.y - this.height)
  }

  def closestPosTo(target: Pos): Pos = target.clamp(this.left, this.right, this.top, this.bottom)

  def left: Double = this.position.x - this.internalAnchorX

  def top: Double = this.position.y - this.internalAnchorY

  def right: Double = this.left + this.width

  def bottom: Double = this.top + this.height

  def topLeft: Pos = Pos(this.left, this.top)

  def bottomLeft: Pos = Pos(this.left, this.bottom)

  def topRight: Pos = Pos(this.right, this.top)

  def bottomRight: Pos = Pos(this.right, this.bottom)

  def center: Pos = this.topLeft + this.centerFromTopLeft

  def containsBetweenEdges(candidate: Pos): Boolean =
    candidate.x.isBetween(this.left, this.right) &&
        candidate.y.isBetween(this.top, this.bottom)

}


