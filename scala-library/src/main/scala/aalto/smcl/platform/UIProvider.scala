package aalto.smcl.platform


import java.awt.{GraphicsEnvironment, Toolkit}
import javax.swing.{LookAndFeel, UIManager, UnsupportedLookAndFeelException}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object UIProvider {

  /** */
  private[platform] val NimbusLookAndFeelName = "Nimbus"

  /** AWT toolkit. */
  private[platform] val awtToolkit = Toolkit.getDefaultToolkit

  /** AWT graphics environment */
  private[platform] val awtGraphEnv = GraphicsEnvironment.getLocalGraphicsEnvironment

  /** Is this environment a headless one? */
  val isHeadless = awtGraphEnv.isHeadlessInstance


  /**
   *
   *
   * @param name
   * @return
   */
  private[platform] def tryToInitializeSpecificLookAndFeel(name: String): Option[UIManager.LookAndFeelInfo] = {
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
  private[platform] def installedLookAndFeels(): Seq[UIManager.LookAndFeelInfo] =
    UIManager.getInstalledLookAndFeels.toSeq

  /**
   *
   *
   * @return
   */
  private[platform] def currentLookAndFeel(): LookAndFeel =
    UIManager.getLookAndFeel

}
