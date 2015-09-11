package aalto.smcl


import aalto.smcl.infrastructure.{InitializablePackage, PackageInitializerBase}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@InitializablePackage(dependsOnPackages = Array(
  "aalto.smcl.infrastructure",
  "aalto.smcl.common",
  "aalto.smcl.colors",
  "aalto.smcl.bitmaps"
))
private[smcl]
class PackageInitializer extends PackageInitializerBase {

}
