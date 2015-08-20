package aalto.smcl.interfaces


/**
 * Interface for querying objects for meta information source object.
 * This interface is intended to be the entry point via which to get
 * a reference to a object, which implements one or more of the other
 * interfaces provided by this library.
 *
 * @author Aleksi Lukkarinen
 */
trait MetaInterfacer {

  /**
   * Returns a reference to a object implementing one or more of the
   * other meta information querying interfaces provided by this library.
   *
   * @return
   */
  def metaInterfaces(): AnyRef

}
