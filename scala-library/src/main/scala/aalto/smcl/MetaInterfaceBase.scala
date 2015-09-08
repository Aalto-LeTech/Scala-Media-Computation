package aalto.smcl


import aalto.smcl.init.ModuleInitializationPhase
import aalto.smcl.interfaces.ProviderMetadataSource




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class MetaInterfaceBase() extends ProviderMetadataSource with Immutable {

  SMCL.performInitialization(ModuleInitializationPhase.Early)


  /**
   *
   *
   * @return
   */
  override def providerNameOption(): Option[String] =
    Option(SMCL.FullName)

  /**
   *
   *
   * @return
   */
  override def providerDescriptionOption(): Option[String] =
    Option(SMCL.Description)

  /**
   *
   *
   * @return
   */
  override def providerAuthorOrganizationNameOption(): Option[String] =
    Option(SMCL.OriginalDeveloperOrganizationName + ", " + SMCL.OriginalDeveloperOrganizationCountry)

  /**
   *
   *
   * @return
   */
  override def providerAuthorPersonFirstNameOption(): Option[String] =
    Option(SMCL.OriginalDeveloperFirstName)

  /**
   *
   *
   * @return
   */
  override def providerAuthorPersonLastNameOption(): Option[String] =
    Option(SMCL.OriginalDeveloperLastName)

  /**
   *
   *
   * @return
   */
  override def providerMajorVersionOption(): Option[Int] =
    Option(SMCL.VersionMajor)

  /**
   *
   *
   * @return
   */
  override def providerMinorVersionOption(): Option[Int] =
    Option(SMCL.VersionMinor)

}
