package aalto.smcl.interfaces


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait MetadataInterfaceSourceProvider {

  /**
   *
   *
   * @param interestingObject
   * @return
   */
  def querySourceFor(interestingObject: Any): Any

}
