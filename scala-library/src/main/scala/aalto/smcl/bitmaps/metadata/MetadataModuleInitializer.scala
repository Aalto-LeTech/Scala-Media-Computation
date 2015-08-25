package aalto.smcl.bitmaps.metadata


import aalto.smcl.ModuleInitializer
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.interfaces.GlobalMetadataInterfaceSourceProviderRegistry



/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object MetadataModuleInitializer extends ModuleInitializer {

  //
  // Register metadata sources
  //
  addInitializer {() =>
    GlobalMetadataInterfaceSourceProviderRegistry.registerProvider(
      Bitmap().getClass, new BitmapMetadataInterfaceSourceProvider())
  }

}
