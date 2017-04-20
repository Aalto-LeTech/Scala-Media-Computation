/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package aalto.smcl.infrastructure.jvmawt


import java.awt.{GraphicsEnvironment, Toolkit}
import javax.swing.{LookAndFeel, UIManager, UnsupportedLookAndFeelException}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class AwtSwingUIProvider() {

  /** */
  private[infrastructure] val NimbusLookAndFeelName = "Nimbus"

  /** AWT toolkit. */
  private[infrastructure] val awtToolkit = Toolkit.getDefaultToolkit

  /** AWT graphics environment */
  private[infrastructure] val awtGraphEnv = GraphicsEnvironment.getLocalGraphicsEnvironment

  /** Is this environment a headless one? */
  val isHeadless: Boolean = awtGraphEnv.isHeadlessInstance


  /**
   *
   *
   * @param name
   *
   * @return
   */
  private[infrastructure] def tryToInitializeSpecificLookAndFeel(name: String): Option[UIManager.LookAndFeelInfo] = {
    val lfInfoOption = installedLookAndFeels().find(i => i.getName.equals(name))

    try {
      lfInfoOption foreach {i => UIManager.setLookAndFeel(i.getClassName)}
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
  private[infrastructure] def installedLookAndFeels(): Seq[UIManager.LookAndFeelInfo] =
    UIManager.getInstalledLookAndFeels.toSeq

  /**
   *
   *
   * @return
   */
  private[infrastructure] def currentLookAndFeel(): LookAndFeel =
    UIManager.getLookAndFeel

}
