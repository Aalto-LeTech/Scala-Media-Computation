package aalto.smcl.platform


import aalto.smcl.infrastructure.settings.Setting
import aalto.smcl.infrastructure.settings.SettingValidatorFactory._
import aalto.smcl.{GS, ModuleInitializer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformModuleInitializer extends ModuleInitializer {

  //
  // Initialize settings
  //
  addInitializer {() =>
    GS += new Setting[BitmapInterpolationMethod.Value](
      key = PlatformBitmapInterpolationMethod,
      initialValue = BitmapInterpolationMethod.NearestNeighbor,
      validator = EmptyValidator)
  }

  //
  // Initialize Look & Feel
  //
  addInitializer {() =>
    UIProvider.tryToInitializeSpecificLookAndFeel(UIProvider.NimbusLookAndFeelName)
  }

}
