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
object Velocity {
/*
  val Still = Velocity(Direction.NoDirection, 0)

  def apply(vector: Pos): Velocity = Velocity(vector.x, vector.y)

  def apply(dx: Double, dy: Double): Velocity = Velocity(Direction.fromDeltas(dx, dy), hypot(dx, dy))

  def between(from: Pos, to: Pos): Velocity = Velocity(to - from)

  def sum(velocities: Seq[Velocity]) = velocities.reduceLeftOption(_ + _) getOrElse Velocity.Still

  def average(velocities: Seq[Velocity]) = if (velocities.nonEmpty) sum(velocities) / velocities.size else Velocity.Still
*/
}




/*
/**
 *
 *
 * @param direction
 * @param speed
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
case class Velocity(
    direction: Direction,
    speed: Double) {

  lazy val dx = this.direction.dx * speed
  lazy val dy = this.direction.dy * speed

  def switchY = this.copy(direction = this.direction.switchY)

  def switchX = this.copy(direction = this.direction.switchX)

  def noFasterThan(maxSpeed: Double) = this.copy(speed = this.speed atMost maxSpeed)

  def noSlowerThan(minSpeed: Double) = this.copy(speed = this.speed atLeast minSpeed)

  def unary_- = this.copy(direction = this.direction.opposite)

  def faster(addition: Double) = Velocity(this.direction, this.speed + addition)

  def slower(reduction: Double) = Velocity(this.direction, this.speed - reduction).noSlowerThan(0)

  def + (another: Velocity) = Velocity(this.dx + another.dx, this.dy + another.dy)

  def - (another: Velocity) = this + -another

  def * (multiplier: Double) = this.copy(speed = this.speed * multiplier)

  def / (divisor: Double) = this * (1 / divisor)

  def roughly = f"$speed%1.2f towards ${direction.roughly}"

  def toPos = Pos(this.dx, this.dy)

  def moveFrom(position: Pos) = position + this.toPos // XXX voisi olla pikemminkin/lisäksi Pos-luokassa

  def changeDirection(newDirection: Direction) = this.copy(direction = newDirection)

  def changeSpeed(newSpeed: Double) = this.copy(speed = newSpeed)

  def stopped = this.changeSpeed(0)

  def counterclockwise(degrees: Double) = this.changeDirection(this.direction.counterclockwise(degrees))

  def clockwise(degrees: Double) = this.changeDirection(this.direction.clockwise(degrees))

}
*/
