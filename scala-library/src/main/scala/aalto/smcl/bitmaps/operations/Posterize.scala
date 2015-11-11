package aalto.smcl.bitmaps.operations


import aalto.smcl.colors.RGBAComponentTranslationTable
import aalto.smcl.infrastructure._




/**
 * Operation to posterize a bitmap, i.e. to reduce the amount of its colors.
 *
 * @param strengthAsPercentage
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class Posterize(strengthAsPercentage: Int)
  extends AbstractOperation
  with OneSourceFilter
  with Immutable {

  new CommonValidators().validatePercentage(strengthAsPercentage, Option("Strength"))

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "strengthInPercents" -> Option(strengthAsPercentage.toString)
  ))


  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources     possible [[PlatformBitmapBuffer]] instances used as sources
   * @return
   */
  override protected def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    require(sources.length == 1,
      s"Negative creation requires exactly one source image (provided: ${sources.length}).")

    sources(0).createFilteredVersionWith(
      RGBAComponentTranslationTable.forPosterization(strengthAsPercentage))
  }

}
