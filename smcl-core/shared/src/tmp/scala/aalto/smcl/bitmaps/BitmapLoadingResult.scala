package aalto.smcl.bitmaps


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class BitmapLoadingResult(
  lowLevelResult: collection.Seq[Either[(Int, Throwable), (Int, Bitmap)]])
  extends SMCLInitializationInvoker {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null


  /**
   *
   *
   * @param index
   * @param bitmap
   */
  case class LoadedBitmap(index: Int, bitmap: Bitmap) extends SMCLInitializationInvoker {}


  /**
   *
   *
   * @param index
   * @param cause
   */
  case class FailedLoad(index: Int, cause: Throwable) extends SMCLInitializationInvoker {}


  private[this] val (throwablesTemp, bitmapsTemp) = lowLevelResult partition (_.isLeft)

  /**
   *
   */
  val throwablesWithIndices: Seq[(Int, Throwable)] = throwablesTemp map (_.left.get)

  /**
   *
   */
  val throwables: Seq[Throwable] = throwablesWithIndices map (_._2)

  /**
   *
   */
  val bitmapsWithIndices: Seq[(Int, Bitmap)] = bitmapsTemp map (_.right.get)

  /**
   *
   */
  val bitmaps: Seq[Bitmap] = bitmapsWithIndices map (_._2)

  /**
   *
   */
  val hasFailures: Boolean = throwables.nonEmpty

  /**
   *
   */
  val hasSuccesses: Boolean = bitmaps.nonEmpty

  /**
   *
   */
  val failedOriginalIndices: Seq[Int] = throwablesWithIndices map (_._1)

  /**
   *
   */
  val succeededOriginalIndices: Seq[Int] = bitmapsWithIndices map (_._1)

  /**
   *
   *
   * @return
   */
  override def toString: String =
    s"Loaded bitmaps: ${bitmaps.length}; failed loadings (see .throwables): ${throwables.length}"

}
