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

package smcl.bitmaps.metadata


import smcl.bitmaps.fullfeatured.AbstractBitmap
import smcl.infrastructure.{Identity, Timestamp}
import smcl.interfaces.{BitmapToMetaImageConverter, MetadataOnResources, ResourceIndex, StaticGeneralAndThumbnailBitmapMetadata}




/**
 * Provides metadata about SMCL bitmaps.
 *
 * @param bitmaps
 * @tparam BitmapType
 * @tparam MetaImageType
 *
 * @author Aleksi Lukkarinen
 */
abstract class MetadataOnBitmaps[BitmapType <: AbstractBitmap, MetaImageType](
    bitmaps: BitmapType*)
    extends MetadataOnResources[BitmapType]
        with StaticGeneralAndThumbnailBitmapMetadata[MetaImageType]
        with BitmapToMetaImageConverter[AbstractBitmap, MetaImageType] {

  /** A constant for number of bitmaps per resource this metadata provider suppoerts. */
  protected val OneBitmap = 1

  /** A sequence of known resources, about which the metadata is to be provisioned. */
  override def knownResources: Vector[BitmapType] = bitmaps.toVector

  /**
   *
   *
   * @param m
   */
  def validateMetaBitmapIndex(m: MetaBitmapIndex): Unit = {
    require(m == FirstMetaBitmapIndex,
      "This metadata provider support only one meta bitmap per resource.")
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def numberOfGeneralMetadataBitmapsFor(
      r: ResourceIndex): MetaBitmapIndex = {

    doIfValidKnownResourceIndex(r){_ => OneBitmap}
  }

  /**
   *
   *
   * @param r
   * @param m
   *
   * @return
   */
  def generalMetadataBitmapFor(
      r: ResourceIndex,
      m: MetaBitmapIndex): MetaImageType = {

    validateMetaBitmapIndex(m)
    generateMetadataBitmapFor(r)
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def generalMetadataBitmapsFor(
      r: ResourceIndex): Seq[MetaImageType] = {

    Seq(generateMetadataBitmapFor(r))
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  private
  def generateMetadataBitmapFor(
      r: ResourceIndex): MetaImageType = {

    doIfValidKnownResourceIndex(r){bitmap =>
      convertBitmapToMetaImage(bitmap)
    }
  }

  /**
   *
   *
   * @return
   */
  def generalMetadataBitmaps: Vector[Seq[MetaImageType]] = {
    knownResources map {bitmap =>
      Seq(convertBitmapToMetaImage(bitmap))
    }
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def numberOfThumbnailMetadataBitmapsFor(
      r: ResourceIndex): MetaBitmapIndex = {

    doIfValidKnownResourceIndex(r){_ => OneBitmap}
  }

  /**
   *
   *
   * @param r
   * @param m
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   *
   * @return
   */
  def thumbnailMetadataBitmapFor(
      r: ResourceIndex,
      m: MetaBitmapIndex,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): MetaImageType = {

    validateMetaBitmapIndex(m)
    generateThumbnailBitmapFor(r,
      maximumWidthInPixels, maximumHeightInPixels)
  }

  /**
   *
   *
   * @param r
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   *
   * @return
   */
  def thumbnailMetadataBitmapsFor(
      r: ResourceIndex,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Seq[MetaImageType] = {

    Seq(generateThumbnailBitmapFor(r,
      maximumWidthInPixels, maximumHeightInPixels))
  }

  /**
   *
   *
   * @param r
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   *
   * @return
   */
  def generateThumbnailBitmapFor(
      r: ResourceIndex,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): MetaImageType = {

    def shouldBeScaled(bitmap: BitmapType): Boolean = {
      (bitmap.widthInPixels > maximumWidthInPixels
          || bitmap.heightInPixels > maximumHeightInPixels)
    }

    convertBitmapToMetaImage(
      doIfValidKnownResourceIndex(r){bitmap =>
        // TODO: After Bitmap can tell a suitable scaling factor for a given target size and has scaling operation, refactor the following code to utilize them

        if (shouldBeScaled(bitmap)) {
          val scalingFactor =
            if (bitmap.widthInPixels > maximumWidthInPixels)
              maximumWidthInPixels.toDouble / bitmap.widthInPixels
            else
              maximumHeightInPixels.toDouble / bitmap.heightInPixels

          bitmap //.scale(scalingFactor)
        }
        else {
          bitmap
        }
      })
  }

  /**
   *
   *
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   *
   * @return
   */
  def thumbnailMetadataBitmaps(
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Vector[Seq[MetaImageType]] = {

    knownResources.indices.toVector map {index =>
      Seq(generateThumbnailBitmapFor(index,
        maximumWidthInPixels, maximumHeightInPixels))
    }
  }

  /**
   *
   *
   * @param i
   *
   * @return
   */
  override def resourceIdentity(i: ResourceIndex): Option[Identity] = {
    doIfValidKnownResourceIndex(i){bitmap => Some(bitmap.uniqueIdentifier)}
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  override def resourceTimestamp(r: ResourceIndex): Option[Timestamp] = {
    doIfValidKnownResourceIndex(r){bitmap => Some(bitmap.created)}
  }

}
