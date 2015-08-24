package aalto.smcl.interfaces


import scala.collection.mutable




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class MetadataInterfaceSourceRegistry private[interfaces] () {

  /** */
  private[this] val _registry =
    new mutable.HashMap[Class[_], mutable.Set[Any]]
      with mutable.MultiMap[Class[_], Any]


  /**
   *
   *
   * @param clazz
   * @return
   */
  def querySourcesFor(clazz: Class[_]): Seq[Any] =
    _registry(clazz).toSeq

  /**
   *
   *
   * @param clazz
   * @param source
   */
  private[smcl] def registerSource(clazz: Class[_], source: Any): Unit = {
    require(clazz != null, "The class argument cannot be null.")
    require(source != null, "The source argument cannot be null.")

    _registry.addBinding(clazz, source)
  }

  /**
   *
   *
   * @param source
   */
  private[smcl] def unregisterSource(source: Any): Unit = {
    require(source != null, "The source argument cannot be null.")

    _registry.keys.foreach { clazz =>
        if (_registry.entryExists(clazz, _ == source))
          _registry.removeBinding(clazz, source)
    }
  }

}
