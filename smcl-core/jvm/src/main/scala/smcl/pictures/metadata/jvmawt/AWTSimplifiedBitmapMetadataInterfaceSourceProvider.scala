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

package smcl.pictures.metadata.jvmawt


import smcl.interfaces.MetadataInterfaceSourceProvider
import smcl.pictures.simplified




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class AWTSimplifiedBitmapMetadataInterfaceSourceProvider()
    extends MetadataInterfaceSourceProvider {

  // TODO: Get class objects some other way (classOf[] ?)

  /** */
  private[this] lazy val _bitmapClass = simplified.Bitmap().getClass


  /**
   *
   *
   * @param interestingObject
   *
   * @return
   */
  override def querySourceFor(interestingObject: Any): Option[Any] = {
    val c = interestingObject.getClass

    if (_bitmapClass.isAssignableFrom(c)) {
      val source = new AWTSimplifiedBitmapMetadataSource(
        interestingObject.asInstanceOf[simplified.Bitmap])

      return Some(source)
    }

    None
  }

}
