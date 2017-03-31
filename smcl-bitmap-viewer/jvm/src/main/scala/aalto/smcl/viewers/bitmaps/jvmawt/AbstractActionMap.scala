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
    def ==>(action: ViewerAction): Unit = _actions = _actions + (self -> action)

  }


}
