# Release Notes for Scala Media Computation Library 0.0.4


In addition to miscellaneous refactorings, small additions, and bug fixes that have been introduced, the most essential changes are listed below.


## User-Visible Functionality

* Viewports can be used to set pictures' sizes.
* Picture elements can be combined into new pictures in various ways.
* Picture elements can be replicated and alternated with others to create new pictures.
* Polygons and basic arcs are implemented.
* Polygons and arcs are used to provide triangles, rectangles, regular pentagons, regular star pentagons, circles, ellipses, and crosshairs.
* Object's positions will be their center points.
* Shape-related coordinates in the DrawingSurfaceAdapter are changed to be Doubles.
* Bitmaps can be loaded from files.
* Bitmaps can be iterated as pixels as well as colors by pixels.
* Rotation is implemented for shapes (scaling and shearing are missing, as well as rotation for bitmaps).
* An "enumeration" SideIndependentAlignment has been added.
* Double-based settings are added and some Int-based settings are converted into Double-based ones.
* A setting for regular star pentagon's cusp radius is added.
* A bunch of helper methods is added for instance to Dims, Bounds, Pos, Movable, and Rotatable.
* Object equality has been implemented for RGB colors.
* Bmp is renamed to Bitmap, Image to Picture, and ImageElement to PictureElement.
* AspectRatio class is added.


## Build Process

* The code providing build information is created by a custom-made sbt-build-info plugin that copies and filters the files from a separate template project that has been added.
* Adds custom-made sbt-env-vars plugin for reading values of environment variables.
* Adds sbt-slack-notify plugin for later implementation of automatic build notifications.
* Started to add support for sbt-github-release and sbt-release plugins.
* SMCLReleaseNotes and SMCLVersion classes are added.
* Library versions (such as Scala.js) have been updated.
