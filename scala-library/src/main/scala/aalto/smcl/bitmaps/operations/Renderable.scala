package aalto.smcl.bitmaps.operations


import aalto.smcl.infrastructure.PlatformBitmapBuffer




/**
 * Ensures that an [[AbstractOperation]] can be rendered to [[PlatformBitmapBuffer]] instances.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
trait Renderable {
  this: AbstractOperation =>

  /**
   * Renders this operation to the destination buffer.
   *
   * @param destination
   */
  def render(destination: PlatformBitmapBuffer): Unit

}
