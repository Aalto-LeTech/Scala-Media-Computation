import java.util.Date




/**
 * Interface for querying objects for common metadata.
 *
 * @author Aleksi Lukkarinen
 */
trait ResourceMetadataSource {

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  def resourceIdOption(bitmapNumber: Int = 0): Option[String]

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  def resourceTimestampOption(bitmapNumber: Int = 0): Option[Date]

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  def resourceTitleOption(bitmapNumber: Int = 0): Option[String]

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  def resourceDescriptionOption(bitmapNumber: Int = 0): Option[String]

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  def resourceAuthorsOption(bitmapNumber: Int = 0): Option[String]

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  def resourceKeywordsOption(bitmapNumber: Int = 0): Option[String]

}
