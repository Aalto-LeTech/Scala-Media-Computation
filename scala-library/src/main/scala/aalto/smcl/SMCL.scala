package aalto.smcl


/**
 * Information and functionality concerning the SMCL as a whole.
 *
 * @author Aleksi Lukkarinen
 */
object SMCL {

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

  /** Name of this library's original author. */
  val OriginalAuthorName: String = "Aleksi Lukkarinen"

  /** Inception year of this library. */
  val InceptionYear: Int = 2015


  // @formatter:off
  /** Short information about SMCL. */
  private val MsgAboutShort: String =
    s"""
      |$FullName ($AbbreviatedName)
      |Version $VersionString.""".stripMargin

  /** Full informaiton about SMCL. */
  private val MsgAboutFull: String =
    s"""$MsgAboutShort
       |
       |$AbbreviatedName was originally created by $OriginalAuthorName in $InceptionYear as
       |a part of his Master's Thesis for Aalto University.""".stripMargin

  /** Usage informaiton. */
  private val MsgUsage: String =
    s"""Help                   SMCL.help
       |General information    SMCL.information""".stripMargin

  /** Welcoming message. */
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
   * Calls the help provider to print help with no given search terms.
   */
  def help():Unit = Help.onTopic(Seq())

  /**
   * Calls the help provider to print help on the given search terms.
   *
   * @param terms
   */
  def help(terms: String*):Unit = Help.onTopic(terms)

  /**
   *
   */
  override def toString: String = s"$FullName ($AbbreviatedName), version $VersionString."

}
