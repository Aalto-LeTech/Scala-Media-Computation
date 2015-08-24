package aalto.smcl.interfaces


import scala.collection.mutable




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class MetadataInterfaceSourceProviderRegistry private[interfaces] () {

  /** */
  private[this] val _registry =
    new mutable.HashMap[Class[_], mutable.Set[MetadataInterfaceSourceProvider]]
      with mutable.MultiMap[Class[_], MetadataInterfaceSourceProvider]


  /**
   *
   *
   * @param interestingObject
   * @return
   */
  def queryProvidersFor(interestingObject: Any): Seq[MetadataInterfaceSourceProvider] =
    _registry(interestingObject.getClass).toSeq

  /**
   *
   *
   * @param clazz
   * @param provider
   */
  private[smcl] def registerProvider(clazz: Class[_], provider: MetadataInterfaceSourceProvider): Unit = {
    require(clazz != null, "The class argument cannot be null.")
    require(provider != null, "The provider argument cannot be null.")

    _registry.addBinding(clazz, provider)
  }

  /**
   *
   *
   * @param provider
   */
  private[smcl] def unregisterProvider(provider: MetadataInterfaceSourceProvider): Unit = {
    require(provider != null, "The source argument cannot be null.")

    _registry.keys.foreach { clazz =>
        if (_registry.entryExists(clazz, _ == provider))
          _registry.removeBinding(clazz, provider)
    }
  }

}
