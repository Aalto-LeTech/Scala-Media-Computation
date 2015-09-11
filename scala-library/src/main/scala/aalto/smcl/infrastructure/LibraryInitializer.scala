package aalto.smcl.infrastructure


import scala.collection.mutable

import aalto.smcl.infrastructure.PackageInitializationPhase.PackageInitializationPhase




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
//private[smcl]
class LibraryInitializer {

  /** */
  private[this] val _startedInitializationPhases: mutable.Set[PackageInitializationPhase] =
    mutable.Set[PackageInitializationPhase]()

  /** */
  private val PackageInitializerClassName: String = "PackageInitializer"

  /** */
  private val PackageInitializerMethodName: String = "performInitialization"


  /**
   *
   *
   * @return
   */
  def resolveNamesOfApplicationPackagesContainingClasses(): Seq[String] =
    ClassProvider.loadApplicationClasses().map(_.getName).map(name =>
      name.substring(0, name.lastIndexOf(StrPeriod))
    ).distinct

  /**
   *
   */
  def printNamesOfApplicationPackagesContainingClasses(): Unit =
    resolveNamesOfApplicationPackagesContainingClasses() foreach println

  /**
   *
   *
   * @return
   */
  def resolvePackageInitializerClassNames(): Seq[String] =
    resolveNamesOfApplicationPackagesContainingClasses().map(_ + StrPeriod + PackageInitializerClassName)

  /**
   *
   */
  def printPackageInitializerClassNames(): Unit =
    resolvePackageInitializerClassNames foreach println

  /**
   *
   *
   * @return
   */
  def loadPackageInitializerClasses(): Seq[(Class[_], Seq[String])] = {
    val classes = resolvePackageInitializerClassNames() map ClassProvider.load
    val dependencies = classes map {clazz =>
      val packageAnnotation: InitializablePackage = clazz.getAnnotation(classOf[InitializablePackage])
      if (packageAnnotation == null) {
        throw new IllegalStateException(s"Class ${clazz.getName} is missing the InitializablePackage annotation.")
      }

      packageAnnotation.dependsOnPackages().toList.toSeq
    }

    classes zip dependencies
  }

  /**
   *
   */
  def performInitialization(): Unit =
    PackageInitializationPhase.values foreach performInitialization

  /**
   *
   *
   * @param phase
   */
  private def performInitialization(phase: PackageInitializationPhase): Unit = {
//    if (!_startedInitializationPhases.contains(phase)) {
//      _startedInitializationPhases += phase
//
//      loadPackageInitializerClasses() foreach {case (clazz, dependencies) =>
//        val method = clazz.getMethod(PackageInitializerMethodName, classOf[PackageInitializationPhase])
//        val instance = clazz.newInstance()
//
//        method.invoke(instance, phase)
//      }
//    }
  }

}


//.filter(clazz =>
//clazz.getInterfaces.exists(_.getName == "aalto.smcl.infrastructure.InitializablePackage") && clazz.getName != "aalto.smcl.infrastructure.InitializablePackage")
//.map(_.asInstanceOf[Class[InitializablePackage]]).sortBy(_.toString)


///**
// *
// *
// * @author Aleksi Lukkarinen
// */
//private[smcl] trait InitializablePackage {
//
//  /**
//   *
//   *
//   * @param body
//   */
//  def delayedInit(body: => Unit): Unit = {
//    body
//
//    SMCL.performInitialization(Early)
//    SMCL.performInitialization(Late)
//  }
//
//}


//trait Initializable[T] extends T {
//  def early() : T
//
//  early()
//
//}
//
//class A extends Initializable[A] {
//
//
//  def early(): Unit = {
//
//  }
//
//}


