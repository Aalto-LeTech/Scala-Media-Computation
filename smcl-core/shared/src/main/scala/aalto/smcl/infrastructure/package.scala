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

import aalto.smcl.infrastructure.BaseSettingKeys.{BooleanSettingKey, ColorSettingKey, EnumSettingKey, IntSettingKey}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object infrastructure extends Constants {

  /** Type for SMCL initializer classes. */
  type SMCLInitializer = () => Unit


  /** Global settings storage. */
  lazy val GS: Settings = new Settings()

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




  /** */
  case object CanvasesAreResizedBasedOnTransformations extends BooleanSettingKey




  /** */
  case object DefaultBackground extends ColorSettingKey




  /** */
  case object NewBitmapsAreDisplayedAutomatically extends BooleanSettingKey




  /** */
  case object BitmapsAreDisplayedAutomaticallyAfterOperations extends BooleanSettingKey




  /** */
  case object ShapesHaveBordersByDefault extends BooleanSettingKey




  /** */
  case object ShapesHaveFillingsByDefault extends BooleanSettingKey




  /** */
  case object DefaultBitmapWidthInPixels extends IntSettingKey




  /** */
  case object DefaultBitmapHeightInPixels extends IntSettingKey




  /** */
  case object BitmapWidthWarningLimitInPixels extends IntSettingKey




  /** */
  case object BitmapHeightWarningLimitInPixels extends IntSettingKey




  /** */
  case object DefaultRoundingWidthInPixels extends IntSettingKey




  /** */
  case object DefaultRoundingHeightInPixels extends IntSettingKey




  /** */
  case object DefaultCircleRadiusInPixels extends IntSettingKey




  /** */
  case object DefaultArcStartAngleInDegrees extends IntSettingKey




  /** */
  case object DefaultArcAngleInDegrees extends IntSettingKey




  /** */
  case object DefaultPaddingInPixels extends IntSettingKey




  /** */
  case object DefaultPrimary extends ColorSettingKey




  /** */
  case object DefaultSecondary extends ColorSettingKey




  /** */
  case object DefaultHorizontalAlignment extends EnumSettingKey[HorizontalAlignment.Value]




  /** */
  case object DefaultVerticalAlignment extends EnumSettingKey[VerticalAlignment.Value]




  /** */
  //noinspection TypeParameterShadow
  private[smcl]
  implicit def GenTraversableWrapper[E, C[E] <: GenTraversable[E]](self: C[E]): RichGenTraversable[E, C] =
  new RichGenTraversable[E, C](self)

}
