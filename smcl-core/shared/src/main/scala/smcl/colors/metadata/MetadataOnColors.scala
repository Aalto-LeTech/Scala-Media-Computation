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

package smcl.colors.metadata


import smcl.colors
import smcl.colors.rgb
import smcl.infrastructure.StrSpace
import smcl.interfaces.{BitmapToMetaImageConverter, MetadataOnResources, ResourceIndex, StaticGeneralBitmapMetadata}
import smcl.pictures.fullfeatured.AbstractBitmap




/**
 * Provides metadata about SMCL colors.
 *
 * @param sourceColors
 * @tparam MetaImageType
 *
 * @author Aleksi Lukkarinen
 */
abstract class MetadataOnColors[MetaImageType](sourceColors: Vector[rgb.Color])
    extends MetadataOnResources[colors.rgb.Color]
        with StaticGeneralBitmapMetadata[MetaImageType]
        with BitmapToMetaImageConverter[AbstractBitmap, MetaImageType] {

  /** A sequence of known resources, about which the metadata is to be provisioned. */
  override def knownResources: Vector[colors.rgb.Color] = sourceColors

  /** Preset keywords for all colors. */
  private val _presetKeywords: Seq[String] = Seq("color")

  /** Bitmap representations for the source colors. */
  private[this]
  lazy val _generalBitmaps: Vector[Seq[MetaImageType]] = Vector()
/*
    for {
      color <- sourceColors

      a = ColorTileCreator.createSingleColorTile(color)
      b = ColorTileCreator.createDoubleColorTile(color, Black)
      c = ColorTileCreator.createDoubleColorTile(color, Gray)
      d = ColorTileCreator.createDoubleColorTile(color, White)

      tilesForSingleColor = Seq(a, b, c, d) map convertBitmapToMetaImage

    } yield tilesForSingleColor
*/

  /**
   *
   *
   * @param r
   *
   * @return
   */
  override
  def resourceTitle(r: ResourceIndex): Option[String] = {
    doIfValidKnownResourceIndex(r){color => color.canonicalName}
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  override
  def resourceDescription(r: ResourceIndex): Option[String] = {
    doIfValidKnownResourceIndex(r){color => Some(color.toHexString)}
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  override
  def resourceKeywords(r: ResourceIndex): Seq[String] = {
    doIfValidKnownResourceIndex(r){color =>
      val nameWords =
        color.canonicalName.fold(Seq.empty[String])(
          _.split(StrSpace).map(_.toLowerCase))

      val cssName = color.cssName.fold(Seq.empty[String])(Seq(_))

      _presetKeywords ++ nameWords ++ cssName
    }
  }

  /**
   *
   *
   * @param r
   * @param m
   *
   * @return
   */
  override
  def generalMetadataBitmapFor(
      r: ResourceIndex,
      m: MetaBitmapIndex): MetaImageType = {

    _generalBitmaps(r)(m)
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  override
  def generalMetadataBitmapsFor(
      r: ResourceIndex): Seq[MetaImageType] = {

    _generalBitmaps(r)
  }

  /**
   *
   *
   * @return
   */
  override
  def numberOfGeneralMetadataBitmapsFor(
      r: ResourceIndex): MetaBitmapIndex = {

    _generalBitmaps(r).length
  }

  /**
   *
   *
   * @return
   */
  override
  def generalMetadataBitmaps: Vector[Seq[MetaImageType]] =
    _generalBitmaps

}
