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


import aalto.smcl.SMCLLibrary




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class MetaInterfaceBase extends ProviderMetadataSource with Immutable {

  /**
   *
   *
   * @return
   */
  override def providerNameOption(): Option[String] =
    Option(SMCLLibrary.FullName)

  /**
   *
   *
   * @return
   */
  override def providerDescriptionOption(): Option[String] =
    Option(SMCLLibrary.Description)

  /**
   *
   *
   * @return
   */
  override def providerAuthorOrganizationNameOption(): Option[String] =
    Option(SMCLLibrary.OriginalDeveloperOrganizationName + ", " + SMCLLibrary.OriginalDeveloperOrganizationCountry)

  /**
   *
   *
   * @return
   */
  override def providerAuthorPersonFirstNameOption(): Option[String] =
    Option(SMCLLibrary.OriginalDeveloperFirstName)

  /**
   *
   *
   * @return
   */
  override def providerAuthorPersonLastNameOption(): Option[String] =
    Option(SMCLLibrary.OriginalDeveloperLastName)

  /**
   *
   *
   * @return
   */
  override def providerMajorVersionOption(): Option[Int] =
    Option(SMCLLibrary.VersionMajor)

  /**
   *
   *
   * @return
   */
  override def providerMinorVersionOption(): Option[Int] =
    Option(SMCLLibrary.VersionMinor)

}
