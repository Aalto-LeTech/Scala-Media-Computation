package aalto.smcl.metadata


import scala.language.implicitConversions

import aalto.smcl.GS
import aalto.smcl.bitmaps._
import aalto.smcl.colors.{PresetRGBAColor, RGBAColor}
import aalto.smcl.infrastructure.SettingValidatorFactory._
import aalto.smcl.infrastructure.{ModuleInitializationPhase, ModuleInitializer, Setting}
import aalto.smcl.interfaces.{GlobalMetadataInterfaceSourceProviderRegistry, MetadataInterfaceSourceProvider}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object MetadataModuleInitializer extends ModuleInitializer {

  //
  // Initialize settings
  //
  addInitializer(ModuleInitializationPhase.Early) {() =>
    GS += new Setting[Int](
      key = ColorVisualizationTileSideLengthInPixels,
      initialValue = 80,
      validator = ConditionFalseValidator[Int]({
        _ < 20
      }, "Side length of color visualization tiles' must be at least 20 pixels"))
  }


  //
  // Register metadata source providers
  //
  addInitializer(ModuleInitializationPhase.Late) {() =>
    val bitmapProvider = new ImmutableBitmapMetadataInterfaceSourceProvider()
    val rgbaColorProvider = new RGBAColorMetadataInterfaceSourceProvider()

    val _providerMap = Map[Class[_], MetadataInterfaceSourceProvider](
      Bitmap().getClass -> bitmapProvider,
      ImmutableBitmap().getClass -> bitmapProvider,
      RGBAColor(0).getClass -> rgbaColorProvider,
      PresetRGBAColor(0, Option("<dummy>")).getClass -> rgbaColorProvider)

    _providerMap.foreach {case (clazz, provider) =>
      GlobalMetadataInterfaceSourceProviderRegistry.registerProvider(clazz, provider)
    }
  }

}

