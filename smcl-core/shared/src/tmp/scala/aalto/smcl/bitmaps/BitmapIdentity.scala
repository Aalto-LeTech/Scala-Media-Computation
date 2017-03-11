package aalto.smcl.bitmaps


import aalto.smcl.infrastructure.UniqueIdProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
object BitmapIdentity {

  /**
   *
   *
   * @return
   */
  def apply(): BitmapIdentity = {
    new BitmapIdentity(new UniqueIdProvider().newId())
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
