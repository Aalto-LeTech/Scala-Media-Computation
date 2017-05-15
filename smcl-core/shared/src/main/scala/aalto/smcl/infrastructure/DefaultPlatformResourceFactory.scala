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

package aalto.smcl.infrastructure


import scala.util.{Either, Try}

import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure.exceptions.ImplementationNotSetError
import aalto.smcl.interfaces.Timestamp




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object DefaultPlatformResourceFactory extends PlatformResourceFactory {

  /** The concrete factory class. */
  private var _implementation: Option[PlatformResourceFactory] = None

  /**
   *
   *
   * @return
   */
  override def availableFonts: Seq[String] = implementation.availableFonts

  /**
   *
   *
   * @return
   */
  override def createCurrentTimestamp: Timestamp =
    implementation.createCurrentTimestamp

  /**
   *
   *
   * @return
   */
  override def createPlatformAffineTransformation: AffineTransformationAdapter =
    implementation.createPlatformAffineTransformation

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  override def createPlatformBitmapBuffer(widthInPixels: Int, heightInPixels: Int): BitmapBufferAdapter =
    implementation.createPlatformBitmapBuffer(widthInPixels, heightInPixels)

  /**
   *
   *
   * @return
   */
  override def createPlatformColor(source: RGBAColor): ColorAdapter =
    implementation.createPlatformColor(source)


  /**
   *
   *
   * @return
   */
  override def createUniqueIdString: String = implementation.createUniqueIdString

  /**
   *
   *
   * @return
   */
  override def screenInformationProvider: ScreenInformationProvider =
    implementation.screenInformationProvider

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override def tryToLoadImagesFromPath(sourceResourcePath: String): Try[Seq[Either[Throwable, BitmapBufferAdapter]]] =
    implementation.tryToLoadImagesFromPath(sourceResourcePath)

  /**
   *
   *
   * @param factoryImplementation
   */
  def setImplementation(factoryImplementation: PlatformResourceFactory): Unit =
    _implementation = Some(factoryImplementation)

  /**
   *
   *
   * @return
   */
  private def implementation: PlatformResourceFactory =
    _implementation.getOrElse(throw ImplementationNotSetError("DefaultPlatformResourceFactory"))

}
