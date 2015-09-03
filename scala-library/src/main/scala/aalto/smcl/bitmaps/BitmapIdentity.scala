package aalto.smcl.bitmaps

import aalto.smcl.platform.UniqueIdProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapIdentity {

  /**
   *
   *
   * @return
   */
  def apply(): BitmapIdentity = {
    new BitmapIdentity(UniqueIdProvider.newId())
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class BitmapIdentity private(identity: String) {

  /**
   *
   *
   * @return
   */
  override def toString: String = identity

}
