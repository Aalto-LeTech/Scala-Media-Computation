package aalto.smcl.images.viewer


/**
  * Represents magnitudes of scrolling for scrollbars.
  *
  * @author Aleksi Lukkarinen
  */
object ScrollingMagnitude {

   /** Type of all magnitudes. */
   trait Value

   /** Unit magnitude. */
   object Unit extends Value

   /** Block magnitude. */
   object Block extends Value

 }
