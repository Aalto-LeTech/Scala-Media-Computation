package aalto.smcl


/**
 * Information and functionality concerning the SMCL as a whole.
 *
 * @author Aleksi Lukkarinen
 */
object Library {

  /** Full name of this library. */
  val FULL_NAME: String = "Scala Media Computation Library"

  /** Abbreviated name of this library. */
  val ABBREVIATED_NAME: String = "SMCL"

  /** Major part of the version identifier of this library. */
  val VERSION_MAJOR: Int = 0

  /** Minor part of the version identifier of this library. */
  val VERSION_MINOR: Int = 1

  /** Full version identifier of this library. */
  val VERSION_STRING: String = s"$VERSION_MAJOR.$VERSION_MINOR"

  /** Name of this library's original author. */
  val ORIGINAL_AUTHOR_NAME: String = "Aleksi Lukkarinen"

  /** Inception year of this library. */
  val INCEPTION_YEAR: Int = 2015


  // @formatter:off
  /** */
  private val MSG_ABOUT: String =
    s"""$FULL_NAME ($ABBREVIATED_NAME)
      |Version $VERSION_STRING.
      |
      |$ABBREVIATED_NAME was originally created by $ORIGINAL_AUTHOR_NAME in $INCEPTION_YEAR as
      |a part of his Master's Thesis for Aalto University.""".stripMargin
  // @formatter:on

  /**
   * Prints an information message about the SMCL.
   */
  def information(): Unit = println(MSG_ABOUT)

}
