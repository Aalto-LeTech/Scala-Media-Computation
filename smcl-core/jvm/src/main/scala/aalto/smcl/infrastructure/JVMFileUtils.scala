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


import java.io.File




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class JVMFileUtils {

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def representsReadableFile(f: File): Boolean =
    f.isFile && f.canRead

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def doesNotRepresentReadableFile(f: File): Boolean =
    !representsReadableFile(f)

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def representsReadableDirectory(f: File): Boolean =
    f.isDirectory && f.canRead

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def doesNotRepresentReadableDirectory(f: File): Boolean =
    !representsReadableDirectory(f)

}
