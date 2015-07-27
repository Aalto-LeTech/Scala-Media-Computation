package aalto.smcl.common


import java.awt.Toolkit
import javax.swing.{LookAndFeel, UIManager, UnsupportedLookAndFeelException}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object Platform {

  /** */
  val NIMBUS_LOOK_AND_FEEL_NAME = "Nimbus"

  /** AWT toolkit. */
  val awtToolkit = Toolkit.getDefaultToolkit


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
    tryToInitializeSpecificLookAndFeel(NIMBUS_LOOK_AND_FEEL_NAME)


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
