package aalto.smcl.interfaces


/**
 * Interface for querying objects for provider-related metadata.
 *
 * @author Aleksi Lukkarinen
 */
trait ProviderMetadataSource {

  /**
   * Returns the canonical name of the provider.
   *
   * @return
   */
  def providerNameOption(): Option[String]

  /**
   * Returns a description describing the provider.
   *
   * @return
   */
  def providerDescriptionOption(): Option[String]

  /**
   * Returns the major version number of the provider.
   *
   * @return
   */
  def providerMajorVersionOption(): Option[Int]

  /**
   * Returns the minor version number of the provider.
   *
   * @return
   */
  def providerMinorVersionOption(): Option[Int]

  /**
   * Returns the name of the authoring organization.
   *
   * @return
   */
  def providerAuthorOrganizationNameOption(): Option[String]

  /**
   * Returns the first name of the authoring person.
   *
   * @return
   */
  def providerAuthorPersonFirstNameOption(): Option[String]

  /**
   * Returns the last name of the authoring person.
   *
   * @return
   */
  def providerAuthorPersonLastNameOption(): Option[String]

}
