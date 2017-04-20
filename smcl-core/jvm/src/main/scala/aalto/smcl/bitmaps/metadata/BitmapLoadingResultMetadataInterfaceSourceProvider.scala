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


import aalto.smcl.bitmaps.BitmapLoadingResult
import aalto.smcl.interfaces.MetadataInterfaceSourceProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[metadata]
class BitmapLoadingResultMetadataInterfaceSourceProvider()
    extends MetadataInterfaceSourceProvider {


  /** */
  // TODO: Get class objects some other way (classOf[] ?)
  private[this] val _bitmapLoadingResultClass = new BitmapLoadingResult(Seq(Right((0, null)))).getClass


  /**
   *
   *
   * @param interestingObject
   *
   * @return
   */
  override def querySourceFor(interestingObject: Any): Option[Any] = {
    val c = interestingObject.getClass

    if (_bitmapLoadingResultClass.isAssignableFrom(c)) {
      return Some(BitmapLoadingResultMetadataSource(interestingObject.asInstanceOf[BitmapLoadingResult]))
    }

    None
  }

}
