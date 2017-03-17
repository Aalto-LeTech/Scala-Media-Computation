package aalto.smcl.bitmaps


import aalto.smcl.infrastructure.PRF




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
    new BitmapIdentity(PRF.createUniqueIdString())
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
