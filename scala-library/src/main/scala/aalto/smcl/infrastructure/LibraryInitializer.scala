package aalto.smcl.infrastructure


import scala.collection.mutable

import aalto.smcl.infrastructure.PackageInitializationPhase.PackageInitializationPhase




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure]
class LibraryInitializer {

  /** */
  private[this] var _initializationStarted: Boolean = false

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
    resolvePackageInitializerClassNamesFrom(resolveNamesOfApplicationPackagesContainingClasses())

  /**
   *
   *
   * @return
   */
  def resolvePackageInitializerClassNameFrom(fullPackageName: String): String =
    fullPackageName + StrPeriod + PackageInitializerClassName

  /**
   *
   *
   * @return
   */
  def resolvePackageInitializerClassNamesFrom(packages: Seq[String]): Seq[String] =
    packages map resolvePackageInitializerClassNameFrom

  /**
   *
   */
  def printPackageInitializerClassNames(): Unit =
    resolvePackageInitializerClassNames foreach println

  /**
   *
   *
   * @param clazz
   */
  private def getPackageAnnotationOf(clazz: Class[PackageInitializerBase]): InitializablePackage = {
    val packageAnnotation = clazz.getAnnotation(classOf[InitializablePackage])
    if (packageAnnotation == null) {
      throw new RuntimeException(
        s"Class ${clazz.getName} is missing the InitializablePackage annotation.")
    }

    packageAnnotation
  }


  /**
   *
   *
   * @param clazz
   * @param dependencies
   */
  case class PackageInitializerClass(
    clazz: Class[PackageInitializerBase],
    dependencies: Seq[Class[PackageInitializerBase]]) {}


  /**
   *
   *
   * @return
   */
  def loadPackageInitializerClasses(): Seq[PackageInitializerClass] = {
    val packages = resolveNamesOfApplicationPackagesContainingClasses()
    val classes = resolvePackageInitializerClassNamesFrom(packages) map {
      ClassProvider.load(_).asInstanceOf[Class[PackageInitializerBase]]
    }

    val nameToClassMap: mutable.Map[String, Class[PackageInitializerBase]] =
      mutable.Map[String, Class[PackageInitializerBase]]()
    classes foreach {clazz =>
      nameToClassMap += (clazz.getName -> clazz)
    }

    for {
      initializerClass <- classes
      packageDependencies = getPackageAnnotationOf(initializerClass).dependsOnPackages() map {_.trim}
      dependencyClasses = packageDependencies map {declaredPackage =>
        if (!packages.contains(declaredPackage)) {
          throw new RuntimeException(
            s"Dependency $declaredPackage of class ${initializerClass.getName} cannot be found.")
        }

        nameToClassMap(resolvePackageInitializerClassNameFrom(declaredPackage))
      }
    } yield PackageInitializerClass(initializerClass, dependencyClasses.toSeq)
  }

  /**
   *
   *
   * @return
   */
  def resolveInitializationOrder(): Seq[Class[PackageInitializerBase]] = {
    val dependencyResolver = new PackageInitializerDependencyResolver()
    for {initializer <- loadPackageInitializerClasses()} {
      if (initializer.dependencies.isEmpty) {
        dependencyResolver.addDependency(initializer.clazz, None)
      }
      else {
        for (destinationClass <- initializer.dependencies)
          dependencyResolver.addDependency(initializer.clazz, Some(destinationClass))
      }
    }

    dependencyResolver.resolveInitializationOrder()
  }

  /**
   *
   */
  def printInitializationOrder(): Unit =
    resolveInitializationOrder() foreach println

  /**
   *
   */
  def performInitialization(phase: PackageInitializationPhase): Unit = {
    if (_initializationStarted)
      return

    _initializationStarted = true

    for {
      clazz <- resolveInitializationOrder()
      method = clazz.getMethod(PackageInitializerMethodName, classOf[PackageInitializationPhase])
      instance = clazz.newInstance()
    } invokeInitializer(instance, method, phase)
  }

  /**
   *
   *
   * @param instance
   * @param method
   * @param phase
   */
  @inline
  protected def invokeInitializer(
    instance: PackageInitializerBase,
    method: java.lang.reflect.Method,
    phase: PackageInitializationPhase): Unit = {

    method.invoke(instance, phase)
  }

}
