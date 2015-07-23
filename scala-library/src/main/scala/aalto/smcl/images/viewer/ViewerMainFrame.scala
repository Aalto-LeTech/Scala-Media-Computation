package aalto.smcl.images.viewer


import java.awt.image.{BufferedImage => JBufferedImage}
import javax.swing.WindowConstants

import scala.swing.Dialog.{Message, Options}
import scala.swing._
import scala.swing.event._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class ViewerMainFrame extends Frame {

  title = "SMCL Image Viewer"
  resizable = true
  preferredSize = new Dimension(600, 400)
  minimumSize = new Dimension(100, 100)

  peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)


  val contentPanel = new ImageDisplayPanel()

  val scroller = new ScrollPane() {contents = contentPanel}

  contents = scroller

  reactions += {
    case WindowClosing(_) =>
      if (userReallyWishesToClose) {
        visible = false
      }

    case _ => ()
  }

  listenTo(this)

  /**
   *
   *
   * @return
   */
  def userReallyWishesToClose(): Boolean = {
    Dialog.showConfirmation(
      this,
      "Do you really want to close this bitmap preview window?",
      this.title,
      Options.YesNo,
      Message.Question
    ) match {
      case Dialog.Result.No  => false
      case Dialog.Result.Yes => true
      case _                 =>
        throw new RuntimeException(
          "Unexpected error: Invalid closing confirmation dialog return value.")
    }
  }

  /**
   *
   *
   * @param newContent
   */
  def updateBitmapBuffer(newContent: JBufferedImage): Unit = {
    contentPanel.updateImageBuffer(newContent)
  }

}
