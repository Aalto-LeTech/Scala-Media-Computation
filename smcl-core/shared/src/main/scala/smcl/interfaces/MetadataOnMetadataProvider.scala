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


import smcl.SMCLLibrary




/**
 * Interface for querying objects for provider-related metadata.
 *
 * @author Aleksi Lukkarinen
 */
trait MetadataOnMetadataProvider extends Immutable {

  // TODO: Implement ADTs to return personal information of several developers
  // TODO: Implement ADT to return institutional information (address etc.)

  /**
   * Returns the canonical name of the provider.
   *
   * @return
   */
  def providerName: Option[String] = {
    Some(SMCLLibrary.fullName)
  }

  /**
   * Returns a description describing the provider.
   *
   * @return
   */
  def providerDescription: Option[String] = {
    Some(SMCLLibrary.description)
  }

  /**
   * Returns the major version number of the provider.
   *
   * @return
   */
  def providerMajorVersion: Option[Int] = {
    Some(SMCLLibrary.majorVersion)
  }

  /**
   * Returns the minor version number of the provider.
   *
   * @return
   */
  def providerMinorVersion: Option[Int] = {
    Some(SMCLLibrary.minorVersion)
  }

  /**
   * Returns the name of the authoring organization.
   *
   * @return
   */
  def providerAuthorOrganizationName: Option[String] = {
    val name = SMCLLibrary.originalOrganizationName
    val country = SMCLLibrary.countryOfOrigin

    Some(s"$name, $country")
  }

  /**
   * Returns the first name of the authoring person.
   *
   * @return
   */
  def providerAuthorPersonName: Option[String] = {
    Some(SMCLLibrary.originalDeveloperName)
  }

}
