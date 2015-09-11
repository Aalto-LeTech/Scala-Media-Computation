package aalto.smcl.infrastructure


import aalto.smcl.infrastructure.SettingValidatorFactory._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@InitializablePackage(dependsOnPackages = Array[String]())
private[smcl]
class PackageInitializer extends PackageInitializerBase {

  //
  // Initialize settings
  //
  addInitializer(PackageInitializationPhase.Early) {() =>
    GS += new Setting[BitmapInterpolationMethod.Value](
      key = PlatformBitmapInterpolationMethod,
      initialValue = BitmapInterpolationMethod.NearestNeighbor,
      validator = EmptyValidator)
  }

  //
  // Initialize Look & Feel
  //
  addInitializer(PackageInitializationPhase.Early) {() =>
    UIProvider.tryToInitializeSpecificLookAndFeel(UIProvider.NimbusLookAndFeelName)
  }

}
