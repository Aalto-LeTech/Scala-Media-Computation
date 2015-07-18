package aalto.smcl.images.viewer

import scala.swing._
import scala.swing.event._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class ViewFrame extends MainFrame {

  title = "SMCL Image Viewer"
  resizable = true
  preferredSize = new Dimension(600, 400)
  minimumSize = new Dimension(100, 100)

  object contentPanel extends ImageDisplayPanel
  object scroller extends ScrollPane { contents = contentPanel }

  contents = scroller

  listenTo(contentPanel, scroller)

  reactions += {
    case WindowClosing(_) =>
      visible = false
      dispose()
  }

}
