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
    val resultSet = mutable.Set[MetadataInterfaceSourceProvider]()
    val searchQueue = mutable.Queue[Class[_]](interestingObject.getClass)

    searchQueue foreach {clazz =>
      _registry.get(clazz).foreach {resultSet ++= _}

      searchQueue.enqueue(clazz.getInterfaces:_*)

      val superClass = clazz.getSuperclass.asInstanceOf[Class[_]]
      if (superClass != null)
        searchQueue.enqueue(superClass)
    }

    resultSet.toSet
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
