package aalto.smcl.platform


import aalto.smcl.SMCL




/**
 * This object provides a way to mimic Java's resource-related `try` facility. The given resource has to implement
 * Java's `AutoCloseable` interface, which enables this class to ensure that the `close()` method of the reserved
 * resource is being called after the defined work unit is finished.
 *
 * @author Aleksi Lukkarinen
 */
object EnsureClosingOfAfter {

  SMCL.performInitialization()


  /**
   *
   *
   * @param resourceToBeUsed
   * @param workUnit
   * @tparam ResourceType
   * @tparam ReturnType
   * @return
   */
  def apply[ResourceType <: AutoCloseable, ReturnType](
    resourceToBeUsed: ResourceType)(
    workUnit: ResourceType => ReturnType): ReturnType = {

    var memorizedThrowable: Throwable = null

    try {
      workUnit(resourceToBeUsed)
    }
    catch {
      case caughtThrowable: Throwable =>
        memorizedThrowable = caughtThrowable
        throw caughtThrowable
    }
    finally {
      if (resourceToBeUsed != null) {
        if (memorizedThrowable != null) {
          try {
            resourceToBeUsed.close()
          }
          catch {
            case caughtThrowable: Throwable =>
              memorizedThrowable.addSuppressed(caughtThrowable)
          }
        }
        else {
          resourceToBeUsed.close()
        }
      }
    }
  }

}
