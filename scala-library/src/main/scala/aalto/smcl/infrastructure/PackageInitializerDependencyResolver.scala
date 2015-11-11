package aalto.smcl.infrastructure


import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._
import scalax.collection.mutable.Graph




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure]
class PackageInitializerDependencyResolver() {

  /** */
  private val _dependencyGraph = Graph[Class[PackageInitializerBase], DiEdge]()

  /**
   *
   *
   * @param fromClass
   * @param toClassOption
   */
  def addDependency(
    fromClass: Class[PackageInitializerBase],
    toClassOption: Option[Class[PackageInitializerBase]]): Unit = {

    toClassOption.fold(_dependencyGraph.add(fromClass)) {toClass =>
      _dependencyGraph.add(fromClass ~> toClass)
    }
  }

  /**
   *
   */
  private def throwExceptionIfCircularDependenciesFound(): Unit =
    _dependencyGraph.findCycle foreach {cycle =>
      throw new RuntimeException(
        "A following circular package dependency is defined and must be resolved: " +
          cycle.nodes.mkString(" -> "))
    }

  /**
   *
   *
   * @return
   */
  def resolveInitializationOrder(): Seq[Class[PackageInitializerBase]] = {
    throwExceptionIfCircularDependenciesFound()

    // -- DEBUG -- println(_dependencyGraph)

    _dependencyGraph.componentTraverser().topologicalSort.reverse
  }

}
