package aalto.smcl.images.viewer


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[viewer] class AbstractActionMap {

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
    def ==> (action: ViewerAction): Unit = _actions = _actions + (self -> action)

  }

}
