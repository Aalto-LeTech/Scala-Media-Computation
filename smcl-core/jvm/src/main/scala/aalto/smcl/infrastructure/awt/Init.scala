package aalto.smcl.infrastructure.awt


import aalto.smcl.infrastructure.{DefaultJvmCalendarProvider, DefaultJvmUniqueIdProvider, DefaultPlatformResourceFactory}




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
    val calendarProvider = new DefaultJvmCalendarProvider()
    val uuidProvider = new DefaultJvmUniqueIdProvider()
    val fontProvider = new DefaultAwtFontProvider()
    val imageProvider = new DefaultAwtImageProvider()
    val screenInfoProvider = new DefaultAwtScreenInformationProvider()

    val factory = new DefaultJvmAwtPlatformResourceFactory(
      calendarProvider, uuidProvider, fontProvider, imageProvider, screenInfoProvider)

    DefaultPlatformResourceFactory.setImplementation(factory)
  }

}
