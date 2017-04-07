package aalto.smcl.infrastructure.js


import aalto.smcl.bitmaps.BitmapValidator
import aalto.smcl.infrastructure.{BitmapValidatorFunctionFactory, SMCLInitializer, SettingValidatorFactory, SharedSettingInitializer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Initializer extends SMCLInitializer {

  /**
   *
   */
  def apply(): Unit = {
    println("JS-based SMCL Core initialization in progress...")

    initSharedSettings()
    initAwtSettings()

    initSwingLookAndFeel()
    initPlatformResourceFactory()
  }

  /**
   * Initialize platform resource factory.
   */
  private def initPlatformResourceFactory(): Unit = {
    /*
    val calendarProvider = new DefaultJvmCalendarProvider()
    val uuidProvider = new DefaultJvmUniqueIdProvider()
    val fontProvider = new DefaultAwtFontProvider()
    val imageProvider = new DefaultAwtImageProvider(new BitmapValidator())
    val screenInfoProvider = new DefaultAwtScreenInformationProvider()

    val factory = new DefaultJvmAwtPlatformResourceFactory(
      calendarProvider, uuidProvider, fontProvider, imageProvider, screenInfoProvider)

    DefaultPlatformResourceFactory.setImplementation(factory)
    */
  }

  /**
   *
   *
   */
  private def initSwingLookAndFeel(): Unit = Unit
    //UIProvider.tryToInitializeSpecificLookAndFeel(UIProvider.NimbusLookAndFeelName)

  /**
   * Initialize settings specific to SMCL's JVM/AWT implementation.
   */
  private def initAwtSettings(): Unit = {
    /*
    val settingValidatorFactory = new SettingValidatorFactory()

    GS += new Setting[AwtAffineTransformationInterpolationMethod.Value](
      key = AffineTransformationInterpolationMethod,
      initialValue = AwtAffineTransformationInterpolationMethod.NearestNeighbor,
      validator = settingValidatorFactory.EmptyValidator)
    */
  }

  /**
   *
   */
  private def initSharedSettings(): Unit = {
    val settingValidatorFactory = new SettingValidatorFactory()
    val bitmapValidatorFunctionFactory = new BitmapValidatorFunctionFactory(new BitmapValidator())

    val settingInitializer = new SharedSettingInitializer(
      settingValidatorFactory, bitmapValidatorFunctionFactory)

    settingInitializer.init()
  }

}
