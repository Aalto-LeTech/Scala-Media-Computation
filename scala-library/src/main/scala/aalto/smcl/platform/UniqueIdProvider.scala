package aalto.smcl.platform


import aalto.smcl.SMCL




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object UniqueIdProvider {

  SMCL.performInitialization()


  /** */
  def newId(): String = java.util.UUID.randomUUID().toString

}
