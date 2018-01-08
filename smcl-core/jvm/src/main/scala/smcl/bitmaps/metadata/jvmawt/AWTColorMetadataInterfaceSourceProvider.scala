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

package smcl.bitmaps.metadata.jvmawt


import smcl.colors.Black
import smcl.colors.rgb.Color
import smcl.interfaces.MetadataInterfaceSourceProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class AWTColorMetadataInterfaceSourceProvider()
    extends MetadataInterfaceSourceProvider {

  /** */
  // TODO: Get class objects some other way (classOf[] ?)
  private[this] val _rgbaColorClass = Color(0).getClass

  /** */
  // TODO: Get class objects some other way (classOf[] ?)
  private[this] val _presetRGBAColorClass = Black.getClass


  /**
   *
   *
   * @param interestingObject
   *
   * @return
   */
  override def querySourceFor(interestingObject: Any): Option[Any] = {
    val c = interestingObject.getClass

    if (_rgbaColorClass.isAssignableFrom(c)) {
      return Some(new AWTColorMetadataSource(interestingObject.asInstanceOf[Color]))
    }

    None
  }

}
