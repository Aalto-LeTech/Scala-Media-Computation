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

package aalto.smcl.viewers.bitmaps.jvmawt


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[jvmawt]
class AbstractActionMap {

  /** */
  private[this] var _actions = Map[Symbol, ViewerAction]()

  /**
   *
   *
   * @param actionId
   *
   * @return
   */
  def get(actionId: Symbol): Option[ViewerAction] = _actions.get(actionId)




  /**
   *
   *
   * @param self
   */
  protected implicit class RichSymbol(self: Symbol) {

    /**
     *
     *
     * @param action
     */
    def ==> (action: ViewerAction): Unit = _actions = _actions + (self -> action)

  }




}
