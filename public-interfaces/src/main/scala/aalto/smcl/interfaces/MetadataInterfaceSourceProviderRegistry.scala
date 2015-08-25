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
  def queryProvidersFor(interestingObject: Any): Option[Set[MetadataInterfaceSourceProvider]] = {
    val clazz: Class[_] = interestingObject.getClass
    val searchResult = _registry.get(clazz)
    if (searchResult.isEmpty)
      return None

    Some(searchResult.get.toSet)
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
