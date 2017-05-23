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


import aalto.smcl.bitmaps.{Bitmap, BitmapValidator, Bitmaps}
import aalto.smcl.colors.{ColorValidator, RGBAColor, RGBAComponentTranslationTable, RGBATranslationTableValidator, RichRGBAColor}
import aalto.smcl.infrastructure.{BitmapValidatorFunctionFactory, CommonValidators, DefaultJvmCalendarProvider, DefaultJvmUniqueIdProvider, DefaultPlatformResourceFactory, GS, InjectableRegistry, RicherString, SMCLInitializer, Setting, SettingValidatorFactory, SharedSettingInitializer, StringUtils}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Initializer extends SMCLInitializer {

  /** */
  val _stringUtils = new StringUtils()

  /** */
  val _settingValidatorFactory = new SettingValidatorFactory()

  /** */
  val _commonValidators = new CommonValidators()

  /** */
  val _colorValidator = new ColorValidator()

  /** */
  val _rgbaTranslationTableValidator =
    new RGBATranslationTableValidator(_colorValidator)

  /** */
  val _bitmapValidator = new BitmapValidator()

  /** */
  val _bitmapValidatorFunctionFactory =
    new BitmapValidatorFunctionFactory(
      _settingValidatorFactory,
      _bitmapValidator)

  /**
   * Initialize SMCL.
   */
  def apply(): Unit = {
    println("JVM-based SMCL Core initialization is in progress...")

    injectMiscellaneousDependencies()

    initPlatformResourceFactory()

    initSharedSettings()
    initAwtSettings()

    initSwingLookAndFeel()
  }

  /**
   * Inject dependencies to miscellaneous objects.
   */
  private def injectMiscellaneousDependencies(): Unit = {
    RicherString.inject(
      InjectableRegistry.IIdStringUtils -> _stringUtils
    )

    RGBAColor.inject(
      InjectableRegistry.IIdColorValidator -> _colorValidator
    )

    RichRGBAColor.inject(
      InjectableRegistry.IIdColorValidator -> _colorValidator,
      InjectableRegistry.IIdCommonValidators -> _commonValidators
    )

    RGBAComponentTranslationTable.inject(
      InjectableRegistry.IIdCommonValidators -> _commonValidators,
      InjectableRegistry.IIdColorValidator -> _colorValidator,
      InjectableRegistry.IIdRGBATranslationTableValidator -> _rgbaTranslationTableValidator
    )

    AwtBitmapBufferAdapter.inject(
      InjectableRegistry.IIdColorValidator -> _colorValidator,
      InjectableRegistry.IIdBitmapValidator -> _bitmapValidator
    )

    Bitmap.inject(
      InjectableRegistry.IIdColorValidator -> _colorValidator,
      InjectableRegistry.IIdBitmapValidator -> _bitmapValidator
    )

    Bitmaps.inject(
      InjectableRegistry.IIdColorValidator -> _colorValidator,
      InjectableRegistry.IIdBitmapValidator -> _bitmapValidator
    )
  }

  /**
   * Initialize platform resource factory.
   */
  private def initPlatformResourceFactory(): Unit = {
    val calendarProvider = new DefaultJvmCalendarProvider()
    val uuidProvider = new DefaultJvmUniqueIdProvider()
    val fontProvider = new DefaultAwtFontProvider()
    val imageProvider = new DefaultAwtImageProvider(_bitmapValidator)
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
    GS += new Setting[AwtAffineTransformationInterpolationMethod.Value](
      key = AffineTransformationInterpolationMethod,
      initialValue = AwtAffineTransformationInterpolationMethod.NearestNeighbor,
      validator = _settingValidatorFactory.EmptyValidator)
  }

  /**
   *
   */
  private def initSharedSettings(): Unit = {
    val settingInitializer = new SharedSettingInitializer(
      _settingValidatorFactory,
      _bitmapValidatorFunctionFactory)

    settingInitializer.init()
  }

}
