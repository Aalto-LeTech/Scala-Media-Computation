package aalto.smcl.common


import java.awt.{GraphicsEnvironment, Toolkit}
import javax.swing.{LookAndFeel, UIManager, UnsupportedLookAndFeelException}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object Platform {

  /** */
  val NimbusLookAndFeelName = "Nimbus"

  /** AWT toolkit. */
  val awtToolkit = Toolkit.getDefaultToolkit

  /** AWT graphics environment */
  val awtGraphEnv = GraphicsEnvironment.getLocalGraphicsEnvironment

  /** Is this environment a headless one? */
  val isHeadless = awtGraphEnv.isHeadlessInstance


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
    tryToInitializeSpecificLookAndFeel(NimbusLookAndFeelName)


  /**
   *
   *
   * @param name
   * @return
   */
  def tryToInitializeSpecificLookAndFeel(name: String): Option[UIManager.LookAndFeelInfo] = {
    val lfInfoOption = installedLookAndFeels().find(i => i.getName.equals(name))

    try {
      lfInfoOption.foreach {i => UIManager.setLookAndFeel(i.getClassName)}
      lfInfoOption
    }
    catch {
      case _: ClassCastException |
           _: ClassNotFoundException |
           _: InstantiationException |
           _: IllegalAccessException |
           _: UnsupportedLookAndFeelException =>
        None
    }
  }

  /**
   *
   *
   * @return
   */
  def installedLookAndFeels(): Array[UIManager.LookAndFeelInfo] = UIManager.getInstalledLookAndFeels

  /**
   *
   *
   * @return
   */
  def currentLookAndFeel(): LookAndFeel = UIManager.getLookAndFeel

}
