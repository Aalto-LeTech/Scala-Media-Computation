package aalto.smcl.common

/**
 * Ensures that a class has a meta information map based on which the class can be tokenized.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] trait Tokenizable {

  /** Meta information based on which the class is tokenized. */
  def metaInformation: MetaInformationMap

}
