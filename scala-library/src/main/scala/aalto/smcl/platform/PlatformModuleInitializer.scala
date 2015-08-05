package aalto.smcl.platform


import aalto.smcl.ModuleInitializer




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformModuleInitializer extends ModuleInitializer {

  //
  // Initialize Look & Feel
  //
  addInitializer {() =>
    UIProvider.tryToInitializeSpecificLookAndFeel(UIProvider.NimbusLookAndFeelName)
  }

}
