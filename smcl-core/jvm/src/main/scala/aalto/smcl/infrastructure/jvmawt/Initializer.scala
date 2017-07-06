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


import aalto.smcl.bitmaps.fullfeatured.{AbstractBitmaps, ShapeCreator}
import aalto.smcl.bitmaps.{BitmapValidator, BitmapValidatorFunctionFactory, fullfeatured, simplified}
import aalto.smcl.colors.ColorValidator
import aalto.smcl.colors.rgb.{Color, ColorComponentTranslationTable, ColorTranslationTableValidator, RichColor}
import aalto.smcl.infrastructure.{CollectionCreator, CommonValidators, DefaultJVMCalendarProvider, DefaultJVMUniqueIDProvider, DefaultPlatformResourceFactory, InjectablesRegistry, RicherString, SMCLInitializer, SettingInitializer, StringUtils}
import aalto.smcl.modeling
import aalto.smcl.modeling.{d1, d2, d3}
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

  /** */
  private val collectionCreator = new CollectionCreator()

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
      InjectablesRegistry.IIdColorTranslationTableValidator -> rgbaTranslationTableValidator,
      InjectablesRegistry.IIdBitmapValidator -> bitmapValidator,
      InjectablesRegistry.IIdCollectionCreator -> collectionCreator
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
      AWTBitmapBufferAdapter,
      d1.PointAnchor,
      d1.RatioAnchor,
      d2.PointAnchor,
      d2.RatioAnchor,
      d3.PointAnchor,
      d3.RatioAnchor,
      fullfeatured.Bitmap,
      ShapeCreator,
      simplified.Bitmap,
      simplified.ShapeCreator,
      AbstractBitmaps
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
    val calendarProvider = new DefaultJVMCalendarProvider()
    val uuidProvider = new DefaultJVMUniqueIDProvider()
    val fontProvider = new DefaultAWTFontProvider()
    val imageProvider = new DefaultAWTImageProvider(bitmapValidator)
    val screenInfoProvider = new DefaultAWTScreenInformationProvider()

    val factory = new DefaultJVMAWTPlatformResourceFactory(
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

/*
  //
  // Register metadata source providers
  //
  addInitializer(PackageInitializationPhase.Late) {() =>
    val bitmapProvider = new ImmutableBitmapMetadataInterfaceSourceProvider()
    val rgbaColorProvider = new RGBAColorMetadataInterfaceSourceProvider()

    val _providerMap = Map[Class[_], MetadataInterfaceSourceProvider](
      Bitmap().getClass -> bitmapProvider,

      ImmutableBitmap().getClass -> bitmapProvider,

      RGBAColor(0).getClass -> rgbaColorProvider,

      PresetRGBAColor(0, Option("<dummy>")).getClass -> rgbaColorProvider,

      classOf[GenTraversableLike[_, _]] -> new GenTraversableLikeMetadataInterfaceSourceProvider()
    )

    _providerMap foreach {case (clazz, provider) =>
      GlobalMetadataInterfaceSourceProviderRegistry.registerProvider(clazz, provider)
    }
  }
*/

}
