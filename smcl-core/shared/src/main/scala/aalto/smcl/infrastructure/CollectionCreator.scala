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


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class CollectionCreator {

  /**
   *
   *
   * @param collectionSize
   * @param contentProvider
   * @tparam ItemType
   *
   * @return
   */
  def array[ItemType](
      collectionSize: Int = 5)(
      contentProvider: => ItemType): Array[ItemType] = {

    validateCollectionSize(collectionSize)

    Array.fill[Any](
      collectionSize)(
      contentProvider).asInstanceOf[Array[ItemType]]
  }

  /**
   *
   *
   * @param collectionSize
   * @param contentProvider
   * @tparam ItemType
   *
   * @return
   */
  def sequence[ItemType](
      collectionSize: Int = 5)(
      contentProvider: => ItemType): Seq[ItemType] = {

    validateCollectionSize(collectionSize)

    Seq.fill[ItemType](collectionSize){
      contentProvider
    }
  }

  /**
   *
   *
   * @param collectionSize
   * @param contentProvider
   * @tparam ItemType
   *
   * @return
   */
  def list[ItemType](
      collectionSize: Int = 5)(
      contentProvider: => ItemType): List[ItemType] = {

    validateCollectionSize(collectionSize)

    List.fill[ItemType](collectionSize){
      contentProvider
    }
  }

  /**
   *
   *
   * @param size
   */
  def validateCollectionSize(size: Int): Unit = {
    require(
      size >= 0,
      s"Size of the collection cannot be negative (was $size)")
  }

}
