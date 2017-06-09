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

package aalto.smcl.interfaces


import aalto.smcl.infrastructure.exceptions.ResourceWithGivenIndexNotFoundError
import aalto.smcl.infrastructure.{Identity, Timestamp}




/**
 * Interface for querying objects for common metadata.
 *
 * @author Aleksi Lukkarinen
 */
trait MetadataOnResources[ResourceType]
    extends MetadataOnMetadataProvider
            with Immutable {

  /** A vector of known resources, about which the metadata is to be provisioned. */
  def knownResources: Vector[ResourceType]

  /** Number of known resources. */
  lazy val numberOfKnownResources: Int =
    knownResources.length

  /** Indices of known resources. */
  lazy val knownResourceIndices: Range =
    knownResources.indices

  /** First possible index of known resource. */
  lazy val firstValidKnownResourceIndex: ResourceIndex =
    knownResourceIndices.head

  /** Last possible index of known resource. */
  lazy val lastValidKnownResourceIndex: ResourceIndex =
    knownResourceIndices.last

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def isValidKnownResourceIndex(r: ResourceIndex): Boolean = {
    knownResourceIndices.contains(r)
  }

  /**
   *
   *
   * @param r
   * @param f
   * @tparam A
   *
   * @return
   */
  def doIfValidKnownResourceIndex[A](r: ResourceIndex)(f: ResourceType => A): A = {
    if (isValidKnownResourceIndex(r))
      return f(knownResources(r))

    throw ResourceWithGivenIndexNotFoundError(r,
      firstValidKnownResourceIndex,
      lastValidKnownResourceIndex)
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def resourceIdentity(
      r: ResourceIndex = FirstResourceIndex): Option[Identity] = {

    doIfValidKnownResourceIndex(r){_ => None}
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def resourceTimestamp(
      r: ResourceIndex = FirstResourceIndex): Option[Timestamp] = {

    doIfValidKnownResourceIndex(r){_ => None}
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def resourceTitle(
      r: ResourceIndex = FirstResourceIndex): Option[String] = {

    doIfValidKnownResourceIndex(r){_ => None}
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def resourceDescription(
      r: ResourceIndex = FirstResourceIndex): Option[String] = {

    doIfValidKnownResourceIndex(r){_ => None}
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def resourceAuthors(
      r: ResourceIndex = FirstResourceIndex): Seq[String] = {

    doIfValidKnownResourceIndex(r){_ => Seq.empty[String]}
  }

  /**
   *
   *
   * @param r
   *
   * @return
   */
  def resourceKeywords(
      r: ResourceIndex = FirstResourceIndex): Seq[String] = {

    doIfValidKnownResourceIndex(r){_ => Seq.empty[String]}
  }

}
