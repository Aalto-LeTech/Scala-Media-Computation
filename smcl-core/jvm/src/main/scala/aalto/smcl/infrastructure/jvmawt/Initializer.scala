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


import aalto.smcl.bitmaps.BitmapValidator
import aalto.smcl.colors.{ColorValidator, RGBAColor, RGBAComponentTranslationTable, RGBATranslationTableValidator, RichRGBAColor}
import aalto.smcl.infrastructure.{BitmapValidatorFunctionFactory, CommonValidators, DefaultJvmCalendarProvider, DefaultJvmUniqueIdProvider, DefaultPlatformResourceFactory, GS, SMCLInitializer, Setting, SettingValidatorFactory, SharedSettingInitializer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Initializer extends SMCLInitializer {

  /** The ColorValidator instance to be passed for SMCL classes */
  private val _commonValidators = new CommonValidators()

  /** The ColorValidator instance to be passed for SMCL classes */
  private val _colorValidator = new ColorValidator()

  /** The RGBATranslationTableValidator instance to be passed for SMCL classes */
  private val _rgbaTranslationTableValidator =
    new RGBATranslationTableValidator(_colorValidator)


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
    AwtBitmapBufferAdapter.setColorValidator(_colorValidator)
    RGBAColor.setColorValidator(_colorValidator)
    RichRGBAColor.setColorValidator(_colorValidator)
    RichRGBAColor.setCommonValidators(_commonValidators)
    RGBAComponentTranslationTable.setColorValidator(_colorValidator)
    RGBAComponentTranslationTable
        .setRGBATranslationTableValidator(_rgbaTranslationTableValidator)
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
