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


/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
object Direction {
/*
  val Up = new Direction(0, -1) {override val toString = "Direction.Up"}

  val Down = new Direction(0, 1) {override val toString = "Direction.Down"}

  val Left = new Direction(-1, 0) {override val toString = "Direction.Left"}

  val Right = new Direction(1, 0) {override val toString = "Direction.Right"}

  val NoDirection = new Direction(0, 0) {override val toString = "(no direction)"}

  def fromRadians(angle: Double) = Direction(cos(angle), -sin(angle))

  def fromDegrees(angle: Double) = fromRadians(angle.toRadians)

  def fromDeltas(dx: Double, dy: Double) = fromRadians(atan2(-dy, dx))

  def random() = {
    Direction.fromDegrees(scala.util.Random.nextInt(360))
  }

  // XXX riippuvuus Swingiin ikävä. siirretään o1.gui-pakkaukseen?
  private type Key = scala.swing.event.Key.Value
  private val Key        = scala.swing.event.Key
  private val ArrowToDir = Map(Key.Up -> Up, Key.Left -> Left, Key.Down -> Down, Key.Right -> Right)
  private val WASDToDir  = Map(Key.W -> Up, Key.A -> Left, Key.S -> Down, Key.D -> Right)
  private val KeyToDir   = ArrowToDir ++ WASDToDir

  def fromArrowKey(key: Key) = ArrowToDir.get(key)

  def fromWASD(key: Key) = WASDToDir.get(key)

  def fromKey(key: Key) = KeyToDir.get(key)
*/
}


/*
/**
 *
 *
 * @param dx
 * @param dy
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
case class Direction private(dx: Double, dy: Double) { // dx*dx+dy*dy ~= 1, or both zero

  def switchY = Direction(dx, -dy)

  def switchX = Direction(-dx, dy)

  def opposite = Direction(-dx, -dy)

  def isRightward = this.dx >= 0

  def isLeftward = this.dx <= 0

  def isDownward = this.dy >= 0

  def isUpward = this.dy <= 0

  def + (another: Direction) = Direction.fromDegrees(this.toDegrees + another.toDegrees)

  def counterclockwise(degrees: Double) = Direction.fromDegrees(this.toDegrees + degrees)

  def clockwise(degrees: Double) = Direction.fromDegrees(this.toDegrees - degrees)

  def sharesQuadrant(another: Direction) = {
    def sameHalf(dx1: Double, dx2: Double) = dx1.signum == dx2.signum || dx1.signum == 0 || dx2.signum == 0

    sameHalf(this.dx, another.dx) && sameHalf(this.dy, another.dy)
  }

  def roughly = f"dx$dx%1.2f,dy$dy%1.2f"

  def toRadians = {
    val atan = atan2(-this.dy, this.dx)
    if (atan < 0) atan + 2 * Pi else atan
  }

  def toDegrees = this.toRadians.toDegrees

}
*/
