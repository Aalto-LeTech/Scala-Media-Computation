package aalto.smcl.infrastructure.jvmawt


import aalto.smcl.infrastructure.BitmapBufferAdapter

import scala.util.{Either, Try}




/**
 *
 */
trait AwtImageProvider {

  /**
   *
   *
   * @param path
   * @return
   */
  def tryToLoadImagesFromFile(path: String): Try[Seq[Either[Throwable, BitmapBufferAdapter]]]

}
