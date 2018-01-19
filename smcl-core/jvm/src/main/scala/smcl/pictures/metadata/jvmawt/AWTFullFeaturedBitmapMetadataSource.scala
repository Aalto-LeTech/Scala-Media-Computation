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

package smcl.pictures.metadata.jvmawt


import java.awt.image.BufferedImage

import smcl.pictures.fullfeatured
import smcl.pictures.metadata.MetadataOnBitmaps
import smcl.{Library, pictures}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class AWTFullFeaturedBitmapMetadataSource(sourceBitmaps: fullfeatured.Bitmap*)
    extends MetadataOnBitmaps[pictures.fullfeatured.Bitmap, BufferedImage](sourceBitmaps: _*)
        with BitmapToAWTImageConverter {

  /**
   * Returns the canonical name of the provider.
   *
   * @return
   */
  override
  def providerName: Option[String] =
    Some(Library.fullName)

  /**
   * Returns a description describing the provider.
   *
   * @return
   */
  override
  def providerDescription: Option[String] =
    Some(Library.description)

  /**
   * Returns the major version number of the provider.
   *
   * @return
   */
  override
  def providerMajorVersion: Option[MetaBitmapIndex] =
    Some(Library.majorVersion)

  /**
   * Returns the minor version number of the provider.
   *
   * @return
   */
  override
  def providerMinorVersion: Option[MetaBitmapIndex] =
    Some(Library.minorVersion)

  /**
   * Returns the name of the authoring organization.
   *
   * @return
   */
  override
  def providerAuthorOrganizationName: Option[String] =
    Some(Library.organizationName)

  /**
   * Returns the first name of the authoring person.
   *
   * @return
   */
  override
  def providerAuthorPersonName: Option[String] = // TODO: Create info for contact person instead of this
    Some(Library.originalDeveloperName)

}
