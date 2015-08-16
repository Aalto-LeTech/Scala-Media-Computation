package aalto.smcl.common


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait RGBAColorTranslator {

  /**
   *
   *
   * @param color
   * @return
   */
  def translate(color: RGBAColor): RGBAColor =
    RGBAColor(translate(ColorOps.rgbaTupleFrom(color)))

  /**
   *
   *
   * @param argbInt
   * @return
   */
  def translate(argbInt: Int): Int =
    ColorOps.argbIntFrom(translate(ColorOps.rgbaTupleFrom(argbInt)))

  /**
   *
   *
   * @param rgbaTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def translate(rgbaTuple: (Int, Int, Int, Int)): (Int, Int, Int, Int) =
    (translate(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   * @return
   */
  def translate(red: Int, green: Int, blue: Int, opacity: Int): (Int, Int, Int, Int)

}
