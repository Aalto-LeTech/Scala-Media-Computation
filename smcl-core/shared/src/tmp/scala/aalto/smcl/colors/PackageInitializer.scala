package aalto.smcl.colors


import aalto.smcl.infrastructure.{InitializablePackage, PackageInitializerBase}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@InitializablePackage(dependsOnPackages = Array(
  "aalto.smcl.infrastructure",
  "aalto.smcl.common"
))
private[smcl]
class PackageInitializer extends PackageInitializerBase {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null


}
