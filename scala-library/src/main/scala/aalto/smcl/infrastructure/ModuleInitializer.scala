package aalto.smcl.infrastructure


import scala.collection.mutable

import aalto.smcl.infrastructure.ModuleInitializationPhase._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] trait ModuleInitializer {

  /** */
  private[this] val _startedInitializationPhases: mutable.Set[ModuleInitializationPhase] =
    mutable.Set[ModuleInitializationPhase]()

  /** */
  private[this] val _earlyInitializers: mutable.ArrayBuffer[(() => Unit)] =
    new mutable.ArrayBuffer[(() => Unit)]()

  /** */
  private[this] val _lateInitializers: mutable.ArrayBuffer[(() => Unit)] =
    new mutable.ArrayBuffer[(() => Unit)]()

  /**
   *
   *
   * @param phase
   */
  def performInitialization(phase: ModuleInitializationPhase): Unit = {
    if (!_startedInitializationPhases.contains(phase)) {
      _startedInitializationPhases += phase

      var initializers: mutable.ArrayBuffer[(() => Unit)] = null

      phase match {
        case Early => initializers = _earlyInitializers
        case Late  => initializers = _lateInitializers
      }

      initializers foreach {f =>
        if (f != null) {
          f()
        }
      }
    }
  }

  /**
   *
   *
   * @param initializer
   * @param phase
   */
  def addInitializer(
    phase: ModuleInitializationPhase)(
    initializer: () => Unit): Unit = {

    if (_startedInitializationPhases.contains(phase)) {
      throw new IllegalStateException(
        "New initializers cannot be added to the specified phase, because initialization of " +
          "the phase has already been triggered for this module.")
    }

    phase match {
      case Early => _earlyInitializers += initializer
      case Late  => _lateInitializers += initializer
    }
  }

}
