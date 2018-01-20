[![GitHub version](https://badge.fury.io/gh/Aalto-LeTech%2FScala-Media-Computation.svg)](https://badge.fury.io/gh/Aalto-LeTech%2FScala-Media-Computation)

# The Scala Media Computation Library (SMCL)

This project is targeted mainly for instructors and students of introductory programming courses. From instructors' viewpoint, its goal is to provide means to motivate students via contextualized teaching. There are many ways to provice context, and the approach choosen here is the one of multimedia computation, that is, programmatically creating and processing multimedia content in a creative way. To provide the necessary scaffolding for novice students already challenged to learn for example logical and algorithmic thinking, programming concepts and paradigms, a new programming language (in this case, [Scala](http://www.scala-lang.org/)), and the related development tools, this library offers ways to concentrate only in the topics to be practiced while still producing hopefully interesting and attractive results.

<br />



## Tools & Dependencies

This library uses [sbt](http://www.scala-sbt.org/) as its build tool. 
...



## Repository Content and Project Structure

The project consists of two parts: SMCL Core and SMCL Bitmap Viewer. Both of these are to be cross-compiled for two platforms: One consisting of the Java Virtual Machine and the Abstract Window Toolkit from Java's class library, while the other one being JavaScript and HTML5 for browser-based usage. At the moment, the JS/HTML5 version has not been developed, and the development efforts have been focused on the JVM/AWT version.

This repository contains only the platform-independent files needed to create a fresh development setup of the project: source code and other resources as well as the some settings for IntelliJ IDEA. The essential structure of this repository is given in the table below:

| Path                                                               | Description |
| ------------------------------------------------------------------ | ----------- |
| .idea          | Platform-independent settings files for IntelliJ IDEA. |
| node_modules   | Modules needed by Node.js to be able to execute JavaScript-based tests during build process. |
| project        | SBT's project folder. |
| smcl-bitmap-viewer<br />&nbsp;&nbsp;/js<br />&nbsp;&nbsp;/jvm<br />&nbsp;&nbsp;/shared | Source files for SMCL's bitmap viewer separated into three folders:<br />(1) those that depend on JS/HTML5 platform,<br />(2) those that depend on JVM/AWT platform, and<br />(3) platform-independent files. |
| smcl-core<br />&nbsp;&nbsp;/js<br />&nbsp;&nbsp;/jvm<br />&nbsp;&nbsp;/shared | Source files for SMCL's core separated into three folders as above for the bitmap viewer. |
| build.sbt      | The project and build description file for SBT. |

...

Build products---including the packaged Java archives (.jar files)---are located under the generated `<project>/js/target` and `<project>/jvm/target` folders.

...



## Setting Up a Development Environment

### Installing Git

### Cloning This Repository onto a Local Mass Storage

One possibility to clone this repository onto your local mass storage is to use [Git](https://git-scm.com/) itself. From command line this can be done like below:
```
git clone https://github.com/Aalto-LeTech/Scala-Media-Computation.git
```
This will create a `Scala-Media-Computation` folder into the folder in which it is executed. Other possibilities to clone the repository include e.g. IDE plugins and other graphical user interfaces developed to make using of Git easier.

### Installing Java Development Kit

### Configuring Maven and Ivy

While Apache Maven is not needed to build this project, local artifact repositories for Maven and Ivy are needed when publishing build products of this project. What's more, SBT uses Apache Ivy for all dependency management, which means that a local Ivy-style artifact repository is definitely needed.

...

### Installing SBT

### Installing Node.js

### Installing IntelliJ IDEA and Related Plugins






## Developing with IntelliJ IDEA and SBT

### Building the Library

...
