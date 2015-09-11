package aalto.smcl.infrastructure


import scala.collection.mutable

import aalto.smcl.infrastructure.PackageInitializationPhase._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] trait PackageInitializerBase {

  /** */
  private[this] val _startedInitializationPhases: mutable.Set[PackageInitializationPhase] =
    mutable.Set[PackageInitializationPhase]()

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
  def performInitialization(phase: PackageInitializationPhase): Unit = {
    if (!_startedInitializationPhases.contains(phase)) {
      _startedInitializationPhases += phase

      println(s"${phase.toString.capitalize} init: ${getClass.getName}")

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
      phase: PackageInitializationPhase)(
      initializer: () => Unit): Unit = {

    if (_startedInitializationPhases.contains(phase)) {
      throw new IllegalStateException(
        "New initializers cannot be added to the specified phase, because initialization of " +
            "the phase has already been triggered for this package.")
    }

    phase match {
      case Early => _earlyInitializers += initializer
      case Late  => _lateInitializers += initializer
    }
  }

}
