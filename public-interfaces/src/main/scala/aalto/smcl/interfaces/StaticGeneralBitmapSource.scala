package aalto.smcl.interfaces


import java.awt.image.BufferedImage




/**
 * Interface for querying objects for bitmap representations.
 *
 * @author Aleksi Lukkarinen
 */
trait StaticGeneralBitmapSource {

  /**
   *
   *
   * @return
   */
  def numberOfGeneralBitmaps(): Int

  /**
   *
   *
   * @return
   */
  def generalBitmapsOption(): Option[Seq[BufferedImage]]

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  def generalBitmapOption(bitmapNumber: Int = 0): Option[BufferedImage]

}
