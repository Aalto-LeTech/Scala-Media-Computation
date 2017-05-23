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
import aalto.smcl.infrastructure.{BitmapValidatorFunctionFactory, CommonValidators, DefaultJvmCalendarProvider, DefaultJvmUniqueIdProvider, DefaultPlatformResourceFactory, GS, InjectablesRegistry, RicherString, SMCLInitializer, Setting, SettingValidatorFactory, SharedSettingInitializer, StringUtils}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Initializer extends SMCLInitializer {

  /** */
  private val stringUtils = new StringUtils()

  /** */
  private val settingValidatorFactory = new SettingValidatorFactory()

  /** */
  private val commonValidators = new CommonValidators()

  /** */
  private val colorValidator = new ColorValidator()

  /** */
  private val rgbaTranslationTableValidator =
    new RGBATranslationTableValidator(colorValidator)

  /** */
  private val bitmapValidator = new BitmapValidator()

  /** */
  private val bitmapValidatorFunctionFactory =
    new BitmapValidatorFunctionFactory(
      settingValidatorFactory,
      bitmapValidator)

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
    val registry = Map[String, Any](
      InjectablesRegistry.IIdStringUtils -> stringUtils,
      InjectablesRegistry.IIdCommonValidators -> commonValidators,
      InjectablesRegistry.IIdColorValidator -> colorValidator,
      InjectablesRegistry.IIdRGBATranslationTableValidator -> rgbaTranslationTableValidator,
      InjectablesRegistry.IIdBitmapValidator -> bitmapValidator
    )

    val injectionTargets: Seq[InjectablesRegistry] = Seq(
      RicherString,
      RGBAColor,
      RichRGBAColor,
      RGBAComponentTranslationTable,
      AwtBitmapBufferAdapter,
      Bitmap,
      Bitmaps
    )

    injectionTargets foreach {
      _.setInjectables(registry)
    }
  }

  /**
   * Initialize platform resource factory.
   */
  private def initPlatformResourceFactory(): Unit = {
    val calendarProvider = new DefaultJvmCalendarProvider()
    val uuidProvider = new DefaultJvmUniqueIdProvider()
    val fontProvider = new DefaultAwtFontProvider()
    val imageProvider = new DefaultAwtImageProvider(bitmapValidator)
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
      validator = settingValidatorFactory.EmptyValidator)
  }

  /**
   *
   */
  private def initSharedSettings(): Unit = {
    val settingInitializer = new SharedSettingInitializer(
      settingValidatorFactory,
      bitmapValidatorFunctionFactory)

    settingInitializer.init()
  }

}
