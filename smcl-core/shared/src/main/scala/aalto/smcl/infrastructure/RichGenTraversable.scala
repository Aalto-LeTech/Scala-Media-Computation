package aalto.smcl.infrastructure

import scala.collection.GenTraversable
import scala.language.higherKinds




/**
 *
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
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

}
