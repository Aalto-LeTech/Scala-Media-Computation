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

package aalto.smcl.bitmaps.metadata


import scala.language.implicitConversions




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class PackageInitializer {

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
