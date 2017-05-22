/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package aalto.smcl.infrastructure.jvmawt


import aalto.smcl.bitmaps.{Bitmap, BitmapValidator}
import aalto.smcl.colors.{ColorValidator, RGBAColor, RGBAComponentTranslationTable, RGBATranslationTableValidator, RichRGBAColor}
import aalto.smcl.infrastructure.{BitmapValidatorFunctionFactory, CommonValidators, DefaultJvmCalendarProvider, DefaultJvmUniqueIdProvider, DefaultPlatformResourceFactory, GS, SMCLInitializer, Setting, SettingValidatorFactory, SharedSettingInitializer}




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
    println("JVM-based SMCL Core initialization is in progress...")

    injectDependencies()

    initPlatformResourceFactory()

    initSharedSettings()
    initAwtSettings()

    initSwingLookAndFeel()
  }

  /**
   *
   */
  private def injectDependencies(): Unit = {
    val commonValidators = new CommonValidators()
    val colorValidator = new ColorValidator()
    val rgbaTranslationTableValidator =
      new RGBATranslationTableValidator(colorValidator)
    val bitmapValidator = new BitmapValidator()

    RGBAColor.setColorValidator(colorValidator)

    RichRGBAColor.setColorValidator(colorValidator)
    RichRGBAColor.setCommonValidators(commonValidators)

    RGBAComponentTranslationTable.setColorValidator(colorValidator)
    RGBAComponentTranslationTable
        .setRGBATranslationTableValidator(rgbaTranslationTableValidator)

    AwtBitmapBufferAdapter.setColorValidator(colorValidator)
    AwtBitmapBufferAdapter.setBitmapValidator(bitmapValidator)

    Bitmap.setColorValidator(colorValidator)
    Bitmap.setBitmapValidator(bitmapValidator)
  }

  /**
   * Initialize platform resource factory.
   */
  private def initPlatformResourceFactory(): Unit = {
    val calendarProvider = new DefaultJvmCalendarProvider()
    val uuidProvider = new DefaultJvmUniqueIdProvider()
    val fontProvider = new DefaultAwtFontProvider()
    val imageProvider = new DefaultAwtImageProvider(new BitmapValidator())
    val screenInfoProvider = new DefaultAwtScreenInformationProvider()

    val factory = new DefaultJvmAwtPlatformResourceFactory(
      calendarProvider, uuidProvider, fontProvider, imageProvider, screenInfoProvider)

    DefaultPlatformResourceFactory.setImplementation(factory)
  }

  /**
   *
   *
   */
  private def initSwingLookAndFeel(): Unit = {
    UIProvider.tryToInitializeSpecificLookAndFeel(UIProvider.NimbusLookAndFeelName)
  }

  /**
   * Initialize settings specific to SMCL's JVM/AWT implementation.
   */
  private def initAwtSettings(): Unit = {
    val settingValidatorFactory = new SettingValidatorFactory()

    GS += new Setting[AwtAffineTransformationInterpolationMethod.Value](
      key = AffineTransformationInterpolationMethod,
      initialValue = AwtAffineTransformationInterpolationMethod.NearestNeighbor,
      validator = settingValidatorFactory.EmptyValidator)
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
