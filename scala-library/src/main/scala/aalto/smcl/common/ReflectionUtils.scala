package aalto.smcl.common


import scala.reflect.runtime.universe.{ClassSymbol, Mirror, Symbol, runtimeMirror => apiRuntimeMirror}

import aalto.smcl.SMCL




/**
 * Miscellaneous reflection-related utility operations.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object ReflectionUtils {

  SMCL.performInitialization()


  /**
   * Returns a runtime mirror.
   */
  def runtimeMirror: Mirror = apiRuntimeMirror(this.getClass.getClassLoader)

  /**
   * Returns the runtime class symbol object for the object referenced by a given variable.
   *
   * @param variable    reference to the object which the symbol is to be retrieved for
   */
  def classSymbolOf(variable: Any): ClassSymbol = runtimeMirror.classSymbol(variable.getClass)

  /**
   * Returns the runtime type symbol object for the object referenced by a given variable.
   *
   * @param variable    reference to the object which the symbol is to be retrieved for
   */
  def typeSymbolOf(variable: Any): Symbol = classSymbolOf(variable).toType.typeSymbol

  /**
   * Returns the runtime short name of the object referenced by a given variable.
   *
   * @param variable    reference to the object which the symbol is to be retrieved for
   */
  def shortTypeNameOf(variable: Any): String = typeSymbolOf(variable).name.decodedName.toString

  /**
   * Returns the runtime full name of the object referenced by a given variable.
   *
   * @param variable
   */
  def fullTypeNameOf(variable: Any): String = typeSymbolOf(variable).fullName

}
