package aalto.smcl.bitmaps.operations


import aalto.smcl.common._
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to flip a bitmap diagonally.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class FlipDiagonally()
    extends AbstractSingleSourceOperation with Immutable {

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map())

  /**
   * Flips the given bitmap diagonally.
   *
   * @param destination
   */
  override def render(destination: PlatformBitmapBuffer): Unit =
    destination.drawingSurface().flipDiagonAlley()

}
