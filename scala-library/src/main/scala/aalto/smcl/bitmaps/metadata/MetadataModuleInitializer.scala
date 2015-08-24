package aalto.smcl.bitmaps.metadata


import aalto.smcl.ModuleInitializer
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.interfaces.GlobalMetadataInterfaceSourceRegistry




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
    GlobalMetadataInterfaceSourceRegistry.registerProvider(Bitmap.getClass, new BitmapMetadataInterfaceSourceProvider())
  }

}
