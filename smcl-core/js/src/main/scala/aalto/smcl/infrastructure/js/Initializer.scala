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

package aalto.smcl.infrastructure.js


import aalto.smcl.bitmaps.{BitmapValidator, BitmapValidatorFunctionFactory}
import aalto.smcl.infrastructure.SMCLInitializer
import aalto.smcl.settings.{SettingValidatorFactory, SharedSettingInitializer}




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
    val bitmapValidatorFunctionFactory = new BitmapValidatorFunctionFactory(
      settingValidatorFactory, new BitmapValidator())

    val settingInitializer = new SharedSettingInitializer()

    settingInitializer(settingValidatorFactory, bitmapValidatorFunctionFactory)
  }

}
