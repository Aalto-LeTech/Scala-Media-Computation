package aalto.smcl.infrastructure.awt


import aalto.smcl.infrastructure.{DefaultPlatformResourceFactory, JvmUniqueIdProvider}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Init {

  /**
   *
   */
  def init(): Unit = {
    initPlatformResourceFactory()
  }

  /**
   * Initialize platform resource factory.
   */
  private def initPlatformResourceFactory(): Unit = {
    val uuidProvider = new JvmUniqueIdProvider()
    val imageProvider = new AwtImageProvider()
    val screenInfoProvider = new AwtScreenInformationProvider()

    val factory = new DefaultJvmAwtPlatformResourceFactory(uuidProvider, imageProvider, screenInfoProvider)

    DefaultPlatformResourceFactory.setImplementation(factory)
  }

}
