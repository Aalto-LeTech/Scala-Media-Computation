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
   * @param clazz
   * @return
   */
  def querySourceFor(clazz: Class[_]): Any

}
