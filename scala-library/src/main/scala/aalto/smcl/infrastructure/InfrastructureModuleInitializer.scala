package aalto.smcl.infrastructure


import aalto.smcl.GS
import aalto.smcl.infrastructure.SettingValidatorFactory._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object InfrastructureModuleInitializer extends ModuleInitializer {

  //
  // Initialize settings
  //
  addInitializer(ModuleInitializationPhase.Early) {() =>
    GS += new Setting[BitmapInterpolationMethod.Value](
      key = PlatformBitmapInterpolationMethod,
      initialValue = BitmapInterpolationMethod.NearestNeighbor,
      validator = EmptyValidator)
  }

  //
  // Initialize Look & Feel
  //
  addInitializer(ModuleInitializationPhase.Early) {() =>
    UIProvider.tryToInitializeSpecificLookAndFeel(UIProvider.NimbusLookAndFeelName)
  }

}
