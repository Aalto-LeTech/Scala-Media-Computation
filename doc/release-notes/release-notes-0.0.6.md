# Release Notes for Scala Media Computation Library 0.0.6


In addition to miscellaneous small refactorings, additions, and bug fixes that have been introduced, the most essential changes are listed below.


## Color-Related Functionality

* Adds `toString` method to `PresetColor`.
* Changes the equality of `Color` instances as follows:
    * By default (by using `.equals()` and `==`) equality is determined by the four color values (RGBA) only. Also, hash codes are calculated on the basis of those four values only. 
    * Method `equalsIncludingName()` considers also canonical names of the colors.
    * In addition to the above, method `equalsIncludingNameAndPresetness()` also differentiates colors based on whether or not both of the colors are presets.
* Adds an `is()` method that directly calls the `equals()` method.
* Implements `darker()` and `lighter()` methods for colors as simplified versions for shading and tinting. These methods use constant factors instead of accepting them as their parameters.
* Fixes a color tinting calculation bug.  
* Implements some methods for mixing colors based on given proportion of other given color.


## Bitmap-Related Functionality

* Implements `crop()` method for the new `Bitmap` class and removes the related code from the old architecture. 
* Refactors bitmap iteration and offers several kinds of methods for performing it, including the following ones in the `Bitmap` class:
    * `mergePixelsWith()` for merging two bitmaps using a function that merges colors of two pixels.
    * `setColorsByLocation()` accepts a function that determines color for a pixel by its coordinates.
    * `transformColorToColor()` accepts a function that determines a new colors for a pixel based on its current color.
    * `transformLocationColorToColor()` accepts a function that determines a new colors for a pixel based on its current color and location.
    * `iteratePixels()` accepts a function that receives a mutable `Pixel` instance and modifies it to set a new color for the corresponding pixel.
    * `colorAt()` returns the color of a pixel in the specified coordinates in the bitmap directly from the related Java/AWT `BufferedImage` without using a `PixelSnapshot`. 
* Moves some bitmap filters, including palette-based color channel, grayscale, and posterization filters, from the old architecture to be usable with the new `Bitmap` class.
* Refactors matrix iterating functionality out of pixel snapshot iterators into generalized enumerator and iterator classes that are located in their own packages under the `infrastructure` package.
* Moves pixel snapshot iterator classes into `smcl.pictures.iterators` package.


## Other User-Visible Functionality

* Adds methods to convert `Pos` instances into
    * new `Pos` instances using given conversion functions
    * tuples of (floored) `Ints` and `Doubles`.  
* Adds methods to wrap `Pos` instances into tuples with other values.
* Renames position type constants to `PosTypeUpperLeftCorner` and `PosTypeCenter`. 
* Renames viewer update style constants to `VUSPreventViewerUpdates` and `VUSUpdateViewerPerDefaults`. These are are not currently being used in the new architecture's API.
* Fixes a percentage validation bug in `CommonValidators`.


## Indpendent JVM/AWT-Specific Low-Level Functionality

* Changes more of the Bitmap interface to use `Doubles` instead of `Ints`. 


## Build Process

* Changes SBT version to 1.1.1.
* Sets versions of sbt-env-vars and sbt-library-info to 0.1.2 and 0.2.2, respectively.
* Starts to prepare for transition to Scala.js 1.0.x.
* Removes `withSources()` and `withJavadoc()` calls from build.sbt, as they are not recommended to be used (and IntelliJ IDEA may not like them).
* Removes unnecessary constants from `build.sbt`.
* Updates `.gitattributes`.
* Adds a script as well as related shortcut icons and .desktop file templates for launching standard SMCL development terminals for SBT and Git in Bash/Gnome environment.
