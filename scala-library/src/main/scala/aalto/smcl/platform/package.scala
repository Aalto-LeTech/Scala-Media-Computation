package aalto.smcl




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object platform {

  initializePlatform()


  /**
   *
   */
  private def initializePlatform(): Unit = {
    initializeLookAndFeel()
  }

  /**
   *
   */
  private def initializeLookAndFeel(): Unit =
    UIProvider.tryToInitializeSpecificLookAndFeel(UIProvider.NimbusLookAndFeelName)

}
