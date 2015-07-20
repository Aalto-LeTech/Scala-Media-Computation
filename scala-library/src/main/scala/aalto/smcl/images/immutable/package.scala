package aalto.smcl.images


import java.awt.{Color => JColor}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object immutable {

  /**
   *
   */
  implicit class RichColor(val self: Color) {

    /** This [[Color]] with full opaqueness. */
    def toOpaqueColor: Color =
      if (self.isOpaque) self
      else Color(self.red, self.green, self.blue, MAX_OPAQUENESS, self.nameOption)

    /** This [[Color]] as a `java.awt.Color` with full opaqueness. */
    def toOpaqueAwtColor: JColor =
      new JColor(self.red, self.green, self.blue, MAX_OPAQUENESS)

  }

}
