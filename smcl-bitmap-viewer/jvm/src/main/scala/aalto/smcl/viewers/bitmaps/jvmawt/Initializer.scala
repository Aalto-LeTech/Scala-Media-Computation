package aalto.smcl.viewers.bitmaps.jvmawt


import aalto.smcl.viewers.DefaultViewerApplicationClient




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Initializer {

  /**
   *
   *
   */
  def apply(): Unit = {
    val viewerApp = new Application()

    DefaultViewerApplicationClient.setBitmapViewer(viewerApp)
  }

}
