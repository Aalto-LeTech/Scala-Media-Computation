package aalto.smcl.common


import aalto.smcl.infrastructure.{InitializablePackage, PackageInitializerBase}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@InitializablePackage(dependsOnPackages = Array(
  "aalto.smcl.infrastructure"
))
private[smcl]
class PackageInitializer extends PackageInitializerBase {

}
