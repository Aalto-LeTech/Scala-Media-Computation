package aalto.smcl.bitmaps.metadata


import aalto.smcl.colors.{PresetRGBAColor, RGBAColor}
import aalto.smcl.interfaces.MetadataInterfaceSourceProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[metadata]
class RGBAColorMetadataInterfaceSourceProvider() extends MetadataInterfaceSourceProvider {

  /** */
  private[this] val _rgbaColorClass = RGBAColor(0).getClass

  /** */
  private[this] val _presetRGBAColorClass = PresetRGBAColor(0, Option("<dummy>")).getClass


  /**
   *
   *
   * @param interestingObject
   * @return
   */
  override def querySourceFor(interestingObject: Any): Option[Any] = {
    val c = interestingObject.getClass

    if (_rgbaColorClass.isAssignableFrom(c)) {
      return Some(RGBAColorMetadataSource(interestingObject.asInstanceOf[RGBAColor]))
    }

    None
  }

}
