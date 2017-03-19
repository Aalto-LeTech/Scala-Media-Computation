package aalto.smcl.interfaces




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
  def resourceTimestampOption(bitmapNumber: Int = 0): Option[Timestamp]

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
