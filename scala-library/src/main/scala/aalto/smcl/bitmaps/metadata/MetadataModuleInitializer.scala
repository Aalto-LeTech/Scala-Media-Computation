package aalto.smcl.bitmaps.metadata


import aalto.smcl.ModuleInitializer
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.common.{PresetRGBAColor, RGBAColor}
import aalto.smcl.interfaces._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object MetadataModuleInitializer extends ModuleInitializer {

  /**  */
  private[this] val _mdispBitmap = new BitmapMetadataInterfaceSourceProvider()

  /**  */
  private[this] val _mdispRGBAColor = new RGBAColorMetadataInterfaceSourceProvider()

  /**  */
  private[this] val _providerMap = Map[Class[_], MetadataInterfaceSourceProvider](
    Bitmap().getClass -> _mdispBitmap,
    RGBAColor(0).getClass -> _mdispRGBAColor,
    PresetRGBAColor(0, Option("<dummy>")).getClass -> _mdispRGBAColor
  )


  //
  // Register metadata source providers
  //
  addInitializer {() =>
    _providerMap.foreach { case (clazz, provider) =>
      GlobalMetadataInterfaceSourceProviderRegistry.registerProvider(clazz, provider)
    }
  }

}
