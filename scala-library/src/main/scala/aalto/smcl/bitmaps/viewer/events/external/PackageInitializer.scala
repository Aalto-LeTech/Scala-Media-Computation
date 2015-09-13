package aalto.smcl.bitmaps.viewer.events.external


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
  "aalto.smcl.bitmaps",
  "aalto.smcl.bitmaps.viewer"
))
private[smcl]
class PackageInitializer extends PackageInitializerBase {

}
