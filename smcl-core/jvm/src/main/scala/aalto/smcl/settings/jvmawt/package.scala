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


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object jvmawt
    extends AwtAffineTransformationInterpolationMethodDefinition {

  /** An internal setting ID for the "AffineTransformationInterpolationMethod" setting. */
  private[smcl]
  val SIdAffineTransformationInterpolationMethod =
    "AffineTransformationInterpolationMethod"

  /** */
  private lazy val AffineTransformationInterpolationMethod0 = {
    Settings(SIdAffineTransformationInterpolationMethod)
        .asInstanceOf[ObjectSetting[AwtAffineTransformationInterpolationMethod]]
  }

  /**
   *
   *
   * @return
   */
  def AffineTransformationInterpolationMethod: AwtAffineTransformationInterpolationMethod = {
    AffineTransformationInterpolationMethod0.value
  }

  /**
   *
   *
   * @param newValue
   */
  def AffineTransformationInterpolationMethod_=(
      newValue: AwtAffineTransformationInterpolationMethod): Unit = {

    AffineTransformationInterpolationMethod0.value = newValue
  }


  /** An internal setting ID for the "ColorVisualizationTileSideLengthInPixels" setting. */
  private[smcl]
  val SIdColorVisualizationTileSideLengthInPixels =
    "ColorVisualizationTileSideLengthInPixels"

  /** */
  private lazy val ColorVisualizationTileSideLengthInPixels0 = {
    Settings(SIdColorVisualizationTileSideLengthInPixels).asInstanceOf[IntSetting]
  }

  /**
   *
   *
   * @return
   */
  def ColorVisualizationTileSideLengthInPixels: Int = {
    ColorVisualizationTileSideLengthInPixels0.value
  }

  /**
   *
   *
   * @param newValue
   */
  def ColorVisualizationTileSideLengthInPixels_=(newValue: Int): Unit = {
    ColorVisualizationTileSideLengthInPixels0.value = newValue
  }

}
