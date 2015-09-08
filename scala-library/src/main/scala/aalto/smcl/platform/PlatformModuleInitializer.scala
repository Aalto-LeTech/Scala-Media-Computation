package aalto.smcl.platform


import aalto.smcl.infrastructure.settings.Setting
import aalto.smcl.infrastructure.settings.SettingValidatorFactory._
import aalto.smcl.init.{ModuleInitializer, ModuleInitializationPhase}
import aalto.smcl.GS




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformModuleInitializer extends ModuleInitializer {

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
