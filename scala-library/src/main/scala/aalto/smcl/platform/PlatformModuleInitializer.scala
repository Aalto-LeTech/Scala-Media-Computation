package aalto.smcl.platform


import aalto.smcl.ModuleInitializer
import aalto.smcl.common.GS
import aalto.smcl.common.settings.Setting
import aalto.smcl.common.settings.SettingValidatorFactory._
import aalto.smcl.platform.PlatformSettingKeys.PlatformBitmapInterpolationMethod




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
      initialValue = BitmapInterpolationMethod.Bicubic,
      validator = EmptyValidator)
  }

  //
  // Initialize Look & Feel
  //
  addInitializer {() =>
    UIProvider.tryToInitializeSpecificLookAndFeel(UIProvider.NimbusLookAndFeelName)
  }

}
