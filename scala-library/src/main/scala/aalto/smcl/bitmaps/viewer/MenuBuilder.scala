package aalto.smcl.bitmaps.viewer


import scala.collection.mutable
import scala.swing.event.Key
import scala.swing.{Component, Menu, MenuBar, MenuItem, Separator}

import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[viewer] object MenuBuilder {

  /**
   *
   *
   * @param actionMap
   * @return
   */
  def newMenuBarUsing(actionMap: AbstractActionMap): MenuBuilder = {
    require(actionMap != null, "The action map must be given (was null).")

    new MenuBuilder(actionMap)
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[viewer] class MenuBuilder private(
  private val _actionMap: AbstractActionMap) {

  /** */
  private val _parents = new mutable.Stack[Component]()

  /** */
  private var _isFinished = false


  _parents.push(new MenuBar())


  /**
   *
   *
   * @param name
   * @param mnemonicOption
   * @return
   */
  def menu(name: String, mnemonicOption: Option[Key.Value]): MenuBuilder = {
    require(name != null, "Name of the menu to be created cannot be null.")
    require(mnemonicOption != null, "Mnemonic of the menu to be created cannot be null.")

    val trimmedName = name.trim
    require(trimmedName != StrEmpty, "Mnemonic of the menu to be created cannot be null.")

    if (_isFinished)
      throw new IllegalStateException("This MenuBuilder has already returned its product.")

    if (_parents.size < 1 || !_parents.last.isInstanceOf[MenuBar])
      throw new IllegalStateException("This MenuBuilder instance is corrupted (no MenuBar exist).")

    val menu = new Menu(trimmedName)
    mnemonicOption foreach {menu.mnemonic = _}

    _parents.top match {
      case m: Menu    => m.contents += menu
      case m: MenuBar => m.contents += menu
      case _          => throw new IllegalStateException(
        "This MenuBuilder instance is corrupted (the top element was not Menu or MenuBar).")
    }

    _parents.push(menu)

    this
  }


  /**
   *
   *
   * @param actionId
   * @return
   */
  def item(actionId: Symbol): MenuBuilder = {
    if (_isFinished)
      throw new IllegalStateException("This MenuBuilder has already returned its product.")

    if (_parents.size < 2)
      throw new IllegalStateException(
        "There is no Menu being built (to get the finished MenuBar, use the get() method).")

    require(_actionMap.get(actionId).isDefined,
      s"The action map does not contain the defined action $actionId.")

    val action: ViewerAction = _actionMap.get(actionId).get

    _parents.top match {
      case m: Menu => m.contents += new MenuItem(action)
      case _       => throw new IllegalStateException(
        "This MenuBuilder instance is corrupted (the top element was not Menu).")
    }

    this
  }


  /**
   *
   *
   * @return
   */
  def separator(): MenuBuilder = {
    if (_isFinished)
      throw new IllegalStateException("This MenuBuilder has already returned its product.")

    if (_parents.size < 2)
      throw new IllegalStateException(
        "There is no Menu being built (to get the finished MenuBar, use the get() method).")

    _parents.top match {
      case m: Menu => m.contents += new Separator
      case _       => throw new IllegalStateException(
        "This MenuBuilder instance is corrupted (the top element was not Menu).")
    }

    this
  }


  /**
   *
   *
   * @return
   */
  def defined(): MenuBuilder = {
    if (_isFinished)
      throw new IllegalStateException("This MenuBuilder has already returned its product.")

    if (_parents.size < 2)
      throw new IllegalStateException(
        "There is no Menu being built (to get the finished MenuBar, use the get() method).")

    _parents.pop()

    this
  }


  /**
   * Returns the menu structure built with this builder.
   *
   * @return
   */
  def get(): MenuBar = {
    if (_isFinished)
      throw new IllegalStateException("This MenuBuilder has already returned its product.")

    if (_parents.size > 1)
      throw new IllegalStateException("Building of the current menu is not finished yet.")

    if (_parents.size < 1 || !_parents.top.isInstanceOf[MenuBar])
      throw new IllegalStateException("This MenuBuilder instance is corrupted (there is no MenuBar to return).")

    _isFinished = true
    _parents.pop().asInstanceOf[MenuBar]
  }

}
