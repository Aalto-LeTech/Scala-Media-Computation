package aalto.smcl


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object infrastructure extends Constants {

  /** */
  lazy val ClassTokenizer: ClassTokenizer = new ClassTokenizer()

  /** */
  lazy val CommonValidators: CommonValidators = new CommonValidators()

  /** */
  lazy val StringUtils: StringUtils = new StringUtils()

  /** */
  lazy val SwingUtils: SwingUtils = new SwingUtils()

}
