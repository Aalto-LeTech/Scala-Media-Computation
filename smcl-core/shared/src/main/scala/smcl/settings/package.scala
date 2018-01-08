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

package smcl


import scala.language.implicitConversions


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object settings
    extends SettingRegistry
        with SharedSettingDefinitions {

  /** A type for setting validator functions. */
  type SettingValidator[SettingDataType] = SettingDataType => Option[Throwable]




  /**
   * Base class for horizontal alignment constants.
   */
  sealed abstract class HorizontalAlignment




  /**
   * A constant for setting horizontal alignment to the left.
   */
  case object HALeft extends HorizontalAlignment




  /**
   * A constant for setting horizontal alignment to the center.
   */
  case object HACenter extends HorizontalAlignment




  /**
   * A constant for setting horizontal alignment to the right.
   */
  case object HARight extends HorizontalAlignment




  /**
   * Base class for vertical alignment constants.
   */
  sealed abstract class VerticalAlignment




  /**
   * A constant for setting vertical alignment to the left.
   */
  case object VATop extends VerticalAlignment




  /**
   * A constant for setting vertical alignment to the center.
   */
  case object VAMiddle extends VerticalAlignment




  /**
   * A constant for setting vertical alignment to the right.
   */
  case object VABottom extends VerticalAlignment




  /**
   * Base class for viewer update style constants.
   */
  sealed abstract class ViewerUpdateStyle




  /**
   * A constant for updating viewer per default setting.
   */
  case object UpdateViewerPerDefaults extends ViewerUpdateStyle




  /**
   * A constant for preventing viewer updates.
   */
  case object PreventViewerUpdates extends ViewerUpdateStyle




  /**
   * Application of the [[RichSettingMap]] class.
   */
  implicit def SettingMapWrapper(self: SettingMap): RichSettingMap = {
    new RichSettingMap(self)
  }

}
