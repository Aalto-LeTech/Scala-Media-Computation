package aalto.smcl.bitmaps.immutable


import aalto.smcl.SMCL
import aalto.smcl.platform.UniqueIdProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapIdentity {

  SMCL.performInitialization()


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
