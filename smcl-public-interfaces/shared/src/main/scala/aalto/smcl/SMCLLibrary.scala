package aalto.smcl




/**
 * Information and functionality concerning the SMCL as a whole.
 *
 * @author Aleksi Lukkarinen
 */
class SMCLLibrary {

  /** Full name of this library. */
  val FullName: String = "Scala Media Computation Library"

  /** Abbreviated name of this library. */
  val AbbreviatedName: String = "SMCL"

  /** Major part of the version identifier of this library. */
  val VersionMajor: Int = 0

  /** Minor part of the version identifier of this library. */
  val VersionMinor: Int = 1

  /** Full version identifier of this library. */
  val VersionString: String = s"$VersionMajor.$VersionMinor"

  /** Name of this library's original developer organization. */
  val OriginalDeveloperOrganizationName: String = "Aalto University"

  /** Country of this library's original developer organization. */
  val OriginalDeveloperOrganizationCountry: String = "Finland"

  /** First name of this library's original developer. */
  val OriginalDeveloperFirstName: String = "Aleksi"

  /** Last name of this library's original developer. */
  val OriginalDeveloperLastName: String = "Lukkarinen"

  /** Full name of this library's original developer. */
  val OriginalDeveloperFullName: String =
    "$OriginalAuthorFirstName $OriginalAuthorLastName"

  /** Inception year of this library. */
  val InceptionYear: Int = 2015

  /** Short information about SMCL. */
  // @formatter:off
  val Description: String =
    s"""$AbbreviatedName is a class library created to support Scala-based
       |media-oriented introductory programming teaching. It was originally
       |created by $OriginalDeveloperFullName in $InceptionYear as a part
       |of his Master's Thesis for $OriginalDeveloperOrganizationName,
       |$OriginalDeveloperOrganizationCountry.""".stripMargin
  // @formatter:on

  /** Short information about SMCL. */
  // @formatter:off
  private val MsgAboutShort: String =
    s"""
      |$FullName ($AbbreviatedName)
      |Version $VersionString.""".stripMargin
  // @formatter:on

  /** Full information about SMCL. */
  // @formatter:off
  private val MsgAboutFull: String =
    s"""$MsgAboutShort
       |
       |$Description""".stripMargin
  // @formatter:on

  /** Usage information. */
  // @formatter:off
  private val MsgUsage: String =
    s"""Help                   SMCL.help
       |General information    SMCL.information""".stripMargin
  // @formatter:on

  /** Welcoming message. */
  // @formatter:off
  private val MsgWelcoming: String =
    s"""$MsgAboutShort
       |
       |$MsgUsage
       |""".stripMargin
  // @formatter:on

  /**
   * Prints a short welcoming message.
   */
  def welcomingMessage(): Unit = println(MsgWelcoming)

  /**
   * Prints a full information message about the SMCL.
   */
  def information(): Unit = println(MsgAboutFull)

  /**
   *
   */
  override def toString: String = s"$FullName ($AbbreviatedName), version $VersionString."

}
