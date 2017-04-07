package aalto.smcl.viewers.bitmaps.jvmawt


import aalto.smcl.infrastructure.SMCLInitializer
import aalto.smcl.viewers.DefaultViewerApplicationClient




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Initializer extends SMCLInitializer {

  /**
   *
   *
   */
  def apply(): Unit = {
    val viewerApp = new Application()

    DefaultViewerApplicationClient.setBitmapViewer(viewerApp)
  }

}
