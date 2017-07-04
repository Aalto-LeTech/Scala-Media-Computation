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

package aalto.smcl.infrastructure


import scala.collection.GenTraversable
import scala.language.higherKinds




/**
 *
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 * @author Juha Sorva
 */
//noinspection TypeParameterShadow
private[smcl]
class RichGenTraversable[E, C[E] <: GenTraversable[E]](val self: C[E]) {

  /** Returns the given collection if this one is empty. */
  final def orIfEmpty(other: C[E]): C[E] = if (self.isEmpty) other else self

  /** Executes a function literal if this collection is empty. */
  final def orForEmpty(f: => Unit): C[E] = {
    if (self.isEmpty) f
    self
  }

  /**
   *
   *
   * @param formKey
   * @param formValue
   * @tparam Key
   * @tparam Value
   *
   * @return
   */
  def mapify[Key, Value](
      formKey: E => Key)(
      formValue: E => Value): Map[Key, Value] = {

    self.map(elem => formKey(elem) -> formValue(elem))(collection.breakOut)
  }

}
