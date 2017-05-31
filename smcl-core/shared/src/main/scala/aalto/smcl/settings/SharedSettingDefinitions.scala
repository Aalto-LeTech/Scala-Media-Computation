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

package aalto.smcl.settings


import aalto.smcl.colors.rgb.Color
import aalto.smcl.settings.VerticalAlignments.VerticalAlignment




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait SharedSettingDefinitions {

  /** An internal setting ID for the "NewBitmapsAreDisplayedAutomatically" setting. */
  private[smcl]
  val SIdNewBitmapsAreDisplayedAutomatically =
    "NewBitmapsAreDisplayedAutomatically"

  private lazy val NewBitmapsAreDisplayedAutomatically0 =
    Settings(SIdNewBitmapsAreDisplayedAutomatically).asInstanceOf[BooleanSetting]

  def NewBitmapsAreDisplayedAutomatically: Boolean = {
    NewBitmapsAreDisplayedAutomatically0.value
  }

  def NewBitmapsAreDisplayedAutomatically_=(newValue: Boolean): Unit = {
    NewBitmapsAreDisplayedAutomatically0.value = newValue
  }

  def DisplayNewBitmapsAutomatically(): Unit = {
    NewBitmapsAreDisplayedAutomatically0.value = true
  }

  def DoNotDisplayNewBitmapsAutomatically(): Unit = {
    NewBitmapsAreDisplayedAutomatically0.value = false
  }


  /** An internal setting ID for the "BitmapsAreDisplayedAutomaticallyAfterOperations" setting. */
  private[smcl]
  val SIdBitmapsAreDisplayedAutomaticallyAfterOperations =
    "BitmapsAreDisplayedAutomaticallyAfterOperations"

  private lazy val BitmapsAreDisplayedAutomaticallyAfterOperations0 =
    Settings(SIdBitmapsAreDisplayedAutomaticallyAfterOperations).asInstanceOf[BooleanSetting]

  def BitmapsAreDisplayedAutomaticallyAfterOperations: Boolean = {
    BitmapsAreDisplayedAutomaticallyAfterOperations0.value
  }

  def BitmapsAreDisplayedAutomaticallyAfterOperations_=(newValue: Boolean): Unit = {
    BitmapsAreDisplayedAutomaticallyAfterOperations0.value = newValue
  }

  def DisplayBitmapsAutomaticallyAfterOperations(): Unit = {
    BitmapsAreDisplayedAutomaticallyAfterOperations0.value = true
  }

  def DoNotDisplayBitmapsAutomaticallyAfterOperations(): Unit = {
    BitmapsAreDisplayedAutomaticallyAfterOperations0.value = false
  }


  /** An internal setting ID for the "ShapesHaveBordersByDefault" setting. */
  private[smcl]
  val SIdShapesHaveBordersByDefault = "ShapesHaveBordersByDefault"

  private lazy val ShapesHaveBordersByDefault0 =
    Settings(SIdShapesHaveBordersByDefault).asInstanceOf[BooleanSetting]

  def ShapesHaveBordersByDefault: Boolean = {
    ShapesHaveBordersByDefault0.value
  }

  def ShapesHaveBordersByDefault_=(newValue: Boolean): Unit = {
    ShapesHaveBordersByDefault0.value = newValue
  }

  def BorderShapesByDefault(): Unit = {
    ShapesHaveBordersByDefault0.value = true
  }

  def BorderNotFillShapesByDefault(): Unit = {
    ShapesHaveBordersByDefault0.value = false
  }


  /** An internal setting ID for the "ShapesHaveFillingsByDefault" setting. */
  private[smcl]
  val SIdShapesHaveFillingsByDefault = "ShapesHaveFillingsByDefault"

  private lazy val ShapesHaveFillingsByDefault0 =
    Settings(SIdShapesHaveFillingsByDefault).asInstanceOf[BooleanSetting]

  def ShapesHaveFillingsByDefault: Boolean = {
    ShapesHaveFillingsByDefault0.value
  }

  def ShapesHaveFillingsByDefault_=(newValue: Boolean): Unit = {
    ShapesHaveFillingsByDefault0.value = newValue
  }

  def FillShapesByDefault(): Unit = {
    ShapesHaveFillingsByDefault0.value = true
  }

  def DoNotFillShapesByDefault(): Unit = {
    ShapesHaveFillingsByDefault0.value = false
  }


  /** An internal setting ID for the "CanvasesAreResizedBasedOnTransformations" setting. */
  private[smcl]
  val SIdCanvasesAreResizedBasedOnTransformations =
    "CanvasesAreResizedBasedOnTransformations"

  private lazy val CanvasesAreResizedBasedOnTransformations0 =
    Settings(SIdCanvasesAreResizedBasedOnTransformations).asInstanceOf[BooleanSetting]

  def CanvasesAreResizedBasedOnTransformations: Boolean = {
    CanvasesAreResizedBasedOnTransformations0.value
  }

  def CanvasesAreResizedBasedOnTransformations_=(newValue: Boolean): Unit = {
    CanvasesAreResizedBasedOnTransformations0.value = newValue
  }

  def ResizeCanvasesBasedOnTransformations(): Unit = {
    ShapesHaveFillingsByDefault0.value = true
  }

  def DoNotResizeCanvasesBasedOnTransformations(): Unit = {
    ShapesHaveFillingsByDefault0.value = false
  }


  /** An internal setting ID for the "DefaultBitmapWidthInPixels" setting. */
  private[smcl]
  val SIdDefaultBitmapWidthInPixels = "DefaultBitmapWidthInPixels"

  private lazy val DefaultBitmapWidthInPixels0 =
    Settings(SIdDefaultBitmapWidthInPixels).asInstanceOf[IntSetting]

  def DefaultBitmapWidthInPixels: Int = {
    DefaultBitmapWidthInPixels0.value
  }

  def DefaultBitmapWidthInPixels_=(newValue: Int): Unit = {
    DefaultBitmapWidthInPixels0.value = newValue
  }


  /** An internal setting ID for the "DefaultBitmapHeightInPixels" setting. */
  private[smcl]
  val SIdDefaultBitmapHeightInPixels = "DefaultBitmapHeightInPixels"

  private lazy val DefaultBitmapHeightInPixels0 =
    Settings(SIdDefaultBitmapHeightInPixels).asInstanceOf[IntSetting]

  def DefaultBitmapHeightInPixels: Int = {
    DefaultBitmapHeightInPixels0.value
  }

  def DefaultBitmapHeightInPixels_=(newValue: Int): Unit = {
    DefaultBitmapHeightInPixels0.value = newValue
  }


  /** An internal setting ID for the "BitmapWidthWarningLimitInPixels" setting. */
  private[smcl]
  val SIdBitmapWidthWarningLimitInPixels = "BitmapWidthWarningLimitInPixels"

  private lazy val BitmapWidthWarningLimitInPixels0 =
    Settings(SIdBitmapWidthWarningLimitInPixels).asInstanceOf[IntSetting]

  def BitmapWidthWarningLimitInPixels: Int = {
    BitmapWidthWarningLimitInPixels0.value
  }

  def BitmapWidthWarningLimitInPixels_=(newValue: Int): Unit = {
    BitmapWidthWarningLimitInPixels0.value = newValue
  }


  /** An internal setting ID for the "BitmapHeightWarningLimitInPixels" setting. */
  private[smcl]
  val SIdBitmapHeightWarningLimitInPixels = "BitmapHeightWarningLimitInPixels"

  private lazy val BitmapHeightWarningLimitInPixels0 =
    Settings(SIdBitmapHeightWarningLimitInPixels).asInstanceOf[IntSetting]

  def BitmapHeightWarningLimitInPixels: Int = {
    BitmapHeightWarningLimitInPixels0.value
  }

  def BitmapHeightWarningLimitInPixels_=(newValue: Int): Unit = {
    BitmapHeightWarningLimitInPixels0.value = newValue
  }


  /** An internal setting ID for the "DefaultCircleRadiusInPixels" setting. */
  private[smcl]
  val SIdDefaultCircleRadiusInPixels = "DefaultCircleRadiusInPixels"

  private lazy val DefaultCircleRadiusInPixels0 =
    Settings(SIdDefaultCircleRadiusInPixels).asInstanceOf[IntSetting]

  def DefaultCircleRadiusInPixels: Int = {
    DefaultCircleRadiusInPixels0.value
  }

  def DefaultCircleRadiusInPixels_=(newValue: Int): Unit = {
    DefaultCircleRadiusInPixels0.value = newValue
  }


  /** An internal setting ID for the "DefaultRoundingWidthInPixels" setting. */
  private[smcl]
  val SIdDefaultRoundingWidthInPixels = "DefaultRoundingWidthInPixels"

  private lazy val DefaultRoundingWidthInPixels0 =
    Settings(SIdDefaultRoundingWidthInPixels).asInstanceOf[IntSetting]

  def DefaultRoundingWidthInPixels: Int = {
    DefaultRoundingWidthInPixels0.value
  }

  def DefaultRoundingWidthInPixels_=(newValue: Int): Unit = {
    DefaultRoundingWidthInPixels0.value = newValue
  }


  /** An internal setting ID for the "DefaultRoundingHeightInPixels" setting. */
  private[smcl]
  val SIdDefaultRoundingHeightInPixels = "DefaultRoundingHeightInPixels"

  private lazy val DefaultRoundingHeightInPixels0 =
    Settings(SIdDefaultRoundingHeightInPixels).asInstanceOf[IntSetting]

  def DefaultRoundingHeightInPixels: Int = {
    DefaultRoundingHeightInPixels0.value
  }

  def DefaultRoundingHeightInPixels_=(newValue: Int): Unit = {
    DefaultRoundingHeightInPixels0.value = newValue
  }


  /** An internal setting ID for the "DefaultPaddingInPixels" setting. */
  private[smcl]
  val SIdDefaultPaddingInPixels = "DefaultPaddingInPixels"

  private lazy val DefaultPaddingInPixels0 =
    Settings(SIdDefaultPaddingInPixels).asInstanceOf[IntSetting]

  def DefaultPaddingInPixels: Int = {
    DefaultPaddingInPixels0.value
  }

  def DefaultPaddingInPixels_=(newValue: Int): Unit = {
    DefaultPaddingInPixels0.value = newValue
  }


  /** An internal setting ID for the "DefaultArcStartAngleInDegrees" setting. */
  private[smcl]
  val SIdDefaultArcStartAngleInDegrees = "DefaultArcStartAngleInDegrees"

  private lazy val DefaultArcStartAngleInDegrees0 =
    Settings(SIdDefaultArcStartAngleInDegrees).asInstanceOf[IntSetting]

  def DefaultArcStartAngleInDegrees: Int = {
    DefaultArcStartAngleInDegrees0.value
  }

  def DefaultArcStartAngleInDegrees_=(newValue: Int): Unit = {
    DefaultArcStartAngleInDegrees0.value = newValue
  }


  /** An internal setting ID for the "DefaultArcAngleInDegrees" setting. */
  private[smcl]
  val SIdDefaultArcAngleInDegrees = "DefaultArcAngleInDegrees"

  private lazy val DefaultArcAngleInDegrees0 =
    Settings(SIdDefaultArcAngleInDegrees).asInstanceOf[IntSetting]

  def DefaultArcAngleInDegrees: Int = {
    DefaultArcAngleInDegrees0.value
  }

  def DefaultArcAngleInDegrees_=(newValue: Int): Unit = {
    DefaultArcAngleInDegrees0.value = newValue
  }


  /** An internal setting ID for the "DefaultBackgroundColor" setting. */
  private[smcl]
  val SIdDefaultBackgroundColor = "DefaultBackgroundColor"

  private lazy val DefaultBackgroundColor0 =
    Settings(SIdDefaultBackgroundColor).asInstanceOf[ColorSetting]

  def DefaultBackgroundColor: Color = {
    DefaultBackgroundColor0.value
  }

  def DefaultBackgroundColor_=(newValue: Color): Unit = {
    DefaultBackgroundColor0.value = newValue
  }


  /** An internal setting ID for the "DefaultPrimaryColor" setting. */
  private[smcl]
  val SIdDefaultPrimaryColor = "DefaultPrimaryColor"

  private lazy val DefaultPrimaryColor0 =
    Settings(SIdDefaultPrimaryColor).asInstanceOf[ColorSetting]

  def DefaultPrimaryColor: Color = {
    DefaultPrimaryColor0.value
  }

  def DefaultPrimaryColor_=(newValue: Color): Unit = {
    DefaultPrimaryColor0.value = newValue
  }


  /** An internal setting ID for the "DefaultSecondaryColor" setting. */
  private[smcl]
  val SIdDefaultSecondaryColor = "DefaultSecondaryColor"

  private lazy val DefaultSecondaryColor0 =
    Settings(SIdDefaultSecondaryColor).asInstanceOf[ColorSetting]

  def DefaultSecondaryColor: Color = {
    DefaultSecondaryColor0.value
  }

  def DefaultSecondaryColor_=(newValue: Color): Unit = {
    DefaultSecondaryColor0.value = newValue
  }


  /** An internal setting ID for the "DefaultHorizontalAlignment" setting. */
  private[smcl]
  val SIdDefaultHorizontalAlignment = "DefaultHorizontalAlignment"

  private lazy val DefaultHorizontalAlignment0 =
    Settings(SIdDefaultHorizontalAlignment).asInstanceOf[ObjectSetting[HorizontalAlignment]]

  def DefaultHorizontalAlignment: HorizontalAlignment = {
    DefaultHorizontalAlignment0.value
  }

  def DefaultHorizontalAlignment_=(newValue: HorizontalAlignment): Unit = {
    DefaultHorizontalAlignment0.value = newValue
  }


  /** An internal setting ID for the "DefaultVerticalAlignment" setting. */
  private[smcl]
  val SIdDefaultVerticalAlignment = "DefaultVerticalAlignment"

  private lazy val DefaultVerticalAlignment0 =
    Settings(SIdDefaultVerticalAlignment).asInstanceOf[ObjectSetting[VerticalAlignment]]

  def DefaultVerticalAlignment: VerticalAlignment = {
    DefaultVerticalAlignment0.value
  }

  def DefaultVerticalAlignment_=(newValue: VerticalAlignment): Unit = {
    DefaultVerticalAlignment0.value = newValue
  }

}
