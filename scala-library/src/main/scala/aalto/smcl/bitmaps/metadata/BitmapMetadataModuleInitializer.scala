package aalto.smcl.bitmaps.metadata


import aalto.smcl.bitmaps.ImmutableBitmap
import aalto.smcl.colors.{PresetRGBAColor, RGBAColor}
import aalto.smcl.infrastructure.settings.Setting
import aalto.smcl.infrastructure.settings.SettingValidatorFactory._
import aalto.smcl.interfaces._
import aalto.smcl.{GS, ModuleInitializer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object BitmapMetadataModuleInitializer extends ModuleInitializer {

  /** */
  private[this] val _bitmapProvider = new BitmapMetadataInterfaceSourceProvider()

  /** */
  private[this] val _rgbaColorProvider = new RGBAColorMetadataInterfaceSourceProvider()

  /** */
  private[this] val _providerMap = Map[Class[_], MetadataInterfaceSourceProvider](
    ImmutableBitmap().getClass -> _bitmapProvider,
    RGBAColor(0).getClass -> _rgbaColorProvider,
    PresetRGBAColor(0, Option("<dummy>")).getClass -> _rgbaColorProvider
  )


  //
  // Initialize settings
  //
  addInitializer {() =>
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
  addInitializer {() =>
    _providerMap.foreach {case (clazz, provider) =>
      GlobalMetadataInterfaceSourceProviderRegistry.registerProvider(clazz, provider)
    }
  }

}
