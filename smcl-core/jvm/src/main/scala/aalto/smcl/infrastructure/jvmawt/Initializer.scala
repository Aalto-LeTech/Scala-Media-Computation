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


import aalto.smcl.bitmaps.{Bitmap, BitmapValidator, BitmapValidatorFunctionFactory, Bitmaps}
import aalto.smcl.colors.ColorValidator
import aalto.smcl.colors.rgb.{Color, ColorComponentTranslationTable, ColorTranslationTableValidator, RichColor}
import aalto.smcl.infrastructure.{CommonValidators, DefaultJvmCalendarProvider, DefaultJvmUniqueIdProvider, DefaultPlatformResourceFactory, InjectablesRegistry, RicherString, SMCLInitializer, SettingInitializer, StringUtils}
import aalto.smcl.settings._
import aalto.smcl.settings.jvmawt.JVMAWTSettingInitializer




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
    new ColorTranslationTableValidator(colorValidator)

  /** */
  private val bitmapValidator = new BitmapValidator()

  /** */
  private val settingRegisterer = new SettingRegisterer()

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

    initSettings()
    initPlatformResourceFactory()
    initSwingLookAndFeel()
  }

  /**
   * Inject dependencies to miscellaneous objects.
   */
  private def injectMiscellaneousDependencies(): Unit = {
    val registry = Map[String, Any](
      InjectablesRegistry.IIdSettingRegisterer -> settingRegisterer,
      InjectablesRegistry.IIdStringUtils -> stringUtils,
      InjectablesRegistry.IIdCommonValidators -> commonValidators,
      InjectablesRegistry.IIdColorValidator -> colorValidator,
      InjectablesRegistry.IIdRGBATranslationTableValidator -> rgbaTranslationTableValidator,
      InjectablesRegistry.IIdBitmapValidator -> bitmapValidator
    )

    val injectionTargets: Seq[InjectablesRegistry] = Seq(
      RicherString,
      Color,
      RichColor,
      ColorComponentTranslationTable,
      BooleanSetting,
      ColorSetting,
      ObjectSetting,
      IntSetting,
      StringSetting,
      AwtBitmapBufferAdapter,
      Bitmap,
      Bitmaps
    )

    injectionTargets foreach {
      _.setInjectables(registry)
    }
  }

  /**
   * Initialize settings.
   */
  private def initSettings(): Unit = {
    val initializers = Seq[SettingInitializer](
      new SharedSettingInitializer(),
      new JVMAWTSettingInitializer())

    for (initializer <- initializers)
      initializer(settingValidatorFactory, bitmapValidatorFunctionFactory)
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

}
