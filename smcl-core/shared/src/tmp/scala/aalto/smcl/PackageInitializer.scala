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
  "aalto.smcl.bitmaps",
  "aalto.smcl.bitmaps.metadata",
  "aalto.smcl.bitmaps.operations",
  "aalto.smcl.bitmaps.viewer",
  "aalto.smcl.bitmaps.viewer.events.external"
))
private[smcl]
class PackageInitializer extends PackageInitializerBase {

}
