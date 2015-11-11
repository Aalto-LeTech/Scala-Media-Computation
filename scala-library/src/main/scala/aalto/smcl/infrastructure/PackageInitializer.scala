package aalto.smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@InitializablePackage(dependsOnPackages = Array[String]())
private[smcl]
class PackageInitializer
  extends PackageInitializerBase {


  //
  // Initialize settings
  //
  addInitializer(PackageInitializationPhase.Early) {() =>
    GS += new Setting[BitmapInterpolationMethod.Value](
      key = PlatformBitmapInterpolationMethod,
      initialValue = BitmapInterpolationMethod.NearestNeighbor,
      validator = new SettingValidatorFactory().EmptyValidator)
  }

  //
  // Initialize Look & Feel
  //
  addInitializer(PackageInitializationPhase.Early) {() =>
    new UIProvider().tryToInitializeSpecificLookAndFeel(new UIProvider().NimbusLookAndFeelName)
  }

}
