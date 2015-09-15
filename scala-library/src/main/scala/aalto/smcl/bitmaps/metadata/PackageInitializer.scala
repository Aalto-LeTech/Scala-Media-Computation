package aalto.smcl.bitmaps.metadata


import scala.collection.GenTraversableLike
import scala.language.implicitConversions

import aalto.smcl.bitmaps._
import aalto.smcl.colors.{PresetRGBAColor, RGBAColor}
import aalto.smcl.infrastructure.SettingValidatorFactory._
import aalto.smcl.infrastructure.{GS, InitializablePackage, PackageInitializationPhase, PackageInitializerBase, Setting}
import aalto.smcl.interfaces.{GlobalMetadataInterfaceSourceProviderRegistry, MetadataInterfaceSourceProvider}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@InitializablePackage(dependsOnPackages = Array(
  "aalto.smcl.infrastructure",
  "aalto.smcl.common",
  "aalto.smcl.colors",
  "aalto.smcl.bitmaps"
))
private[smcl]
class PackageInitializer extends PackageInitializerBase {

  //
  // Initialize settings
  //
  addInitializer(PackageInitializationPhase.Early) {() =>
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
  addInitializer(PackageInitializationPhase.Late) {() =>
    val bitmapProvider = new ImmutableBitmapMetadataInterfaceSourceProvider()
    val rgbaColorProvider = new RGBAColorMetadataInterfaceSourceProvider()

    val _providerMap = Map[Class[_], MetadataInterfaceSourceProvider](
      Bitmap().getClass -> bitmapProvider,

      ImmutableBitmap().getClass -> bitmapProvider,

      RGBAColor(0).getClass -> rgbaColorProvider,

      PresetRGBAColor(0, Option("<dummy>")).getClass -> rgbaColorProvider,

      new BitmapLoadingResult(Seq(Right((0, null)))).getClass ->
        new BitmapLoadingResultMetadataInterfaceSourceProvider(),

      classOf[GenTraversableLike[_, _]] -> new GenTraversableLikeMetadataInterfaceSourceProvider()
    )

    _providerMap foreach {case (clazz, provider) =>
      GlobalMetadataInterfaceSourceProviderRegistry.registerProvider(clazz, provider)
    }
  }

}
