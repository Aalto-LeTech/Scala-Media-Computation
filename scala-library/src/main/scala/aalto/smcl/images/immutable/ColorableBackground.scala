package aalto.smcl.images.immutable

/**
 * Ensures, that a place to store a background color exists.
 * 
 * @author Aleksi Lukkarinen
 */
private[images] trait ColorableBackground {
  
  /**
   * The background color that was initially given (may not be the actual background color at a later time).
   */
  def initialBackgroundColor: Int
  
}