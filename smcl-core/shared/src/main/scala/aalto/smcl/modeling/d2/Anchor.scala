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

package aalto.smcl.modeling.d2


/**
 * Anchor presets and functionality for creating new anchors.
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
object Anchor {

  /** Width and height factors for preset anchors. */
  private val PresetData = Seq(
    (0.0, 0.0, Some("top left")), (0.5, 0.0, Some("top center")), (1.0, 0.0, Some("top right")),
    (0.0, 0.5, Some("center left")), (0.5, 0.5, Some("center")), (1.0, 0.5, Some("center right")),
    (0.0, 1.0, Some("bottom left")), (0.5, 1.0, Some("bottom center")), (1.0, 1.0, Some("bottom righ")))

  /**
   * Creates a preset ratio anchor based on given data table index.
   *
   * @param index number of an entry in the <code>PresetData</code> table
   *
   * @return the new [[RatioAnchor]] instance
   */
  private def ratioPreset(index: Int): RatioAnchor = {
    val data = PresetData(index)
    RatioAnchor(data._1, data._2, data._3)
  }

  /** An anchor representing the top left corner of an object. */
  val TopLeft: RatioAnchor = ratioPreset(0)

  /** An anchor representing the center of the top edge of an object. */
  val TopCenter: RatioAnchor = ratioPreset(1)

  /** An anchor representing the top right corner of an object. */
  val TopRight: RatioAnchor = ratioPreset(2)

  /** An anchor representing the center of the left edge of an object. */
  val CenterLeft: RatioAnchor = ratioPreset(3)

  /** An anchor representing the center of an object. */
  val Center: RatioAnchor = ratioPreset(4)

  /** An anchor representing the center of the right edge of an object. */
  val CenterRight: RatioAnchor = ratioPreset(5)

  /** An anchor representing the bottom left corner of an object. */
  val BottomLeft: RatioAnchor = ratioPreset(6)

  /** An anchor representing the center of the bottom edge of an object. */
  val BottomCenter: RatioAnchor = ratioPreset(7)

  /** An anchor representing the bottom right corner of an object. */
  val BottomRight: RatioAnchor = ratioPreset(8)

}




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
trait Anchor[HasAnchorType]
    extends CommonAnchorAPI[HasAnchorType] {

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def toPointAnchor(anchored: HasAnchorType): PointAnchor = {
    PointAnchor(
      internalXWithin(anchored),
      internalYWithin(anchored),
      name)
  }

  /**
   *
   *
   * @param anchored
   *
   * @return
   */
  def internalDimsWithin(anchored: HasAnchorType): Dims = {
    Dims(
      internalXWithin(anchored),
      internalYWithin(anchored))
  }

}
