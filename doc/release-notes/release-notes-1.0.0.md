# Release Notes for Scala Media Computation Library 1.0.0


In addition to miscellaneous small refactorings, additions, and bug fixes that have been introduced, the most essential changes are listed below.


* Improves scaling of `Picture` instances, i.e. several picture elements at once.
* Sets bitmaps to be scaled using Area Averaging algorithm.
* Adds `Bitmap.apply(width, height, Seq(colors))` method.
* Reimplements `Bitmap.saveAsPngTo()` in a more robust way.
* Corrects typos in some color names (ChartReuse to `Chartreuse`; GoldenRod to `Goldenrod`).
