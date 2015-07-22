package aalto.smcl.images.viewer


import java.awt.image.{BufferedImage => JBufferedImage}

import scala.swing._
import scala.swing.event._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class ViewerMainFrame extends MainFrame {

  title = "SMCL Image Viewer"
  resizable = true
  preferredSize = new Dimension(600, 400)
  minimumSize = new Dimension(100, 100)


  object contentPanel extends ImageDisplayPanel


  object scroller extends ScrollPane {contents = contentPanel}


  contents = scroller

  listenTo(contentPanel, scroller)

  reactions += {
    case WindowClosing(_) =>
      visible = false
      dispose()
  }

  def updateBitmapBuffer(newContent: JBufferedImage): Unit = {
    contentPanel.updateImageBuffer(newContent)
  }

}
