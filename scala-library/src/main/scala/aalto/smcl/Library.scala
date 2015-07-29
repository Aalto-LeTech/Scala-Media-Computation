package aalto.smcl


/**
 * Information and functionality concerning the SMCL as a whole.
 *
 * @author Aleksi Lukkarinen
 */
object Library {

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
  /** */
  private val MsgAbout: String =
    s"""$FullName ($AbbreviatedName)
      |Version $VersionString.
      |
      |$AbbreviatedName was originally created by $OriginalAuthorName in $InceptionYear as
      |a part of his Master's Thesis for Aalto University.""".stripMargin
  // @formatter:on

  /**
   * Prints an information message about the SMCL.
   */
  def information(): Unit = println(MsgAbout)

}
