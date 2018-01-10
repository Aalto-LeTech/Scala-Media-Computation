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

package smcl.interfaces


/**
 * Interface for querying objects for bitmap representations.
 *
 * @author Aleksi Lukkarinen
 */
trait StaticGeneralBitmapMetadata[MetaImageType] {

  /** Type for metadata bitmap indices. */
  type MetaBitmapIndex = Int

  /** Constant for the first meta bitmap index (zero). */
  val FirstMetaBitmapIndex = 0


  /**
   *
   *
   * @param r
   * @param m
   *
   * @return
   */
  def generalMetadataBitmapFor(
      r: ResourceIndex = FirstResourceIndex,
      m: MetaBitmapIndex = FirstMetaBitmapIndex): MetaImageType

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def generalMetadataBitmapsFor(
      r: ResourceIndex = FirstResourceIndex): Seq[MetaImageType]

  /**
   *
   *
   * @return
   */
  def numberOfGeneralMetadataBitmapsFor(
      r: ResourceIndex = FirstResourceIndex): Int

  /**
   *
   *
   * @return
   */
  def generalMetadataBitmaps: Vector[Seq[MetaImageType]]

}
