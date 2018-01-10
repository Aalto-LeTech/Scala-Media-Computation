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

package smcl.fonts


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Fonts private[fonts]()
    extends // Map[String, Font]
        //with
        Immutable {

  /** */
  //private[this] var _fontMap: Map[String, Font] = new HashMap[String, Font]()


  initializeFontMap()


  /**
   *
   */
  def initializeFontMap(): Unit = {
    /*
    new FontProvider().availableFonts() foreach {font =>
      _fontMap = _fontMap + (font.getName -> font)
    }
    */
  }

  /**
   *
   *
   * @param key
   *
   * @return
   */
  //override def get(key: String): Option[Font] = _fontMap.get(key)

  /**
   *
   *
   * @return
   */
  //override def iterator: Iterator[(String, Font)] = _fontMap.iterator

  /**
   *
   *
   * @param key
   *
   * @return
   */
  //override def -(key: String): Map[String, Font] = _fontMap.-(key)

  /**
   *
   *
   * @param kv
   * @tparam B1
   *
   * @return
   */
  //override def +[B1 >: Font](kv: (String, B1)): Map[String, B1] = _fontMap + kv

}
