package aalto.smcl.infrastructure


import scala.collection.mutable

import aalto.smcl.infrastructure.PackageInitializationPhase.PackageInitializationPhase




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure]
object LibraryInitializer {

  val FixedPackageInitializationOrder__HACK: Seq[Class[PackageInitializerBase]] = Seq(
    classOf[aalto.smcl.PackageInitializer],
    classOf[aalto.smcl.infrastructure.PackageInitializer],
    classOf[aalto.smcl.common.PackageInitializer],
    classOf[aalto.smcl.colors.PackageInitializer],
    classOf[aalto.smcl.bitmaps.PackageInitializer],
    classOf[aalto.smcl.bitmaps.operations.PackageInitializer],
    classOf[aalto.smcl.bitmaps.viewer.PackageInitializer],
    classOf[aalto.smcl.bitmaps.viewer.events.external.PackageInitializer],
    classOf[aalto.smcl.bitmaps.metadata.PackageInitializer]
  ) map {_.asInstanceOf[Class[PackageInitializerBase]]}

  private[this] var _initializationInitiatorOption: Option[AnyRef] = None

  /** */
  private[this] val _startedInitializationPhases: mutable.Set[PackageInitializationPhase] =
    mutable.Set[PackageInitializationPhase]()

  /** */
  private val PackageInitializerClassName: String = "PackageInitializer"

  /** */
  private val PackageInitializerMethodName: String = "performInitialization"

  /** */
  private val _classProvider: ClassProvider = new ClassProvider()



  /**
   *
   *
   * @return
   */
  def resolveNamesOfApplicationPackagesContainingClasses(): Seq[String] =
    _classProvider.loadApplicationClasses().map(_.getName).map(name =>
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
      _classProvider.load(_).asInstanceOf[Class[PackageInitializerBase]]
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
    //loadPackageInitializerClasses()
    // for {initializer <- loadPackageInitializerClasses()} { }

    // TODO: HACK ALERT!!! Code related to resolving initialization order is removed!
    FixedPackageInitializationOrder__HACK
  }

  /**
   *
   */
  def printInitializationOrder(): Unit =
    resolveInitializationOrder() foreach println

  /**
   *
   */
  def performInitialization(
    initiator: SMCLInitializationInvoker,
    phase: PackageInitializationPhase): Unit = {

    if (_initializationInitiatorOption.isDefined) {
      if (!_initializationInitiatorOption.contains(initiator))
        return
    }
    else {
      _initializationInitiatorOption = Some(initiator)
    }

    if (_startedInitializationPhases.contains(phase))
      return

    _startedInitializationPhases += phase

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
