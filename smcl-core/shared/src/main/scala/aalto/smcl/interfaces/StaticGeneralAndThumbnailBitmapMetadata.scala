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

package aalto.smcl.interfaces


/**
 * Interface for querying objects for general and thumbnail bitmap representations.
 *
 * @author Aleksi Lukkarinen
 */
trait StaticGeneralAndThumbnailBitmapMetadata[MetaImageType]
    extends StaticGeneralBitmapMetadata[MetaImageType] {

  /**
   *
   *
   * @param r
   * @param m
   *
   * @return
   */
  def thumbnailMetadataBitmapFor(
      r: ResourceIndex = FirstResourceIndex,
      m: MetaBitmapIndex = FirstMetaBitmapIndex,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): MetaImageType

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def thumbnailMetadataBitmapsFor(
      r: ResourceIndex = FirstResourceIndex,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Seq[MetaImageType]

  /**
   *
   *
   * @return
   */
  def numberOfThumbnailMetadataBitmapsFor(
      r: ResourceIndex = FirstResourceIndex): Int

  /**
   *
   *
   * @return
   */
  def thumbnailMetadataBitmaps(
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Vector[Seq[MetaImageType]]

}
