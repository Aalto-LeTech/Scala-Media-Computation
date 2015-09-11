package aalto.smcl.bitmaps


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ConvolutionKernel {

  /**
   *
   *
   * @param width
   * @param height
   * @param data
   * @return
   */
  def apply(width: Int, height: Int, data: Array[Float]): ConvolutionKernel = {
    require(width > 1, s"Width of the kernel must be greater than 1 (was $width).")
    require(height > 1, s"Height of the kernel must be greater than 1 (was $height).")

    require(width % 2 == 1, s"Width of the kernel must be an odd integer (was $width).")
    require(height % 2 == 1, s"Height of the kernel must be an odd integer (was $height).")

    require(data.length >= width * height,
      "The data array must contain at least width * height items.")

    val matrix = data.take(width * height).grouped(width).map(_.toList).toList

    new ConvolutionKernel(matrix)
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class ConvolutionKernel private(matrix: Seq[Seq[Float]]) extends Immutable {

  /** Height of this [[ConvolutionKernel]] instance. */
  val height: Int = matrix.head.length

  /** Width of this [[ConvolutionKernel]] instance. */
  val width: Int = matrix.length

  /** Horizontal index of the center item of the data matrix. */
  val centerX: Int = Math.round(width / 2 + 1)

  /** Vertical index of the center item of the data matrix. */
  val centerY: Int = Math.round(height / 2 + 1)

  /** The data of this [[ConvolutionKernel]] instance as an `Array` of `Floats`. */
  lazy val toRowMajorArray: Array[Float] = matrix.flatten.toArray


  /**
   *
   *
   * @return
   */
  override def toString: String =
    matrix.map(_.mkString("[", ", ", "]")).mkString("[", ", ", "]")

}
