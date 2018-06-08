# Release Notes for Scala Media Computation Library 0.0.7


In addition to miscellaneous small refactorings, additions, and bug fixes that have been introduced, the most essential changes are listed below.


## User-Visible Functionality

* Modifies the `toString` method of `PresetColor` to return the canonical name of the color in question without spaces and with all words capitalized.
* Moves functionality from `RichColor` to `Color`, and renames `RicherColor` to `RichColor`.
* Implements loading bitmaps from both classpath-based resources and the Internet. 
* Implements rotation and scaling for bitmaps and shapes.
* Adds scaling methods to the `Transformer`.
* Adjusts rendering of arcs and polygons.
* Adds subtraction methods to the 'Pos' class.
* Modifies the bitmap viewer to use the new `Bitmap` class.
* For JVM/AWT platform, sets the default interpolation method to bilinear to increase the quality of down-scaled bitmaps.
* For JVM/AWT platform, adds a platform-dependent setting for anti-aliasing.


## Build Process

* Adds a folder for common resources, and configures SBT to use it for the root project as well as for the core projects.
* Sets Scala version to 2.12.3 to be equal with the Scala version currently included into Scala IDE.
