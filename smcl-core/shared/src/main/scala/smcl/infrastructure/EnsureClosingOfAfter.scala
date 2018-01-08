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

package smcl.infrastructure


/**
 * This object provides a way to mimic Java's resource-related `try` facility. The given resource has to implement
 * Java's `AutoCloseable` interface, which enables this class to ensure that the `close()` method of the reserved
 * resource is being called after the defined work unit is finished.
 *
 * @author Aleksi Lukkarinen
 */
object EnsureClosingOfAfter {

  /**
   *
   *
   * @param resourceToBeUsed
   * @param workUnit
   * @tparam ResourceType
   * @tparam ReturnType
   *
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
