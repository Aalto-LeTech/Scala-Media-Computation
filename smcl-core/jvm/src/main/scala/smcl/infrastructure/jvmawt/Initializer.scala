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

package smcl.infrastructure.jvmawt


import smcl.colors.ColorValidator
import smcl.colors.rgb.{Color, ColorComponentTranslationTable, ColorTranslationTableValidator, PresetColor}
import smcl.infrastructure._
import smcl.infrastructure.jvmawt.imageio.ImageInputStreamProvider
import smcl.modeling.d1
import smcl.pictures._
import smcl.settings.jvmawt.JVMAWTSettingInitializer
import smcl.settings.{SettingValidatorFactory, _}
import smcl.{SMCL, modeling}




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
    if (SMCL.build.isSnapshot) {
      println("JVM/AWT-based SMCL Core initialization is in progress...")
    }

    injectMiscellaneousDependencies()

    initSettings()
    initPlatformResourceFactory()
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
      RichOptionString,
      Color,
      PresetColor,
      ColorComponentTranslationTable,
      BooleanSetting,
      ColorSetting,
      ObjectSetting,
      IntSetting,
      DoubleSetting,
      StringSetting,
      AWTBitmapBufferAdapter,
      d1.PointAnchor,
      d1.RatioAnchor,
      modeling.d2.PointAnchor,
      modeling.d2.RatioAnchor,
      modeling.d3.PointAnchor,
      modeling.d3.RatioAnchor,
      RenderingController,
      Bitmap,
      filters.Posterize,
      filters.ToWeightedGrayscale,
      fullfeatured.Bitmap,
      fullfeatured.ShapeCreator
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
    val screenInfoProvider = new DefaultAWTScreenInformationProvider()

    val imageProvider =
      new DefaultAWTImageProvider(
        new URLProvider(),
        new HTTPConnectionProvider(),
        new ImageInputStreamProvider(),
        bitmapValidator)

    val factory = new DefaultJVMAWTPlatformResourceFactory(
      calendarProvider, uuidProvider, fontProvider, imageProvider, screenInfoProvider)

    DefaultPlatformResourceFactory.setImplementation(factory)
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
