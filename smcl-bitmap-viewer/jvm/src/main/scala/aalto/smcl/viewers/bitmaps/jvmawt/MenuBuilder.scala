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


import scala.swing.event.Key
import scala.swing.{Component, Menu, MenuBar, MenuItem, Separator}

import aalto.smcl.infrastructure.StrEmpty




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object MenuBuilder {

  /**
   *
   *
   * @param actionMap
   *
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
private[jvmawt]
class MenuBuilder private(
    private val _actionMap: AbstractActionMap) {

  /** */
  private var _parents: List[Component] = new MenuBar() :: Nil

  /** */
  private var _isFinished: Boolean = false


  /**
   *
   *
   * @param name
   * @param mnemonicOption
   *
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

    _parents.head match {
      case m: Menu    => m.contents += menu
      case m: MenuBar => m.contents += menu
      case _          => throw new IllegalStateException(
        "This MenuBuilder instance is corrupted (the top element was not Menu or MenuBar).")
    }

    _parents = menu :: _parents

    this
  }


  /**
   *
   *
   * @param actionId
   *
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

    _parents.head match {
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

    _parents.head match {
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

    _parents = _parents.tail

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

    if (_parents.size < 1 || !_parents.head.isInstanceOf[MenuBar])
      throw new IllegalStateException("This MenuBuilder instance is corrupted (there is no MenuBar to return).")

    _isFinished = true

    val menu = _parents.head.asInstanceOf[MenuBar]
    _parents = _parents.tail

    menu
  }

}
