package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.infrastructure.BitmapBufferAdapter




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait DrawingSurface {

  /**
   *
   * @param x
   * @param y
   */
  def drawPoint(x: Int, y: Int): Unit = {

  }


  /**
   *
   *
   * @param buffer
   */
  def drawBitmap(buffer: BitmapBufferAdapter): Unit = {

  }

}
