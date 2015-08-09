package aalto.smcl


import scala.collection.mutable




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] trait ModuleInitializer {

  /** */
  private[this] var _isInitialized: Boolean = false

  /** */
  private[this] val _initializers: mutable.ArrayBuffer[(() => Unit)] =
    new mutable.ArrayBuffer[(() => Unit)]()

  /**
   *
   */
  def performInitialization(): Unit = {
    if (!_isInitialized) {
      _isInitialized = true

      _initializers.foreach { f =>
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
   */
  def addInitializer(initializer: () => Unit): Unit = {
    if (_isInitialized) {
      throw new IllegalStateException(
        "New initializers cannot be added, because this module initializer has already been triggered.")
    }

    _initializers += initializer
  }

}
