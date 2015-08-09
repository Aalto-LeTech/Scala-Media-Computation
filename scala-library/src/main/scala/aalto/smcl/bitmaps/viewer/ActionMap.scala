package aalto.smcl.bitmaps.viewer


import scala.swing.event.Key.Modifier.{Alt, Control}
import scala.swing.event.{Key, WindowClosing}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[viewer] class ActionMap(val relatedMainFrame: ViewerMainFrame) extends AbstractActionMap {

  'copyToClipboard ==>
    ViewerAction("Copy to Clipboard", Option(Key.C), Option((Key.C, Control))) {

    }

  'saveToFile ==>
    ViewerAction("Save to a File", Option(Key.S), Option((Key.S, Control))) {

    }

  'exitViewer ==>
    ViewerAction("Exit", Option(Key.E), Option((Key.F4, Alt))) {
      relatedMainFrame.publish(new WindowClosing(relatedMainFrame))
    }

  'ZoomIn ==>
    ViewerAction("Zoom In", Option(Key.I)) {
      relatedMainFrame.imagePanel.adjustZoomWith(_.enlargeByStandardAmountIfPossible())
    }

  'ZoomOut ==>
    ViewerAction("Zoom Out", Option(Key.O)) {
      relatedMainFrame.imagePanel.adjustZoomWith(_.reduceByStandardAmountIfPossible())
    }

  'Zoom60Percent ==>
    ViewerAction("Zoom 60 %", Option(Key.Key6), Option((Key.Key6, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Percents60
    }

  'Zoom70Percent ==>
    ViewerAction("Zoom 70 %", Option(Key.Key7), Option((Key.Key7, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Percents70
    }

  'Zoom80Percent ==>
    ViewerAction("Zoom 80 %", Option(Key.Key8), Option((Key.Key8, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Percents80
    }

  'Zoom90Percent ==>
    ViewerAction("Zoom 90 %", Option(Key.Key9), Option((Key.Key9, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Percents90
    }

  'Zoom100Percent ==>
    ViewerAction("Zoom 100 %", Option(Key.Key0), Option((Key.Key0, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Identity
    }

  'Zoom150Percent ==>
    ViewerAction("Zoom 150 %", Option(Key.Key1), Option((Key.Key1, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Percents150
    }

  'Zoom200Percent ==>
    ViewerAction("Zoom 200 %", Option(Key.Key2), Option((Key.Key2, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Percents200
    }

  'Zoom300Percent ==>
    ViewerAction("Zoom 300 %", Option(Key.Key3), Option((Key.Key3, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Percents300
    }

  'Zoom400Percent ==>
    ViewerAction("Zoom 400 %", Option(Key.Key4), Option((Key.Key4, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Percents400
    }

  'Zoom500Percent ==>
    ViewerAction("Zoom 500 %", Option(Key.Key5), Option((Key.Key5, 0))) {
      relatedMainFrame.imagePanel.zoomFactor = ZoomFactor.Percents500
    }

  'About ==>
    ViewerAction("About", Option(Key.F1), Option((Key.F1, 0))) {
      relatedMainFrame.showAboutBox()
    }

}
