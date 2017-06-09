/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package aalto.smcl.interfaces


import scala.collection.mutable




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class MetadataInterfaceSourceProviderRegistry private[interfaces]() {

  /** */
  private[this]
  val _registry =
    new mutable.HashMap[Class[_], mutable.Set[MetadataInterfaceSourceProvider]]
        with mutable.MultiMap[Class[_], MetadataInterfaceSourceProvider]

  /**
   *
   *
   * @param interestingObject
   *
   * @return
   */
  def queryProvidersFor(interestingObject: Any): Set[MetadataInterfaceSourceProvider] = {
    val searchQueue = mutable.Queue[Class[_]](interestingObject.getClass)
    val classesAlreadyProcessed = mutable.Set[Class[_]]()
    val resultSet = mutable.Set[MetadataInterfaceSourceProvider]()

    def markForProcessing(clazz: Class[_]) =
      if (clazz != null && !classesAlreadyProcessed.contains(clazz)) {
        classesAlreadyProcessed += clazz
        searchQueue.enqueue(clazz)
      }

    while (searchQueue.nonEmpty) {
      val clazz = searchQueue.dequeue()

      _registry.get(clazz) foreach {_ foreach resultSet.add}

      clazz.getInterfaces foreach markForProcessing
      markForProcessing(clazz.getSuperclass.asInstanceOf[Class[_]])
    }

    resultSet.toSet
  }

  /**
   *
   *
   * @param provider
   */
  private[smcl]
  def registerProvider(
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
  private[smcl]
  def unregisterProvider(provider: MetadataInterfaceSourceProvider): Unit = {
    require(provider != null, "The source argument cannot be null.")

    _registry.keys.foreach{classId =>
      if (_registry.entryExists(classId, _ == provider))
        _registry.removeBinding(classId, provider)
    }
  }

}
