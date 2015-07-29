package aalto.smcl.images.immutable

import aalto.smcl.common.{Color, GS}
import aalto.smcl.images.SettingKeys.{DefaultBitmapWidthInPixels, DefaultPrimary}




/**
  *
  *
  * @author Aleksi Lukkarinen
  */
object Square {

   aalto.smcl.images.SettingsInitializer.perform()

   /**
    * Creates a new empty [[Bitmap]] instance with a square drawn on it.
    */
   def apply(
       sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
       color: Color = GS.colorFor(DefaultPrimary)): Bitmap = {

     require(sideLengthInPixels >= 10,
       s"Side of the square must be at least 10 pixels (was $sideLengthInPixels)")

     Rectangle(sideLengthInPixels, sideLengthInPixels, color)
   }

 }
