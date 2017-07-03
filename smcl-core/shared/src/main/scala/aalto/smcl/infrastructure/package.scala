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

package aalto.smcl


import scala.collection.GenTraversable
import scala.language.{higherKinds, implicitConversions}

import aalto.smcl.bitmaps.BitmapValidatorFunctionFactory
import aalto.smcl.settings.SettingValidatorFactory


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object infrastructure extends Constants {

  /** Type for SMCL initializer classes. */
  type SMCLInitializer = () => Unit

  /** Type for setting initializer classes. */
  type SettingInitializer =
    (SettingValidatorFactory, BitmapValidatorFunctionFactory) => Unit

  /** Global platform resource factory. */
  lazy val PRF: PlatformResourceFactory = DefaultPlatformResourceFactory

  /** */
  lazy val Screen: DefaultScreen = new DefaultScreen(PRF.screenInformationProvider)

  /**
   *
   *
   * @return
   */
  def isScalaJSPlatform: Boolean = scala.sys.props("java.vm.name") == "Scala.js"

  /**
   *
   *
   * @return
   */
  def isNotScalaJSPlatform: Boolean = !isScalaJSPlatform


  /**
   * Prints a description of a describable object.
   *
   * @param describee the object to be described
   */
  def describe(describee: Describable): Unit = {
    println(describee.toDescription)
  }

  /**
   * Prints descriptions for a sequence of describable objects.
   *
   * @param describees the sequence the content of which is to be described
   */
  def describe(describees: Seq[Describable]): Unit = {
    for (describee <- describees)
      println(describee.toDescription)
  }

  /**
   * Application of the [[RichDouble]] class.
   *
   * @param self
   *
   * @return
   */
  implicit def DoubleWrapper(self: Double): RichDouble = {
    new RichDouble(self)
  }

  /**
   * Application of the [[RichFloat]] class.
   *
   * @param self
   *
   * @return
   */
  implicit def FloatWrapper(self: Float): RichFloat = {
    new RichFloat(self)
  }

  /**
   * Application of the [[RichGenTraversable]] class.
   *
   * @param self
   * @tparam E
   * @tparam C
   *
   * @return
   */
  //noinspection TypeParameterShadow
  private[smcl]
  implicit def GenTraversableWrapper[E, C[E] <: GenTraversable[E]](
      self: C[E]): RichGenTraversable[E, C] = {

    new RichGenTraversable[E, C](self)
  }

  /**
   * Application of the [[RichInt]] class.
   *
   * @param self
   *
   * @return
   */
  implicit def IntWrapper(self: Int): RichInt = {
    new RichInt(self)
  }

  /**
   * Application of the [[RichLong]] class.
   *
   * @param self
   *
   * @return
   */
  implicit def LongWrapper(self: Long): RichLong = {
    new RichLong(self)
  }

  /**
   * Application of the [[RicherString]] class.
   *
   * @param self
   *
   * @return
   */
  implicit def StringWrapper(self: String): RicherString = {
    RicherString(self)
  }

  /**
   * Application of the [[RichOptionString]] class.
   *
   * @param self
   *
   * @return
   */
  implicit def OptionStringWrapper(self: Option[String]): RichOptionString = {
    RichOptionString(self)
  }

}
