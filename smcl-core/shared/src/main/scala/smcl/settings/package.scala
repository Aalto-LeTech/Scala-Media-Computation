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
   * Base class for stating alignment on a side independently of the side in question.
   */
  sealed abstract class SideIndependentAlignment {

    /**
     *
     *
     * @return
     */
    def toHorizontal: HorizontalAlignment

    /**
     *
     *
     * @return
     */
    def toVertical: VerticalAlignment

  }




  /**
   * A constant for stating that alignment is to be at the top or the left of a side.
   */
  case object SIATopOrLeft
      extends SideIndependentAlignment {

    /** */
    override
    val toHorizontal: HorizontalAlignment = HALeft

    /** */
    override
    val toVertical: VerticalAlignment = VATop

  }




  /**
   * A constant for stating that alignment is to be at the center of a side.
   */
  case object SIACenter
      extends SideIndependentAlignment {

    /** */
    override
    val toHorizontal: HorizontalAlignment = HACenter

    /** */
    override
    val toVertical: VerticalAlignment = VAMiddle

  }




  /**
   * A constant for stating that alignment is to be at the bottom or the right of a side.
   */
  case object SIABottomOrRight
      extends SideIndependentAlignment {

    /** */
    override
    val toHorizontal: HorizontalAlignment = HARight

    /** */
    override
    val toVertical: VerticalAlignment = VABottom

  }




  /**
   * Base class for horizontal alignment constants.
   */
  sealed abstract class HorizontalAlignment {

    /**
     *
     *
     * @return
     */
    def sideIndependent: SideIndependentAlignment

  }




  /**
   * A constant for setting horizontal alignment to the left.
   */
  case object HALeft
      extends HorizontalAlignment {

    /**
     *
     *
     * @return
     */
    override
    val sideIndependent: SideIndependentAlignment = SIATopOrLeft

  }




  /**
   * A constant for setting horizontal alignment to the center.
   */
  case object HACenter
      extends HorizontalAlignment {

    /**
     *
     *
     * @return
     */
    override
    val sideIndependent: SideIndependentAlignment = SIACenter

  }




  /**
   * A constant for setting horizontal alignment to the right.
   */
  case object HARight
      extends HorizontalAlignment {

    /**
     *
     *
     * @return
     */
    override
    val sideIndependent: SideIndependentAlignment = SIABottomOrRight

  }




  /**
   * Base class for vertical alignment constants.
   */
  sealed abstract class VerticalAlignment {

    /**
     *
     *
     * @return
     */
    def sideIndependent: SideIndependentAlignment

  }




  /**
   * A constant for setting vertical alignment to the left.
   */
  case object VATop
      extends VerticalAlignment {

    /**
     *
     *
     * @return
     */
    override
    val sideIndependent: SideIndependentAlignment = SIATopOrLeft

  }




  /**
   * A constant for setting vertical alignment to the center.
   */
  case object VAMiddle
      extends VerticalAlignment {

    /**
     *
     *
     * @return
     */
    override
    val sideIndependent: SideIndependentAlignment = SIACenter

  }




  /**
   * A constant for setting vertical alignment to the right.
   */
  case object VABottom
      extends VerticalAlignment {

    /**
     *
     *
     * @return
     */
    override
    val sideIndependent: SideIndependentAlignment = SIABottomOrRight

  }




  /**
   * Base class for viewer update style constants.
   */
  sealed abstract class ViewerUpdateStyle




  /**
   * A constant for updating viewer per default setting.
   */
  case object UpdateViewerPerDefaults
      extends ViewerUpdateStyle




  /**
   * A constant for preventing viewer updates.
   */
  case object PreventViewerUpdates
      extends ViewerUpdateStyle




  /**
   * Base class for position type constants.
   */
  sealed abstract class PositionType




  /**
   * A constant for stating that positions are center points.
   */
  case object CenterPosition
      extends PositionType




  /**
   * A constant for stating that positions are upper left corner points.
   */
  case object UpperLeftCornerPosition
      extends PositionType




  /**
   * Application of the [[RichSettingMap]] class.
   */
  implicit def SettingMapWrapper(self: SettingMap): RichSettingMap = {
    new RichSettingMap(self)
  }

}
