package aalto.smcl.interfaces


import scala.collection.mutable




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class MetadataInterfaceSourceProviderRegistry private[interfaces]() {

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
  def queryProvidersFor(interestingObject: Any): Set[MetadataInterfaceSourceProvider] = {
    val searchResult = mutable.Set[MetadataInterfaceSourceProvider]()

    var clazz: Class[_] = interestingObject.getClass
    while (clazz != null) {
      _registry.get(clazz).foreach { searchResult ++= _ }

      for (interface <- clazz.getInterfaces)
        _registry.get(interface).foreach { searchResult ++= _ }

      clazz = clazz.getSuperclass
    }

    searchResult.toSet
  }

  /**
   *
   *
   * @param provider
   */
  private[smcl] def registerProvider(
    clazz: Class[_],
    provider: MetadataInterfaceSourceProvider): Unit = {
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

    _registry.keys.foreach {classId =>
      if (_registry.entryExists(classId, _ == provider))
        _registry.removeBinding(classId, provider)
    }
  }

}
